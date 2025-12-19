import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.management.StringValueExp;
import javax.swing.*;

public class SpaceInvaders extends JPanel implements ActionListener, KeyListener {



    class Block{
            int x;
            int y;
            int height;
            int width;
            Image img;
            boolean alive =true; // for aliens
            boolean used = false; //for bullets

            Block(int x, int y, int width, int height, Image img){
                this.x=x;
                this.y=y;
                this.height=height;
                this.width=width;
                this.img=img;
            }
        }
    int tilesize=32;
    int rows = 16;
    int columns =16;
    int boardWidth =tilesize*columns;
    int boardHeight =tilesize*rows;

    Image shipImg;
    Image alienImg;
    Image alienCyanImg;
    Image alienMagentaImg;
    Image alienYellowImg;

    ArrayList <Image> alienImgArray;

    //Ship Metrics
    int shipwidth = tilesize*2;
    int shipheight = tilesize;
    int shipX = (tilesize*(columns/2)) - (tilesize);
    int shipY = boardHeight-(tilesize*2);
    int shipVelocityX = tilesize;

    Block ship;

    //Aliens
    ArrayList<Block> alienArray;
    int alienWidth = tilesize*2;
    int alienHeight = tilesize;
    int alienX = tilesize;
    int alienY = tilesize;

    int alienRows = 2;
    int alienCols = 3;
    int alienCount =0;
    int alienVelocityX = 1;

    ArrayList<Block> bulletArray;
    int bulletWidth = tilesize/8;
    int bulletHeight = tilesize/2;
    int bulletVelocityY = -10;

    Timer gameLoop;
    int score =0;
    boolean gameover =false;


    SpaceInvaders(){
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);

        shipImg = new ImageIcon(getClass().getResource("./ship.png")).getImage();
        alienImg = new ImageIcon(getClass().getResource("./alien.png")).getImage();
        alienCyanImg = new ImageIcon(getClass().getResource("./alien-cyan.png")).getImage();
        alienMagentaImg = new ImageIcon(getClass().getResource("./alien-magenta.png")).getImage();
        alienYellowImg = new ImageIcon(getClass().getResource("./alien-yellow.png")).getImage();

        alienImgArray = new ArrayList<Image>();
        alienImgArray.add(alienImg);
        alienImgArray.add(alienCyanImg);
        alienImgArray.add(alienMagentaImg);
        alienImgArray.add(alienYellowImg);

        ship = new Block(shipX, shipY, shipwidth, shipheight, shipImg);
        alienArray = new ArrayList<Block>();
        bulletArray = new ArrayList<Block>();

        //game Loop
        gameLoop = new Timer(1000 / 60, this);
        createAliens();
        gameLoop.start();
    }
   public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
   }
   public void draw(Graphics g){
        //Ship
        g.drawImage(ship.img, ship.x, ship.y, ship.width, ship.height, null);

        //Alien
       for(int i=0;i<alienArray.size();i++){
           Block alien = alienArray.get(i);
           if(alien.alive){
               g.drawImage(alien.img, alien.x, alien.y, alien.width, alien.height, null);
           }
       }

       g.setColor(Color.white);
       for(int i=0;i<bulletArray.size();i++){
           Block bullet = bulletArray.get(i);
           if(!bullet.used){
               //g.drawRect(bullet.x, bullet.y ,bullet.width, bullet.height);
               g.fillRect(bullet.x, bullet.y ,bullet.width, bullet.height);
           }
       }
         g.setColor(Color.white);
       g.setFont(new Font("Arial",Font.PLAIN,32));
       if(gameover){
           g.drawString("GAME OVER :"+ String.valueOf(score), 10, 35);
       }
       else{
           g.drawString(String.valueOf(score), 10, 35);
       }
   }


//Alien movement
   public void move (){
        for(int i=0;i<alienArray.size();i++){
            Block alien = alienArray.get(i);
            if(alien.alive){
                alien.x +=alienVelocityX;

            if(alien.x + alienWidth >= boardWidth || alien.x<=0) {
                alienVelocityX *= -1; //Course reversal
                alien.x += alienVelocityX * 2; //--

                //Moving alien down the row
                for (int j = 0; j < alienArray.size(); j++) {
                    alienArray.get(j).y += alienHeight;

                     }
                }
            if(alien.y>=ship.y){
                gameover=true;
            }
            }
        }

        //Bullets
       for (int i=0;i<bulletArray.size();i++){
           Block bullet = bulletArray.get(i);
           bullet.y += bulletVelocityY;

           for (int j = 0; j < alienArray.size(); j++) {
               Block alien = alienArray.get(j);
               if(!bullet.used && alien.alive && detectCollision(bullet , alien)){
                   bullet.used = true;
                   alien.alive = false;
                   alienCount--;
                   score+=100;

               }
           }

       }
       while (bulletArray.size()>0 && (bulletArray.get(0).used || bulletArray.get(0).y<0)) {
           bulletArray.remove(0);
           // Not efficient should use linked list not Arraylist
       }
            //Next lvl
       if(alienCount ==0){
           score += alienCols*alienRows*100; //pts for clearing 1 lvl
           alienCols = Math.min(alienCols+1 , columns/2-2);
           alienRows = Math.min(alienRows+1 , rows-6);
           alienArray.clear();
           bulletArray.clear();
           alienVelocityX=1;
           createAliens();
       }
   }

   public void createAliens(){
        Random random = new Random();
        for(int r=0 ; r<alienRows; r++){
            for(int c=0 ; c<alienCols; c++){
                //alienImgArray.size() is the upper bound value so the random alue will be 0-3
                int ramdomImgIndex = random.nextInt(alienImgArray.size());
                Block alien  = new Block(
                        alienX + c*alienWidth,
                        alienY + r*alienHeight,
                        alienWidth,
                        alienHeight,
                        alienImgArray.get(ramdomImgIndex)
                );
                alienArray.add(alien);
            }
        }
        alienCount = alienArray.size();
   }

   public boolean detectCollision(Block a , Block b){
        return a.x<b.x+b.width &&
                a.x+a.width > b.x &&
                a.y<b.y+b.height&&
                a.y+a.height > b.y;
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameover) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if(gameover){
            ship.x=shipX;
            alienArray.clear();
            bulletArray.clear();
            score=0;
            alienVelocityX =1;
            alienCols=3;
            alienRows=2;
            gameover=false;
            createAliens();
            gameLoop.start();
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && (ship.x-shipVelocityX) >= 0){
           ship.x -= shipVelocityX;
        }
       else if (e.getKeyCode() == KeyEvent.VK_RIGHT && (ship.x+shipwidth+shipVelocityX) <= (boardWidth)){
           ship.x += shipVelocityX;
       }
       else if (e.getKeyCode() == KeyEvent.VK_SPACE){
           Block bullet = new Block(ship.x + shipwidth*15/32, ship.y , bulletWidth, bulletHeight, null);
           bulletArray.add(bullet);
        }
    }

}

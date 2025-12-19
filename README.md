🧩 Project Overview

The game consists of a player-controlled spaceship that can move horizontally and shoot bullets to destroy descending alien formations. The aliens move as a group, reverse direction upon hitting screen boundaries, and advance downward toward the player. The game supports multiple levels, scoring, and game-over detection.

🛠 Technologies Used

Java

Swing (JPanel, JFrame, Timer)

AWT (Graphics, Events, Images)

🧱 Core Architecture
1️⃣ Entity Representation (Block Class)

All game objects (ship, aliens, bullets) are represented using a single inner class called Block.

Each Block stores:

Position (x, y)

Size (width, height)

Image reference (img)

State flags:

alive → used for aliens

used → used for bullets

This design simplifies collision handling and rendering by using a common structure for all entities.

2️⃣ Game Board Configuration

The game board uses a tile-based system (32 × 32 pixels per tile).

Screen dimensions are derived from tile rows and columns.

All movement and placement logic is aligned to this grid for consistency.

3️⃣ Rendering Pipeline

Rendering is handled by overriding paintComponent(Graphics g).

The draw() method is responsible for:

Drawing the ship

Rendering aliens

Rendering bullets

Displaying the score and game-over text

All drawing operations use Swing’s Graphics API.

4️⃣ Game Loop (Timer)

A Swing Timer runs at approximately 60 FPS.

The actionPerformed() method acts as the main game loop:

Updates entity movement

Handles collisions

Triggers screen repainting

Stops the game when a game-over condition occurs

5️⃣ Alien Behavior

Aliens are generated in a grid formation using configurable rows and columns.

Each alien is assigned a random sprite from a predefined image set.

Aliens:

Move horizontally as a group

Reverse direction on hitting screen edges

Move downward after each direction change

New levels increase alien rows and columns dynamically.

6️⃣ Player Controls

Keyboard input is handled using KeyListener:

Left Arrow → Move ship left

Right Arrow → Move ship right

Space Bar → Fire bullet

Any key after Game Over → Reset and restart the game

7️⃣ Bullet & Collision System

Bullets move vertically upward at a fixed velocity.

Axis-Aligned Bounding Box (AABB) collision detection is used to detect:

Bullet–alien collisions

On collision:

Alien is destroyed

Bullet is marked as used

Score is updated

Bullets are cleaned up efficiently once off-screen or used.

8️⃣ Scoring & Game State

Each alien destroyed awards points.

Clearing all aliens advances the game to the next level.

Game-over is triggered if an alien reaches the player’s row.

Score and game-over messages are displayed on-screen.

🎯 Key Learning Outcomes

This project demonstrates:

Swing-based game loops

Object-oriented game entity design

Real-time rendering using Graphics

Keyboard input handling

Collision detection

State management (score, game-over, levels)

📌 Future Improvements

Replace KeyListener with Swing Key Bindings

Add sound effects

Introduce alien bullets

Optimize data structures for bullets

Add start and pause menus

<img width="970" height="882" alt="image" src="https://github.com/user-attachments/assets/6a8380ec-9e6d-4aad-b284-8d240cd10741" />

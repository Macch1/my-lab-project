# Asteroids — Component-Based Software Engineering

A multi-module Java Asteroids game built as part of the **Komponentbaserede Systemer (Component-Based Software Engineering)** course at the **University of Southern Denmark (SDU)**.

The project demonstrates core principles of component-based software engineering — components can be installed and uninstalled **without recompiling** the system, using the Java Platform Module System (JPMS), `ServiceLoader`, Spring Framework, and JPMS `ModuleLayer`.

---

## Table of Contents

- [About the Project](#about-the-project)
- [Module Structure](#module-structure)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Running the Game](#running-the-game)
- [Running the HighScore Board](#running-the-highscore-board)
- [Installing and Uninstalling Components](#installing-and-uninstalling-components)
- [Running the Tests](#running-the-tests)
- [Gameplay](#gameplay)
- [Project Structure](#project-structure)

---

## About the Project

This is a component-based implementation of the classic Asteroids arcade game. Each game element — Player, Enemy, Asteroids, Bullets and Collision — is implemented as an independent JPMS module that can be added or removed from the system without recompiling any other module.

The central engineering challenge: in a naively built system, all components are compiled together. Replacing or updating one component forces a full recompile. This project solves that problem using:

- **JPMS module boundaries** — strict encapsulation and explicit dependency declarations
- **Java `ServiceLoader`** — components discover each other at runtime via interfaces, without direct imports
- **Spring Framework** — dependency injection wires processors and plugins into the game core
- **JPMS `ModuleLayer`** — plugins loaded from a `plugins/` folder at startup, enabling install/uninstall without recompilation
- **Spring Boot REST** — a standalone HighScore microservice that runs independently from the game

---

## Module Structure

```
asteroids.jpms (root)
|
+-- Common                  # Shared data types and service interfaces
|   +-- Entity              # Base entity class
|   +-- EntityType          # Entity type enum
|   +-- GameData            # Display dimensions and key input
|   +-- GameKeys            # Key state tracking
|   +-- World               # Entity container and game state
|   +-- IGamePluginService  # Plugin lifecycle interface
|   +-- IEntityProcessingService     # Per-frame entity processor interface
|   +-- IPostEntityProcessingService # Post-frame processor interface (collision)
|   +-- IScoreTracker       # Score communication interface
|
+-- Core                    # Game loop, Spring wiring, JavaFX window
+-- Player                  # Player movement, shooting, health
+-- CommonPlayer            # Player entity type
+-- Bullet                  # Bullet movement, lifecycle, BulletSPI factory
+-- CommonBullet            # Bullet entity type and BulletSPI interface
+-- Collision               # Collision detection and resolution
+-- EnemyShip               # Enemy AI, movement, shooting, respawning
+-- CommonEnemy             # Enemy entity type and EnemySPI interface
+-- Asteroids               # Asteroid movement, splitting, respawning
+-- CommonAsteroids         # Asteroid entity type and AsteroidSplitterSPI interface
+-- ScoreSystem             # HTTP client — submits final score to HighScoreSystem
+-- HighScoreSystem         # Spring Boot REST microservice — persists scores to file
```

---

## Technologies

| Technology | Purpose |
|---|---|
| Java 17 | Primary language |
| JavaFX 21 | Game rendering and window management |
| JPMS (Java Platform Module System) | Module encapsulation and dependency enforcement |
| Java `ServiceLoader` | Runtime component discovery |
| Spring Framework 6 | Dependency injection in Core |
| Spring Boot 3 | Standalone HighScore REST microservice |
| JUnit 5 | Unit testing |
| Maven | Build system |

---

## Prerequisites

- **Java 17** or higher
- **Maven 3.8** or higher

Verify your installation:

```bash
java -version
mvn -version
```

---

## Getting Started

Clone the repository:

```bash
git clone https://github.com/Macch1/my-lab-project.git
cd my-lab-project
```

Build all modules:

```bash
mvn clean install
```

A successful build will output:

```
[INFO] BUILD SUCCESS
```

---

## Running the Game

After a successful build, run the game from the project root:

```bash
mvn exec:exec
```

The game window will open automatically. See [Gameplay](#gameplay) for controls.

---

## Running the HighScore Board

The HighScore Board is a standalone Spring Boot microservice that runs independently from the game. Start it in a separate terminal **before** launching the game:

```bash
java -jar HighScoreSystem/target/HighScoreSystem-0.0.1-SNAPSHOT.jar
```

Once running, the HighScore Board is available at:

| Endpoint | Method | Description |
|---|---|---|
| `/score/submit?score=X` | POST | Submit a final score |
| `/score/highscores` | GET | View all saved highscores |

View your highscores in a browser at:
```
http://localhost:8080/score/highscores
```

Scores are saved to `highscores.txt` in the directory where the HighScore Board is running.

To stop the HighScore Board cleanly, press `Ctrl+C` in its terminal. Port 8080 will be released immediately.

> The game runs perfectly without the HighScore Board running. Scores simply will not be persisted if the board is unavailable.

---

## Installing and Uninstalling Components

The game supports installing and uninstalling components **without recompiling** any source code. Components are loaded from the `mods-mvn/` folder at startup using JPMS `ModuleLayer`.

### Uninstall a component

Remove the component's JAR from the `mods-mvn/` folder:

```bash
# Example - uninstall the Asteroids component
rm mods-mvn/Asteroids-1.0-SNAPSHOT.jar
```

### Run without rebuilding

```bash
mvn exec:exec
```

The game starts without the removed component. No asteroids will appear, but all other components — Player, Enemy, Bullets and Collision — continue to work correctly.

### Reinstall the component

Copy the JAR back into the `mods-mvn/` folder:

```bash
# Example - reinstall the Asteroids component
cp ~/.m2/repository/dk/sdu/se4/groupX/asteroids/Asteroids/1.0-SNAPSHOT/Asteroids-1.0-SNAPSHOT.jar mods-mvn/
```

### Run again without rebuilding

```bash
mvn exec:exec
```

Asteroids reappear — no recompilation was needed at any point.

---

## Running the Tests

Run all tests:

```bash
mvn test
```

The test suite includes **8 JUnit 5 unit tests** in the `Collision` module, covering:

- Overlap detection between entities
- Boundary cases (touching but not overlapping)
- Self-collision immunity (entities cannot collide with themselves)
- Damage application between collidable entities
- Immunity rules (same-type entities, non-damageable entities)

---

## Gameplay

### Controls

| Key | Action |
|---|---|
| `UP` | Thrust forward |
| `LEFT` | Rotate left |
| `RIGHT` | Rotate right |
| `SPACE` | Fire bullet |
| `ENTER` | Restart after game over |

### Scoring

| Event | Points |
|---|---|
| Asteroid destroyed | +10 |
| Enemy destroyed | +50 |

### Rules

- The player starts in the center of the screen with 100 health.
- Asteroids and enemies spawn continuously — asteroids at random positions away from the player, enemies at random points along the screen edges.
- Asteroids split into two smaller fragments when destroyed. Fragments too small to split are simply removed.
- Enemies track and shoot at the player. They respawn automatically when the count drops below the maximum.
- When the player's health reaches zero, the game ends. The final score is submitted to the HighScore Board (if running).
- Press `ENTER` to restart without closing the game.

---

## Project Structure

```
my-lab-project/
|
+-- Common/                 # Shared module
+-- Core/                   # Game loop and Spring wiring
+-- Player/                 # Player component
+-- CommonPlayer/           # Player entity type
+-- Bullet/                 # Bullet component
+-- CommonBullet/           # Bullet entity type and SPI
+-- Collision/              # Collision component
+-- EnemyShip/              # Enemy component
+-- CommonEnemy/            # Enemy entity type and SPI
+-- Asteroids/              # Asteroid component
+-- CommonAsteroids/        # Asteroid entity type and SPI
+-- ScoreSystem/            # Score HTTP client component
+-- HighScoreSystem/        # Standalone HighScore REST microservice
+-- mods-mvn/               # Compiled JARs — module path at runtime
+-- pom.xml                 # Root Maven build file
+-- highscores.txt          # Generated at runtime by HighScoreSystem
```

---

## Author

Developed as a university exam project for **Komponentbaserede Systemer** at SDU, Spring 2026.


---

## Disclaimer

AI have been used in the project for the following tasks: *Updating Comments*, *Updating JavaDocs*, *Debugging*, *Update ReadMe*, and as a *RubberDuck*.

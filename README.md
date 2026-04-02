# Poke-Hangman CLI

A robust, terminal-based Hangman game themed around Pokémon. This project demonstrates core Java programming concepts, including Object-Oriented design, file I/O, array manipulation, and algorithmic optimization.

## Features

* **Dynamic Word Loading**: Automatically parses a Pokémon word bank from an external `.txt` file.
* **Intelligent Input Validation**: Utilizes $O(1)$ Direct Address Tables to efficiently prevent wasted guesses on non-alphabetic characters or repeated letters.
* **Adaptive Difficulty**: The total number of allowed guesses scales dynamically based on the length of the chosen Pokémon's name.
* **Game State Tracking**: Real-time display of "Word in Progress," correct guesses, and incorrect guesses to guide the player.

---

## Technical Implementation

The application is structured into a modular, Object-Oriented architecture separating data management, game state, and user interaction:

| Component | Responsibility |
| :--- | :--- |
| `Hangman.java` | The primary controller. Manages the core game loop, console UI rendering, and user input validation. |
| `GameState.java` | The data model. Encapsulates game metrics and utilizes $O(1)$ Direct Address Tables (`characterMap` and `alreadyGuessed`) for instantaneous letter matching and duplicate checking. |
| `Wordbank.java` | The data loader. Handles File I/O to dynamically parse and populate the dictionary array for the session. |

---

## Getting Started

### Prerequisites
* **Java Development Kit (JDK) 8** or higher.
* A terminal or IDE (like IntelliJ, Eclipse, or VS Code).

### Installation & Execution
1.  **Clone the repository**:
    ```bash
    git clone [https://github.com/DipTaken/java-cli-hangman.git](https://github.com/DipTaken/java-cli-hangman.git)
    ```
2.  **Ensure the word bank is present**:
    Place `hangman-wordbank.txt` in the same directory as the `.java` files.
3.  **Compile the program**:
    ```bash
    javac Hangman.java
    ```
4.  **Run the game**:
    ```bash
    java Hangman
    ```

---

## Project Background
This CLI tool was originally developed in **November 2024** as part of a series of Java-based software projects. It has since been refactored to serve as a foundation for the more complex logic, physics simulations, and game development projects I am currently pursuing as a Computer Science student at the **University of Waterloo**.

## License
This project is licensed under the **MIT License**.
# Poke-Hangman CLI

A robust, terminal-based Hangman game themed around Pokémon. This project demonstrates core Java programming concepts, including file I/O, array manipulation, and defensive user-input handling.

## Features

* **Dynamic Word Loading**: Automatically parses a Pokémon word bank from an external `.txt` file.
* **Intelligent Input Validation**: Features a `DuplicateChecker` and `InputChecker` to prevent wasted guesses on non-alphabetic characters or repeated letters.
* **Adaptive Difficulty**: The total number of allowed guesses scales dynamically based on the length of the chosen Pokémon's name.
* **Game State Tracking**: Real-time display of "Word in Progress," correct guesses, and incorrect guesses to guide the player.

---

## Technical Implementation

The game is structured into several modular static methods to ensure clean, readable code:

| Method | Description |
| :--- | :--- |
| `GrabWordsFromFile` | Handles `Scanner` and `File` objects to count lines and populate the initial word bank. |
| `PickRandomWord` | Utilizes the `Random` class to select a word and converts it into a `char[]` for game logic. |
| `Game` | The primary engine that manages the game loop, guess increments, and UI updates. |
| `WinChecker` | Compares the current progress against the target word to determine a victory state. |

---

## Getting Started

### Prerequisites
* **Java Development Kit (JDK) 8** or higher.
* A terminal or IDE (like IntelliJ, Eclipse, or VS Code).

### Installation & Execution
1.  **Clone the repository**:
    ```bash
    git clone https://github.com/DipTaken/java-cli-hangman.git
    ```
2.  **Ensure the word bank is present**:
    Place `hangman-wordbank.txt` in the same directory as the `.java` file.
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
This CLI tool was originally developed in **November 2024** as part of a series of Java-based software projects. It serves as a foundation for the more complex logic, physics simulations, and game development projects I am currently pursuing as a Computer Science student at the **University of Waterloo**.

## License
This project is licensed under the **MIT License**.

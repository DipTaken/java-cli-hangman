
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    static Scanner kb = new Scanner(System.in); 
    
    public static void main(String[] args) throws Exception {
        Wordbank wordbank = new Wordbank();
        String[] words = wordbank.InitializeWordBank();

        //game menu
        int gameChoice = 0;	
		do {
			System.out.println("Welcome to Poke-Hangman!\n\t1) Let's play!\n\t2) Quit. ");
			gameChoice = kb.nextInt();

			if (gameChoice < 1 || gameChoice > 2) {
				System.out.println("Not a valid option. Try again. ");
			}
			if (gameChoice == 1) {
				System.out.println("Choosing word...");
                System.out.println("Choice made.");
                char[] word = PickRandomWord(words); //pick a word from word bank and store each individual letter into an element of the array
                //PrintCharArray(word); //debug
                Game(word);
			}			
		} 
        while (gameChoice != 2);		
        //exits the game
        System.out.println("Thank you for playing!");
        kb.close();
	}
	
	public static void Game(char[] word) {	
        //initializes the game state; holds word and all relevant information about the game
        GameState gameState = new GameState(word);
        System.out.println();
        
        //UI-guessing
        PrintCharArray(gameState.wordInProgress);
        System.out.println("You have " + gameState.totalGuesses + " guesses to get the word.\n");

        //main loop
        for (int i = 0; i < gameState.totalGuesses; i++) {
            System.out.print("Make a guess! "); // asks for a guess
            char guessedChar = GrabChar();
            guessedChar = ValidateGuess(gameState, guessedChar);
            DisplayGuess(gameState, guessedChar);
            
            if (UserHasWon(gameState, word)) {
                System.out.println("You win! It took you " + gameState.guesses + " tries to win.");
                System.out.print("The word was: ");
                PrintCharArray(word);
                System.out.println();
                break;
            }

            kb.nextLine(); //clears the buffer - spaces will create multiple inputs
        }

        //if user did not win
        if (!UserHasWon(gameState, word)) {
            System.out.println("You did not get the word correct.");
            System.out.print("The word was: ");
            PrintCharArray(word);
            System.out.println();
        }
	}
    
    public static char GrabChar() {
        return kb.next().toLowerCase().charAt(0); //taking the first letter of input, and converting it into lowercase
    }

    public static void DisplayGuess(GameState gameState, char guessedChar) {
        //checks if guess is correct or not
        if (InWord(gameState, guessedChar)) {
            //adds it to the word in progress
            for (int i = 0; i < gameState.wordLength; i++) {
                if (guessedChar == gameState.word[i]) {
                    gameState.wordInProgress[i] = guessedChar;
                    gameState.correctLetters++;
                }
            }
            //adds it to the correct word array
            gameState.correctGuesses[gameState.correctGuessesIndex] = guessedChar;
            gameState.correctGuessesIndex++;
        }
        else {
            //adds it to the incorrect word array
            gameState.incorrectGuesses[gameState.incorrectGuessesIndex] = guessedChar;
            gameState.incorrectGuessesIndex++;
        }

        gameState.alreadyGuessed[guessedChar - 'a'] = true; // adds the guessed letter to the direct address table for duplicate checking

        //displays word in progress, correct, incorrect, and total guesses
        System.out.println();
        PrintCharArray(gameState.wordInProgress);
        System.out.print("Correct guesses: ");
        PrintCharArray(gameState.correctGuesses);
        System.out.print("Incorrect guesses: ");
        PrintCharArray(gameState.incorrectGuesses);    
        gameState.guesses++;
        System.out.println("Total guesses: " + gameState.guesses + " out of " + gameState.totalGuesses + "\n");
    }
        
    public static char ValidateGuess(GameState gameState, char guessedChar) {
        //prompts the user to enter a valid guess if they entered an invalid character or a duplicate character, and continues to prompt until they enter a valid guess
        while (!ValidChar(guessedChar) || IsDuplicate(gameState, guessedChar)) {
            if (!ValidChar(guessedChar)) {
                System.out.print("Invalid character! Please choose another one. ");
            }
            else {
                System.out.print("You have already guessed this letter! Please choose another one. ");
            }
            //clears the buffer - spaces will create multiple inputs
            kb.nextLine();
            guessedChar = GrabChar(); //takes the next guess and validates it again
        }
        return guessedChar;
    }

    public static char[] PickRandomWord(String[] wordBank) {
        //chooses a number between 0 and the number of words in the word bank, and assigns it to a string
        Random rand = new Random();
        String randomWord = wordBank[rand.nextInt(0, wordBank.length)];
        //converts the random word into an char[] array of letters
        char[] word = new char[randomWord.length()];
        for (int i = 0; i < word.length; i++) {
            word[i] = Character.toLowerCase(randomWord.charAt(i));
        }
        return word;
    }

    public static boolean InWord(GameState gameState, char guessedChar) {
        //checks if the guessed letter matches any letters in the word using a direct address table
        int index = guessedChar - 'a';
        return gameState.characterMap[index];
    }

    public static void PrintCharArray(char[] arr){
        //prints every element in a char[] array
        for (char c: arr) System.out.print(c + " ");
        System.out.println();
    }
    
    public static boolean ValidChar(char guessedChar) {   
        //makes sure that the guess is a letter from a-z, lower and upper case
        return guessedChar >= 'A' && guessedChar <= 'Z' || guessedChar >= 'a' && guessedChar <= 'z';
    }

    public static boolean IsDuplicate(GameState gameState, char guessedChar) {
        int index = guessedChar - 'a';
        return gameState.alreadyGuessed[index]; // checks if the guessed letter has already been guessed using a direct address table
    }

    public static boolean UserHasWon(GameState gameState, char[] word) {
        return gameState.correctLetters == gameState.wordLength; // user wins if the number of correct letters guessed is equal to the length of the word
    }
}
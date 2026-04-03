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
                System.out.println("Choice made.\n");
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
        
        //UI-guessing
        PrintCharArray(gameState.WordInProgress());
        System.out.println("You have " + gameState.Lives() + " lives to get the word!\n");

        //main loop
        while (gameState.Lives() > 0) { //continues until user runs out of lives
            System.out.print("Make a guess! "); // asks for a guess
            char guessedChar = GrabChar();
            guessedChar = ValidateGuess(gameState, guessedChar);
            DisplayGuess(gameState, guessedChar);
            
            if (UserHasWon(gameState)) {
                System.out.println("You win! It took you " + gameState.Guesses() + " tries to win.");
                System.out.print("The word was: ");
                PrintCharArray(word);
                System.out.println();
                break;
            }

            kb.nextLine(); //clears the buffer - spaces will create multiple inputs
        }

        //if user did not win
        if (!UserHasWon(gameState)) {
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
        gameState.RecordGuess(guessedChar); //updates gamestate with the guess

        //displays word in progress, correct, incorrect, and lives
        System.out.println();
        PrintCharArray(gameState.WordInProgress());
        System.out.print("Correct guesses: ");
        PrintCharArray(gameState.CorrectGuesses());
        System.out.print("Incorrect guesses: ");
        PrintCharArray(gameState.IncorrectGuesses());
        System.out.println("You have " + gameState.Lives() + " lives left.\n");
    }
        
    public static char ValidateGuess(GameState gameState, char guessedChar) {
        //prompts the user to enter a valid guess if they entered an invalid character or a duplicate character, and continues to prompt until they enter a valid guess
        while (!ValidChar(guessedChar) || gameState.IsDuplicate(guessedChar)) {
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

    public static boolean ValidChar(char guessedChar) {   
        //makes sure that the guess is a letter from a-z, lower and upper case
        return guessedChar >= 'A' && guessedChar <= 'Z' || guessedChar >= 'a' && guessedChar <= 'z';
    }

    public static boolean UserHasWon(GameState gameState) {
        return gameState.CorrectLetters() == gameState.WordLength(); // user wins if the number of correct letters guessed is equal to the length of the word
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

    public static void PrintCharArray(char[] arr){
        //prints every element in a char[] array
        for (char c: arr) System.out.print(c + " ");
        System.out.println();
    }
}
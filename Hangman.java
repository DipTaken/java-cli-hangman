//Dennis Q, 2024-10-29

import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Hangman
{
    static Scanner kb = new Scanner(System.in); 
    public static void main(String[] args) throws Exception
    {
        //getting array of words from a file
        //note: downloaded wordbank does not function properly until a new file is created and its contents are copied into it
        String[] wordBank = GrabWordsFromFile("hangman-wordbank.txt"); //enter filename to read from
        //the file must be located in user/denni/
        
        //uncomment if not using word bank from file 
        //String[] wordBank = { 
        //   "bulbasaur", "weedle", "caterpie", "oddish", "ditto", "machoke", 
        //   "ponyta", "gyrados", "squirtle", "pikachu", "charmander", "jigglypuff", 
        //    "magneton", "staryu", "zapdos", "raichu", "mewtwo", "rattata", "spearow", "sandshrew" 
        //};
        
        //game menu
        int gameChoice = 0;	
		do 
        {
			System.out.println("Welcome to Poke-Hangman!\n\t1) Let's play!\n\t2) Quit. ");
			gameChoice = kb.nextInt();

			if (gameChoice < 1 || gameChoice > 2) 
            {
				System.out.println("Not a valid option. Try again. ");
			}
			if (gameChoice == 1) 
            {
				System.out.println("Choosing word...");
                System.out.println("Choice made.");
                //pick a word from word bank and store each individual letter into an element of the array
                char[] word = PickRandomWord(wordBank); //the instructions say to pass a word into the Game() method, so it is chosen here.
                //PrintCharArray(word); //debug
                Game(word);
			}			
		} 
        while ( gameChoice != 2 );		

        System.out.println("Thank you for playing!");
        kb.close();
	}
	
	public static void Game (char[] word) 
    {	
        //set max guesses
        int guesses;
        int totalGuesses = (word.length + 5);

        //creates an array for storing correct/incorrect guesses and populates with blankspace so that NULL does not display.
        char[] correctGuesses = new char[word.length];
        char[] incorrectGuesses = new char[totalGuesses];
        for (int i = 0; i < correctGuesses.length; i++)
        {
            correctGuesses[i] = ' ';
        }
        for (int i = 0; i < incorrectGuesses.length; i++)
        {
            incorrectGuesses[i] = ' ';
        }
        int correctGuessesIndex = 0, incorrectGuessesIndex = 0;

        //initialize wordInProgress array and populates indices
        char[] wordInProgress = new char[word.length];
        for (int i = 0; i < wordInProgress.length; i++)
        {
            //populates with underscores unless there is a special char
            if (word[i] >= 'a' && word[i] <= 'z') 
            {
                wordInProgress[i] = '_';
            }
            else
            {
                wordInProgress[i] = word[i];
            }
        }
        System.out.println();
        //UI-guessing
        PrintCharArray(wordInProgress);
        System.out.println("You have " + totalGuesses + " guesses to get the word.\n");
        
        //main loop
        for (guesses = 0; guesses < totalGuesses; guesses++)
        {
            // asks for a guess
            System.out.print("Make a guess! ");
            char guessedChar = kb.next().toLowerCase().charAt(0); //taking the first letter of input, and converting it into lowercase

            //checks if the input is valid and if a duplicate has been entered
            while (InputChecker(guessedChar, word) == false || DuplicateChecker(guessedChar, correctGuesses, incorrectGuesses) == false)
            {
                if (InputChecker(guessedChar, word) == false)
                {
                    System.out.print("Invalid character! Please choose another one. ");
                }
                else
                {
                    System.out.print("You have already guessed this letter! Please choose another one. ");
                }
                //clears the buffer - spaces will create multiple inputs
                kb = new Scanner(System.in);
                guessedChar = kb.next().toLowerCase().charAt(0); //taking the first letter of input, and converting it into lowercase
            }
            
            //checks if guess is correct or not
            if (GuessChecker(guessedChar, word))
            {
                //adds it to the word in progress
                for (int i = 0; i < word.length; i++)
                {
                    if (guessedChar == word[i])
                    {
                        wordInProgress[i] = guessedChar;
                    }
                }
                //adds it to the correct word array
                correctGuesses[correctGuessesIndex] = guessedChar;
                correctGuessesIndex++;
            }
            else
            {
                //adds it to the incorrect word array
                incorrectGuesses[incorrectGuessesIndex] = guessedChar;
                incorrectGuessesIndex++;
            }

            //displays word in progress, correct, incorrect, and total guesses
            PrintCharArray(wordInProgress);
            System.out.print("Correct guesses: ");
            PrintCharArray(correctGuesses);
            System.out.print("Incorrect guesses: ");
            PrintCharArray(incorrectGuesses);
            System.out.println("Total guesses: " + (guesses + 1) + " out of " + totalGuesses + "\n"); //uses guesses + 1 since we are on our nth guess, but user has just guessed and we have not incremented guess

            //checking if player has won, if so, break out of the loop
            if (WinChecker(wordInProgress, word))
            {
                System.out.println("You win! It took you " + (guesses + 1) + " tries to win."); //uses guesses + 1 since we are on our nth guess, but user has just guessed and we have not incremented guess
                System.out.print("The word was: ");
                PrintCharArray(word);
                System.out.println();
                break;
            }

            //clears the buffer - spaces will create multiple inputs
            kb = new Scanner(System.in);
        }

        //if user did not win
        if (!WinChecker(wordInProgress, word))
        {
            System.out.println("You did not get the word correct.");
            System.out.print("The word was: ");
            PrintCharArray(word);
            System.out.println();
        }
	}

    public static char[] PickRandomWord(String[] wordBank)
    {
        //chooses a number between 0 and the number of words in the word bank, and assigns it to a string
        Random rand = new Random();
        String randomWord = wordBank[rand.nextInt(0, wordBank.length)];

        //converts the random word into an char[] array of letters
        char[] word = new char[randomWord.length()];
        for (int i = 0; i < word.length; i++)
        {
            word[i] = Character.toLowerCase(randomWord.charAt(i));
        }
        return word;
    }

    public static boolean GuessChecker(char guessedChar, char[] word)
    {
        //checks if the guessed letter matches any letters in the word
        for (int i = 0; i < word.length; i++)
        {
            if (guessedChar == word[i])
            {
                return true;
            }
        }
        return false;
    }

    public static void PrintCharArray(char[] arr)
    {
        //prints every element in a char[] array
        for (char c: arr)
        {
            System.out.print(c + " ");
        }
        System.out.println();
    }
    
    public static boolean InputChecker(char guessedChar, char[] word)
    {   
        //makes sure that the guess is a letter from a-z, lower and upper case
        if (guessedChar >= 'A' && guessedChar <= 'Z' || guessedChar >= 'a' && guessedChar <= 'z') 
        {
            return true;
        }
        return false;
    }

    public static boolean DuplicateChecker(char guessedChar, char[] correctGuesses, char[] incorrectGuesses)
    {
        //checks if the guessed letter already exists in the correct/incorrect array
        for (int i = 0; i < correctGuesses.length; i++)
        {
            if (guessedChar == correctGuesses[i])
            {
                return false;
            }
        }

        for (int i = 0; i < incorrectGuesses.length; i++)
        {
            if (guessedChar == incorrectGuesses[i])
            {
                return false;
            }
        }
        return true;
    }
    
    public static boolean WinChecker(char[] wordInProgress, char[] word)
    {
        //checks if all letters match the word
        for (int i = 0; i < word.length; i++)
        {
            if (wordInProgress[i] != word[i])
            {
                return false;
            }
        }
        return true;
    }

    public static String[] GrabWordsFromFile(String fileName) throws Exception
    {
        //grabbing words from file
        File wordBankFile = new File(fileName);
        Scanner fileReader = new Scanner(wordBankFile);

        //counting the number of words
        int lines = 0;
        while (fileReader.hasNextLine())
        {
            if (!fileReader.nextLine().isEmpty())
            {
                lines++;
            } 
            //System.out.println(lines); //debug
        }
        fileReader.close();
        
        //assigning words to an array of length of lines in the file
        fileReader = new Scanner(wordBankFile);
        String[] wordBank = new String[lines];
        for (int i = 0; i < wordBank.length; i++)
        {
            wordBank[i] = fileReader.nextLine();
        }
        fileReader.close();

        return wordBank;
    }
}
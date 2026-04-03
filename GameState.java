public class GameState {
    private static final int ALPHABET_SIZE = 26;

    //vars that will never appear outside of this class
    private final char[] word;
    private int correctGuessesIndex = 0;
    private int incorrectGuessesIndex = 0;
    private final boolean[] characterMap; // keeps track of guessed letters for O(1) duplicate checking
    private final boolean[] alreadyGuessed = new boolean[ALPHABET_SIZE]; 
 
    //vars that can be accessed outside of this class using getters
    private int correctLetters = 0;
    private int guesses = 0;
    private int lives;
    private final int wordLength;
    private final char[] wordInProgress;
    private final char[] correctGuesses;
    private final char[] incorrectGuesses;
    
    public GameState(char[] targetWord) {
        this.word = targetWord;
        this.wordLength = targetWord.length;
        this.characterMap = GenerateCharacterMap(targetWord);
        this.lives = 7 + (wordLength / 5); // gives more lives for longer words

        this.correctGuesses = new char[ALPHABET_SIZE];
        this.incorrectGuesses = new char[ALPHABET_SIZE];
        //creates an array for storing correct/incorrect guesses and populates with blankspace so that NULL does not display.
        java.util.Arrays.fill(correctGuesses, ' ');
        java.util.Arrays.fill(incorrectGuesses, ' ');
        
        //initialize wordInProgress array and populates indices
        this.wordInProgress = new char[wordLength];
        for (int i = 0; i < wordLength; i++) {
            //populates with underscores unless there is a special char
            if (word[i] >= 'a' && word[i] <= 'z') {
                wordInProgress[i] = '_';
            } 
            else {
                wordInProgress[i] = word[i];
                this.correctLetters++; //counts special characters since user does not have to guess them
            }
        }
    }

    //direct address table for O(1) letter checking
    private boolean[] GenerateCharacterMap(char[] word) {
        boolean[] map = new boolean[ALPHABET_SIZE];
        for (char c : word) {
            if (c >= 'a' && c <= 'z') { //ignore special characters and spaces
                int index = c - 'a';
                map[index] = true;
            }
        }
        return map;
    }

    //used for mutating gamestate
    public void RecordGuess(char guessedChar) {
        //checks if guess is correct or not
        if (InWord(guessedChar)) {
            //adds it to the word in progress
            for (int i = 0; i < this.wordLength; i++) {
                if (guessedChar == this.word[i]) {
                    this.wordInProgress[i] = guessedChar;
                    this.correctLetters++;
                }
            }
            //adds it to the correct word array
            this.correctGuesses[this.correctGuessesIndex] = guessedChar;
            this.correctGuessesIndex++;
        }
        else {
            //adds it to the incorrect word array
            this.incorrectGuesses[this.incorrectGuessesIndex] = guessedChar;
            this.incorrectGuessesIndex++;
            this.lives--; //subtracts a life for an incorrect guess
        }
        this.alreadyGuessed[guessedChar - 'a'] = true; // adds the guessed letter to the direct address table for duplicate checking
        this.guesses++;
    }

    private boolean InWord(char guessedChar) {
        //checks if the guessed letter matches any letters in the word using a direct address table
        int index = guessedChar - 'a';
        return this.characterMap[index];
    }

    public boolean IsDuplicate(char guessedChar) {
        int index = guessedChar - 'a';
        return this.alreadyGuessed[index]; // checks if the guessed letter has already been guessed using a direct address table
    }

    //getters for vars
    public int Lives() { return lives; }
    public int Guesses() { return guesses; }
    public int WordLength() { return wordLength; }
    public int CorrectLetters() { return correctLetters; }
    public char[] WordInProgress() { return wordInProgress; }
    public char[] CorrectGuesses() { return correctGuesses; }
    public char[] IncorrectGuesses() { return incorrectGuesses; }
}
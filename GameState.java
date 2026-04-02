public class GameState {
    private static final int ALPHABET_SIZE = 26;
    
    public final char[] word;
    public final char[] wordInProgress;
    public final char[] correctGuesses;
    public final char[] incorrectGuesses;
    public final int totalGuesses;
    public final int wordLength;
    public final boolean[] characterMap;

    public int guesses = 0;
    public int correctLetters = 0;
    public int correctGuessesIndex = 0;
    public int incorrectGuessesIndex = 0;
    public boolean[] alreadyGuessed = new boolean[ALPHABET_SIZE]; // keeps track of guessed letters for O(1) duplicate checking

    public GameState(char[] targetWord) {
        this.word = targetWord;
        this.wordLength = targetWord.length;
        this.characterMap = GenerateCharacterMap(targetWord);
        this.totalGuesses = wordLength + wordLength / 2; // 1.5 times the length of the word
        
        this.correctGuesses = new char[ALPHABET_SIZE];
        this.incorrectGuesses = new char[ALPHABET_SIZE];
        //creates an array for storing correct/incorrect guesses and populates with blankspace so that NULL does not display.
        java.util.Arrays.fill(correctGuesses, ' ');
        java.util.Arrays.fill(incorrectGuesses, ' ');
        
        //initialize wordInProgress array and populates indices
        this.wordInProgress = new char[wordLength];
        for (int i = 0; i < wordLength; i++) {
            //populates with underscores unless there is a special char
            wordInProgress[i] = (word[i] >= 'a' && word[i] <= 'z') ? '_' : word[i];
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

}
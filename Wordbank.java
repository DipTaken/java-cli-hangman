import java.io.File;
import java.util.Scanner;

public class Wordbank {
    private static final boolean USE_WORD_BANK_FROM_FILE = true; //set to false if not using word bank from file

    public String[] GrabWordsFromFile(String fileName) throws Exception {
        //grabbing words from file
        File wordBankFile = new File(fileName);
        Scanner fileReader = new Scanner(wordBankFile);
        //counting the number of words
        int lines = 0;
        while (fileReader.hasNextLine()) {
            if (!fileReader.nextLine().isEmpty()) lines++;
            //System.out.println(lines); //debug
        }
        fileReader.close();

        //assigning words to an array of length of lines in the file
        fileReader = new Scanner(wordBankFile);
        String[] wordBank = new String[lines];
        for (int i = 0; i < wordBank.length; i++) {
            wordBank[i] = fileReader.nextLine();
        }
        fileReader.close();
        return wordBank;
    }

    public String[] InitializeWordBank() throws Exception {
        String[] wordBank;
        if (USE_WORD_BANK_FROM_FILE) {
            //getting array of words from a file
            wordBank = GrabWordsFromFile("hangman-wordbank.txt"); //enter filename to read from
        }
        else {
            wordBank = new String[] {
            "bulbasaur", "weedle", "caterpie", "oddish", "ditto", "machoke",
            "ponyta", "gyrados", "squirtle", "pikachu", "charmander", "jigglypuff",
            "magneton", "staryu", "zapdos", "raichu", "mewtwo", "rattata", "spearow", "sandshrew"
            };
        }
        return wordBank;
    }
}
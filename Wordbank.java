import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Wordbank {
    private static final boolean USE_WORD_BANK_FROM_FILE = true; //set to false if not using word bank from file

    public String[] GrabWordsFromFile(String fileName) throws Exception {
        //grabbing words from file
        File wordBankFile = new File(fileName);
        Scanner fileReader = new Scanner(wordBankFile);
        ArrayList<String> wordsList = new ArrayList<>(); //list to store words

        //counting the number of words
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine(); //reads each line of the file
            if (!line.isEmpty()) wordsList.add(line); //adds non-empty lines to the list
            //System.out.println(lines); //debug
        }
        fileReader.close();

        //assigning words to an array of length of lines in the file
        return wordsList.toArray(new String[0]);
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
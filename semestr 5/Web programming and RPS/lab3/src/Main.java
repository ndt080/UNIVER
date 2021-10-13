import Core.Models.Sentence;
import Core.Services.FindWordsService;
import Core.Services.Parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
//        ArrayList<Sentence> text = parser.textToSentences(new File("src/Resources/data.txt"));
        ArrayList<Sentence> text = parser.textToSentencesWithLocale(
                new File("src/Resources/data.txt"),
                "US",
                "US"
        );

        System.out.println("SENTENCES: ");
        for (Sentence sentence: text){
            System.out.println("\t" + sentence.toString());
        }

        System.out.println("RESULT:\n\t" + FindWordsService.notInSentences(text).getText());

        System.out.println("RESULT:\n\t" + FindWordsService.lengthWordOfSentence(text, 5));
    }
}
package Core.Services;

import Core.Models.Sentence;
import Core.Models.Word;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Word search service in sentences
 */
public class FindWordsService {
    /**
     *  Search for a word from the first sentence that does not occur in subsequent ones.
     * @param sentences Array of sentence
     * @return found word or null
     */
    public static Word notInSentences(ArrayList<Sentence> sentences) {
        Set<Word> allWords = new HashSet<>();
        for (int i = 1; i < sentences.size(); i++) {
            allWords.addAll(sentences.get(i).getText());
        }
        for (Word word : sentences.get(0).getText()) {
            if (!allWords.contains(word))
                return word;
        }
        return null;
    }
}

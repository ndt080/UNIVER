package Core.Models;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Sentence model
 */
public class Sentence {
    /**
     * Sentence is a sequence of words
     */
    private ArrayList<Word> text;

    /**
     *  Get the sequence of words
     * @return  sequence of words
     */
    public ArrayList<Word> getText() {
        return text;
    }

    /**
     *  Set the sequence of words
     * @param text  sequence of words
     */
    public void setText(ArrayList<Word> text) {
        this.text = text;
    }

    /**
     *  Create sentence
     * @param text text
     */
    public Sentence(String text) {
        this.text = new ArrayList<Word>();
        String[] words = text.split("\\s+");

        for (String word : words) {
            if (!word.equals("")) {
                if ("([\u2026.,!?;:()])".indexOf(word.charAt(word.length() - 1)) > -1) {
                    this.text.add(new Word(word.substring(0, word.length() - 1)));
                    this.text.add(new Word("" + word.charAt(word.length() - 1)));
                } else {
                    this.text.add(new Word(word));
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        this.text.forEach((word) -> {
            text.append(word.getText().matches("([\u2026.,!?;:()])") ? "" : " ");
            text.append(word.getText());
        });
        return text.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sentence)) return false;
        Sentence sentence1 = (Sentence) o;
        return getText().equals(sentence1.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText());
    }
}

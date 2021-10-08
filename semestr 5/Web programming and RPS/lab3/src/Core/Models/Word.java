package Core.Models;

/**
 * Word model
 */
public class Word {
    /**
     * Character sequence
     */
    private final String text;

    /**
     * get character sequence
     */
    public String getText() {
        return text;
    }

    /**
     * set character sequence
     */
    public String setText(String text) {
        return text;
    }

    /**
     *  Constructor word model
     * @param text character sequence
     */
    public Word(String text){
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word = (Word) o;
        return text.equals(word.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return text;
    }
}
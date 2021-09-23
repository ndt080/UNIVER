package Entities;

import java.util.Objects;

/**
 * Class Newspaper
 */
public class Newspaper extends Periodical {
    /**
     * Language publication
     */
    private String language;

    /**
     * @param name Name periodical publication
     * @param coast Coast periodical publication
     * @param printEdition Is the publication printed?
     * @param electronicEdition Is the electronic printed?
     * @param officialMassMedia Is the official mass media?
     * @param language language publication
     */
    public Newspaper(String name, int coast, boolean printEdition, boolean electronicEdition,
                     boolean officialMassMedia, String language) {
        super(name, coast, printEdition, electronicEdition, officialMassMedia);
        this.language = language;
    }

    /**
     * @return get Language publication
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language set Language publication
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @param o Object publication
     * @return Publication is equal?
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Newspaper)) return false;
        if (!super.equals(o)) return false;
        Newspaper newspaper = (Newspaper) o;
        return getLanguage().equals(newspaper.getLanguage());
    }

    /**
     * @return get hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLanguage());
    }
}

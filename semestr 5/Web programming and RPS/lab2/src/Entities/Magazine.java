package Entities;

import java.util.Objects;

/**
 * Class Magazine
 */
public class Magazine extends Periodical{
    /**
     * Audience publication
     */
    private String audience;

    /**
     * @param name Name periodical publication
     * @param coast Coast periodical publication
     * @param printEdition Is the publication printed?
     * @param electronicEdition Is the electronic printed?
     * @param officialMassMedia Is the official mass media?
     * @param audience audience publication
     */
    public Magazine(String name, int coast, boolean printEdition,
                    boolean electronicEdition, boolean officialMassMedia, String audience) {
        super(name, coast, printEdition, electronicEdition, officialMassMedia);
        this.audience = audience;
    }

    /**
     * @return get audience publication
     */
    public String getAudience() {
        return audience;
    }

    /**
     * @param audience set audience publication
     */
    public void setAudience(String audience) {
        this.audience = audience;
    }

    /**
     * @param o Object publication
     * @return Publication is equal?
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Magazine)) return false;
        if (!super.equals(o)) return false;
        Magazine magazine = (Magazine) o;
        return getAudience().equals(magazine.getAudience());
    }

    /**
     * @return get hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAudience());
    }
}

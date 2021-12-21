package Core.Entities;

import java.io.*;
import java.util.Comparator;
import java.util.Objects;

/**
 * Class Magazine
 */
public class Magazine extends Periodical implements Serializable, Comparable<Magazine> {
    /**
     * Audience publication
     */
    private String audience;

    /**
     * @param name              Name periodical publication
     * @param coast             Coast periodical publication
     * @param printEdition      Is the publication printed?
     * @param electronicEdition Is the electronic printed?
     * @param audience          audience publication
     */
    public Magazine(String name, double coast, boolean printEdition, boolean electronicEdition, String audience) {
        super(name, coast, printEdition, electronicEdition, false);
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

    @Override
    public int compareTo(Magazine o) {
        return Comparator.comparing(Magazine::getAudience)
                .thenComparingDouble(Magazine::getCoast)
                .compare(this, o);
    }

    @Serial
    private void writeObject(ObjectOutputStream out)
            throws IOException {
        out.writeObject(getName());
        out.writeObject(getCoast());
        out.writeObject(isPrintEdition());
        out.writeObject(isElectronicEdition());
        out.writeObject(isOfficialMassMedia());
        out.writeObject(getAudience());
    }

    @Serial
    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        this.setName((String) in.readObject());
        this.setCoast((double) in.readObject());
        this.setPrintEdition((boolean) in.readObject());
        this.setElectronicEdition((boolean) in.readObject());
        this.setOfficialMassMedia((boolean) in.readObject());
        this.setAudience((String) in.readObject());
    }
}

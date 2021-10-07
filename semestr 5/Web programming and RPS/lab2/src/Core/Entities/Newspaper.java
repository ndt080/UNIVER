package Core.Entities;

import java.io.*;
import java.util.Comparator;
import java.util.Objects;

/**
 * Class Newspaper
 */
public class Newspaper extends Periodical implements Serializable, Comparable<Newspaper> {
    /**
     * Language publication
     */
    private String language;

    /**
     * @param name              Name periodical publication
     * @param coast             Coast periodical publication
     * @param printEdition      Is the publication printed?
     * @param electronicEdition Is the electronic printed?
     * @param language          language publication
     */
    public Newspaper(String name, double coast, boolean printEdition, boolean electronicEdition, String language) {
        super(name, coast, printEdition, electronicEdition, true);
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

    @Override
    public int compareTo(Newspaper o) {
        return Comparator.comparing(Newspaper::getLanguage)
                .thenComparingDouble(Newspaper::getCoast)
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
        out.writeObject(getLanguage());
    }

    @Serial
    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        this.setName((String) in.readObject());
        this.setCoast((double) in.readObject());
        this.setPrintEdition((boolean) in.readObject());
        this.setElectronicEdition((boolean) in.readObject());
        this.setOfficialMassMedia((boolean) in.readObject());
        this.setLanguage((String) in.readObject());
    }
}

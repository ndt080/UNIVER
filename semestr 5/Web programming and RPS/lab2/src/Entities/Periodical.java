package Entities;

import java.util.Objects;

/**
 * Class Periodical publication
 */
public abstract class Periodical {
    /**
     * Name periodical publication
     */
    private String name;
    /**
     * Coast periodical publication
     */
    private int coast;
    /**
     * Is the publication printed?
     */
    private boolean printEdition;
    /**
     * Is the electronic printed?
     */
    private boolean electronicEdition;
    /**
     * Is the official mass media?
     */
    private boolean officialMassMedia;

    /**
     * @param name Name periodical publication
     * @param coast Coast periodical publication
     * @param printEdition Is the publication printed?
     * @param electronicEdition Is the electronic printed?
     * @param officialMassMedia Is the official mass media?
     */
    protected Periodical(String name, int coast, boolean printEdition, boolean electronicEdition, boolean officialMassMedia) {
        this.name = name;
        this.coast = coast;
        this.printEdition = printEdition;
        this.electronicEdition = electronicEdition;
        this.officialMassMedia = officialMassMedia;
    }

    /**
     * @return get name periodical publication
     */
    public String getName() {
        return name;
    }

    /**
     * @param name set name periodical publication
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return get coast periodical publication
     */
    public int getCoast() {
        return coast;
    }

    /**
     * @param coast set coast periodical publication
     */
    public void setCoast(int coast) {
        this.coast = coast;
    }

    /**
     * @return get is print
     */
    public boolean isPrintEdition() {
        return printEdition;
    }

    /**
     * @param printEdition set is print periodical publication
     */
    public void setPrintEdition(boolean printEdition) {
        this.printEdition = printEdition;
    }

    /**
     * @return get is electronic periodical publication
     */
    public boolean isElectronicEdition() {
        return electronicEdition;
    }

    /**
     * @param electronicEdition set is electronic periodical publication
     */
    public void setElectronicEdition(boolean electronicEdition) {
        this.electronicEdition = electronicEdition;
    }

    /**
     * @return get is official mass media periodical publication
     */
    public boolean isOfficialMassMedia() {
        return officialMassMedia;
    }

    /**
     * @param officialMassMedia  set is official mass media periodical publication
     */
    public void setOfficialMassMedia(boolean officialMassMedia) {
        this.officialMassMedia = officialMassMedia;
    }

    /**
     * @param o Object publication
     * @return Publication is equal?
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Periodical)) return false;
        Periodical that = (Periodical) o;
        return getCoast() == that.getCoast() && isPrintEdition() == that.isPrintEdition() &&
                isElectronicEdition() == that.isElectronicEdition() &&
                isOfficialMassMedia() == that.isOfficialMassMedia() &&
                getName().equals(that.getName());
    }

    /**
     * @return get hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCoast(), isPrintEdition(), isElectronicEdition(), isOfficialMassMedia());
    }

    @Override
    public String toString() {
        return "Periodical{" +
                "name='" + name + '\'' +
                ", coast=" + coast +
                ", printEdition=" + printEdition +
                ", electronicEdition=" + electronicEdition +
                ", officialMassMedia=" + officialMassMedia +
                '}';
    }
}

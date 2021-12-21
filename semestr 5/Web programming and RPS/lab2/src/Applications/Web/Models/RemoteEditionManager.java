package Applications.Web.Models;

import Core.Entities.Magazine;
import Core.Entities.Newspaper;
import Core.Entities.Periodical;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Remote interface for Edition Manager
 */
public interface RemoteEditionManager extends Remote {
    /**
     * Get array of magazines
     * @return array of magazines
     */
    Magazine[] getMagazines() throws RemoteException;

    /**
     * Get array of Newspapers
     * @return array of Newspapers
     */
    Newspaper[] getNewspapers() throws RemoteException;

    /**
     * Add new periodical edition
     * @param name name of edition
     * @param coast coast of edition
     * @param printEdition is print edition?
     * @param electronicEdition id electronic edition?
     * @param officialMassMedia is official mass media?
     * @param language language of edition
     * @param audience audience of edition
     */
    void addPeriodical(String name, int coast, boolean printEdition, boolean electronicEdition, boolean officialMassMedia, String language, String audience) throws RemoteException;

    /**
     * Remove magazine
     * @param index index of collection
     */
    void removeMagazine(int index)  throws RemoteException;

    /**
     * Remove newspaper
     * @param index index of collection
     */
    void removeNewspaper(int index)  throws RemoteException;

    /**
     * Calculate coast periodicals
     * @param editions Array of periodicals
     * @return return coast periodicals
     */
    double calculateCost(Periodical[] editions) throws RemoteException;

    /**
     * Calculate coast all periodicals
     * @param editions Arrays of editions
     * @return return coast all periodicals
     */
    double calculateCostAllEditions(Periodical[]... editions) throws RemoteException;

    /**
     * Sort by coast
     * @param editions Array of periodicals (newspapers, magazines)
     */
    void sortByCoast(Periodical[] editions) throws RemoteException;

    /**
     * Sort by language
     * @param editions Array of newspapers
     */
    void sortByLanguage(Newspaper[] editions) throws RemoteException;

    /**
     * Sort by print editions
     * @param editions Array of periodicals (newspapers, magazines)
     */
    void sortByPrintEdition(Periodical[] editions) throws RemoteException;

    /**
     * Sort by electronic editions
     * @param editions Array of periodicals (newspapers, magazines)
     */
    void sortByElectronicEdition(Periodical[] editions) throws RemoteException;

    /**
     * Sort by audience
     * @param editions Array of magazines
     */
    void sortByAudience(Magazine[] editions) throws RemoteException;

    /**
     * Search by coast
     * @param minCoast min coast
     * @param maxCoast max coast
     * @param editions Array of periodicals (newspapers, magazines)
     * @return collection periodicals;
     */
    List<Periodical> searchByCoast(double minCoast, double maxCoast, Periodical[] editions) throws RemoteException;

    /**
     * Search by the contained name
     * @param name searched string
     * @param editions Array of periodicals (newspapers, magazines)
     * @return collection periodicals;
     */
    List<Periodical> searchByContainName(String name, Periodical[] editions) throws RemoteException;

    /**
     * Search all electronic edition
     * @param editions Array of periodicals (newspapers, magazines)
     * @return collection periodicals;
     */
    List<Periodical> searchElectronicEdition(Periodical[] editions) throws RemoteException;

    /**
     * Search all print edition
     * @param editions Array of periodicals (newspapers, magazines)
     * @return collection periodicals;
     */
    List<Periodical> searchPrintEdition(Periodical[] editions) throws RemoteException;
}

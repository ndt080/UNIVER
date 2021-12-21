package Applications.Web.Models;

import Core.Entities.Magazine;
import Core.Entities.Newspaper;
import Core.Entities.Periodical;
import Core.Services.CalcCostService;
import Core.Services.SearchService;
import Core.Services.SortService;

import java.util.ArrayList;
import java.util.List;

/**
 * Class of Edition Manager
 */
public class EditionManager implements RemoteEditionManager {
    /**
     * List of Magazine
     */
    ArrayList<Magazine> magazines = new ArrayList<>();
    /**
     * List of Newspaper
     */
    ArrayList<Newspaper> newspapers = new ArrayList<>();

    /**
     * Constructor of Edition Manager
     * @param magazines List of Magazines
     * @param newspapers List of Newspaper
     */
    public EditionManager(ArrayList<Magazine> magazines, ArrayList<Newspaper> newspapers) {
        this.magazines = magazines;
        this.newspapers = newspapers;
    }

    /**
     * Get array of magazines
     * @return array of magazines
     */
    @Override
    public Magazine[] getMagazines() {
        return this.magazines.toArray(new Magazine[0]);
    }

    /**
     * Get array of Newspapers
     * @return array of Newspapers
     */
    @Override
    public Newspaper[] getNewspapers() {
        return this.newspapers.toArray(new Newspaper[0]);
    }

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
    @Override
    public synchronized void addPeriodical(String name, int coast, boolean printEdition, boolean electronicEdition, boolean officialMassMedia, String language, String audience) {
        if (officialMassMedia) {
            this.newspapers.add(new Newspaper(name, coast, printEdition, electronicEdition, language));
        } else {
            this.magazines.add(new Magazine(name, coast, printEdition, electronicEdition, audience));
        }
    }

    /**
     * Remove magazine
     * @param index index of collection
     */
    @Override
    public synchronized void removeMagazine(int index) {
        this.magazines.remove(index);
    }

    /**
     * Remove newspaper
     * @param index index of collection
     */
    @Override
    public synchronized void removeNewspaper(int index) {
        this.newspapers.remove(index);
    }

    /**
     * Calculate coast periodicals
     * @param editions Array of periodicals
     * @return return coast periodicals
     */
    @Override
    public double calculateCost(Periodical[] editions) {
        return CalcCostService.calculateCost(editions);
    }

    /**
     * Calculate coast all periodicals
     * @param editions Arrays of editions
     * @return return coast all periodicals
     */
    @Override
    public double calculateCostAllEditions(Periodical[]... editions) {
        return CalcCostService.calculateCostAllEditions(editions);
    }

    /**
     * Sort by coast
     * @param editions Array of periodicals (newspapers, magazines)
     */
    @Override
    public void sortByCoast(Periodical[] editions) {
        SortService.sortByCoast(editions);
    }

    /**
     * Sort by language
     * @param editions Array of newspapers
     */
    @Override
    public void sortByLanguage(Newspaper[] editions) {
        SortService.sortByLanguage(editions);
    }

    /**
     * Sort by print editions
     * @param editions Array of periodicals (newspapers, magazines)
     */
    @Override
    public void sortByPrintEdition(Periodical[] editions) {
        SortService.sortByPrintEdition(editions);
    }

    /**
     * Sort by electronic editions
     * @param editions Array of periodicals (newspapers, magazines)
     */
    @Override
    public void sortByElectronicEdition(Periodical[] editions) {
        SortService.sortByElectronicEdition(editions);
    }

    /**
     * Sort by audience
     * @param editions Array of magazines
     */
    @Override
    public void sortByAudience(Magazine[] editions) {
        SortService.sortByAudience(editions);
    }

    /**
     * Search by coast
     * @param minCoast min coast
     * @param maxCoast max coast
     * @param editions Array of periodicals (newspapers, magazines)
     * @return collection periodicals;
     */
    @Override
    public List<Periodical> searchByCoast(double minCoast, double maxCoast, Periodical[] editions) {
        return SearchService.searchByCoast(minCoast, maxCoast, editions);
    }

    /**
     * Search by the contained name
     * @param name searched string
     * @param editions Array of periodicals (newspapers, magazines)
     * @return collection periodicals;
     */
    @Override
    public List<Periodical> searchByContainName(String name, Periodical[] editions) {
        return SearchService.searchByContainName(name, editions);
    }

    /**
     * Search all electronic edition
     * @param editions Array of periodicals (newspapers, magazines)
     * @return collection periodicals;
     */
    @Override
    public List<Periodical> searchElectronicEdition(Periodical[] editions) {
        return SearchService.searchElectronicEdition(editions);
    }

    /**
     * Search all print edition
     * @param editions Array of periodicals (newspapers, magazines)
     * @return collection periodicals;
     */
    @Override
    public List<Periodical> searchPrintEdition(Periodical[] editions) {
        return SearchService.searchPrintEdition(editions);
    }

}

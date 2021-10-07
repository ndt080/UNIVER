package Core.Services;

import Core.Entities.Periodical;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SearchService {
    /**
     * Search by coast
     * @param minCoast min coast
     * @param maxCoast max coast
     * @param editions Array of periodicals (newspapers, magazines)
     * @return collection periodicals;
     */
    public static List<Periodical> searchByCoast(double minCoast, double maxCoast, Periodical[] editions) {
        List<Periodical> result = new ArrayList<Periodical>();
        for (Periodical elem : editions) {
            if (elem.getCoast() > minCoast && elem.getCoast() < maxCoast) {
                result.add(elem);
            }
        }
        return result;
    }
    /**
     * Search by the contained name
     * @param name searched string
     * @param editions Array of periodicals (newspapers, magazines)
     * @return collection periodicals;
     */
    public static List<Periodical> searchByContainName(String name, Periodical[] editions) {
        List<Periodical> result = new ArrayList<Periodical>();
        for (Periodical elem : editions) {
            if (elem.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(elem);
            }
        }
        return result;
    }

    /**
     * Search all electronic edition
     * @param editions Array of periodicals (newspapers, magazines)
     * @return collection periodicals;
     */
    public static List<Periodical> searchElectronicEdition(Periodical[] editions) {
        List<Periodical> result = new ArrayList<Periodical>();
        for (Periodical elem : editions) {
            if (elem.isElectronicEdition()){
                result.add(elem);
            }
        }
        return result;
    }

    /**
     * Search all print edition
     * @param editions Array of periodicals (newspapers, magazines)
     * @return collection periodicals;
     */
    public static List<Periodical> searchPrintEdition(Periodical[] editions) {
        List<Periodical> result = new ArrayList<Periodical>();
        for (Periodical elem : editions) {
            if (elem.isPrintEdition()){
                result.add(elem);
            }
        }
        return result;
    }

}

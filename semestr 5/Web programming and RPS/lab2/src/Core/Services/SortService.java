package Core.Services;

import Core.Entities.Magazine;
import Core.Entities.Newspaper;
import Core.Entities.Periodical;

import java.util.Arrays;
import java.util.Comparator;

public class SortService {
    /**
     * Sort by coast
     * @param editions Array of periodicals (newspapers, magazines)
     */
    public static void sortByCoast(Periodical[] editions) {
        Arrays.sort(editions, new Comparator<Periodical>() {
            public int compare(Periodical o1, Periodical o2) {
                return Double.compare(o1.getCoast(), o2.getCoast());
            }
        });
    }

    /**
     * Sort by language
     * @param editions Array of newspapers
     */
    public static void sortByLanguage(Newspaper[] editions) {
        Arrays.sort(editions, new Comparator<Newspaper>() {
            public int compare(Newspaper obj1, Newspaper obj2) {
                return Comparator.comparing(Newspaper::getLanguage)
                        .compare(obj1, obj2);
            }
        });
    }

    /**
     * Sort by print editions
     * @param editions Array of periodicals (newspapers, magazines)
     */
    public static void sortByPrintEdition(Periodical[] editions) {
        Arrays.sort(editions, new Comparator<Periodical>() {
            public int compare(Periodical o1, Periodical o2) {
                return Boolean.compare(o1.isPrintEdition(), o2.isPrintEdition());
            }
        });
    }

    /**
     * Sort by electronic editions
     * @param editions Array of periodicals (newspapers, magazines)
     */
    public static void sortByElectronicEdition(Periodical[] editions) {
        Arrays.sort(editions, new Comparator<Periodical>() {
            public int compare(Periodical o1, Periodical o2) {
                return Boolean.compare(o1.isElectronicEdition(), o2.isElectronicEdition());
            }
        });
    }

    /**
     * Sort by audience
     * @param editions Array of magazines
     */
    public static void sortByAudience(Magazine[] editions) {
        Arrays.sort(editions, new Comparator<Magazine>() {
            public int compare(Magazine obj1, Magazine obj2) {
                return Comparator.comparing(Magazine::getAudience)
                        .compare(obj1, obj2);
            }
        });
    }
}

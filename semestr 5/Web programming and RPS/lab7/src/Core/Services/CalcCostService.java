package Core.Services;
import Core.Entities.Magazine;
import Core.Entities.Newspaper;
import Core.Entities.Periodical;

public class CalcCostService {
    /**
     * Calculate coast periodicals
     * @param editions Array of periodicals
     * @return return coast periodicals
     */
    public static double calculateCost(Periodical[] editions) {
        int sum = 0;
        for (Periodical elem : editions) {
            sum += elem.getCoast();
        }
        return sum;
    }

    /**
     * Calculate coast all periodicals
     * @param editions Arrays of editions
     * @return return coast all periodicals
     */
    public static double calculateCostAllEditions(Periodical[] ...editions) {
        double sum = 0;
        for (Periodical[] periodicals : editions) {
            sum += calculateCost(periodicals);
        }
        return sum;
    }
}

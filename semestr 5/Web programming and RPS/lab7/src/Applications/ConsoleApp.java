package Applications;

import Core.Entities.Magazine;
import Core.Entities.Newspaper;
import Core.Entities.Periodical;
import Core.Services.CalcCostService;
import Core.Services.SearchService;
import Core.Services.SortService;

import java.util.Arrays;
import java.util.List;

public class ConsoleApp {
    static Magazine[] magazines = new Magazine[]{
            new Magazine("CookBook", 13.4, true, true, "22-66"),
            new Magazine("Cars", 22.3, true, false, "22-66"),
            new Magazine("Tools", 11.4, true, false, "22-66"),
            new Magazine("Learning JS", 19, false, true, "18-36"),
            new Magazine("JAVA today", 20.5, false, true, "35-60"),
    };
    static Newspaper[] newspapers = new Newspaper[]{
            new Newspaper("Комсомольская правда", 13.4, true, false, "Ru-ru"),
            new Newspaper("The Daily Telegraph", 22.3, true, false, "En-en"),
            new Newspaper("Вечерний Минск", 11.4, true, true, "Ru-ru"),
            new Newspaper("Milliyet", 10, false, false, "En-en"),
            new Newspaper("Financial Times", 40.5, true, true, "En-en"),
    };

    public static void main(String[] args) {
        System.out.println("Coast all magazines:" + CalcCostService.calculateCost(magazines));
        System.out.println("Coast all newspapers:" + CalcCostService.calculateCost(newspapers));
        System.out.println("Coast all periodical:" + CalcCostService.calculateCostAllEditions(magazines, newspapers));

        System.out.println("--------------------------------------------------");

        System.out.println("Array magazines before sorting: " + Arrays.toString(magazines));
        SortService.sortByCoast(magazines);
        System.out.println("Array magazines after sorting by coast: " + Arrays.toString(magazines));

        System.out.println("--------------------------------------------------");

        System.out.println("Array newspapers before sorting: " + Arrays.toString(newspapers));
        SortService.sortByElectronicEdition(newspapers);
        System.out.println("Array newspapers after sorting by electronic edition: " + Arrays.toString(newspapers));

        System.out.println("--------------------------------------------------");

        List<Periodical> res = SearchService.searchByCoast(12, 20, magazines);
        System.out.println("Search for magazines priced from 12 to 20: " + res.toString());
    }
}

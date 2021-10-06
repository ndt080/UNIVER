package Factory;

import Entities.Magazine;
import Entities.Newspaper;
import Entities.Periodical;

/**
 * Class periodical publication factory
 */
public class PeriodicalFactory {
    public Periodical getEdition(String name, int coast, boolean printEdition,
                                 boolean electronicEdition, boolean officialMassMedia, String language, String audience){
        if (officialMassMedia) {
            return new Newspaper(name, coast, printEdition, electronicEdition, true, language);
        } else {
            return new Magazine(name, coast, printEdition, electronicEdition, false, audience);
        }
    }
}
  
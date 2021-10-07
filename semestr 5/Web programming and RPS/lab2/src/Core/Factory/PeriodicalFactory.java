package Core.Factory;

import Core.Entities.Magazine;
import Core.Entities.Newspaper;
import Core.Entities.Periodical;

/**
 * Class periodical publication factory
 */
public class PeriodicalFactory {
    public Periodical getEdition(String name, int coast, boolean printEdition,
                                 boolean electronicEdition, boolean officialMassMedia, String language, String audience){
        if (officialMassMedia) {
            return new Newspaper(name, coast, printEdition, electronicEdition, language);
        } else {
            return new Magazine(name, coast, printEdition, electronicEdition, audience);
        }
    }
}
  
package core.services;

import core.models.config.Config;
import core.models.exceptions.UnresolvedLocaleException;
import core.models.locale.Localization;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class Utils {

    /**
     * Initialize application locale
     */
    public static void initLocale(Logger Logger){
        ResourceBundle resources;
        Localization appLocale = Localization.valueOf(Config.getInstance().locale.toUpperCase());

        try {
            if (!Arrays.asList(Localization.values()).contains(appLocale)) {
                Logger.error("Error occurs on parserLocale setting.");
                throw new UnresolvedLocaleException(appLocale.toString() + " Isn't supported");
            }

            Locale locale = new Locale(appLocale.toString(), appLocale.toString());
            resources = ResourceBundle.getBundle("property/locale", locale);
            MessageService.setLocale(locale);

            Logger.info("Locale set to" + " " + resources.getLocale().toString());
        } catch (UnresolvedLocaleException e) {
            e.printStackTrace();
            Logger.error(e);
        }
    }
}

package core.services;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageService {
    private static ResourceBundle instance;

    public static void setLocale(Locale locale){
        instance = ResourceBundle.getBundle("property/locale", locale);
    }

    public static ResourceBundle getInstance() {
        if (instance == null) {
            instance = fromDefaults();
        }
        return instance;
    }

    private static ResourceBundle fromDefaults(){
        return ResourceBundle.getBundle("property/locale", new Locale("EN", "EN"));
    }

}

import models.common.CallCenter;
import models.common.Client;
import models.common.Operator;
import models.config.Config;
import models.exceptions.UnresolvedLocaleException;
import models.locale.Localization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.MessageService;

import java.util.*;

/*
17.	CallCenter. В организации работает несколько операторов.
Оператор может обслуживать только одного клиента, остальные должны ждать в очереди.
Клиент, стоящий  в очереди,  может положить трубку и перезвонить еще раз через некоторое время.
 */
public class Main {
    private static final Logger Logger = LogManager.getLogger(Main.class);
    public static ResourceBundle resources;

    public static void main(String[] args) {
        Config.load("src/main/resources/config.json");
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

        ArrayList<Operator> operators = new ArrayList<Operator>();
        for (int i = 0; i < Config.getInstance().number_operators; i++) {
            operators.add(new Operator(i));
        }

        CallCenter callCenter = new CallCenter(operators);
        for (int i = 0; i < Config.getInstance().number_clients; i++) {
            Thread thread = new Thread(new Client(callCenter, i));
            thread.start();
        }
    }
}

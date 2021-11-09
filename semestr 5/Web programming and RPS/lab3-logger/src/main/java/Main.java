import core.consts.Locales;
import core.models.Sentence;
import core.models.exception.FileException;
import core.models.exception.ParsingException;
import core.models.exception.UnresolvedLocaleException;
import core.services.FindWordsService;
import core.services.Parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger Logger = LogManager.getLogger(Main.class);
    public static ResourceBundle messages;

    public static void main(String[] args) {
        Parser parser = new Parser();
        ArrayList<Sentence> text = null;
        Locales localeProp = Locales.EN;

        try {
            if (!Arrays.asList(Locales.values()).contains(localeProp)) {
                Logger.error("Error occurs on parserLocale setting.");
                throw new UnresolvedLocaleException(localeProp + " Isn't supported");
            }

            Locale locale = new Locale(localeProp.toString(), localeProp.toString());

            messages = ResourceBundle.getBundle("property/locale", locale);
            Logger.info("Locale set to" + "   " + messages.getLocale().toString());

            Logger.info(messages.getString("startProgram"));
            text = parser.textToSentencesWithLocale(new File("src/main/resources/data.txt"), Locales.US);
        } catch (ParsingException | FileException | UnresolvedLocaleException e) {
            Logger.error(e.getMessage());
            e.printStackTrace();
        }

        Logger.info(messages.getString("Text") + "\t" + text);
        Logger.info(messages.getString("notInSentences") + "\t" + FindWordsService.notInSentences(text).getText());
        Logger.info(messages.getString("lengthWordOfSentence") + "\t" + FindWordsService.lengthWordOfSentence(text, 5));

        Logger.info(messages.getString("endProgram"));
    }
}
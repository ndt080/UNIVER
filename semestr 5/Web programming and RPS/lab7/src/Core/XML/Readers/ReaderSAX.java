package Core.XML.Readers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import Core.Entities.Magazine;
import Core.Entities.Newspaper;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 * Class ReaderSAX
 */
public class ReaderSAX {
    private static final Logger LOGGER = LogManager.getLogger(ReaderSAX.class);

    /**
     * The method show work of SAX parser with our handler
     * @param pathXml - path to XML
     */
    public static void xmlReaderSAX(String pathXml) {
        BasicConfigurator.configure();
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            PeriodicalHandler handler = new PeriodicalHandler();
            saxParser.parse(new File(pathXml), handler);

            List<Magazine> magazines = handler.getMagazines();
            List<Newspaper> newspapers = handler.getNewspapers();

            LOGGER.info("Magazines in the XML file by SAX: ");
            for (Magazine m : magazines) { System.out.println("\t" + m.toString()); }

            LOGGER.info("Newspapers in the XML file by SAX: ");
            for (Newspaper n : newspapers) { System.out.println("\t" + n.toString()); }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
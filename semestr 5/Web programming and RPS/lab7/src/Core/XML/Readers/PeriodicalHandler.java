package Core.XML.Readers;


import Core.Entities.Magazine;
import Core.Entities.Newspaper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Class MyHandler
 */
public class PeriodicalHandler extends DefaultHandler {
    private static final Logger LOGGER = LogManager.getLogger(PeriodicalHandler.class);

    private final List<Magazine> magazines = new ArrayList<>();
    private final List<Newspaper> newspapers = new ArrayList<>();

    private Magazine magazine;
    private Newspaper newspaper;
    private StringBuilder elementValue;

    /**
     * The method returns list of Magazines objects
     * @return List of Magazines objects
     */
    public List<Magazine> getMagazines() {
        return this.magazines;
    }

    /**
     * The method returns list of Newspapers objects
     * @return List of Newspapers objects
     */
    public List<Newspaper> getNewspapers() { return this.newspapers; }

    /**
     * Overriding the method startElement().
     * We are overriding this method to set boolean variables that will be used to identify the element.
     * @param uri - the namespace URI
     * @param localName - the local name
     * @param qName - the qualified name
     * @param attributes - the attributes attached to the element
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        LOGGER.debug("Take start element.(SAX)");

        switch (qName) {
            case "Magazine" -> magazine = new Magazine("", 0, false, false, "");
            case "Newspaper" -> newspaper = new Newspaper("", 0, false, false, "");
            default -> elementValue = new StringBuilder();
        }
    }

    /**
     * Overriding the method endElement().
     * @param uri - the namespace URI
     * @param localName - the local name
     * @param qName - the qualified name
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(magazine != null) {
            switch (qName) {
                case "name" -> magazine.setName(elementValue.toString());
                case "coast" -> magazine.setCoast(Double.parseDouble(elementValue.toString()));
                case "printEdition" -> magazine.setPrintEdition(Boolean.parseBoolean(elementValue.toString()));
                case "electronicEdition" -> magazine.setElectronicEdition(Boolean.parseBoolean(elementValue.toString()));
                case "audience" -> magazine.setAudience(elementValue.toString());
            }
        }

        if(newspaper != null) {
            switch (qName) {
                case "name" -> newspaper.setName(elementValue.toString());
                case "coast" -> newspaper.setCoast(Double.parseDouble(elementValue.toString()));
                case "printEdition" -> newspaper.setPrintEdition(Boolean.parseBoolean(elementValue.toString()));
                case "electronicEdition" -> newspaper.setElectronicEdition(Boolean.parseBoolean(elementValue.toString()));
                case "language" -> newspaper.setLanguage(elementValue.toString());
            }
        }

        if (qName.equalsIgnoreCase("Magazine")) {
            LOGGER.debug("End element is reached, adding TariffBonus object to list.(SAX)");
            magazines.add(magazine);
            magazine = null;
        }
        if (qName.equalsIgnoreCase("Newspaper")) {
            LOGGER.debug("End element is reached, adding TariffBonus object to list.(SAX)");
            newspapers.add(newspaper);
            newspaper = null;
        }
    }

    /**
     * Overriding the method characters().
     * We are using boolean fields to set the value to correct field in periodical object.
     * @param ch - the characters
     * @param start - the start position in the character array
     * @param length -  the number of characters to use from the character array
     * @throws SAXException
     */
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }
}

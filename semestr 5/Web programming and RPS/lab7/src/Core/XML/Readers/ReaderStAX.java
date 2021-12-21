package Core.XML.Readers;


import Core.Entities.Magazine;
import Core.Entities.Newspaper;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * Class ReaderStAX
 */
public class ReaderStAX {
    private static final Logger LOGGER = LogManager.getLogger(ReaderStAX.class);

    /**
     * The method show work of StAX parser
     * @param pathXml- path to XML
     */
    public static void xmlReaderStAX(String pathXml) {
        BasicConfigurator.configure();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        List<Magazine> magazines = new ArrayList<>();
        List<Newspaper> newspapers = new ArrayList<>();
        Magazine magazine = null;
        Newspaper newspaper = null;

        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(pathXml));

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();

                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();

                    String localPart = startElement.getName().getLocalPart();
                    xmlEvent = xmlEventReader.nextEvent();
                    String value = xmlEvent.asCharacters().getData();

                    switch (localPart) {
                        case "Magazine" -> magazine = new Magazine("", 0, false, false, "");
                        case "Newspaper" -> newspaper = new Newspaper("", 0, false, false, "");
                    }

                    if(magazine != null) {
                        switch (localPart) {
                            case "name" -> magazine.setName(value);
                            case "coast" -> magazine.setCoast(Double.parseDouble(value));
                            case "printEdition" -> magazine.setPrintEdition(Boolean.parseBoolean(value));
                            case "electronicEdition" -> magazine.setElectronicEdition(Boolean.parseBoolean(value));
                            case "audience" -> magazine.setAudience(value);
                        }
                    }

                    if(newspaper != null) {
                        switch (localPart) {
                            case "name" -> newspaper.setName(value);
                            case "coast" -> newspaper.setCoast(Double.parseDouble(value));
                            case "printEdition" -> newspaper.setPrintEdition(Boolean.parseBoolean(value));
                            case "electronicEdition" -> newspaper.setElectronicEdition(Boolean.parseBoolean(value));
                            case "language" -> newspaper.setLanguage(value);
                        }
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();

                    if (endElement.getName().getLocalPart().equals("Magazine")) {
                        magazines.add(magazine);
                        magazine = null;
                    }
                    if (endElement.getName().getLocalPart().equals("Newspaper")) {
                        newspapers.add(newspaper);
                        newspaper = null;
                    }
                }
            }
            LOGGER.info("Magazines in the XML file by StAX: ");
            for (Magazine m : magazines) { System.out.println("\t" + m); }

            LOGGER.info("Newspapers in the XML file by StAX: ");
            for (Newspaper n : newspapers) { System.out.println("\t" + n); }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
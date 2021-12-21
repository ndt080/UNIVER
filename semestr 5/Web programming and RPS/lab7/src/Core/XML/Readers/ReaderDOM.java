package Core.XML.Readers;

import Core.Entities.Magazine;
import Core.Entities.Newspaper;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Class ReaderDOM
 */
public class ReaderDOM {
    private static final Logger LOGGER = LogManager.getLogger(ReaderDOM.class);

    /**
     * The method show work of DOM parser
     * @param pathXml - path to XML
     */
    public static void xmlReaderDOM(String pathXml) {
        BasicConfigurator.configure();
        File xmlFile = new File(pathXml);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            LOGGER.debug("Root element in XML file:" + doc.getDocumentElement().getNodeName());

            NodeList nodeMagazineList = doc.getElementsByTagName("Magazine");
            List<Magazine> magazines = new ArrayList<>();

            NodeList nodeNewspaperList = doc.getElementsByTagName("Newspaper");
            List<Newspaper> newspapers = new ArrayList<>();

            LOGGER.debug("Now XML is loaded as Document in memory, convert it to Periodical(Magazine/Newspaper) object List.");

            for (int i = 0; i < nodeMagazineList.getLength(); i++) {
                magazines.add(getMagazine(nodeMagazineList.item(i)));
            }

            for (int i = 0; i < nodeNewspaperList.getLength(); i++) {
                newspapers.add(getNewspaper(nodeNewspaperList.item(i)));
            }

            LOGGER.info("Magazines in the XML file by DOM: ");
            for (Magazine m : magazines) { System.out.println("\t" + m.toString()); }

            LOGGER.info("Newspapers in the XML file by DOM: ");
            for (Newspaper n : newspapers) { System.out.println("\t" + n.toString()); }
        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * The method parse elements from XML to Magazine
     * @param node - node
     * @return Magazine object
     */
    private static Magazine getMagazine(Node node) {
        if (node.getNodeType() != Node.ELEMENT_NODE) return null;

        Element element = (Element) node;
        return new Magazine(
                getTagValue("name", element),
                Double.parseDouble(getTagValue("coast", element)),
                Boolean.parseBoolean(getTagValue("printEdition", element)),
                Boolean.parseBoolean(getTagValue("electronicEdition", element)),
                getTagValue("audience", element)
        );
    }

    /**
     * The method parse elements from XML to Newspaper
     * @param node - node
     * @return Magazine object
     */
    private static Newspaper getNewspaper(Node node) {
        if (node.getNodeType() != Node.ELEMENT_NODE) return null;

        Element element = (Element) node;
        return new Newspaper(
                getTagValue("name", element),
                Double.parseDouble(getTagValue("coast", element)),
                Boolean.parseBoolean(getTagValue("printEdition", element)),
                Boolean.parseBoolean(getTagValue("electronicEdition", element)),
                getTagValue("language", element)
        );
    }

    /**
     * Get tag
     * @param tag - tag
     * @param element - element
     * @return node value
     */
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
}
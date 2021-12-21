package Core.XML.Services;

import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 * Class Validation Service
 */
public class ValidationService {
    private static final Logger LOGGER = LogManager.getLogger(ValidationService.class);

    /**
     * The method check XML and XSD files for matching.
     *
     * @param pathXml - path to XML
     * @param pathXsd - path to XSD
     */
    public static boolean checkXMLforXSD(String pathXml, String pathXsd) throws Exception {
        BasicConfigurator.configure();
        try {
            LOGGER.debug("Opening files...");
            File xml = new File(pathXml);
            File xsd = new File(pathXsd);

            if (!xml.exists()) LOGGER.debug("XML not found " + pathXml);
            if (!xsd.exists()) LOGGER.debug("XSD not found " + pathXsd);
            if (!xml.exists() || !xsd.exists()) return false;

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(pathXsd));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(pathXml));

            LOGGER.debug("Completed! XML matches XSD");
            return true;
        } catch (SAXException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }
}
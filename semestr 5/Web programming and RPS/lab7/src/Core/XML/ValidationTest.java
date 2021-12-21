package Core.XML;

import Core.XML.Readers.ReaderDOM;
import Core.XML.Readers.ReaderSAX;
import Core.XML.Readers.ReaderStAX;
import Core.XML.Services.ValidationService;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ValidationTest {
    private static final Logger LOGGER = LogManager.getLogger(ValidationTest.class);
    final static String pathXML = "periodicals.xml";
    final static String pathXSD = "schema.xsd";

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();

        LOGGER.info("Start validation...");
        LOGGER.info("XML matches XSD : " + ValidationService.checkXMLforXSD(pathXML, pathXSD) +"\n");

        ReaderDOM.xmlReaderDOM(pathXML);
        ReaderSAX.xmlReaderSAX(pathXML);
        ReaderStAX.xmlReaderStAX(pathXML);

        LOGGER.info("Completion of validation!");
    }
}

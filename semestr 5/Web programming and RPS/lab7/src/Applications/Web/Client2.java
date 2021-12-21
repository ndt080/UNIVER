package Applications.Web;

import Applications.Web.Models.RemoteEditionManager;
import Core.Entities.Magazine;
import Core.Entities.Newspaper;
import Core.Entities.Periodical;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;

public class Client2 {
    private static final Logger logger = LogManager.getLogger(Client2.class);
    static Magazine[] magazines;
    static Newspaper[] newspapers;

    public static void main(String[] args) throws IOException, NotBoundException {
        BasicConfigurator.configure();
        Registry reg  = LocateRegistry.getRegistry(9000);
        RemoteEditionManager stub  = (RemoteEditionManager) reg.lookup("EditionManager");

        magazines = stub.getMagazines();
        logger.info("Magazines: " + Arrays.toString(magazines));

        newspapers = stub.getNewspapers();
        logger.info("Newspapers: " + Arrays.toString(newspapers));


        logger.info("Coast all magazines:" + stub.calculateCost(magazines));
        logger.info("Coast all newspapers:" + stub.calculateCost(newspapers));
        logger.info("Coast all periodical:" + stub.calculateCostAllEditions(magazines, newspapers));

        logger.info("--------------------------------------------------");

        logger.info("Array magazines before sorting: " + Arrays.toString(magazines));
        stub.sortByCoast(magazines);
        logger.info("Array magazines after sorting by coast: " + Arrays.toString(magazines));

        logger.info("--------------------------------------------------");

        logger.info("Array newspapers before sorting: " + Arrays.toString(newspapers));
        stub.sortByElectronicEdition(newspapers);
        logger.info("Array newspapers after sorting by electronic edition: " + Arrays.toString(newspapers));

        logger.info("--------------------------------------------------");

        List<Periodical> res = stub.searchByCoast(12, 20, magazines);
        logger.info("Search for magazines priced from 12 to 20: " + res.toString());
    }
}

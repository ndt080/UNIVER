package Applications.Web;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

import Applications.Web.Models.EditionManager;
import Applications.Web.Models.RemoteEditionManager;
import Core.Entities.Magazine;
import Core.Entities.Newspaper;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Server
 */
public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);
    static ArrayList<Magazine> magazines = new ArrayList<Magazine>(Arrays.asList(
            new Magazine("CookBook", 13.4, true, true, "22-66"),
            new Magazine("Cars", 22.3, true, false, "22-66"),
            new Magazine("Tools", 11.4, true, false, "22-66"),
            new Magazine("Learning JS", 19, false, true, "18-36"),
            new Magazine("JAVA today", 20.5, false, true, "35-60")
    ));
    static ArrayList<Newspaper> newspapers = new ArrayList<Newspaper>(Arrays.asList(
            new Newspaper("Комсомольская правда", 13.4, true, false, "Ru-ru"),
            new Newspaper("The Daily Telegraph", 22.3, true, false, "En-en"),
            new Newspaper("Вечерний Минск", 11.4, true, true, "Ru-ru"),
            new Newspaper("Milliyet", 10, false, false, "En-en"),
            new Newspaper("Financial Times", 40.5, true, true, "En-en")
            ));

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        EditionManager em = new EditionManager(magazines, newspapers);

        try {
            RemoteEditionManager stub = (RemoteEditionManager) UnicastRemoteObject.exportObject(em, 0);
            Registry reg = LocateRegistry.createRegistry(9000);
            reg.bind("EditionManager", stub);
            logger.info("Register manager");
        } catch(RemoteException | AlreadyBoundException e) {
            logger.error(e);
        }
    }
}

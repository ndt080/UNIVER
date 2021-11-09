package models.common;

import models.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.MessageService;

public class Client implements Runnable {
    /**
     * Logger
     */
    private static final Logger Logger = LogManager.getLogger(Client.class);

    /**
     * Waiting time
     */
    private final static int WAITING_TIME = Config.getInstance().client_waiting_time;

    /**
     * Client id
     */
    private final int id;

    /**
     * CallCenter
     */
    private final CallCenter callCenter;

    /**
     * Constructor
     * @param callCenter call center object
     * @param id client id
     */
    public Client(CallCenter callCenter, int id) {
        this.callCenter = callCenter;
        this.id = id;
    }

    /**
     * Get id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Run of runnable
     */
    @Override
    public void run() {
        Operator operator = null;
        try {
            while (operator == null) {
                Logger.info(String.format(MessageService.getInstance().getString("callCenter_call"), this.id));
                operator = callCenter.tryCall(this, WAITING_TIME);
            }
            Logger.info(String.format(MessageService.getInstance().getString("client_calling"), this.id, operator.getId()));
            operator.talk();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error(e);
        }

        if (operator != null) {
            Logger.info(String.format(MessageService.getInstance().getString("client_endCalling"), this.id, operator.getId()));
            callCenter.endCall(operator);
        }
    }
}
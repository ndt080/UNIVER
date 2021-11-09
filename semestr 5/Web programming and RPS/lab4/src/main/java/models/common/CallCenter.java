package models.common;

import models.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.MessageService;

import java.util.ArrayList;
import java.util.List;

public class CallCenter {
    /**
     * Logger
     */
    private static final Logger Logger = LogManager.getLogger(CallCenter.class);
    /**
     * Number try
     */
    private final static int NUMBER_TRY = Config.getInstance().call_center_number_try;
    /**
     * All operators
     */
    ArrayList<Operator> operators = new ArrayList<>();

    /**
     * Constructor
     * @param operators List of operators
     */
    public CallCenter(List<Operator> operators) {
        this.operators.addAll(operators);
    }

    /**
     * Try call
     * @param client client
     * @param waitingTime time
     * @return operator
     */
    public synchronized Operator tryCall(Client client, int waitingTime) {
        int numberTrying = 0;
        while (true) {
            try {
                for (Operator operator : operators) {
                    if (searchFreeOperator(operator, client)) {
                        return operator;
                    }
                }
                numberTrying++;
                if (checkNumberTry(numberTrying, waitingTime, client)) {
                    return null;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Logger.error(e);
            }
        }
    }

    /**
     * End call
     * @param operator operator
     */
    public synchronized void endCall(Operator operator) {
        operator.setTouchClient(null);
        operators.add(operator);
        notify();
    }

    /**
     * Search free operator
     * @param operator operator
     * @param client client
     * @return search result (boolean)
     */
    private boolean searchFreeOperator(Operator operator, Client client) {
        if (operator.getTouchClient() == null) {
            operator.setTouchClient(client);
            operators.remove(operator);
            return true;
        }
        return false;
    }

    /**
     * Check number of try
     * @param numberTrying number try
     * @param waitingTime waiting time
     * @param client client
     * @return bool
     * @throws InterruptedException trow
     */
    private synchronized boolean checkNumberTry(int numberTrying, int waitingTime, Client client)
            throws InterruptedException {
        if (numberTrying == NUMBER_TRY) {
            Logger.info(String.format(MessageService.getInstance().getString("callCenter_putDown"), client.getId()));
            wait(waitingTime);
            return true;
        }

        Logger.info(String.format(MessageService.getInstance().getString("callCenter_waitOperator"), client.getId()));
        wait(waitingTime);
        return false;
    }
}
package models.common;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class CallCenter {
    /**
     * All operators
     */
    BlockingQueue<Operator> operators = new LinkedBlockingDeque<>();

    /**
     * Constructor
     *
     * @param operators List of operators
     */
    public CallCenter(List<Operator> operators) {
        this.operators.addAll(operators);
    }

    /**
     * Try call
     *
     * @param client client
     * @return operator
     */
    public Operator tryCall(Client client) throws InterruptedException {
        Operator operator = operators.take();
        operator.setTouchClient(client);
        return operator;
    }

    /**
     * End call
     *
     * @param operator operator
     */
    public void endCall(Operator operator) {
        operator.setTouchClient(null);
        operators.add(operator);
    }
}
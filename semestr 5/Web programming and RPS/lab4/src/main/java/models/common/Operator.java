package models.common;

import models.config.Config;
import java.util.Random;

/**
 * Operator of CallCenter
 */
public class Operator {
    /**
     * Operator id
     */
    private final int id;

    /**
     * Touch client
     */
    private Client touchClient;

    /**
     * Constructor Operator class
     * @param id id of operator
     */
    public Operator(int id) {
        this.id = id;
    }

    /**
     * Get id of operator
     * @return id of operator
     */
    public int getId() {
        return id;
    }

    /**
     * Get touch client
     * @return touch client
     */
    public Client getTouchClient() {
        return this.touchClient;
    }

    /**
     * Set touch client
     * @param touchClient touch client
     */
    public void setTouchClient(Client touchClient) {
        this.touchClient = touchClient;
    }

    /**
     * Call between operator and touch client
     */
    public void talk() {
        try {
            int callLength = new Random().nextInt(Config.getInstance().max_call_length);
            Thread.sleep(callLength);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
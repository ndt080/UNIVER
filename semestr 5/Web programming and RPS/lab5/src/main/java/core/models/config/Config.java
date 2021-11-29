package core.models.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Configuration of run application
 */
public class Config {
    /**
     * Config instance
     */
    private static Config instance;

    /**
     * Application name
     */
    public String app_name;

    /**
     * Application localization
     */
    public String locale;

    /**
     * Server Address
     */
    public String server_address;

    /**
     * Server Port
     */
    public int server_port;

    /**
     * Client Port
     */
    public int client_port;

    public Config() {}

    /**
     * Get instance of config
     * @return instance
     */
    public static Config getInstance() {
        if (instance == null) {
            instance = fromDefaults();
        }
        return instance;
    }

    /**
     * Load config
     * @param file path to config
     */
    public static void load(File file) {
        instance = fromFile(file);

        if (instance == null) {
            instance = fromDefaults();
        }
    }

    /**
     * Load config
     * @param file path to config
     */
    public static void load(String file) {
        load(new File(file));
    }

    /**
     * Default config
     * @return default config
     */
    private static Config fromDefaults() {
        Config config = new Config();
        config.app_name = "proj";
        config.locale = "EN";
        config.server_address = "127.0.0.1";
        config.server_port = 45000;
        config.client_port = 45000;
        return config;
    }

    private static Config fromFile(File configFile) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
            return gson.fromJson(reader, Config.class);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public void toFile(String file) {
        toFile(new File(file));
    }

    public void toFile(File file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonConfig = gson.toJson(this);
        FileWriter writer;
        try {
            writer = new FileWriter(file);
            writer.write(jsonConfig);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
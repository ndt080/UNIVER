package Core.Services;

import java.io.*;
import java.nio.file.Files;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileStream {
    public static <T> void write(ObservableList<T> data, File file) {
        try {
            OutputStream fos = Files.newOutputStream(file.toPath());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (T obj : data) {
                oos.writeObject(obj);
            }
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> ObservableList<T> read(File file) {
        ObservableList<T> data = FXCollections.observableArrayList();
        try {
            InputStream in = Files.newInputStream(file.toPath());
            ObjectInputStream ois = new ObjectInputStream(in);
            try {
                while (true) {
                    data.add((T) ois.readObject());
                }
            } catch (Exception ignored) {}

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}

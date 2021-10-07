package View;

import Applications.UIApp;
import javafx.scene.control.Alert;

public class AlertView {
    public static void show(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(UIApp.APP_TITLE);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(UIApp.APP_TITLE);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(UIApp.APP_TITLE);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

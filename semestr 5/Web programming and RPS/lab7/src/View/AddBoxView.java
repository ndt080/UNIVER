package View;

import Core.Entities.Magazine;
import Core.Entities.Newspaper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static Applications.UIApp.*;

public class AddBoxView {
    public static void createAddMagazineBox(){
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setStyle("-fx-border-style: solid;"
                + "-fx-border-width: 1;"
                + "-fx-font-size: 15;"
                + "-fx-border-color: black");

        HBox nameBox = new HBox(10);
        Label nameLabel = new Label("Enter name:");
        TextField nameField = new TextField();
        HBox.setHgrow(nameField, Priority.ALWAYS);
        nameBox.getChildren().addAll(nameLabel, nameField);

        HBox coastBox = new HBox(10);
        Label coastLabel = new Label("Enter coast:");
        TextField coastField = new TextField();
        HBox.setHgrow(coastField, Priority.ALWAYS);
        coastBox.getChildren().addAll(coastLabel, coastField);

        HBox printEditionBox = new HBox(10);
        Label printEditionLabel = new Label("Print edition?");
        ComboBox<Boolean> printEditionCB = new ComboBox<Boolean>(FXCollections.observableArrayList(true, false));
        HBox.setHgrow(printEditionCB, Priority.ALWAYS);
        printEditionBox.getChildren().addAll(printEditionLabel, printEditionCB);

        HBox electronicEditionBox = new HBox(10);
        Label electronicEditionLabel = new Label("Electronic edition?");
        ComboBox<Boolean> electronicEditionCB = new ComboBox<Boolean>(FXCollections.observableArrayList(true, false));
        HBox.setHgrow(electronicEditionCB, Priority.ALWAYS);
        electronicEditionBox.getChildren().addAll(electronicEditionLabel, electronicEditionCB);

        HBox audienceBox = new HBox(10);
        Label audienceLabel = new Label("Enter audience:");
        TextField audienceField = new TextField();
        HBox.setHgrow(audienceField, Priority.ALWAYS);
        audienceBox.getChildren().addAll(audienceLabel, audienceField);

        HBox btnBox = new HBox(10);
        Button btn = new Button("Create new item");
        btn.setMaxWidth(1f * Integer.MAX_VALUE * 100);
        btnBox.getChildren().addAll(btn);

        vBox.getChildren().addAll(nameBox, coastBox, printEditionBox, electronicEditionBox,audienceBox, btnBox);
        Stage window = createWindow(vBox);

        btn.setOnAction((ActionEvent e) -> {
            try {
                Magazine obj = new Magazine(
                        nameField.getText(),
                        Double.parseDouble(coastField.getText()),
                        printEditionCB.getValue(),
                        electronicEditionCB.getValue(),
                        audienceField.getText()
                );
                obj.setOfficialMassMedia(false);

                APP_DATA_MAGAZINES.add(obj);
                APP_DATA_ALL.add(obj);
                APP_TABLE_VIEW.refresh();

                window.hide();
            } catch (Exception error) {
                AlertView.showError(error.getMessage());
            }
        });
    }

    public static void createAddNewspaperBox(){
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setStyle("-fx-border-style: solid;"
                + "-fx-border-width: 1;"
                + "-fx-font-size: 15;"
                + "-fx-border-color: black");

        HBox nameBox = new HBox(10);
        Label nameLabel = new Label("Enter name:");
        TextField nameField = new TextField();
        HBox.setHgrow(nameField, Priority.ALWAYS);
        nameBox.getChildren().addAll(nameLabel, nameField);

        HBox coastBox = new HBox(10);
        Label coastLabel = new Label("Enter coast:");
        TextField coastField = new TextField();
        HBox.setHgrow(coastField, Priority.ALWAYS);
        coastBox.getChildren().addAll(coastLabel, coastField);

        HBox printEditionBox = new HBox(10);
        Label printEditionLabel = new Label("Print edition?");
        ComboBox<Boolean> printEditionCB = new ComboBox<Boolean>(FXCollections.observableArrayList(true, false));
        HBox.setHgrow(printEditionCB, Priority.ALWAYS);
        printEditionBox.getChildren().addAll(printEditionLabel, printEditionCB);

        HBox electronicEditionBox = new HBox(10);
        Label electronicEditionLabel = new Label("Electronic edition?");
        ComboBox<Boolean> electronicEditionCB = new ComboBox<Boolean>(FXCollections.observableArrayList(true, false));
        HBox.setHgrow(electronicEditionCB, Priority.ALWAYS);
        electronicEditionBox.getChildren().addAll(electronicEditionLabel, electronicEditionCB);

        HBox languageBox = new HBox(10);
        Label languageLabel = new Label("Select language: ");
        ComboBox<String> languageCB = new ComboBox<String>(FXCollections.observableArrayList(
                "ru-Ru", "en-En", "cn-Cn", "fr-Fr"
        ));
        HBox.setHgrow(languageCB, Priority.ALWAYS);
        languageBox.getChildren().addAll(languageLabel, languageCB);

        HBox btnBox = new HBox(10);
        Button btn = new Button("Create new item");
        btn.setMaxWidth(1f * Integer.MAX_VALUE * 100);
        btnBox.getChildren().addAll(btn);

        vBox.getChildren().addAll(nameBox, coastBox, printEditionBox, electronicEditionBox, languageBox, btnBox);
        Stage window = createWindow(vBox);

        btn.setOnAction((ActionEvent e) -> {
            try {
                Newspaper obj = new Newspaper(
                        nameField.getText(),
                        Double.parseDouble(coastField.getText()),
                        printEditionCB.getValue(),
                        electronicEditionCB.getValue(),
                        languageCB.getValue()
                );
                obj.setOfficialMassMedia(true);
                APP_DATA_NEWSPAPERS.add(obj);
                APP_DATA_ALL.add(obj);
                APP_TABLE_VIEW.refresh();

                window.hide();
            } catch (Exception error) {
                AlertView.showError(error.getMessage());
            }
        });
    }

    private static Stage createWindow(VBox vBox){
        Scene secondScene = new Scene(vBox, 450, 280);
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Add new item");
        newWindow.setScene(secondScene);

        newWindow.show();
        return newWindow;
    }
}

package View;

import Applications.UIApp;
import Core.Entities.Magazine;
import Core.Entities.Newspaper;
import Core.Entities.Periodical;
import Core.Services.FileStream;
import Core.Services.SearchService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.util.List;

import static Applications.UIApp.*;

public class MenuBarView {
    public static void create(MenuBar menuBar, Stage primaryStage) {
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");
        Menu searchMenu = new Menu("Search");

        MenuItem exitItem = new MenuItem("Exit");
        MenuItem saveItem = new MenuItem("Save data");
        MenuItem aboutItem = new MenuItem("About");

        MenuItem searchItem1 = new MenuItem("Search all electronic edition");
        MenuItem searchItem2 = new MenuItem("Search all print edition");

        helpMenu.getItems().addAll(aboutItem);
        searchMenu.getItems().addAll(searchItem1, searchItem2);
        fileMenu.getItems().addAll(exitItem, saveItem);
        menuBar.getMenus().addAll(fileMenu, searchMenu, helpMenu);


        aboutItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AlertView.show(UIApp.APP_TITLE, "version: 0.1\nDeveloped by Petrov A.A.");
            }
        });
        exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        saveItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        saveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FileStream.<Magazine>write(APP_DATA_MAGAZINES, APP_DATA_MAGAZINES_PATH);
                    FileStream.<Newspaper>write(APP_DATA_NEWSPAPERS, APP_DATA_NEWSPAPERS_PATH);
                    AlertView.showInfo("Success save data!");
                } catch (Exception e) {
                    AlertView.showError(e.getMessage());
                }
            }
        });

        // Search
        searchItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<Periodical> result = SearchService.searchElectronicEdition(
                        APP_DATA_ALL.toArray(new Periodical[0])
                );
                TableDataView.changeToPeriodicalTable(
                        APP_TABLE_VIEW,
                        FXCollections.observableArrayList(result)
                );
                result.clear();
            }
        });
        searchItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<Periodical> result = SearchService.searchPrintEdition(
                        APP_DATA_ALL.toArray(new Periodical[0])
                );
                TableDataView.changeToPeriodicalTable(
                        APP_TABLE_VIEW,
                        FXCollections.observableArrayList(result)
                );
                result.clear();
            }
        });
    }
}

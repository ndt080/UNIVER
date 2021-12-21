package View;

import Applications.UIApp;
import Core.Entities.Magazine;
import Core.Entities.Newspaper;
import Core.Entities.Periodical;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import static Applications.UIApp.*;

public class TableDataView {
    public static TableView createEmpty() {
        return new TableView<>();
    }

    public static void changeToPeriodicalTable(TableView table, ObservableList<Periodical> data) {
        if(table.getContextMenu() != null){
            table.getContextMenu().getItems().clear();
        }

        table.getColumns().clear();
        table.setItems(data);

        TableColumn<Periodical, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Periodical, Double> coastColumn = new TableColumn<>("Coast");
        TableColumn<Periodical, Boolean> printEditionColumn = new TableColumn<>("Print edition");
        TableColumn<Periodical, Boolean> electronicEditionColumn = new TableColumn<>("Electronic edition");
        TableColumn<Periodical, Boolean> officialMassMediaColumn = new TableColumn<>("Official mass media");

        nameColumn.setCellValueFactory(new PropertyValueFactory<Periodical, String>("Name"));
        coastColumn.setCellValueFactory(new PropertyValueFactory<Periodical, Double>("Coast"));
        printEditionColumn.setCellValueFactory(new PropertyValueFactory<Periodical, Boolean>("PrintEdition"));
        electronicEditionColumn.setCellValueFactory(new PropertyValueFactory<Periodical, Boolean>("ElectronicEdition"));
        officialMassMediaColumn.setCellValueFactory(new PropertyValueFactory<Periodical, Boolean>("OfficialMassMedia"));

        table.getColumns().addAll(nameColumn, coastColumn, printEditionColumn, electronicEditionColumn, officialMassMediaColumn);

        table.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        nameColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 40 );
        coastColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );
        officialMassMediaColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );
        printEditionColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );
        electronicEditionColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );

        cellContextMenu("PERIODICAL");
        table.refresh();
    }

    public static void changeToMagazineTable(TableView table, ObservableList<Magazine> data) {
        if(table.getContextMenu() != null){
            table.getContextMenu().getItems().clear();
        }
        table.getColumns().clear();
        table.setItems(data);

        TableColumn<Magazine, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Magazine, Double> coastColumn = new TableColumn<>("Coast");
        TableColumn<Magazine, Boolean> printEditionColumn = new TableColumn<>("Print edition");
        TableColumn<Magazine, Boolean> electronicEditionColumn = new TableColumn<>("Electronic edition");
        TableColumn<Magazine, String> audienceColumn = new TableColumn<>("Audience");

        nameColumn.setCellValueFactory(new PropertyValueFactory<Magazine, String>("Name"));
        coastColumn.setCellValueFactory(new PropertyValueFactory<Magazine, Double>("Coast"));
        printEditionColumn.setCellValueFactory(new PropertyValueFactory<Magazine, Boolean>("PrintEdition"));
        electronicEditionColumn.setCellValueFactory(new PropertyValueFactory<Magazine, Boolean>("ElectronicEdition"));
        audienceColumn.setCellValueFactory(new PropertyValueFactory<Magazine, String>("Audience"));

        table.getColumns().addAll(nameColumn, coastColumn, audienceColumn, printEditionColumn, electronicEditionColumn);

        table.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        nameColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 40 );
        coastColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );
        audienceColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );
        printEditionColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );
        electronicEditionColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );

        cellContextMenu("MAGAZINE");
        table.refresh();
    }

    public static void changeToNewspapersTable(TableView table, ObservableList<Newspaper> data) {
        if(table.getContextMenu() != null){
            table.getContextMenu().getItems().clear();
        }

        table.getColumns().clear();
        table.setItems(data);

        TableColumn<Newspaper, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Newspaper, Double> coastColumn = new TableColumn<>("Coast");
        TableColumn<Newspaper, Boolean> printEditionColumn = new TableColumn<>("Print edition");
        TableColumn<Newspaper, Boolean> electronicEditionColumn = new TableColumn<>("Electronic edition");
        TableColumn<Newspaper, String> languageColumn = new TableColumn<>("Language");

        nameColumn.setCellValueFactory(new PropertyValueFactory<Newspaper, String>("Name"));
        coastColumn.setCellValueFactory(new PropertyValueFactory<Newspaper, Double>("Coast"));
        printEditionColumn.setCellValueFactory(new PropertyValueFactory<Newspaper, Boolean>("PrintEdition"));
        electronicEditionColumn.setCellValueFactory(new PropertyValueFactory<Newspaper, Boolean>("ElectronicEdition"));
        languageColumn.setCellValueFactory(new PropertyValueFactory<Newspaper, String>("Language"));

        table.getColumns().addAll(nameColumn, coastColumn, languageColumn, printEditionColumn, electronicEditionColumn);

        table.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        nameColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 40 );
        coastColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );
        languageColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );
        printEditionColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );
        electronicEditionColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );

        cellContextMenu("NEWSPAPER");
        table.refresh();
    }


    private static void cellContextMenu(String table){
        ContextMenu cm = new ContextMenu();
        MenuItem addItem = new MenuItem("Add record");
        MenuItem removeItem = new MenuItem("Remove record");

        switch (table.toUpperCase()){
            case "MAGAZINE" -> {
                cm.getItems().addAll(addItem, removeItem);
                addItem.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        AddBoxView.createAddMagazineBox();
                    }
                });
            }
            case "NEWSPAPER" -> {
                cm.getItems().addAll(addItem, removeItem);
                addItem.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        AddBoxView.createAddNewspaperBox();
                    }
                });
            }
            case "PERIODICAL" -> {
                cm.getItems().addAll(removeItem);
            }
            default -> { }
        }

        removeItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                var item = (Periodical) APP_TABLE_VIEW.getSelectionModel().getSelectedItem();
                APP_DATA_NEWSPAPERS.remove(item);
                APP_DATA_MAGAZINES.remove(item);
                UIApp.setAppDataAll();
                APP_TABLE_VIEW.refresh();
            }
        });

        APP_TABLE_VIEW.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if(t.getButton() == MouseButton.SECONDARY) {
                    cm.show(APP_TABLE_VIEW, t.getScreenX(), t.getScreenY());
                }
            }
        });
        APP_TABLE_VIEW.setContextMenu(cm);
        APP_TABLE_VIEW.getContextMenu().setAutoHide(true);
    }

}

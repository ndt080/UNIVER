package View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Objects;

import static Applications.UIApp.*;

public class TreeBarView {
    private static TreeView<String> treeView;

    public static TreeView<String> create() {
        TreeItem<String> rootTreeNode = new TreeItem<String>("Periodical");
        TreeItem<String> magazines = new TreeItem<String>("Magazines");
        TreeItem<String> newspapers = new TreeItem<String>("Newspapers");

        rootTreeNode.getChildren().add(magazines);
        rootTreeNode.getChildren().add(newspapers);

        treeView = new TreeView<String>(rootTreeNode);
        treeView.setPrefSize(150, 200);

        ContextMenu cm = createContextMenu();
        setCellFactory(treeView, cm);
        treeView.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<TreeItem<String>>() {
                    @Override
                    public void changed(ObservableValue<? extends TreeItem<String>> observable,
                                        TreeItem<String> old_val, TreeItem<String> new_val) {
                        switch (new_val.getValue()) {
                            case "Periodical" -> {
                                TableDataView.changeToPeriodicalTable(APP_TABLE_VIEW, APP_DATA_ALL);
                            }
                            case "Magazines" -> {
                                TableDataView.changeToMagazineTable(APP_TABLE_VIEW, APP_DATA_MAGAZINES);
                            }
                            case "Newspapers" -> {
                                TableDataView.changeToNewspapersTable(APP_TABLE_VIEW, APP_DATA_NEWSPAPERS);
                            }
                            default -> {
                            }
                        }
                    }
                });
        return treeView;
    }


    private static ContextMenu createContextMenu() {
        ContextMenu cm = new ContextMenu();
        MenuItem addItem = new MenuItem("Add new item");
        addItem.setOnAction(event -> {
            TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItems().get(0);
            switch (selectedItem.getValue()) {
                case "Magazines" -> {
                    AddBoxView.createAddMagazineBox();
                }
                case "Newspapers" -> {
                    AddBoxView.createAddNewspaperBox();
                }
            }
        });
        cm.getItems().add(addItem);
        return cm;
    }

    private static void setCellFactory(TreeView<String> treeView, ContextMenu contextMenu) {
        treeView.setCellFactory(tc -> {
            TreeCell<String> cell = new TreeCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                }
            };

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    if (!Objects.equals(cell.getTreeItem().getValue(), "Periodical")) {
                        cell.setContextMenu(contextMenu);
                    }
                }
            });
            return cell;
        });
    }
}


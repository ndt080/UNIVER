package Applications;

import Core.Entities.Magazine;
import Core.Entities.Newspaper;
import Core.Entities.Periodical;
import Core.Services.FileStream;
import View.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class UIApp extends Application {
    public static final String APP_TITLE = "Periodical editions manager";
    public static final int APP_MIN_WIDTH = 900;
    public static final int APP_MIN_HEIGHT = 450;

    public static final File APP_DATA_MAGAZINES_PATH = new File("src/Data/magazines.txt");
    public static final File APP_DATA_NEWSPAPERS_PATH = new File("src/Data/newspapers.txt");
    public static ObservableList<Periodical> APP_DATA_ALL;
    public static ObservableList<Magazine> APP_DATA_MAGAZINES;
    public static ObservableList<Newspaper> APP_DATA_NEWSPAPERS;

    private TreeView<String> APP_TREE_VIEW;
    public static TableView APP_TABLE_VIEW;
    private final MenuBar menuBar = new MenuBar();
    private final VBox root = new VBox();

    public static void main(String[] args) {
        getData();
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new Group());
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setMinWidth(APP_MIN_WIDTH);
        primaryStage.setMinHeight(APP_MIN_HEIGHT);
        primaryStage.setResizable(false);

        MenuBarView.create(menuBar, primaryStage);
        menuBar.setMinWidth(APP_MIN_WIDTH);

        APP_TREE_VIEW = TreeBarView.create();
        APP_TREE_VIEW.setMinHeight(APP_MIN_HEIGHT);
        APP_TREE_VIEW.setMinWidth(50);

        APP_TABLE_VIEW = TableDataView.createEmpty();
        APP_TABLE_VIEW.setMinWidth(APP_MIN_WIDTH-50);
        APP_TABLE_VIEW.setMinHeight(APP_MIN_HEIGHT-10);

        HBox hbox = new HBox();
        VBox tBox = new VBox(10);

        VBox.setVgrow(APP_TREE_VIEW, Priority.ALWAYS);
        tBox.setPadding(new Insets(10, 0, 10, 10));
        tBox.getChildren().addAll(APP_TREE_VIEW);

        HBox searchBox = SearchView.create();

        VBox allBox = new VBox(10);
        VBox.setVgrow(searchBox, Priority.ALWAYS);
        allBox.setPadding(new Insets(10, 10, 10, 10));
        allBox.getChildren().addAll(APP_TABLE_VIEW, searchBox);

        hbox.getChildren().addAll(tBox, allBox);
        root.getChildren().addAll(menuBar, hbox);
        ((Group) scene.getRoot()).getChildren().addAll(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void getData(){
        APP_DATA_MAGAZINES = FileStream.<Magazine>read(APP_DATA_MAGAZINES_PATH);
        APP_DATA_NEWSPAPERS = FileStream.<Newspaper>read(APP_DATA_NEWSPAPERS_PATH);

        setAppDataAll();
    }

    public static void setAppDataAll(){
        APP_DATA_ALL = FXCollections.observableArrayList(APP_DATA_MAGAZINES);
        APP_DATA_ALL.addAll(APP_DATA_NEWSPAPERS);
    }
}

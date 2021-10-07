package View;

import Applications.UIApp;
import Core.Entities.Periodical;
import Core.Services.SearchService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.List;

import static Applications.UIApp.*;

public class SearchView {
    public static HBox create() {
        HBox box = new HBox(10);

        TextField querySearch = new TextField();
        querySearch.setMinWidth(UIApp.APP_MIN_WIDTH - 70);
        querySearch.setPromptText("Search...");

        Button searchBtn = new Button("Search");
        box.getChildren().addAll(querySearch, searchBtn);

        searchBtn.setOnAction((ActionEvent e) -> {
            List<Periodical> result = SearchService.searchByContainName(
                    querySearch.getText(),
                    APP_DATA_ALL.toArray(new Periodical[0])
            );
            TableDataView.changeToPeriodicalTable(
                    APP_TABLE_VIEW,
                    FXCollections.observableArrayList(result)
            );
            result.clear();
        });
        return box;
    }

}

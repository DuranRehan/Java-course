package g56055.atlg.stibride.views;

import g56055.atlg.stibride.models.data.dto.FavoriteDto;
import g56055.atlg.stibride.models.data.dto.StationsDto;
import g56055.atlg.stibride.presenters.Presenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.SearchableComboBox;

import java.util.List;

public class FxController {
    @FXML
    private Label searchLabel;
    @FXML
    private Label nbStationsLabel;
    @FXML
    private Button searchButton;
    @FXML
    private SearchableComboBox<StationsDto> originStation;
    @FXML
    private SearchableComboBox<StationsDto> destinationStation;
    @FXML
    private TableView<StationsDto> pathTableView;
    @FXML
    private TableColumn<StationsDto, String> stationsCol;
    @FXML
    private TableColumn<StationsDto, String> linesCol;
    @FXML
    private MenuItem quitBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private SearchableComboBox<FavoriteDto> favoriteSearch;
    @FXML
    private Button addFavBtn;
    @FXML
    private Button removeFavBtn;
    @FXML
    private Label favLabel;
    @FXML
    private MenuItem nlBtn;
    private TextInputDialog textInput;
    private Presenter presenter;
    private ObservableList<StationsDto> allStations;

    public void initialize(List<StationsDto> list) {
        updateStationList(list);
        stationsCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        linesCol.setCellValueFactory(new PropertyValueFactory<>("lines"));
        pathTableView.getColumns().setAll(stationsCol, linesCol);

        quitBtn.setOnAction(e -> System.exit(0));
        nlBtn.setOnAction(e -> {
            var lastOrigin = originStation.getValue();
            var lastDest = destinationStation.getValue();
            presenter.update_NL();
            if (originStation.getValue() != null && destinationStation.getValue() != null) {
                presenter.search(lastOrigin, lastDest);
            }
            originStation.setValue(lastOrigin);
            destinationStation.setValue(lastDest);
        });

        searchButton.setOnAction(e -> {
            if (originStation.getValue() != null && destinationStation.getValue() != null) {
                presenter.search(originStation.getValue(), destinationStation.getValue());
            }
        });

        clearBtn.setOnAction(e -> {
            resetUi();
        });

        addFavBtn.setOnAction(e -> {
            if (originStation.getValue() != null && destinationStation.getValue() != null) {
                textInput = new TextInputDialog("");
                textInput.setResizable(true);
                textInput.setHeaderText("Entrée le nom du trajet \n(automatiquement mis à jour si le même nom est entrée)");
                textInput.setTitle("Trajet Favoris");
                textInput.setContentText("Nom : ");
                textInput.showAndWait();
                String fav_name = textInput.getResult();
                if (fav_name != null) {
                    FavoriteDto dto = new FavoriteDto(fav_name, originStation.getValue(), destinationStation.getValue());
                    presenter.addFavorite(dto);
                    favLabel.setVisible(true);
                    presenter.search(originStation.getValue(), destinationStation.getValue());
                }
            }
        });

        removeFavBtn.setOnAction(actionEvent -> {
            if (favoriteSearch.getValue() != null) {
                presenter.deleteFavorite(favoriteSearch.getValue().getKey());
            }
        });
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void updatePath(List<StationsDto> list) {
        pathTableView.getItems().clear();
        ObservableList<StationsDto> path = FXCollections.observableArrayList(list);
        for (var station : path) {
            pathTableView.getItems().add(station);
        }
        nbStationsLabel.setText("Nombre de stations : " + path.size());
    }

    public void updateLabel() {
        searchLabel.setVisible(true);
        nbStationsLabel.setVisible(true);
        removeFavBtn.setVisible(false);
    }

    public void updateDeletingLabel() {
        resetUi();
    }

    public void updateFavorite(List<FavoriteDto> list) {
        favoriteSearch.setItems(FXCollections.observableArrayList(list));
        favoriteSearch.setOnAction(e -> {
            if (favoriteSearch.getValue() != null) {
                StationsDto source = favoriteSearch.getValue().getSource();
                StationsDto dest = favoriteSearch.getValue().getDestination();
                presenter.search(source, dest);
                removeFavBtn.setVisible(true);
            }
        });
    }

    public void updateNlMode(List<StationsDto> list) {
        updateStationList(list);
    }

    private void resetUi() {
        pathTableView.getItems().clear();
        nbStationsLabel.setVisible(false);
        searchLabel.setVisible(false);
        favLabel.setVisible(false);
        removeFavBtn.setVisible(false);
    }

    private void updateStationList(List<StationsDto> list) {
        allStations = FXCollections.observableArrayList(list);

        originStation.setItems(allStations);
        destinationStation.setItems(allStations);
        originStation.getSelectionModel().selectFirst();
        destinationStation.getSelectionModel().selectLast();
    }
}

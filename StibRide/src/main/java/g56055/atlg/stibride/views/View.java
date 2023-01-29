package g56055.atlg.stibride.views;

import g56055.atlg.stibride.StibRide;
import g56055.atlg.stibride.models.data.dto.FavoriteDto;
import g56055.atlg.stibride.models.data.dto.StationsDto;
import g56055.atlg.stibride.presenters.Presenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class View {
    private final FxController controller;

    public View(Stage stage) throws IOException {
        var loader = new FXMLLoader(StibRide.class.getResource("stib-view.fxml"));
        controller = new FxController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("G56055 - Stib Ride ");
        stage.getIcons().add(
                new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/logo.png"))));
        stage.setScene(scene);
        stage.show();
    }

    public void setPresenter(Presenter presenter) {
        controller.setPresenter(presenter);
    }

    public void initialize(List<StationsDto> list) {
        controller.initialize(list);
    }

    public void updatePath(List<StationsDto> list) {
        controller.updatePath(list);
    }

    public void updateLabel() {
        controller.updateLabel();
    }

    public void updateFavorite(List<FavoriteDto> list) {
        controller.updateFavorite(list);
    }
    public void updateNlMode(List<StationsDto> list){
        controller.updateNlMode(list);
    }
    public void updateDeletingLabel() {
        controller.updateDeletingLabel();
    }
}

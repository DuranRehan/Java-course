package g56055.atlg.stibride;

import g56055.atlg.stibride.models.Model;
import g56055.atlg.stibride.presenters.Presenter;
import g56055.atlg.stibride.views.View;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class StibRide extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        View view = new View(stage);
        Model model = new Model();
        Presenter presenter = new Presenter(model, view);
        presenter.initialize();
        model.subscribe(presenter);
        view.setPresenter(presenter);
    }

    public static void main(String[] args) {
        launch();
    }
}
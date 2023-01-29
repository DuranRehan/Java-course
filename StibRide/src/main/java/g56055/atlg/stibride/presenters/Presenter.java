package g56055.atlg.stibride.presenters;

import g56055.atlg.stibride.models.Model;
import g56055.atlg.stibride.models.data.dto.FavoriteDto;
import g56055.atlg.stibride.models.data.dto.StationsDto;
import g56055.atlg.stibride.views.View;
import g56055.atlg.stibride.models.utils.observers.*;
import javafx.application.Platform;

public class Presenter implements Observer {
    private final Model model;
    private final View view;

    public Presenter(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void initialize() {
        model.initialize();
        view.initialize(model.getAllStations());
        if (!model.getAllFavorite().isEmpty()) {
            view.updateFavorite(model.getAllFavorite());
        }
    }

    public void search(StationsDto source, StationsDto dest) {
        model.search(source, dest);
    }

    public void addFavorite(FavoriteDto item) {
        model.addFavorite(item);
    }

    public void deleteFavorite(String key) {
        model.deleteFavorite(key);
    }
    public void update_NL(){
        model.updateNl();
    }
    @Override
    public void update(String toUpdate) {
        Platform.runLater(()-> {
            switch (toUpdate) {
                case "SEARCH" -> {
                    view.updatePath(model.getShortestPath());
                    view.updateLabel();
                }
                case "ADD_FAV" -> view.updateFavorite(model.getAllFavorite());
                case "DELETE_FAV" -> {
                    view.updateDeletingLabel();
                    view.updateFavorite(model.getAllFavorite());
                }
                case "NL_MODE"->{
                    view.updateNlMode(model.getAllStations());
                }
                default -> throw new IllegalStateException("Unexpected value: " + toUpdate);
            }

        });
    }
}

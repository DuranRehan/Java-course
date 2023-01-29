package g56055.sortingrace.sortingrace;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class FxControl implements Observer {
    @FXML
    Label rightStatus;
    @FXML
    Label leftStatus;
    @FXML
    Button start;
    @FXML
    ProgressBar progressBar;
    @FXML
    ChoiceBox<String> configurationChoice;
    @FXML
    ChoiceBox<String> sortChoice;
    @FXML
    Spinner<Integer> threadSpinner;
    @FXML
    LineChart<Integer, Long> chart;
    @FXML
    TableColumn<SortingData, Integer> durationCol;
    @FXML
    TableColumn<SortingData, Long> swapCol;
    @FXML
    TableColumn<SortingData, Integer> sizeCol;
    @FXML
    TableColumn<SortingData, String> nameCol;
    @FXML
    TableView<SortingData> table;
    @FXML
    MenuItem quitItem;

    private XYChart.Series<Integer, Long> merge;
    private XYChart.Series<Integer, Long> bubble;
    private int count = 0;

    public FxControl() {
    }

    public void initialize() {
        leftStatus.setText("Thread actif:" + Thread.activeCount());

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        threadSpinner.setValueFactory(valueFactory);

        ObservableList<String> toChoice = FXCollections.observableArrayList("MERGE", "BUBBLE");
        sortChoice.setItems(toChoice);

        ObservableList<String> config = FXCollections.observableArrayList("Easy: 0-100-10", "Medium: 0-10.000-1000", "Hard: 0-100.000-10.000");
        configurationChoice.setItems(config);

        nameCol.setCellValueFactory(new PropertyValueFactory<>("nameSort"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        swapCol.setCellValueFactory(new PropertyValueFactory<>("operation"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        table.getColumns().setAll(nameCol, sizeCol, swapCol, durationCol);

        bubble = new XYChart.Series<>();
        bubble.setName("Tri bulle");
        chart.getData().add(bubble);

        merge = new XYChart.Series<>();
        merge.setName("Tri fusion");
        chart.getData().add(merge);

        start.setOnAction(actionEvent -> {
            if (sortChoice.getValue() != null && configurationChoice.getValue() != null) {
                progressBar.setProgress(0);
                if (configurationChoice.getValue().hashCode() == "Easy: 0-100-10".hashCode()) {
                    launchEasySort();
                }
                if (configurationChoice.getValue().hashCode() == "Medium: 0-10.000-1000".hashCode()) {
                    launchMidSort();
                }
                if (configurationChoice.getValue().hashCode() == "Hard: 0-100.000-10.000".hashCode()) {
                    launchHardSort();
                }
            }
        });
        quitItem.setOnAction(actionEvent -> System.exit(0));
    }

    private void launchEasySort() {
        count = 0;
        for (int th = 1; th <= threadSpinner.getValue(); th++) {
            Sorting sort = new Sorting(sortChoice.getValue(), count);
            sort.addObserver(this);
            count += 10;
            sort.setArray(count);
            sort.start();
        }
    }

    private void launchMidSort() {
        count = 0;
        for (int th = 1; th <= threadSpinner.getValue(); th++) {
            Sorting sort = new Sorting(sortChoice.getValue(), count);
            sort.addObserver(this);
            count += 1000;
            sort.setArray(count);
            sort.start();
        }
    }

    private void launchHardSort() {
        count = 0;
        for (int th = 1; th <= threadSpinner.getValue(); th++) {
            Sorting sort = new Sorting(sortChoice.getValue(), count);
            sort.addObserver(this);
            count += 10000;
            sort.setArray(count);
            sort.start();
        }
    }

    @Override
    public void update(Sorting sort) {
        Platform.runLater(() -> {
            SortingData sortData = new SortingData(sort);
            table.getItems().add(sortData);
            rightStatus.setText("Dernière exécution : " + sortData.getDuration() + "ms");
            leftStatus.setText("Thread actif: " + Thread.activeCount());
            if (sortChoice.getValue().equals("MERGE")) {
                merge.getData().add(new XYChart.Data<>(sortData.getSize(), sortData.getOperation()));
            } else {
                bubble.getData().add(new XYChart.Data<>(sortData.getSize(), sortData.getOperation()));
            }
            if (configurationChoice.getValue().hashCode() == "Easy: 0-100-10".hashCode()) {
                launchSort(sort, 100, count, 10);
            } else if (configurationChoice.getValue().hashCode() == "Medium: 0-10.000-1000".hashCode()) {
                launchSort(sort, 10000, count, 1000);
            } else if (configurationChoice.getValue().hashCode() == "Hard: 0-100.000-10.000".hashCode()) {
                launchSort(sort, 100000, count, 10000);
            }
        });
    }

    private void launchSort(Sorting sort, int maxToReach, int actualCount, int toIncrement) {
        if (actualCount < maxToReach) {
            count += toIncrement;
            sort.setArray(count);
            sort.run();
            progressBar.setProgress(0.1);
        } else {
            double progress = progressBar.getProgress() + 0.1;
            progressBar.setProgress(progress);
        }
    }
}

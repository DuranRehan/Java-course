module g56055.sortingrace.sortingrace {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens g56055.sortingrace.sortingrace to javafx.fxml;
    exports g56055.sortingrace.sortingrace;
}
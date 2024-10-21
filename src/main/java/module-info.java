module at.ac.fhcampuswien.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.ac.fhcampuswien.minesweeper to javafx.fxml;
    exports at.ac.fhcampuswien.minesweeper;

}
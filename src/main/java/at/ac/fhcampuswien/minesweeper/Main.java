package at.ac.fhcampuswien.minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("minesweeper.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), Board.COLS * Board.CELL_SIZE + 50, Board.ROWS * Board.CELL_SIZE + 100);

        Controller controller = fxmlLoader.getController();
        controller.initialize();

        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

}
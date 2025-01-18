package at.ac.fhcampuswien.minesweeper;

import at.ac.fhcampuswien.minesweeper.Cell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Controller {
    private Board board;
    private boolean isActive;

    @FXML
    private Label message;
    @FXML
    private GridPane grid;
    @FXML
    private Button restart;

    @FXML
    public void initialize() {
        this.board = new Board(); // Initialisiere das Board neu
        Cell[][] cells = this.board.getCells(); // Lade die Zellen

        for (int row = 0; row < Board.ROWS; row++) {
            for (int col = 0; col < Board.COLS; col++) {
                Cell cell = cells[row][col];
                grid.add(cell, col, row);

                cell.setOnMouseClicked(event -> {
                    int r = GridPane.getRowIndex(cell);
                    int c = GridPane.getColumnIndex(cell);
                    handleCellClick(event, r, c);
                });
            }
        }

        refreshGrid();
        message.setText("Good Luck!");
        isActive = true;
    }

    private void refreshGrid() {
        for (int row = 0; row < Board.ROWS; row++) {
            for (int col = 0; col < Board.COLS; col++) {
                Cell cell = board.getCells()[row][col];
                if (cell.isUncovered()) {
                    cell.update(cell.getImageForState()); // Bild für aufgedeckte Zellen
                } else {
                    cell.update(cell.getImageForMark()); // Bild für markierte/verdeckte Zellen
                }
            }
        }
    }


    private void handleCellClick(MouseEvent event, int row, int col) {
        if (!isActive) return; // Überprüfen, ob das Spiel aktiv ist

        if (event.getButton() == MouseButton.PRIMARY) {
            if (board.uncover(row, col)) {
                if (board.isGameOver()) {
                    message.setText("Sorry. Leider verloren.");
                    isActive = false;
                }
            }
        } else if (event.getButton() == MouseButton.SECONDARY) {
            if (board.markCell(row, col)) {
                message.setText("Marker: " + board.getMinesMarked() + "/" + Board.NUM_MINES);
            }
        }

        if (board.getCellsUncovered() == (Board.ROWS * Board.COLS - Board.NUM_MINES) && board.getMinesMarked() == Board.NUM_MINES) {
            message.setText("Glückwunsch! Du hast gewonnen.");
            isActive = false;
        }

        refreshGrid();
    }


    @FXML
    public void update(MouseEvent event) {
        if (!isActive) return;

        int col = (int) event.getX() / Board.CELL_SIZE;
        int row = (int) event.getY() / Board.CELL_SIZE;

        if (event.getButton() == MouseButton.PRIMARY) {
            if (board.uncover(row, col)) {
                if (board.isGameOver()) {
                    message.setText("Sorry. Leider verloren.");
                    isActive = false;
                }
            }
        } else if (event.getButton() == MouseButton.SECONDARY) {
            if (board.markCell(row, col)) {
                message.setText("Marker: " + board.getMinesMarked() + "/" + Board.NUM_MINES);
            }
        }

        if (board.getCellsUncovered() == (Board.ROWS * Board.COLS - Board.NUM_MINES) && board.getMinesMarked() == Board.NUM_MINES) {
            message.setText("Glückwunsch! Du hast gewonnen.");
            isActive = false;
        }
    }

    @FXML
    public void restart(ActionEvent actionEvent) {
        grid.getChildren().clear();
        this.board = new Board();
        Cell[][] cells = this.board.getCells(); // Lade die Zellen

        for (int row = 0; row < Board.ROWS; row++) {
            for (int col = 0; col < Board.COLS; col++) {
                Cell cell = cells[row][col];
                grid.add(cell, col, row);

                cell.setOnMouseClicked(event -> {
                    int r = GridPane.getRowIndex(cell);
                    int c = GridPane.getColumnIndex(cell);
                    handleCellClick(event, r, c);
                });
            }
        }

        refreshGrid();
        message.setText("Good Luck!");
        isActive = true;
    }

}

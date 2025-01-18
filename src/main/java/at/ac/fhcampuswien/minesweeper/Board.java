package at.ac.fhcampuswien.minesweeper;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    public static final int CELL_SIZE = 17;
    public static final int ROWS = 25;
    public static final int COLS = 25;
    public static final int NUM_IMAGES = 13;
    public static final int NUM_MINES = 25; // Anzahl der Minen wird hier definiert

    private Cell[][] cells;
    private Image[] images;
    private int cellsUncovered;
    private int minesMarked;
    private boolean gameOver;

    public Board() {
        cells = new Cell[ROWS][COLS];
        cellsUncovered = 0;
        minesMarked = 0;
        gameOver = false;
        loadImages();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                cells[i][j] = new Cell(images[0], 0);
            }
        }

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                cells[i][j].setNeighbours(computeNeighbours(i, j));
            }
        }

        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < NUM_MINES) {
            int row = random.nextInt(ROWS);
            int col = random.nextInt(COLS);
            if (cells[row][col].getState() != -1) { // -1 = Mine
                cells[row][col].setState(-1);
                updateNeighbours(row, col);
                minesPlaced++;
            }
        }
    }

    public boolean uncover(int row, int col) {
        if (!isValidCell(row, col) || cells[row][col].isUncovered() || cells[row][col].isMarked()) {
            return false;
        }

        cells[row][col].uncover();
        cellsUncovered++;

        if (cells[row][col].getState() == -1) {
            gameOver = true;
            uncoverAllCells();
            return true;
        }

        if (cells[row][col].getState() == 0) {
            uncoverEmptyCells(cells[row][col]);
        }

        return false;
    }

    public boolean markCell(int row, int col) {
        if (isValidCell(row, col) && !cells[row][col].isUncovered()) {
            cells[row][col].toggleMark();
            minesMarked += cells[row][col].isMarked() ? 1 : -1;
            return true;
        }
        return false;
    }

    public void uncoverEmptyCells(Cell cell) {
        if (cell.getNeighbours() == null) return;

        for (Cell neighbour : cell.getNeighbours()) {
            if (!neighbour.isUncovered() && !neighbour.isMarked()) {
                neighbour.uncover();
                cellsUncovered++;
                if (neighbour.getState() == 0) {
                    uncoverEmptyCells(neighbour);
                }
            }
        }
    }

    public void uncoverAllCells() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (!cells[i][j].isUncovered()) {
                    cells[i][j].uncover();
                }
            }
        }
    }

    private void updateNeighbours(int row, int col) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (isValidCell(newRow, newCol) && cells[newRow][newCol].getState() != -1) {
                    cells[newRow][newCol].incrementState();
                }
            }
        }
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    public List<Cell> computeNeighbours(int x, int y) {
        List<Cell> neighbours = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = x + i;
                int newCol = y + j;
                if (isValidCell(newRow, newCol) && !(i == 0 && j == 0)) {
                    neighbours.add(cells[newRow][newCol]);
                }
            }
        }
        return neighbours;
    }

    private void loadImages() {
        images = new Image[NUM_IMAGES];
        for (int i = 0; i < NUM_IMAGES; i++) {
            var path = "images/" + i + ".png";

            try (InputStream is = getClass().getResourceAsStream(path)) {
                if (is == null) {
                    System.err.println("Bild fehlt: " + path);
                    continue;
                }
                this.images[i] = new Image(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getMinesMarked() {
        return minesMarked;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getCellsUncovered() {
        return cellsUncovered;
    }
}

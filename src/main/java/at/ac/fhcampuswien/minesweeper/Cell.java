package at.ac.fhcampuswien.minesweeper;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.List;

public class Cell extends Pane {
    private ImageView view;
    private List<Cell> neighbours;

    private int state; // -1 = Mine, 0 = leer, 1-8 = Anzahl benachbarter Minen
    private boolean uncovered;
    private boolean marked;

    public Cell(Image img, int state) {
        view = new ImageView(img);
        getChildren().add(view);
        this.state = state;
        this.uncovered = false;
        this.marked = false;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void incrementState() {
        if (state != -1) {
            state++;
        }
    }

    public boolean isUncovered() {
        return uncovered;
    }

    public boolean isMarked() {
        return marked;
    }

    public void uncover() {
        this.uncovered = true;
        update(getImageForState());
    }

    public void toggleMark() {
        this.marked = !marked;
        update(getImageForMark());
    }

    public Image getImageForState() {
        String path = "/at/ac/fhcampuswien/minesweeper/images/";
        if (state == -1) {
            return new Image(getClass().getResourceAsStream(path + "9.png")); // Mine
        } else {
            return new Image(getClass().getResourceAsStream(path + state + ".png")); // Zahl 0-8
        }
    }

    public Image getImageForMark() {
        String path = "/at/ac/fhcampuswien/minesweeper/images/";
        if (marked) {
            return new Image(getClass().getResourceAsStream(path + "11.png")); // Markiert
        } else {
            return new Image(getClass().getResourceAsStream(path + "10.png")); // Verdeckt
        }
    }

    public void setNeighbours(List<Cell> neighbours) {
        this.neighbours = neighbours;
    }

    public List<Cell> getNeighbours() {
        return neighbours;
    }

    public void update(Image img) {
        this.view.setImage(img);
    }
}

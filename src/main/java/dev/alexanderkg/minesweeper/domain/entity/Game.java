package dev.alexanderkg.minesweeper.domain.entity;

import dev.alexanderkg.minesweeper.domain.valueobjects.Grid;
import jakarta.persistence.*;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Transient
    private Grid grid;

    protected Game() {
        // Empty constructor for JPA.
    }

    public Game(Grid grid) {
        this.grid = grid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}

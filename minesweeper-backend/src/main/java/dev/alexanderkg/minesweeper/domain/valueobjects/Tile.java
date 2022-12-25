package dev.alexanderkg.minesweeper.domain.valueobjects;

public class Tile {

    private IsMine isMine;
    private int amountOfNeighboringMines;
    private boolean hasBeenClicked;

    public Tile(IsMine value) {
        this.isMine = value;
    }

    @Override
    public String toString() {
        return String.format("Is mine: %b", isMine.isValue());
    }
}

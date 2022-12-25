package dev.alexanderkg.minesweeper.domain.valueobjects;

public class Tile {

    private TileValue amountOfNeighboringMines;
    private Coordinate coordinate;
    private TileType tileType;
    private TileState state;

    protected Tile() {
        // Empty constructor for JPA.
    }

    public Tile(Coordinate coordinate, TileType tileType) {
        this.coordinate = coordinate;
        this.tileType = tileType;
    }

    public void setAmountOfNeighboringMines(TileValue amountOfNeighboringMines) {
        this.amountOfNeighboringMines = amountOfNeighboringMines;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public TileType getTileType() {
        return tileType;
    }

    public TileState getState() {
        return state;
    }

    @Override
    public String toString() {
        return String.format("%s: %s %s", this.tileType, this.amountOfNeighboringMines.getValue(), this.coordinate);
    }
}
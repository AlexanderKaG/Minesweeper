package dev.alexanderkg.minesweeper.domain.valueobjects;

public class Coordinate {

    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;

    }

    @Override
    public String toString() {
        return String.format("x: %d, y: %d", this.x, this.y);
    }
}

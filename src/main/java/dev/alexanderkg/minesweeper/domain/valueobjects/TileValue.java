package dev.alexanderkg.minesweeper.domain.valueobjects;

public class TileValue {

    private final int value;

    public TileValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

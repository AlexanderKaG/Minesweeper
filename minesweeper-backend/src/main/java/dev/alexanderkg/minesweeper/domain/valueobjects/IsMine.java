package dev.alexanderkg.minesweeper.domain.valueobjects;

public class IsMine {

    private final boolean value;

    public IsMine(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }
}

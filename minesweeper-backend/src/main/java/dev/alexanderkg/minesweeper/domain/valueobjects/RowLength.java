package dev.alexanderkg.minesweeper.domain.valueobjects;

public class RowLength {

    private final int value;

    public RowLength(int value) {
        final int shortestRowLength = 1;
        final int longestRowLength = 100;
        if (value < shortestRowLength || value > longestRowLength) {
            throw new IllegalArgumentException("The row length has to be at least 1, and at most 100.");
        }

        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

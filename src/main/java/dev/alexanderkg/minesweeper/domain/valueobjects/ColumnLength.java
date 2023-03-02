package dev.alexanderkg.minesweeper.domain.valueobjects;

public class ColumnLength {

    private final int value;

    public ColumnLength(int value) {
        final int shortestColumnLength = 1;
        final int longestColumnLength = 100;
        if (value < shortestColumnLength || value > longestColumnLength) {
            throw new IllegalArgumentException("The column length has to be at least 1, and at most 100.");
        }

        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

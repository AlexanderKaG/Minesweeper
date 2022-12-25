package dev.alexanderkg.minesweeper.domain.valueobjects;

public class MineAmount {

    private final int value;

    public MineAmount(int value) {
        final int smallestAmountOfMines = 1;
        final int largestAMountOfMines = 100;
        if (value < smallestAmountOfMines || value > largestAMountOfMines) {
            throw new IllegalArgumentException("You can't have less than 1 mine or more than 100 mines.");
        }

        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

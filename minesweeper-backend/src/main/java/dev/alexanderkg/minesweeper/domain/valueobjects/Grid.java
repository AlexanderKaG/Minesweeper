package dev.alexanderkg.minesweeper.domain.valueobjects;

import java.util.Random;

public class Grid {

    private final Random random = new Random();
    private final Tile[][] tiles;

    public Grid(RowLength rowLength, ColumnLength columnLength, MineAmount amountOfMines) {
        this.tiles = populate(rowLength, columnLength, amountOfMines);
    }

    private Tile[][] populate(RowLength rowLength, ColumnLength columnLength, MineAmount amountOfMines) {
        Tile[][] populatedTiles = new Tile[rowLength.getValue()][columnLength.getValue()];

        int minesLeftToPlace = amountOfMines.getValue();
        for (int i = 0; i < rowLength.getValue(); i++) {
            for (int j = 0; j < columnLength.getValue(); j++) {
                int chanceToPlaceMine = random.nextInt(100);
                if (minesLeftToPlace > 0 && chanceToPlaceMine < 25) {
                    populatedTiles[i][j] = new Tile(new IsMine(true));
                    minesLeftToPlace = minesLeftToPlace - 1;
                } else {
                    populatedTiles[i][j] = new Tile(new IsMine(false));
                }
            }
        }

        return populatedTiles;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}

package dev.alexanderkg.minesweeper.domain.valueobjects;

import java.util.Random;

public class Grid {

    private final Random random = new Random();
    private final Tile[][] tiles;

    public Grid(RowLength rowLength, ColumnLength columnLength, MineAmount amountOfMines) {
        this.tiles = populateGrid(rowLength, columnLength, amountOfMines);
    }

    private Tile[][] populateGrid(RowLength rowLength, ColumnLength columnLength, MineAmount amountOfMines) {
        Tile[][] populatedTiles = new Tile[rowLength.getValue()][columnLength.getValue()];

        int minesLeftToPlace = amountOfMines.getValue();
        while (minesLeftToPlace > 0) {
            for (int i = 0; i < rowLength.getValue(); i++) {
                for (int j = 0; j < columnLength.getValue(); j++) {
                    minesLeftToPlace = placeTiles(rowLength, columnLength, amountOfMines, populatedTiles, minesLeftToPlace, i, j);
                }

            }
        }

        calculateAllTileValues(populatedTiles, rowLength, columnLength);

        return populatedTiles;
    }

    private int placeTiles(RowLength rowLength, ColumnLength columnLength, MineAmount amountOfMines, Tile[][] populatedTiles, int minesLeftToPlace, int i, int j) {
        int chanceToPlaceMine = (rowLength.getValue() * columnLength.getValue()) / amountOfMines.getValue();
        boolean placeMine = random.nextInt(100) < chanceToPlaceMine;
        if (populatedTiles[i][j] == null) {
            if (minesLeftToPlace > 0 && placeMine) {
                populatedTiles[i][j] = new Tile(new Coordinate(j, i), TileType.MINE);
                minesLeftToPlace = minesLeftToPlace - 1;
            } else {
                populatedTiles[i][j] = new Tile(new Coordinate(j, i), TileType.VALUE);
            }
        }
        if (populatedTiles[i][j] != null && populatedTiles[i][j].getTileType() == TileType.VALUE && minesLeftToPlace > 0 && placeMine) {
            populatedTiles[i][j] = new Tile(new Coordinate(i, j), TileType.MINE);
            minesLeftToPlace = minesLeftToPlace - 1;
        }
        return minesLeftToPlace;
    }

    private void calculateAllTileValues(Tile[][] populatedTiles, RowLength rowLength, ColumnLength columnLength) {
        iterateThroughArray(populatedTiles, rowLength, columnLength);
    }

    private static void iterateThroughArray(Tile[][] populatedTiles, RowLength rowLength, ColumnLength columnLength) {
        for (int i = 0; i < rowLength.getValue(); i++) {
            for (int j = 0; j < columnLength.getValue(); j++) {
                calculateEachTile(populatedTiles, rowLength, columnLength, i, j);
            }
        }
    }

    private static void calculateEachTile(Tile[][] populatedTiles, RowLength rowLength, ColumnLength columnLength, int i, int j) {
        int amountOfNeighboringMines = 0;

        if (isTopLeftCornerTile(i, j)) {
            amountOfNeighboringMines = calculateTopLeftCornerTile(populatedTiles, i, j, amountOfNeighboringMines);
        }

        if (isTopRightCornerTile(columnLength, i, j)) {
            amountOfNeighboringMines = calculateTopRightCornerTile(populatedTiles, i, j, amountOfNeighboringMines);
        }

        if (isBottomLeftCornerTile(rowLength, i, j)) {
            amountOfNeighboringMines = calculateBottomLeftCornerTile(populatedTiles, i, j, amountOfNeighboringMines);
        }

        if (isBottomRightCornerTile(rowLength, columnLength, i, j)) {
            amountOfNeighboringMines = calculateBottomRightCornerTile(populatedTiles, i, j, amountOfNeighboringMines);
        }

        if (isTopEdgeNotCornerTile(columnLength, i, j)) {
            amountOfNeighboringMines = calculateTopEdgeNotCornerTile(populatedTiles, i, j, amountOfNeighboringMines);
        }

        if (isBottomEdgeNotCornerTile(rowLength, columnLength, i, j)) {
            amountOfNeighboringMines = calculateBottomEdgeNotCornerTile(populatedTiles, i, j, amountOfNeighboringMines);
        }

        if (isLeftEdgeNotCornerTile(rowLength, i, j)) {
            amountOfNeighboringMines = calculateLeftEdgeNotCornerTile(populatedTiles, i, j, amountOfNeighboringMines);
        }

        if (isRightEdgeNotCornerTile(rowLength, columnLength, i, j)) {
            amountOfNeighboringMines = calculateRightEdgeNotCornerTile(populatedTiles, i, j, amountOfNeighboringMines);
        }

        if (isNotEdgeTile(rowLength, columnLength, i, j)) {
            amountOfNeighboringMines = calculateNotEdgeTile(populatedTiles, i, j, amountOfNeighboringMines);
        }

        TileValue value = new TileValue(amountOfNeighboringMines);
        populatedTiles[i][j].setAmountOfNeighboringMines(value);
    }

    private static int calculateNotEdgeTile(Tile[][] populatedTiles, int i, int j, int amountOfNeighboringMines) {
        if (populatedTiles[i - 1][j - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i - 1][j].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i - 1][j + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i][j - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i][j + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i + 1][j - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i + 1][j].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i + 1][j + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private static boolean isNotEdgeTile(RowLength rowLength, ColumnLength columnLength, int i, int j) {
        return i > 0 && j > 0 && i < rowLength.getValue() - 1 && j < columnLength.getValue() - 1;
    }

    private static int calculateRightEdgeNotCornerTile(Tile[][] populatedTiles, int i, int j, int amountOfNeighboringMines) {
        if (populatedTiles[i - 1][j].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i - 1][j - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i][j - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i + 1][j - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i + 1][j].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private static boolean isRightEdgeNotCornerTile(RowLength rowLength, ColumnLength columnLength, int i, int j) {
        return j == columnLength.getValue() - 1 && i != 0 && i != rowLength.getValue() - 1;
    }

    private static int calculateLeftEdgeNotCornerTile(Tile[][] populatedTiles, int i, int j, int amountOfNeighboringMines) {
        if (populatedTiles[i - 1][j].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i - 1][j + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i][j + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i + 1][j + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i + 1][j].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private static boolean isLeftEdgeNotCornerTile(RowLength rowLength, int i, int j) {
        return j == 0 && i != 0 && i != rowLength.getValue() - 1;
    }

    private static int calculateBottomEdgeNotCornerTile(Tile[][] populatedTiles, int i, int j, int amountOfNeighboringMines) {
        if (populatedTiles[i][j - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i - 1][j - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i - 1][j].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i - 1][j + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i][j + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private static boolean isBottomEdgeNotCornerTile(RowLength rowLength, ColumnLength columnLength, int i, int j) {
        return i == rowLength.getValue() - 1 && j != 0 && j != columnLength.getValue() - 1;
    }

    private static int calculateTopEdgeNotCornerTile(Tile[][] populatedTiles, int i, int j, int amountOfNeighboringMines) {
        if (populatedTiles[i][j - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i + 1][j - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i + 1][j].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i + 1][j + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i][j + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private static boolean isTopEdgeNotCornerTile(ColumnLength columnLength, int i, int j) {
        return i == 0 && j != 0 && j != columnLength.getValue() - 1;
    }

    private static int calculateBottomRightCornerTile(Tile[][] populatedTiles, int i, int j, int amountOfNeighboringMines) {
        if (populatedTiles[i][j - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i - 1][j - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i - 1][j].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private static boolean isBottomRightCornerTile(RowLength rowLength, ColumnLength columnLength, int i, int j) {
        return i == rowLength.getValue() - 1 && j == columnLength.getValue() - 1;
    }

    private static int calculateBottomLeftCornerTile(Tile[][] populatedTiles, int i, int j, int amountOfNeighboringMines) {
        if (populatedTiles[i][j + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i - 1][j].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i - 1][j + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private static boolean isBottomLeftCornerTile(RowLength rowLength, int i, int j) {
        return i == rowLength.getValue() - 1 && j == 0;
    }

    private static int calculateTopRightCornerTile(Tile[][] populatedTiles, int i, int j, int amountOfNeighboringMines) {
        if (populatedTiles[i][j - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i + 1][j].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i + 1][j - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private static boolean isTopRightCornerTile(ColumnLength columnLength, int i, int j) {
        return i == 0 && j == columnLength.getValue() - 1;
    }

    private static int calculateTopLeftCornerTile(Tile[][] populatedTiles, int i, int j, int amountOfNeighboringMines) {
        if (populatedTiles[i][j + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i + 1][j].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (populatedTiles[i + 1][j + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private static boolean isTopLeftCornerTile(int i, int j) {
        return i == 0 && j == 0;
    }


    public Tile[][] getTiles() {
        return tiles;
    }
}

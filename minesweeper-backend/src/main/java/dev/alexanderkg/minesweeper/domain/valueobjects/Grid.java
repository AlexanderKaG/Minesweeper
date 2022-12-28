package dev.alexanderkg.minesweeper.domain.valueobjects;

import java.util.Random;

public class Grid {

    private final int rowLength;
    private final int columnLength;
    private final int amountOfMines;
    private final Tile[][] tiles;

    public Grid(RowLength rowLength, ColumnLength columnLength, MineAmount amountOfMines) {
        this.rowLength = rowLength.getValue();
        this.columnLength = columnLength.getValue();
        this.amountOfMines = amountOfMines.getValue();
        this.tiles = generateTiles();
    }

    private Tile[][] generateTiles() {

        final Tile[][] blueprint = new Tile[rowLength][columnLength];

        int minesLeftToGenerate = amountOfMines;

        while (minesLeftToGenerate > 0) {
            for (int y = 0; y < rowLength; y++) {
                for (int x = 0; x < columnLength; x++) {
                    minesLeftToGenerate = generateTile(blueprint, minesLeftToGenerate, y, x);
                }
            }
        }

        calculateTileValues(blueprint);

        return blueprint;
    }

    private int generateTile(Tile[][] blueprint, int minesLeftToGenerate, int y, int x) {
        final Random random = new Random();
        int chanceToGenerateMine = (rowLength * columnLength) / amountOfMines;
        boolean generateMine = random.nextInt(100) < chanceToGenerateMine;

        if (blueprint[y][x] == null) {
            if (minesLeftToGenerate > 0 && generateMine) {
                blueprint[y][x] = new Tile(new Coordinate(x, y), TileType.MINE, TileState.CLOSED);
                minesLeftToGenerate--;
            } else {
                blueprint[y][x] = new Tile(new Coordinate(x, y), TileType.NORMAL, TileState.CLOSED);
            }
        }

        if (blueprint[y][x] != null && blueprint[y][x].getTileType() == TileType.NORMAL && minesLeftToGenerate > 0 && generateMine) {
            blueprint[y][x].setTileType(TileType.MINE);
            minesLeftToGenerate--;
        }


        return minesLeftToGenerate;
    }

    private void calculateTileValues(Tile[][] blueprintTiles) {
        for (int y = 0; y < rowLength; y++) {
            for (int x = 0; x < columnLength; x++) {
                calculateEachTile(blueprintTiles, y, x);
            }
        }
    }

    private void calculateEachTile(Tile[][] blueprintTiles, int y, int x) {
        int amountOfNeighboringMines = 0;

        if (isTopLeftCornerTile(y, x)) {
            amountOfNeighboringMines = calculateTopLeftCornerTile(blueprintTiles, y, x, amountOfNeighboringMines);
        }

        if (isTopRightCornerTile(y, x)) {
            amountOfNeighboringMines = calculateTopRightCornerTile(blueprintTiles, y, x, amountOfNeighboringMines);
        }

        if (isBottomLeftCornerTile(y, x)) {
            amountOfNeighboringMines = calculateBottomLeftCornerTile(blueprintTiles, y, x, amountOfNeighboringMines);
        }

        if (isBottomRightCornerTile(y, x)) {
            amountOfNeighboringMines = calculateBottomRightCornerTile(blueprintTiles, y, x, amountOfNeighboringMines);
        }

        if (isTopEdgeNotCornerTile(y, x)) {
            amountOfNeighboringMines = calculateTopEdgeNotCornerTile(blueprintTiles, y, x, amountOfNeighboringMines);
        }

        if (isBottomEdgeNotCornerTile(y, x)) {
            amountOfNeighboringMines = calculateBottomEdgeNotCornerTile(blueprintTiles, y, x, amountOfNeighboringMines);
        }

        if (isLeftEdgeNotCornerTile(y, x)) {
            amountOfNeighboringMines = calculateLeftEdgeNotCornerTile(blueprintTiles, y, x, amountOfNeighboringMines);
        }

        if (isRightEdgeNotCornerTile(y, x)) {
            amountOfNeighboringMines = calculateRightEdgeNotCornerTile(blueprintTiles, y, x, amountOfNeighboringMines);
        }

        if (isNotEdgeTile(y, x)) {
            amountOfNeighboringMines = calculateNotEdgeTile(blueprintTiles, y, x, amountOfNeighboringMines);
        }

        TileValue value = new TileValue(amountOfNeighboringMines);
        blueprintTiles[y][x].setAmountOfNeighboringMines(value);
    }

    private static int calculateNotEdgeTile(Tile[][] blueprintTiles, int y, int x, int amountOfNeighboringMines) {
        if (blueprintTiles[y - 1][x - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y - 1][x].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y - 1][x + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y][x - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y][x + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y + 1][x - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y + 1][x].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y + 1][x + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private boolean isNotEdgeTile(int y, int x) {
        return y > 0 && x > 0 && y < rowLength - 1 && x < columnLength - 1;
    }

    private static int calculateRightEdgeNotCornerTile(Tile[][] blueprintTiles, int y, int x, int amountOfNeighboringMines) {
        if (blueprintTiles[y - 1][x].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y - 1][x - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y][x - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y + 1][x - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y + 1][x].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private boolean isRightEdgeNotCornerTile(int y, int x) {
        return x == columnLength - 1 && y != 0 && y != rowLength - 1;
    }

    private static int calculateLeftEdgeNotCornerTile(Tile[][] blueprintTiles, int y, int x, int amountOfNeighboringMines) {
        if (blueprintTiles[y - 1][x].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y - 1][x + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y][x + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y + 1][x + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y + 1][x].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private boolean isLeftEdgeNotCornerTile(int y, int x) {
        return x == 0 && y != 0 && y != rowLength - 1;
    }

    private static int calculateBottomEdgeNotCornerTile(Tile[][] blueprintTiles, int y, int x, int amountOfNeighboringMines) {
        if (blueprintTiles[y][x - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y - 1][x - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y - 1][x].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y - 1][x + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y][x + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private boolean isBottomEdgeNotCornerTile(int y, int x) {
        return y == rowLength - 1 && x != 0 && x != columnLength - 1;
    }

    private static int calculateTopEdgeNotCornerTile(Tile[][] blueprintTiles, int y, int x, int amountOfNeighboringMines) {
        if (blueprintTiles[y][x - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y + 1][x - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y + 1][x].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y + 1][x + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y][x + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private boolean isTopEdgeNotCornerTile(int y, int x) {
        return y == 0 && x != 0 && x != columnLength - 1;
    }

    private static int calculateBottomRightCornerTile(Tile[][] blueprintTiles, int y, int x, int amountOfNeighboringMines) {
        if (blueprintTiles[y][x - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y - 1][x - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y - 1][x].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private boolean isBottomRightCornerTile(int y, int x) {
        return y == rowLength - 1 && x == columnLength - 1;
    }

    private static int calculateBottomLeftCornerTile(Tile[][] blueprintTiles, int y, int x, int amountOfNeighboringMines) {
        if (blueprintTiles[y][x + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y - 1][x].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y - 1][x + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private boolean isBottomLeftCornerTile(int y, int x) {
        return y == rowLength - 1 && x == 0;
    }

    private static int calculateTopRightCornerTile(Tile[][] blueprintTiles, int y, int x, int amountOfNeighboringMines) {
        if (blueprintTiles[y][x - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y + 1][x].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y + 1][x - 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private boolean isTopRightCornerTile(int y, int x) {
        return y == 0 && x == columnLength - 1;
    }

    private static int calculateTopLeftCornerTile(Tile[][] blueprintTiles, int y, int x, int amountOfNeighboringMines) {
        if (blueprintTiles[y][x + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y + 1][x].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        if (blueprintTiles[y + 1][x + 1].getTileType() == TileType.MINE) {
            amountOfNeighboringMines++;
        }
        return amountOfNeighboringMines;
    }

    private static boolean isTopLeftCornerTile(int y, int x) {
        return y == 0 && x == 0;
    }


    public Tile[][] getTiles() {
        return this.tiles;
    }
}

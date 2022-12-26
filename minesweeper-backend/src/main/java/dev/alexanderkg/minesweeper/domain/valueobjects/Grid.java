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
                    if (populatedTiles[i][j] != null) {
                        if (populatedTiles[i][j].getTileType() == TileType.VALUE && minesLeftToPlace > 0 && placeMine) {
                            populatedTiles[i][j] = new Tile(new Coordinate(i, j), TileType.MINE);
                            minesLeftToPlace = minesLeftToPlace - 1;
                        }
                    }
                }

            }
        }


        for (int i = 0; i < rowLength.getValue(); i++) {
            for (int j = 0; j < columnLength.getValue(); j++) {
                int amountOfNeighboringMines = 0;

                if (i == 0 && j == 0) {
                    if (populatedTiles[i][j + 1].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                    if (populatedTiles[i + 1][j].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                    if (populatedTiles[i + 1][j + 1].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                }

                if (i == 0 && j == columnLength.getValue() - 1) {
                    if (populatedTiles[i][j - 1].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                    if (populatedTiles[i + 1][j].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                    if (populatedTiles[i + 1][j - 1].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                }

                if (i == rowLength.getValue() - 1 && j == 0) {
                    if (populatedTiles[i][j + 1].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                    if (populatedTiles[i - 1][j].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                    if (populatedTiles[i - 1][j + 1].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                }

                if (i == rowLength.getValue() - 1 && j == columnLength.getValue() - 1) {
                    if (populatedTiles[i][j - 1].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                    if (populatedTiles[i - 1][j - 1].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                    if (populatedTiles[i - 1][j].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                }

                if (i == 0 && j != 0 && j != columnLength.getValue() - 1) {
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
                }

                if (i == rowLength.getValue() - 1 && j != 0 && j != columnLength.getValue() - 1) {
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
                }

                if (j == 0 && i != 0 && i != rowLength.getValue() - 1) {
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
                }

                if (j == columnLength.getValue() - 1 && i != 0 && i != rowLength.getValue() - 1) {
                    if (populatedTiles[i - 1][j].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                    if (populatedTiles[i - 1][j - 1].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                    if (populatedTiles[i][j - 1].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                    if (populatedTiles[i + 1][j -1].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                    if (populatedTiles[i + 1][j].getTileType() == TileType.MINE) {
                        amountOfNeighboringMines++;
                    }
                }

                if (isNotEdgeTile(rowLength, columnLength, i, j)) {
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
                }

                TileValue value = new TileValue(amountOfNeighboringMines);
                populatedTiles[i][j].setAmountOfNeighboringMines(value);
            }
        }

        return populatedTiles;
    }

    private static boolean isNotEdgeTile(RowLength rowLength, ColumnLength columnLength, int i, int j) {
        return i > 0 && j > 0 && i < rowLength.getValue() - 1 && j < columnLength.getValue() - 1;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}

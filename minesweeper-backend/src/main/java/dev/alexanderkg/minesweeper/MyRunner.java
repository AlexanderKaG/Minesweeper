package dev.alexanderkg.minesweeper;

import dev.alexanderkg.minesweeper.domain.entity.Game;
import dev.alexanderkg.minesweeper.domain.valueobjects.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        RowLength rowLength = new RowLength(9);
        ColumnLength columnLength = new ColumnLength(9);
        MineAmount amountOfMines = new MineAmount(10);
        Game minesweeper = new Game(new Grid(rowLength, columnLength, amountOfMines));

        int loopCount = 0;
        for (int i = 0; i < rowLength.getValue(); i++) {
            for (int j = 0; j < columnLength.getValue(); j++) {
                Tile tile = minesweeper.getGrid().getTiles()[i][j];
                loopCount++;
                System.out.println(loopCount + " - " + tile);
            }
        }
    }
}

package dev.alexanderkg.minesweeper;

import dev.alexanderkg.minesweeper.domain.entity.Game;
import dev.alexanderkg.minesweeper.domain.valueobjects.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MinesweeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinesweeperApplication.class, args);
    }

}

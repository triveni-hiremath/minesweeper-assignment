package com.minesweeper;

import com.minesweeper.service.MinesweeperGame;
import com.minesweeper.ui.MinesweeperUI;

import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Application {
    public static void main(String[] args) {
        java.util.Random random = new java.util.Random();
        MinesweeperUI ui = new MinesweeperUI(new InputStreamReader(System.in),
                new PrintWriter(System.out, true));
        MinesweeperGame game = new MinesweeperGame(ui, random);
        game.run();
    }
}
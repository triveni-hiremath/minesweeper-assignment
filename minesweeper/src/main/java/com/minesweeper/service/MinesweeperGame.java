package com.minesweeper.service;

import com.minesweeper.model.GridBoard;
import com.minesweeper.model.Coordinates;
import com.minesweeper.ui.MinesweeperUI;
import java.util.Random;

public final class MinesweeperGame {
    private final MinesweeperUI ui;
    private final Random random;

    public MinesweeperGame(MinesweeperUI ui, Random random) {
        this.ui = ui;
        this.random = random;
    }

    public void run() {
        boolean playAgain;
        do {
            playRound();  // play one complete game
            playAgain = askPlayAgain();
        } while (playAgain);
    }

    private void playRound() {
        int size = ui.askSize();
        int mines = ui.askMines(size);
        GridBoard board = new GridBoard(size, mines, random);

        ui.println("\nHere is your minefield:");
        ui.printBoard(board, false);

        while (true) {
            Coordinates move = ui.askMove(size);
            GridBoard.RevealResult result = board.reveal(move);

            if (result.hitMine) {
                handleLoss(board);
                break;
            } else {
                handleSafeMove(board, result);
                if (board.isWon()) {
                    ui.println("🎉 Congratulations! You won the game!");
                    break;
                }
            }
        }
    }

    private void handleLoss(GridBoard board) {
        ui.println("💥 Oh no, you hit a mine! Game over.");
        ui.println("\nFinal minefield (mines shown as *):");
        ui.printBoard(board, true);
    }

    private void handleSafeMove(GridBoard board, GridBoard.RevealResult result) {
        ui.println("This square contains " + result.lastRevealedAdjacent + " adjacent mines.");
        ui.println("\nHere is your updated minefield:");
        ui.printBoard(board, false);
    }

    private boolean askPlayAgain() {
        ui.println("Play again? (Y/N)");
        return ui.nextNonEmpty().trim().equalsIgnoreCase("Y");
    }
}
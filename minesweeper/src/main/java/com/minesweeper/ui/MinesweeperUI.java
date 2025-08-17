package com.minesweeper.ui;

import com.minesweeper.model.GridBoard;
import com.minesweeper.model.Coordinates;
import com.minesweeper.utility.InputParser;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Scanner;

public final class MinesweeperUI {
    private final Scanner in;
    private final PrintWriter out;

    public MinesweeperUI(Reader reader, PrintWriter out) {
        this.in = new Scanner(reader);
        this.out = out;
    }

    public int askSize() {
        out.println("Enter the size of the grid:");
        while (true) {
            String line = nextNonEmpty();
            try {
                int size = Integer.parseInt(line.trim());
                if (size < 2 || size > 26) throw new NumberFormatException();
                return size;
            } catch (NumberFormatException e) {
                out.println("Please enter a number between 2 and 26:");
            }
        }
    }

    public int askMines(int size) {
        int max = (int) Math.floor(size * size * 0.35);
        out.println("Enter the number of mines to place on the grid" +
                " (maximum is 35% of the total squares):");
        while (true) {
            String line = nextNonEmpty();
            try {
                int mines = Integer.parseInt(line.trim());
                if (mines < 0 || mines > max) throw new NumberFormatException();
                return mines;
            } catch (NumberFormatException e) {
                out.println("Please enter a number between 0 and " + max + ":");
            }
        }
    }

    public String nextNonEmpty() {
        while (true) {
            if(!in.hasNextLine()) return "";
            String s = in.nextLine();
            if (s != null && !s.trim().isEmpty()) return s;
        }
    }

    public void printBoard(GridBoard board, boolean revealMines) {
        int n = board.getSize();
        out.print("  ");
        for (int c = 1; c <= n; c++) out.print(c + (c==n?"":" "));
        out.println();
        for (int r=0;r<n;r++) {
            char rowChar = (char)('A' + r);
            out.print(rowChar + " ");
            for (int c=0;c<n;c++) {
                var cell = board.cell(r,c);
                String symbol = "_";
                if (cell.isRevealed()) {
                    symbol = String.valueOf(cell.getAdjacent());
                } else if (revealMines && cell.isMine()) {
                    symbol = "*";
                }
                out.print(symbol + (c==n-1?"":" "));
            }
            out.println();
        }
        out.println();
    }

    public Coordinates askMove(int size) {
        out.println("Select a square to reveal:");
        while (true) {
            String token = nextNonEmpty();
            try {
                return InputParser.parse(token, size);
            } catch (IllegalArgumentException e) {
                out.println(e.getMessage() + "Try again:");
            }
        }
    }

    public void println(String s) {
        out.println(s);
    }
}
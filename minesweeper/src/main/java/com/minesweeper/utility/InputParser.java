package com.minesweeper.utility;

import com.minesweeper.model.Coordinates;

public final class InputParser {
    private InputParser(){}

    public static Coordinates parse(String token, int size) {
        if (token == null) throw new IllegalArgumentException("Null input");
        String t = token.trim().toUpperCase();
        if (t.length() < 2) throw new IllegalArgumentException("Use format like A1");
        char rowChar = t.charAt(0);
        if (rowChar < 'A' || rowChar >= 'A' + size) {
            throw new IllegalArgumentException("Row must be between A and "
                    + (char)('A' + size - 1));
        }
        String colStr = t.substring(1);
        int col;
        try { col = Integer.parseInt(colStr); }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Column must be a number"); }
        if (col < 1 || col > size) {
            throw new IllegalArgumentException("Column must be between 1 and " + size);
        }
        int row = rowChar - 'A';
        return new Coordinates(row, col - 1);
    }

    public static String format(Coordinates c) {
        return "" + (char)('A' + c.getRow()) + (c.getCol() + 1);
    }
}

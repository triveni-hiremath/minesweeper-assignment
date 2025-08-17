package com.minesweeper;

import com.minesweeper.model.Coordinates;
import com.minesweeper.model.GridBoard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

public class GridBoardTest {
    @Test
    void adjacencyComputed() {
        Random r = new Random(123);
        GridBoard b = new GridBoard(4, 2, r);
        int mines = 0;
        for (int i=0;i<4;i++) for (int j=0;j<4;j++) if (b.cell(i,j).isMine()) mines++;
        assertEquals(2, mines);

        for (int i=0;i<4;i++) for (int j=0;j<4;j++) {
            if (!b.cell(i,j).isMine()) {
                int a = b.cell(i,j).getAdjacent();
                assertTrue(a >= 0 && a <= 8);
            }
        }
    }

    @Test
    void loseOnMine() {
        Random r = new Random(7);
        GridBoard b = new GridBoard(3, 1, r);
        Coordinates mine = null;
        for (int i=0;i<3;i++) for (int j=0;j<3;j++) if (b.cell(i,j).isMine()) mine = new Coordinates(i,j);
        GridBoard.RevealResult res = b.reveal(mine);
        assertTrue(res.hitMine);
        assertTrue(b.cell(mine.getRow(), mine.getCol()).isRevealed());
    }

    @Test
    void winCondition() {
        Random r = new Random(11);
        GridBoard b = new GridBoard(2, 1, r);
        // reveal all non-mine cells
        for (int i=0;i<2;i++) for (int j=0;j<2;j++) if (!b.cell(i,j).isMine()) b.reveal(new Coordinates(i,j));
        assertTrue(b.isWon());
    }
}
package com.minesweeper.model;

import lombok.Data;
import java.util.*;

@Data
public class GridBoard {
    private final int size;
    private final Unit[][] cells;
    private final int mineCount;

    public GridBoard(int size, int mineCount, Random random) {
        this.size = size;
        this.cells = new Unit[size][size];
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                cells[r][c] = new Unit();
        this.mineCount = mineCount;
        placeMines(random);
        computeAdjacency();
    }

    public Unit cell(int r, int c){
        return cells[r][c];
    }

    boolean inBounds(int r, int c){
        return r>=0 && r<size && c>=0 && c<size;
    }

    private void placeMines(Random random) {
        int placed = 0;
        Set<Integer> used = new HashSet<>();
        while (placed < mineCount) {
            int idx = random.nextInt(size * size);
            if (used.add(idx)) {
                int r = idx / size,
                        c = idx % size;
                cells[r][c].setMine(true);
                placed++;
            }
        }
    }

    private void computeAdjacency() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (cells[r][c].isMine()) {
                    cells[r][c].setAdjacent(0);
                    continue;
                }
                int count = 0;
                for (int dr=-1; dr<=1; dr++)
                    for (int dc=-1; dc<=1; dc++) {
                        if (dr==0 && dc==0)
                            continue;
                        int nr=r+dr, nc=c+dc;
                        if (inBounds(nr,nc) && cells[nr][nc].isMine()) count++;
                    }
                cells[r][c].setAdjacent(count);
            }
        }
    }

    public static final class RevealResult {
        public final boolean hitMine;
        final int revealedCount;
        public final int lastRevealedAdjacent;
        RevealResult(boolean hitMine, int revealedCount, int lastRevealedAdjacent){
            this.hitMine = hitMine;
            this.revealedCount = revealedCount;
            this.lastRevealedAdjacent = lastRevealedAdjacent;
        }
    }

    public RevealResult reveal(Coordinates coord) {
        Unit start = cells[coord.row][coord.col];

        if (start.isRevealed()) {
            return new RevealResult(false, 0, start.getAdjacent());
        }
        if (start.isMine()) {
            start.reveal();
            return new RevealResult(true, 1, -1);
        }
        int count = 0;
        Deque<Coordinates> q = new ArrayDeque<>();
        q.add(coord);
        while(!q.isEmpty()){
            Coordinates cur = q.removeFirst();
            Unit cell = cells[cur.row][cur.col];
            if (cell.isRevealed() || cell.isMine()) continue;
            cell.reveal();
            count++;
            if (cell.getAdjacent() == 0) {
                for (int dr=-1; dr<=1; dr++) for (int dc=-1; dc<=1; dc++) {
                    if (dr==0 && dc==0) continue;
                    int nr = cur.row + dr, nc = cur.col + dc;
                    if (inBounds(nr,nc) && !cells[nr][nc].isRevealed()) {
                        q.addLast(new Coordinates(nr,nc));
                    }
                }
            }
        }
        return new RevealResult(false, count, cells[coord.row][coord.col].getAdjacent());
    }

    public boolean isWon() {
        int total = size * size;
        int toReveal = total - mineCount;
        int revealed = 0;
        for (int r=0;r<size;r++) for (int c=0;c<size;c++) if (!cells[r][c].isMine() && cells[r][c].isRevealed()) revealed++;
        return revealed == toReveal;
    }
}
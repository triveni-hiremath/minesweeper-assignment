package com.minesweeper.model;

import lombok.Data;;

@Data
public class Unit {
    private boolean mine;
    private int adjacent; // 0..8
    private boolean revealed;

    void setMine(boolean mine) { this.mine = mine; }

    void setAdjacent(int adjacent) {
        this.adjacent = adjacent; }

    void reveal() { this.revealed = true; }
}
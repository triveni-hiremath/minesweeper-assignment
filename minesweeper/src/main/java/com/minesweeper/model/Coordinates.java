package com.minesweeper.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Coordinates {
    final int row; // 0-based
    final int col;
}
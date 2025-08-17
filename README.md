# MinesweeperGame (Java 17)

A Minesweeper implementation.

## Requirements
- Java 17+
- Maven 3.8+
- OS: Windows, macOS, or Linux

## Build & Test
```bash
mvn -q -DskipTests=false test
```

## Instructions
- You will be prompted for grid size (N) and number of mines (<= 35% of N*N).
- Enter coordinates like `A1`, `D4` (row letter, column number).
- Revealing a mine ends the game. Revealing an empty (0) cascades to neighbors.
- Game ends when all safe cells are revealed.

## Design
- `GridBoard` holds immutable dimensions and a 2D array of `Unit`.
- `GridBoard#reveal` performs BFS-based flood fill for zero-adjacent units.
- `InputParser` validates and converts between `A1` <-> coordinates.
- `MinesweeperUI` is responsible for I/O and formatted grid rendering.
- `MinesweeperGame` orchestrates the loop; `Main class` wires it up.

package battleship;

import java.util.Arrays;

public class GameBoard {

    private final int NUM_ROWS = 10;
    private final int NUM_COLS = 10;
    public final char[][] gameBoard;

    public GameBoard() {
        this.gameBoard = new char[NUM_ROWS][NUM_COLS];
        initializeBoard();
    }

    public void initializeBoard() {
        for (char[] chars : gameBoard) {
            Arrays.fill(chars, '~');
        }
    }

    public void printBoard() {
        for (int j = 1; j <= gameBoard[0].length; j++) {
            System.out.print(j == 1 ? "  " + j + " " : j + " ");
        }
        System.out.println();
        for (int i = 0; i < gameBoard.length; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < gameBoard[i].length;  j++) {
                System.out.print(gameBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void placeShips(UserInterface ui)  {
        for (Ship ship : Ship.values()) {
            boolean placed = false;
            while (!placed) {
                String shipCoordinates = null;
                try {
                    shipCoordinates = ui.promptShipCoordinates(ship.getName(), ship.getSize());
                    assert shipCoordinates != null;
                    String[] parts = shipCoordinates.split(" ");
                    int startRow = parts[0].charAt(0) - 'A';
                    int startCol = Integer.parseInt(parts[0].substring(1)) -1;
                    int endRow = parts[1].charAt(0) - 'A';
                    int endCol = Integer.parseInt(parts[1].substring(1)) - 1;
                    if (startCol == endCol && endRow < startRow) {
                        int tempRow = startRow;
                        startRow = endRow;
                        endRow = tempRow;
                    } else if (startRow == endRow && endCol < startCol) {
                        int tempCol = startCol;
                        startCol = endCol;
                        endCol = tempCol;
                    }
                    if ((startCol == endCol && (endRow - startRow + 1 != ship.getSize()))
                            || (startRow == endRow && (endCol - startCol + 1 != ship.getSize()))) {
                        System.out.printf("\nError: Wrong length of the %s! Try again:\n", ship.getName());
                        continue;
                    }
                    if (startCol != endCol && startRow != endRow) {
                        System.out.println("Error! Can't place ship diagonally. Try again:");
                        continue;
                    }
                    int size = ship.getSize();
                    int row = startRow;
                    int col = startCol;
                    int rowStep = startCol == endCol ? 1 : 0;
                    int colStep = startRow == endRow ? 1 : 0;
                    boolean valid = true;
                    for (int i = 0; i < size; i++) {
                        if (isOutOfBounds(row, col)
                                || isOccupied(row, col)
                                || isAdjacent(row, col)
                        ) {
                            valid = false;
                            break;
                        }
                        row += rowStep;
                        col += colStep;
                    }
                    if (valid) {
                        row = startRow;
                        col = startCol;
                        for (int i = 0; i < size; i++) {
                            setValue(row, col);
                            row += rowStep;
                            col += colStep;
                        }
                        placed = true;
                        printBoard();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("INVALID INPUT");
                }
            }
        }
    }

    public  boolean isOutOfBounds(int row, int col) {
        if (row < 0 || row >= NUM_ROWS || col < 0 || col >= NUM_COLS) {
            System.out.println("Error! Out of bounds. Try again:");
            return true;
        }
        return false;
    }

    public boolean isOccupied(int row, int col) {
        if (gameBoard[row][col] != '~') {
            System.out.println("Error! Ships already at that location. Try again:");
            return true;
        }
        return false;
    }

    public boolean isAdjacent(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < NUM_ROWS && j >= 0 && j < NUM_COLS && gameBoard[i][j] == 'O') {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return true;
                }
            }
        }
        return false;
    }

    public int getNUM_ROWS() {
        return this.NUM_ROWS;
    }

    public int getNUM_COLS() {
        return NUM_COLS;
    }

    public char getCell(int row, int col) {
        return gameBoard[row][col];
    }

    public void setCell(int row, int col, char ch) {
        gameBoard[row][col] = ch;
    }

    public void setValue(int row, int col) {
        gameBoard[row][col] = 'O';
    }

    public void testBoard() {
        // Set an Aircraft Carrier (5 cells) horizontally starting at F3
        int row = 5;
        int col = 2;
        for (int i = 0; i < 5; i++) {
            gameBoard[row][col+i] = 'O';
        }
        // Set a Battleship (4 cells) vertically starting at A1
        row = 0;
        col = 0;
        for (int i = 0; i < 4; i++) {
            gameBoard[row+i][col] = 'O';
        }
        // Set a Submarine (3 cells) horizontally starting at J8
        row = 9;
        col = 7;
        for (int i = 0; i < 3; i++) {
            gameBoard[row][col+i] = 'O';
        }
        // Set a Cruiser (3 cells) vertically starting at B9
        row = 1;
        col = 8;
        for (int i = 0; i < 3; i++) {
            gameBoard[row+i][col] = 'O';
        }
        // Set a Destroyer (2 cells) vertically starting at I2
        row = 8;
        col = 1;
        for (int i = 0; i < 2; i++) {
            gameBoard[row+i][col] = 'O';
        }
    }
}

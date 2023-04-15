package battleship;

import java.util.*;

public class GameBoard {

    private final int NUM_ROWS = 10;
    private final int NUM_COLS = 10;
    private final int NUM_SHIPS = 5;
    private final UserInterface ui;
    public char[][] playerOneBoard;
    public char[][] playerTwoBoard;
    public char[][] playerOneTrackingBoard;
    public char[][] playerTwoTrackingBoard;
    public Ship[] playerOneShips;
    public Ship[] playerTwoShips;
    public Ship[][] playerOneShipLocations;
    public Ship[][] playerTwoShipLocations;

    public GameBoard() {
        this.ui = new UserInterface();
        playerOneBoard = new char[NUM_ROWS][NUM_COLS];
        playerTwoBoard = new char[NUM_ROWS][NUM_COLS];
        playerOneTrackingBoard = new char[NUM_ROWS][NUM_COLS];
        playerTwoTrackingBoard = new char[NUM_ROWS][NUM_COLS];
        playerOneShips = new Ship[NUM_SHIPS];
        playerTwoShips = new Ship[NUM_SHIPS];
        playerOneShipLocations = new Ship[NUM_ROWS][NUM_COLS];
        playerTwoShipLocations = new Ship[NUM_ROWS][NUM_COLS];

        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                playerOneBoard[i][j] = '~';
                playerTwoBoard[i][j] = '~';
                playerOneTrackingBoard[i][j] = '~';
                playerTwoTrackingBoard[i][j] = '~';
            }
        }

        // Player One Board
        System.out.println("Player 1, place your ships on the game field\n");
        displayPlayerBoard(1);
        initializeShips(playerOneShips, playerOneBoard, ui, 1);
        ui.passGame();
        // Player Two Board
        System.out.println("Player 2, place your ships on the game field\n");
        displayPlayerBoard(2);
        initializeShips(playerTwoShips, playerTwoBoard, ui, 2);
        ui.passGame();

    }

    private void initializeShips(Ship[] ships, char[][] board, UserInterface ui, int player) {
        ships[0] = new Ship("Aircraft Carrier", 5);
        ships[1] = new Ship("Battleship", 4);
        ships[2] = new Ship("Submarine", 3);
        ships[3] = new Ship("Cruiser", 3);
        ships[4] = new Ship("Destroyer", 2);

        for (Ship ship : ships) {
            boolean placed = false;
            while (!placed) {
                String shipCoordinates;
                try {
                    shipCoordinates = ui.promptShipCoordinates(ship.getName(), ship.getSize());
                    assert shipCoordinates != null;
                    String[] parts = shipCoordinates.split(" ");
                    int startRow = parts[0].charAt(0) - 'A';
                    int startCol = Integer.parseInt(parts[0].substring(1)) - 1;
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
                        System.out.println("\nError! Can't place ship diagonally. Try again:");
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
                                || isOccupied(board, row, col)
                                || isAdjacent(board, row, col)
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
                            // setValue(row, col);
                            // board[row][col] = ship.getName().charAt(0);
                            board[row][col] = 'O';
                            if (player == 1) {
                                playerOneShipLocations[row][col] = ship;
                            } else {
                                playerTwoShipLocations[row][col] = ship;
                            }
                            row += rowStep;
                            col += colStep;
                        }
                        placed = true;
                        System.out.println();
                        displayPlayerBoard(player);
                        System.out.println();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("INVALID INPUT");
                }
            }
        }

    }

    public boolean isOutOfBounds(int row, int col) {
        if (row < 0 || row >= NUM_ROWS || col < 0 || col >= NUM_COLS) {
            System.out.println("\nError! Out of bounds. Try again:");
            return true;
        }
        return false;
    }


    public boolean isOccupied(char[][] board, int row, int col) {

        if (board[row][col] != '~') {
            System.out.println("\nError! Ships already at that location. Try again:");
            return true;
        }
        return false;
    }

    public boolean isAdjacent(char[][] board, int row, int col) {

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < NUM_ROWS && j >= 0 && j < NUM_COLS && board[i][j] == 'O') {
                    System.out.println("\nError! You placed it too close to another one. Try again:");
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

    public void displayBoards(int player) {
        // System.out.println("Player " + player + "'s Boards:");

        if (player == 1) {
            displayBoard(playerOneTrackingBoard);
            System.out.println("---------------------");
            displayBoard(playerOneBoard);
        } else {
            displayBoard(playerTwoTrackingBoard);
            System.out.println("---------------------");
            displayBoard(playerTwoBoard);
        }
    }

    public void displayPlayerBoard(int player) {
        if (player == 1) {
            displayBoard(playerOneBoard);
        } else {
            displayBoard(playerTwoBoard);
        }
    }

    private void displayBoard(char[][] board) {
        System.out.print("  ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < 10; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isValidAttack(int row, int col, int currentPlayer) {
        if (currentPlayer == 1) {
            return playerOneTrackingBoard[row][col] == '~';
        } else {
            return playerTwoTrackingBoard[row][col] == '~';
        }
    }

    public void attack(int row, int col, int currentPlayer) {
        if (currentPlayer == 1) {
            playerOneTrackingBoard[row][col] = playerTwoBoard[row][col] == '~' ? 'M' : 'X';
            playerTwoBoard[row][col] = playerTwoBoard[row][col] == '-' ? 'M' : 'X';
        } else {
            playerTwoTrackingBoard[row][col] = playerOneBoard[row][col] == '~' ? 'M' : 'X';
            playerOneBoard[row][col] = playerOneBoard[row][col] == '-' ? 'M' : 'X';
        }
    }

    public boolean checkForHit(int row, int col, int currentPlayer) {
        if (currentPlayer == 1) {
            return playerTwoBoard[row][col] != '~' && playerTwoBoard[row][col] != 'M';
        } else {
            return playerOneBoard[row][col] != '~' && playerOneBoard[row][col] != 'M';
        }
    }

    public Ship getShipAt(int row, int col, int currentPlayer) {

        if (currentPlayer == 1) {
            return playerTwoShipLocations[row][col];
        } else {
            return playerOneShipLocations[row][col];
        }

        // char shipChar;
        // if (currentPlayer == 1) {
        //     shipChar = playerTwoBoard[row][col];
        // } else {
        //     shipChar = playerOneBoard[row][col];
        // }
        //
        // for (Ship ship : (currentPlayer == 1) ? playerTwoShips : playerOneShips) {
        //     if (ship.getName().charAt(0) == shipChar) {
        //         return ship;
        //     }
        // }
        // return null;
    }

    public boolean allShipsSunk(int currentPlayer) {
        Ship[] ships = (currentPlayer == 1) ? playerTwoShips : playerOneShips;
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }
}


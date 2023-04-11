package battleship;

public class GameLogic {

    private final GameBoard gameBoard;

    public GameLogic(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

    }

    public void playGame(UserInterface ui) {
        while (true) {
            String callShot = ui.shotCaller();
            int[] coords = getCoords(callShot);
            int row = coords[0];
            int col = coords[1];
            if (isOutOfBounds(row, col)) {
                continue;
            }
            boolean hit = false;
            boolean sunk = false;

            for (Ship ship : gameBoard.getShips()) {
                for (int[] coordinate : ship.getCoordinates()) {
                    if (coordinate[0] == row && coordinate[1] == col) {
                        hit = true;
                        ship.hit();
                        if (ship.isSunk(gameBoard)) {
                            sunk = true;
                        }
                        break;
                    }
                }
                if (hit) {
                    break;
                }
            }
            if (hit) {
                gameBoard.setCell(row, col, 'X');
                gameBoard.printFogOfWarBoard();
                boolean allSunk = true;
                for (Ship ship : gameBoard.getShips()) {
                    if (!ship.isSunk(gameBoard)) {
                        allSunk = false;
                        break;
                    }
                }
                if (allSunk) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    break;
                }
                if (sunk) {
                    System.out.println("\nYou sank a ship! Specify a new target:\n");
                } else {
                    System.out.println("\nYou hit a ship!\n");
                }

                gameBoard.printBoard();
            } else {
                gameBoard.setCell(row, col, 'M');
                gameBoard.printFogOfWarBoard();
                System.out.println("\nYou missed!\n");
                gameBoard.printBoard();
            }
        }
    }

    public  boolean isOutOfBounds(int row, int col) {
        if (row < 0 || row >= gameBoard.getNUM_ROWS() || col < 0 || col >= gameBoard.getNUM_COLS()) {
            System.out.print("Error! You entered the wrong coordinates! Try again:");
            return true;
        }
        return false;
    }

    public int[] getCoords(String callShot) {
        int row = callShot.substring(0, 1).charAt(0) - 'A';
        int col = Integer.parseInt(callShot.substring(1)) - 1;
        return new int[]{row, col};
    }
}

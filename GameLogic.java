package battleship;

public class GameLogic {

    private GameBoard gameBoard;
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
            if (gameBoard.getCell(row, col) == 'O') {
                gameBoard.setCell(row, col, 'X');
                gameBoard.printBoard();
                System.out.println("\nYou hit a ship!");
            } else {
                gameBoard.setCell(row, col, 'M');
                gameBoard.printBoard();
                System.out.println("\nYou missed!");
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

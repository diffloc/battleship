package battleship;

import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.printBoard();
        gameBoard.placeShips(this);
    }

    public String promptsCoordinates(String shipName, int shipSize) {
        System.out.printf("\nEnter the coordinates of the %s (%d cells):\n\n", shipName, shipSize);

        return scanner.nextLine();
    }
}

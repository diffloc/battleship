package battleship;

import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        GameBoard gameBoard = new GameBoard();
        // gameBoard.testBoard();
        GameLogic gameLogic = new GameLogic(gameBoard);
        gameBoard.printBoard();
        gameBoard.placeShips(this);
        System.out.println("\nThe game starts!\n");
        gameBoard.printBoard();
        gameLogic.playGame(this);

    }

    public String promptShipCoordinates(String shipName, int shipSize) {
        System.out.printf("\nEnter the coordinates of the %s (%d cells):\n\n", shipName, shipSize);
        return scanner.nextLine();
    }

    public String shotCaller() {
        System.out.println("\nTake a shot!\n");
        return scanner.nextLine();
    }
}

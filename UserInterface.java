package battleship;

import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    public String promptShipCoordinates(String shipName, int shipSize) {
        System.out.printf("\nEnter the coordinates of the %s (%d cells):\n\n", shipName, shipSize);
        return scanner.nextLine().toUpperCase();
    }

    public String shotCaller() {
        return scanner.nextLine();
    }

    public void passGame() {
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...\n");
        scanner.nextLine();
        // System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}

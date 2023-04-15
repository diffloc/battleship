package battleship;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserInterface ui = new UserInterface();
        GameBoard gameBoard = new GameBoard();


        int currentPlayer = 1;
        boolean gameOver = false;

        while (!gameOver) {
            gameBoard.displayBoards(currentPlayer);
            System.out.println();
            System.out.printf("Player %s, it's your turn:\n\n", currentPlayer);
            String input = scanner.nextLine().toUpperCase();

            int[] attackCoords = parseCoordinates(input);

            if (attackCoords == null) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            int row = attackCoords[0];
            int col = attackCoords[1];

            // if (!gameBoard.isValidAttack(row, col, currentPlayer)) {
            //     System.out.println("Invalid attack. Please try again.");
            //     continue;
            // }
            boolean hitShip = gameBoard.checkForHit(row, col, currentPlayer);

            if (hitShip) {
                Ship ship = gameBoard.getShipAt(row, col, currentPlayer);
                if (ship != null) {
                    ship.hit();
                }
            } else {
                System.out.println("\nYou missed!");
            }
            gameBoard.attack(row, col, currentPlayer);

            if (gameBoard.allShipsSunk(currentPlayer)) {
                gameOver = true;
                System.out.println("\"You sank the last ship. You won. Congratulations!\"");
            } else {
                currentPlayer = (currentPlayer == 1) ? 2 : 1;
            }
            if (!gameOver) {
                ui.passGame();
            }
        }
        scanner.close();
    }

    private static int[] parseCoordinates(String input) {
        if (input.length() < 2 || input.length() > 3) {
            return null;
        }

        char rowChar = input.charAt(0);
        int row = rowChar - 'A';
        int col;

        try {
            col = Integer.parseInt(input.substring(1)) - 1;
        } catch (NumberFormatException e) {
            return null;
        }

        if (row >= 0 && row < 10 && col >= 0 && col < 10) {
            return new int[]{row, col};
        } else {
            return null;
        }
    }

    // public static void main(String[] args) {
    //     Game game = new Game();
    //     game.initializeGame();
    //     game.play();
    // }
}

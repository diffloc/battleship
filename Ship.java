package battleship;

import java.util.ArrayList;
import java.util.List;

public enum Ship {

    CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private final String name;
    private final int size;
    private final List<int[]> coordinates;
    private int remaining;

    Ship(String name, int size) {
        this.name = name;
        this.size = size;
        this.remaining = size;
        this.coordinates = new ArrayList<>();
    }

    public void addCoords(int row, int col) {
        this.coordinates.add(new int[]{row, col});
    }

    public List<int[]> getCoordinates() {
        return this.coordinates;
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public void hit() {
        this.remaining--;
        if (this.remaining == 0) {
            System.out.println("Ship " + this.getName() + " sunk!");
        }
    }

    public boolean isSunk(GameBoard gameBoard) {
        for (int[] coordinate : this.coordinates) {
            if (gameBoard.gameBoard[coordinate[0]][coordinate[1]] != 'X') {
                return false;
            }
        }
        return true;
    }
}

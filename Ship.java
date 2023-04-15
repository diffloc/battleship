package battleship;

public class Ship {

    private final String name;
    private final int size;
    private int remaining;
    private boolean sunk;

    public Ship(String name, int size) {
        this.name = name;
        this.size = size;
        this.remaining = size;
        this.sunk = false;
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
            // System.out.println("Ship " + this.getName() + " sunk!");
            System.out.println("\nYou sank a ship!");
            this.sunk = true;
        } else {
            System.out.println("\nYou hit a ship!");
        }




    }

    public boolean isSunk() {
        return this.sunk;
    }
}

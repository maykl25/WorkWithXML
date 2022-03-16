package theGame;

public class Player {
    private int id;
    private String name;
    private char symbol;
    private String location;
    public int count = 0;
    public static int step = 1;

    public Player(int id, String name, char symbol) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public char getSymbol() {
        return this.symbol;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}

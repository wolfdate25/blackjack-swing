package project.blackjack;

public class Card {
    private final int value;
    private final String name;

    public Card(int value, String name) {

        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}

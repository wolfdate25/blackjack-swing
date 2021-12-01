package project.blackjack;

import javax.swing.*;

public class Card {

    private final ImageIcon image;
    private final int value;
    private final String name;

    public Card(ImageIcon image, int value, String name) {
        this.image = image;
        this.value = value;
        this.name = name;
    }

    public ImageIcon getImage() {
        return image;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}

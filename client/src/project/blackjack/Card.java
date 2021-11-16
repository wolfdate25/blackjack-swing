package project.blackjack;

import javax.swing.*;
import java.awt.*;

public class Card {

    private ImageIcon image;
    private int value;
    private String name;

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

package project.blackjack;

import java.io.Serializable;

public class Packet implements Serializable {

    String code;
    String name;
    String action;

    public Packet(String code, String name, String action) {
        this.code = code;
        this.action = action;
        this.name = name;
    }

}

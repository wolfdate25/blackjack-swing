package project.blackjack;

import java.util.Iterator;
import java.util.Vector;

public class Player {
    String name;
    private int score;
    private int coin;
    private Vector<Card> playerCards;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.coin = 20;
        playerCards = new Vector<>(10);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Vector<Card> getPlayerCards() {
        return playerCards;
    }

    public void addPlayerCards(Card card) {
        this.playerCards.add(card);
        updateScore();
    }
    public void setCoin(int value) {
        this.coin  = value;
    }
    public int getCoin() {
        return coin;
    }

    public void resetPlayerParameter() {
        playerCards.clear();
        score=0;
    }

    public void updateScore() {
        Iterator<Card> itr = playerCards.iterator();
        while(itr.hasNext()) {
            this.score = score+itr.next().getValue();
        }
    }
}

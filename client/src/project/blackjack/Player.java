package project.blackjack;

import java.util.Iterator;
import java.util.Vector;

public class Player {
    private final String name;
    private final Vector<Card> playerCards;
    private int score;
    private int coin;
    private int idx = -1;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.coin = 20;
        playerCards = new Vector<>(10);
    }

    public int getIdx() {
        return this.idx;
    }

    public void setIdx(int n) {
        this.idx = n;
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

    public int getCoin() {
        return coin;
    }

    public void setCoin(int value) {
        this.coin = value;
    }

    public void resetPlayerParameter(boolean b) {
        if (b) {
            idx = -1;
        }
        playerCards.clear();
        score = 0;
    }

    public void updateScore() {
        Iterator<Card> itr = playerCards.iterator();
        int sum = 0;
        while (itr.hasNext()) {
            sum = score + itr.next().getValue();
        }
        score = sum;
    }
}

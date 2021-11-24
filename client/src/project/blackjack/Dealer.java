package project.blackjack;

import java.util.Iterator;
import java.util.Vector;

public class Dealer{
    private Vector<Card> cards;
    int score = 0;

    public Dealer() {
        cards = new Vector<>(10);
    }

    public Vector<Card> getCards() {
        return cards;
    }

    public void addPlayerCards(Card card) {
        this.cards.add(card);
        updateScore();
    }

    public int getScore() {
        return score;
    }

    public void updateScore() {
        Iterator<Card> itr = cards.iterator();
        int sum = 0;
        while(itr.hasNext()) {
            sum =sum+itr.next().getValue();
        }
        score = sum;
    }
}

package project.blackjack;

import java.util.Vector;

public class Dealer {
    private int score;

    private final Room room;

    private final Vector<Card> cards;

    public Dealer(Room room) {
        this.room = room;
        this.cards = new Vector<>(6);
    }

    public int getScore() {
        return score;
    }

    public Vector<Card> getDrawnCards() {
        return cards;
    }

    private void calculateScore() {
        score = 0;
        for (Card card : cards) {
            score += card.getValue();
        }
    }

    public void drawCard() {
        Card card = room.deck.getRandomCard();
        cards.add(card);
        calculateScore();
        room.sendCard("Dealer", card.getName());
    }

    public void reset() {
        cards.clear();
        score = 0;
    }
}

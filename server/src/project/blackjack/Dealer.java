package project.blackjack;

public class Dealer {
    private int score;

    private Room room;

    public Dealer(Room room) {
        this.room = room;
    }

    public void drawCard() {
        Card card = room.deck.getRandomCard();
        score += card.getValue();
        room.sendCard("Dealer", card.getName());
    }
}

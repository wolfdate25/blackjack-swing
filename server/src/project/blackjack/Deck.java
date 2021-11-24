package project.blackjack;

import java.util.Random;
import java.util.Vector;

public class Deck {
    Vector<Card> cards = new Vector<>(52);
    Random random = new Random();

    public Deck() {
        genNewDeck();
    }

    private void genNewDeck() {
        Card c2 = new Card(2, "c2");
        Card c3 = new Card(3, "c3");
        Card c4 = new Card(4, "c4");
        Card c5 = new Card(5, "c5");
        Card c6 = new Card(6, "c6");
        Card c7 = new Card(7, "c7");
        Card c8 = new Card(8, "c8");
        Card c9 = new Card(9, "c9");
        Card c10 = new Card(10, "c10");
        Card cJack = new Card(10, "cJack");
        Card cKing = new Card(10, "cKing");
        Card cQueen = new Card(10, "cQueen");
        Card cAce = new Card(11, "cAce");

        Card h2 = new Card(2, "h2");
        Card h3 = new Card(3, "h3");
        Card h4 = new Card(4, "h4");
        Card h5 = new Card(5, "h5");
        Card h6 = new Card(6, "h6");
        Card h7 = new Card(7, "h7");
        Card h8 = new Card(8, "h8");
        Card h9 = new Card(9, "h9");
        Card h10 = new Card(10, "h10");
        Card hJack = new Card(10, "hJack");
        Card hKing = new Card(10, "hKing");
        Card hQueen = new Card(10, "hQueen");
        Card hAce = new Card(11, "hAce");

        Card d2 = new Card(2, "d2");
        Card d3 = new Card(3, "d3");
        Card d4 = new Card(4, "d4");
        Card d5 = new Card(5, "d5");
        Card d6 = new Card(6, "d6");
        Card d7 = new Card(7, "d7");
        Card d8 = new Card(8, "d8");
        Card d9 = new Card(9, "d9");
        Card d10 = new Card(10, "d10");
        Card dJack = new Card(10, "dJack");
        Card dKing = new Card(10, "dKing");
        Card dQueen = new Card(10, "dQueen");
        Card dAce = new Card(11, "dAce");

        Card s2 = new Card(2, "s2");
        Card s3 = new Card(3, "s3");
        Card s4 = new Card(4, "s4");
        Card s5 = new Card(5, "s5");
        Card s6 = new Card(6, "s6");
        Card s7 = new Card(7, "s7");
        Card s8 = new Card(8, "s8");
        Card s9 = new Card(9, "s9");
        Card s10 = new Card(10, "s10");
        Card sJack = new Card(10, "sJack");
        Card sKing = new Card(10, "sKing");
        Card sQueen = new Card(10, "sQueen");
        Card sAce = new Card(11, "sAce");

        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        cards.add(c5);
        cards.add(c6);
        cards.add(c7);
        cards.add(c8);
        cards.add(c9);
        cards.add(c10);
        cards.add(cJack);
        cards.add(cKing);
        cards.add(cQueen);
        cards.add(cAce);

        cards.add(h2);
        cards.add(h3);
        cards.add(h4);
        cards.add(h5);
        cards.add(h6);
        cards.add(h7);
        cards.add(h8);
        cards.add(h9);
        cards.add(h10);
        cards.add(hJack);
        cards.add(hKing);
        cards.add(hQueen);
        cards.add(hAce);

        cards.add(d2);
        cards.add(d3);
        cards.add(d4);
        cards.add(d5);
        cards.add(d6);
        cards.add(d7);
        cards.add(d8);
        cards.add(d9);
        cards.add(d10);
        cards.add(dJack);
        cards.add(dKing);
        cards.add(dQueen);
        cards.add(dAce);

        cards.add(s2);
        cards.add(s3);
        cards.add(s4);
        cards.add(s5);
        cards.add(s6);
        cards.add(s7);
        cards.add(s8);
        cards.add(s9);
        cards.add(s10);
        cards.add(sJack);
        cards.add(sKing);
        cards.add(sQueen);
        cards.add(sAce);
    }

    public Card getRandomCard() {

        int index = random.nextInt(52);
        Card result;
        try {
            result = cards.get(index);
            cards.remove(index);
        } catch (IndexOutOfBoundsException e) {
            genNewDeck();
            result = cards.get(index);
            cards.remove(index);
        }
        return result;
    }
}

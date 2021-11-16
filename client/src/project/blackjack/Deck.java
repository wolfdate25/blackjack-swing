package project.blackjack;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Deck {
    ArrayList<Card> cards = new ArrayList<>(52);
    Random random = new Random();

    public Deck() {

        genNewDeck();


        System.out.println(cards.size());

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

    private void genNewDeck() {

        // Colver Images
        ImageIcon c2Icon = new ImageIcon(getClass().getResource("/2c.jpg"));
        ImageIcon c3Icon = new ImageIcon(getClass().getResource("/3c.jpg"));
        ImageIcon c4Icon = new ImageIcon(getClass().getResource("/4c.jpg"));
        ImageIcon c5Icon = new ImageIcon(getClass().getResource("/5c.jpg"));
        ImageIcon c6Icon = new ImageIcon(getClass().getResource("/6c.jpg"));
        ImageIcon c7Icon = new ImageIcon(getClass().getResource("/7c.jpg"));
        ImageIcon c8Icon = new ImageIcon(getClass().getResource("/8c.jpg"));
        ImageIcon c9Icon = new ImageIcon(getClass().getResource("/9c.jpg"));
        ImageIcon c10Icon = new ImageIcon(getClass().getResource("/10c.jpg"));
        ImageIcon cJackIcon = new ImageIcon(getClass().getResource("/jackc.jpg"));
        ImageIcon cKingIcon = new ImageIcon(getClass().getResource("/kingc.jpg"));
        ImageIcon cQueenIcon = new ImageIcon(getClass().getResource("/queenc.jpg"));
        ImageIcon cAceIcon = new ImageIcon(getClass().getResource("/acec.jpg"));

        // Heart Images
        ImageIcon h2Icon = new ImageIcon(getClass().getResource("/2h.jpg"));
        ImageIcon h3Icon = new ImageIcon(getClass().getResource("/3h.jpg"));
        ImageIcon h4Icon = new ImageIcon(getClass().getResource("/4h.jpg"));
        ImageIcon h5Icon = new ImageIcon(getClass().getResource("/5h.jpg"));
        ImageIcon h6Icon = new ImageIcon(getClass().getResource("/6h.jpg"));
        ImageIcon h7Icon = new ImageIcon(getClass().getResource("/7h.jpg"));
        ImageIcon h8Icon = new ImageIcon(getClass().getResource("/8h.jpg"));
        ImageIcon h9Icon = new ImageIcon(getClass().getResource("/9h.jpg"));
        ImageIcon h10Icon = new ImageIcon(getClass().getResource("/10h.jpg"));
        ImageIcon hJackIcon = new ImageIcon(getClass().getResource("/jackh.jpg"));
        ImageIcon hKingIcon = new ImageIcon(getClass().getResource("/kingh.jpg"));
        ImageIcon hQueenIcon = new ImageIcon(getClass().getResource("/queenh.jpg"));
        ImageIcon hAceIcon = new ImageIcon(getClass().getResource("/aceh.jpg"));

        // Diamond Images
        ImageIcon d2Icon = new ImageIcon(getClass().getResource("/2d.jpg"));
        ImageIcon d3Icon = new ImageIcon(getClass().getResource("/3d.jpg"));
        ImageIcon d4Icon = new ImageIcon(getClass().getResource("/4d.jpg"));
        ImageIcon d5Icon = new ImageIcon(getClass().getResource("/5d.jpg"));
        ImageIcon d6Icon = new ImageIcon(getClass().getResource("/6d.jpg"));
        ImageIcon d7Icon = new ImageIcon(getClass().getResource("/7d.jpg"));
        ImageIcon d8Icon = new ImageIcon(getClass().getResource("/8d.jpg"));
        ImageIcon d9Icon = new ImageIcon(getClass().getResource("/9d.jpg"));
        ImageIcon d10Icon = new ImageIcon(getClass().getResource("/10d.jpg"));
        ImageIcon dJackIcon = new ImageIcon(getClass().getResource("/jackd.jpg"));
        ImageIcon dKingIcon = new ImageIcon(getClass().getResource("/kingd.jpg"));
        ImageIcon dQueenIcon = new ImageIcon(getClass().getResource("/queend.jpg"));
        ImageIcon dAceIcon = new ImageIcon(getClass().getResource("/aced.jpg"));

        // Space Images
        ImageIcon s2Icon = new ImageIcon(getClass().getResource("/2s.jpg"));
        ImageIcon s3Icon = new ImageIcon(getClass().getResource("/3s.jpg"));
        ImageIcon s4Icon = new ImageIcon(getClass().getResource("/4s.jpg"));
        ImageIcon s5Icon = new ImageIcon(getClass().getResource("/5s.jpg"));
        ImageIcon s6Icon = new ImageIcon(getClass().getResource("/6s.jpg"));
        ImageIcon s7Icon = new ImageIcon(getClass().getResource("/7s.jpg"));
        ImageIcon s8Icon = new ImageIcon(getClass().getResource("/8s.jpg"));
        ImageIcon s9Icon = new ImageIcon(getClass().getResource("/9s.jpg"));
        ImageIcon s10Icon = new ImageIcon(getClass().getResource("/10s.jpg"));
        ImageIcon sJackIcon = new ImageIcon(getClass().getResource("/jacks.jpg"));
        ImageIcon sKingIcon = new ImageIcon(getClass().getResource("/kings.jpg"));
        ImageIcon sQueenIcon = new ImageIcon(getClass().getResource("/queens.jpg"));
        ImageIcon sAceIcon = new ImageIcon(getClass().getResource("/aces.jpg"));

        Card c2 = new Card(c2Icon, 2, "c2");
        Card c3 = new Card(c3Icon, 2, "c3");
        Card c4 = new Card(c4Icon, 2, "c4");
        Card c5 = new Card(c5Icon, 2, "c5");
        Card c6 = new Card(c6Icon, 2, "c6");
        Card c7 = new Card(c7Icon, 2, "c7");
        Card c8 = new Card(c8Icon, 2, "c8");
        Card c9 = new Card(c9Icon, 2, "c9");
        Card c10 = new Card(c10Icon, 2, "c10");
        Card cJack = new Card(cJackIcon, 2, "cJack");
        Card cKing = new Card(cKingIcon, 2, "cKing");
        Card cQueen = new Card(cQueenIcon, 2, "cQueen");
        Card cAce = new Card(cAceIcon, 2, "cAce");

        Card h2 = new Card(h2Icon, 2, "h2");
        Card h3 = new Card(h3Icon, 2, "h3");
        Card h4 = new Card(h4Icon, 2, "h4");
        Card h5 = new Card(h5Icon, 2, "h5");
        Card h6 = new Card(h6Icon, 2, "h6");
        Card h7 = new Card(h7Icon, 2, "h7");
        Card h8 = new Card(h8Icon, 2, "h8");
        Card h9 = new Card(h9Icon, 2, "h9");
        Card h10 = new Card(h10Icon, 2, "h10");
        Card hJack = new Card(hJackIcon, 2, "hJack");
        Card hKing = new Card(hKingIcon, 2, "hKing");
        Card hQueen = new Card(hQueenIcon, 2, "hQueen");
        Card hAce = new Card(hAceIcon, 2, "hAce");

        Card d2 = new Card(d2Icon, 2, "d2");
        Card d3 = new Card(d3Icon, 2, "d3");
        Card d4 = new Card(d4Icon, 2, "d4");
        Card d5 = new Card(d5Icon, 2, "d5");
        Card d6 = new Card(d6Icon, 2, "d6");
        Card d7 = new Card(d7Icon, 2, "d7");
        Card d8 = new Card(d8Icon, 2, "d8");
        Card d9 = new Card(d9Icon, 2, "d9");
        Card d10 = new Card(d10Icon, 2, "d10");
        Card dJack = new Card(dJackIcon, 2, "dJack");
        Card dKing = new Card(dKingIcon, 2, "dKing");
        Card dQueen = new Card(dQueenIcon, 2, "dQueen");
        Card dAce = new Card(dAceIcon, 2, "dAce");

        Card s2 = new Card(s2Icon, 2, "s2");
        Card s3 = new Card(s3Icon, 2, "s3");
        Card s4 = new Card(s4Icon, 2, "s4");
        Card s5 = new Card(s5Icon, 2, "s5");
        Card s6 = new Card(s6Icon, 2, "s6");
        Card s7 = new Card(s7Icon, 2, "s7");
        Card s8 = new Card(s8Icon, 2, "s8");
        Card s9 = new Card(s9Icon, 2, "s9");
        Card s10 = new Card(s10Icon, 2, "s10");
        Card sJack = new Card(sJackIcon, 2, "sJack");
        Card sKing = new Card(sKingIcon, 2, "sKing");
        Card sQueen = new Card(sQueenIcon, 2, "sQueen");
        Card sAce = new Card(sAceIcon, 2, "sAce");

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
}
package project.blackjack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Room extends JFrame {
    private JPanel rootPanel;
    private JButton sendMessage;
    private JButton bet;
    private JButton draw;
    private JButton exit;
    private JButton split;
    private JPanel fieldPanel;
    private JTextField chatField;
    private JTextArea chatArea;
    private JLabel coinField;
    private JPanel gamePanel;
    private JPanel actionPanel;
    private Field field;
    private JButton doubleDownButton;
    private JButton surrenderButton;

    Deck deck;
    Vector<Player> players = new Vector(4);

    public Room(Player player) {
        setTitle("Blackjack");
        setContentPane(rootPanel);

        pack();
        setVisible(true);

        // print Parameters
        System.out.println(fieldPanel.getSize());
        // Field setting
        deck = new Deck();

        // Player Setting
        addPlayer(player);
        coinField.setText("Coin: " + player.getCoin());

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Lobby lobby = new Lobby(player);
//                field.reset();
                player.resetPlayerParameter();
                dispose();
            }
        });
        split.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                chatArea.append(chatField.getText() + '\n');
            }
        });
        draw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card newCard = deck.getRandomCard();
                int size = player.getPlayerCards().size();
                player.addPlayerCards(newCard);
                field.paintCard(newCard,1,size);
                field.paintScore(1,player.getScore());
            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        fieldPanel = new Field();
    }

    public void addPlayer(Player player) {
        if (players.size() < 4) {
            players.add(player);
            field.setTitleBorder(players.size(),player.getName());
        }
    }
}

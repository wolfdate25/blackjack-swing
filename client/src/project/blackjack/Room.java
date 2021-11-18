package project.blackjack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
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

    String roomName;
    GameThread game;

    @Override
    public void dispose() {
        super.dispose();
        game.requestLeaveRoom();
        game.setLobbyVisible(true);
    }

    public Room(GameThread thread, String roomName, Player player) {
        setTitle("Blackjack");
        setContentPane(rootPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        pack();
        setVisible(true);

        this.roomName = roomName;
        this.game = thread;

        // print Parameters
        System.out.println(fieldPanel.getSize());
        // Field setting
        deck = new Deck();

        // Player Setting
        addPlayer(player);

        // Env initializing
        game.requestRoomEnv();


        coinField.setText("Coin: " + player.getCoin());

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Lobby lobby = new Lobby(player);
//                field.reset();
//                game.requestLeaveRoom();
                player.resetPlayerParameter();
                dispose();
                game.setLobbyVisible(true);
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
                field.paintCard(newCard, 1, size);
                field.paintScore(1, player.getScore());
            }
        });
    }

    public void addPlayer(Player player) {
        player.resetPlayerParameter();
        if (players.size() < 4) {
            players.add(player);
            // 플레이어에게 인덱스 부과
            int idx = 0;
            for (int s = 0; s < players.size(); s++) {
                if (players.get(s).getIdx() == idx) {
                    idx++;
                }
            }
            player.setIdx(idx);
        }
        field.setTitleBorder(player.getIdx(), player.getName());

}

    public void removePlayer(String name) {
        // {name}을 가진 플레이어를 제거한다.
        Iterator<Player> itr = players.iterator();
        while (itr.hasNext()) {
            Player player = itr.next();
            if (player.getName().equals(name)) {
                players.remove(player);
                field.setTitleBorder(player.getIdx(), player.getName());

            }
        }
    }

    public void setTimer(int timer) {
        field.setTimer(timer);
    }
}

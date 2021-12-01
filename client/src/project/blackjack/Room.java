package project.blackjack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Vector;

public class Room extends JFrame {
    Deck deck;
    Vector<Player> players = new Vector(4);
    String roomName;
    GameThread game;
    Dealer dealer;
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

        // Add dealer
        dealer = new Dealer();


        setCoinField(player.getCoin());

        // where is keyboard focus
        chatField.requestFocus();

        // keyboard event listener
        chatField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                System.out.println(e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Lobby lobby = new Lobby(player);
//                field.reset();
//                game.requestLeaveRoom();
//                player.resetPlayerParameter(true);
                dispose();
                GameThread.setLobbyVisible(true);
            }
        });

        bet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String coin = JOptionPane.showInputDialog("배팅할 코인을 입력하세요");
                if (coin != null) {
                    game.requestBetCoin(coin);
                }
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

//                chatArea.append(chatField.getText() + '\n');
                sendMessage();
            }
        });

        draw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.requestDrawCard();

                /*Card newCard = deck.cards.get(1);
                int size = player.getPlayerCards().size();
                player.addPlayerCards(newCard);
                field.paintCard(newCard, 1, size);
                field.paintScore(1, player.getScore());*/
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        game.requestLeaveRoom();
        GameThread.setLobbyVisible(true);
    }

    private void sendMessage() {
        game.requestChat(chatField.getText());
        chatField.setText("");
    }

    public void addPlayer(Player player) {
        player.resetPlayerParameter(true);
        if (players.size() < 4) {
            players.add(player);
            // 플레이어에게 인덱스 부과
            int idx = 1;
            for (int s = 0; s < players.size(); s++) {
                if (players.get(s).getIdx() == idx) {
                    idx++;
                }
            }
            // 플레이어 인덱스 부여
            player.setIdx(idx);
            field.setTitleBorder(player.getIdx(), player.getName());
            // 플레이어 초기 라벨 설정
            field.setStateLabel(idx, "대기");
            field.setCoinLabel(idx, 0);
            chatArea.append("[" + player.getName() + "]님이 입장하셨습니다.\n");
        }

    }

    // {name}을 가진 플레이어를 제거한다.
    public void removePlayer(String name) {
        Iterator<Player> itr = players.iterator();
        while (itr.hasNext()) {
            Player player = itr.next();
            if (player.getName().equals(name)) {
                players.remove(player);
                field.setTitleBorder(player.getIdx(), "Empty");
                field.setStateLabel(player.getIdx(), "");
                field.setCoinLabel(player.getIdx(), -1);
                chatArea.append("[" + player.getName() + "]님이 퇴장하셨습니다.\n");
                break;
            }
        }
    }

    public void setTimer(int timer) {
        field.setTimer(timer);
    }

    public void setBetCoin(String name, int coin) {
        Iterator<Player> itr = players.iterator();
        while (itr.hasNext()) {
            Player player = itr.next();
            if (player.getName().equals(name)) {
                field.setCoinLabel(player.getIdx(), coin);
                break;
            }
        }
    }

    public void drawCard(String name, String cardName) {
        Card card = deck.getTheCard(cardName);
        if (name.equals("Dealer")) {
            dealer.addPlayerCards(card);
            field.paintCard(card, 0, dealer.getCards().size() - 1);
            field.paintScore(0, dealer.getScore());
        } else {
            Iterator<Player> itr = players.iterator();
            while (itr.hasNext()) {
                Player player = itr.next();
                if (player.getName().equals(name)) {
                    int size = player.getPlayerCards().size();
                    if (card != null) {
                        player.addPlayerCards(card);
                        field.paintCard(card, player.getIdx(), size);
                        field.paintScore(player.getIdx(), player.getScore());
                    }
                    break;
                }
            }
        }
    }

    public void setPlayerState(String name, String action) {
        Iterator<Player> itr = players.iterator();
        while (itr.hasNext()) {
            Player player = itr.next();
            if (player.getName().equals(name)) {
                field.setStateLabel(player.getIdx(), action);
                break;
            }
        }
    }

    public void setCoinField(int coin) {
        coinField.setText("Coin: " + coin);
    }

    public void resetEnv() {
        dealer.clear();
        field.resetEnv();
        Iterator<Player> itr = players.iterator();
        while (itr.hasNext()) {
            Player player = itr.next();
            player.resetPlayerParameter(false);
            field.setTitleBorder(player.getIdx(), player.getName());
            field.setStateLabel(player.getIdx(), "대기");
            field.setCoinLabel(player.getIdx(), 0);
        }
    }

    public void appendChat(String name, String text) {
        chatArea.append(name + ": " + text + '\n');
    }
}

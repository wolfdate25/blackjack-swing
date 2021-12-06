package project.blackjack;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Locale;
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
        chatField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
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

        doubleDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.requestDoubleDown();
            }
        });

        surrenderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.requestSurrender();
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

    public void setPlayerState(String name, String state) {
        Iterator<Player> itr = players.iterator();
        while (itr.hasNext()) {
            Player player = itr.next();
            if (player.getName().equals(name)) {
                if (state.equals("더블다운")) {
                    field.setCoinLabelX2(player.getIdx());
                } else {
                    field.setStateLabel(player.getIdx(), state);
                }
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.setMinimumSize(new Dimension(808, 100));
        rootPanel.setPreferredSize(new Dimension(1000, 700));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(780, -1), null, 0, false));
        gamePanel = new JPanel();
        gamePanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        gamePanel.setEnabled(true);
        panel1.add(gamePanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        fieldPanel = new JPanel();
        fieldPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        gamePanel.add(fieldPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        fieldPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(700, -1), null, 0, false));
        field = new Field();
        fieldPanel.add(field, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(770, 669), null, 0, false));
        actionPanel = new JPanel();
        actionPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 7, new Insets(0, 0, 0, 0), -1, -1));
        gamePanel.add(actionPanel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, 1, 1, null, null, null, 0, false));
        bet = new JButton();
        bet.setText("베팅");
        actionPanel.add(bet, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        exit = new JButton();
        exit.setText("나가기");
        actionPanel.add(exit, new com.intellij.uiDesigner.core.GridConstraints(0, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        draw = new JButton();
        draw.setText("드로우");
        actionPanel.add(draw, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        split = new JButton();
        split.setLabel("스플릿");
        split.setText("스플릿");
        actionPanel.add(split, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        coinField = new JLabel();
        Font coinFieldFont = this.$$$getFont$$$("Arial", Font.BOLD, 14, coinField.getFont());
        if (coinFieldFont != null) coinField.setFont(coinFieldFont);
        coinField.setText("Coin:");
        actionPanel.add(coinField, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        doubleDownButton = new JButton();
        doubleDownButton.setText("더블다운");
        actionPanel.add(doubleDownButton, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        surrenderButton = new JButton();
        surrenderButton.setText("서렌더");
        actionPanel.add(surrenderButton, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(300, -1), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setAutoscrolls(true);
        panel2.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        Font chatAreaFont = this.$$$getFont$$$(null, -1, 16, chatArea.getFont());
        if (chatAreaFont != null) chatArea.setFont(chatAreaFont);
        scrollPane1.setViewportView(chatArea);
        sendMessage = new JButton();
        sendMessage.setText("전송");
        panel2.add(sendMessage, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chatField = new JTextField();
        panel2.add(chatField, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}

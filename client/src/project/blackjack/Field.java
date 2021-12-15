package project.blackjack;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Vector;

public class Field extends JPanel {

    private final JLabel dealerScoreLabel;
    private final JLabel p1ScoreLabel;
    private final JLabel p2ScoreLabel;
    private final JLabel p3ScoreLabel;
    private final JLabel p4ScoreLabel;
    private final JLabel p1StateLabel;
    private final JLabel p2StateLabel;
    private final JLabel p3StateLabel;
    private final JLabel p4StateLabel;
    private final JLabel p1CoinLabel;
    private final JLabel p2CoinLabel;
    private final JLabel p3CoinLabel;
    private final JLabel p4CoinLabel;
    private final TitledBorder dealerTitleBorder;
    private final TitledBorder p1TitleBorder;
    private final TitledBorder p2TitleBorder;
    private final TitledBorder p3TitleBorder;
    private final TitledBorder p4TitleBorder;
    Vector<JLabel> drawnCards = new Vector<>(52); // 필드에 올라갈 카드
    Font font = new Font("Arial", Font.PLAIN, 24); // 사용할 폰트
    private int timer = 0;


    public Field() {
        setLayout(null);
        // Width = 700, Height = 569
        setBackground(Color.GREEN);

//        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/2c.jpg")));
//        imageLabel.setSize(78, 108);
//        imageLabel.setLocation(58,162);
//        add(imageLabel);

        // 사용할 폰트
        Font font = new Font("nanumgothic", Font.PLAIN, 24);
        Font smallFont = new Font("nanumgothic", Font.BOLD, 20);

        LineBorder lineBorder = new LineBorder(Color.DARK_GRAY, 2);

        JLabel dPanel = new JLabel();
        dealerTitleBorder = new TitledBorder("Dealer");
        dealerTitleBorder.setBorder(lineBorder);
        dPanel.setBounds(20, 140, 150, 500);
        dPanel.setBorder(dealerTitleBorder);
        add(dPanel);

        JLabel p1Panel = new JLabel();
        p1TitleBorder = new TitledBorder("Player");
        p1TitleBorder.setBorder(lineBorder);
        p1Panel.setBounds(230, 140, 125, 400);
        p1Panel.setBorder(p1TitleBorder);
        add(p1Panel);

        JLabel p2Panel = new JLabel();
        p2TitleBorder = new TitledBorder("Empty");
        p2TitleBorder.setBorder(lineBorder);
        p2Panel.setBounds(360, 140, 125, 400);
        p2Panel.setBorder(p2TitleBorder);
        add(p2Panel);

        JLabel p3Panel = new JLabel();
        p3TitleBorder = new TitledBorder("Empty");
        p3TitleBorder.setBorder(lineBorder);
        p3Panel.setBounds(490, 140, 125, 400);
        p3Panel.setBorder(p3TitleBorder);
        add(p3Panel);

        JLabel p4Panel = new JLabel();
        p4TitleBorder = new TitledBorder("Empty");
        p4TitleBorder.setBorder(lineBorder);
        p4Panel.setBounds(620, 140, 125, 400);
        p4Panel.setBorder(p4TitleBorder);
        add(p4Panel);

        // 점수판
        dealerScoreLabel = new JLabel("0", SwingConstants.CENTER);
        dealerScoreLabel.setSize(94, 38);
        dealerScoreLabel.setLocation(50, 597);
        dealerScoreLabel.setFont(font);
        add(dealerScoreLabel);

        p1ScoreLabel = new JLabel("0", SwingConstants.CENTER);
        p1ScoreLabel.setSize(94, 38);
        p1ScoreLabel.setLocation(246, 491);
        p1ScoreLabel.setFont(font);
        add(p1ScoreLabel);

        p2ScoreLabel = new JLabel("0", SwingConstants.CENTER);
        p2ScoreLabel.setSize(94, 38);
        p2ScoreLabel.setLocation(376, 491);
        p2ScoreLabel.setFont(font);
        add(p2ScoreLabel);

        p3ScoreLabel = new JLabel("0", SwingConstants.CENTER);
        p3ScoreLabel.setSize(94, 38);
        p3ScoreLabel.setLocation(506, 491);
        p3ScoreLabel.setFont(font);
        add(p3ScoreLabel);

        p4ScoreLabel = new JLabel("0", SwingConstants.CENTER);
        p4ScoreLabel.setSize(94, 38);
        p4ScoreLabel.setLocation(636, 491);
        p4ScoreLabel.setFont(font);
        add(p4ScoreLabel);

        JLabel StateLabel = new JLabel("상태", SwingConstants.CENTER);
        StateLabel.setSize(60, 56);
        StateLabel.setLocation(170, 550);
        StateLabel.setFont(smallFont);
        add(StateLabel);

        p1StateLabel = new JLabel("", SwingConstants.CENTER); // default 대기
        p1StateLabel.setSize(125, 56);
        p1StateLabel.setLocation(230, 550);
        p1StateLabel.setFont(font);
        add(p1StateLabel);

        p2StateLabel = new JLabel("", SwingConstants.CENTER);
        p2StateLabel.setSize(125, 56);
        p2StateLabel.setLocation(360, 550);
        p2StateLabel.setFont(font);
        add(p2StateLabel);

        p3StateLabel = new JLabel("", SwingConstants.CENTER);
        p3StateLabel.setSize(125, 56);
        p3StateLabel.setLocation(490, 550);
        p3StateLabel.setFont(font);
        add(p3StateLabel);

        p4StateLabel = new JLabel("", SwingConstants.CENTER);
        p4StateLabel.setSize(125, 56);
        p4StateLabel.setLocation(620, 550);
        p4StateLabel.setFont(font);
        add(p4StateLabel);

        JLabel coinLabel = new JLabel("배팅액", SwingConstants.CENTER);
        coinLabel.setSize(60, 56);
        coinLabel.setLocation(170, 600);
        coinLabel.setFont(smallFont);
        add(coinLabel);

        p1CoinLabel = new JLabel("", SwingConstants.CENTER); // default 0코인
        p1CoinLabel.setSize(125, 56);
        p1CoinLabel.setLocation(230, 600);
        p1CoinLabel.setFont(font);
        add(p1CoinLabel);

        p2CoinLabel = new JLabel("", SwingConstants.CENTER);
        p2CoinLabel.setSize(125, 56);
        p2CoinLabel.setLocation(360, 600);
        p2CoinLabel.setFont(font);
        add(p2CoinLabel);

        p3CoinLabel = new JLabel("", SwingConstants.CENTER);
        p3CoinLabel.setSize(125, 56);
        p3CoinLabel.setLocation(490, 600);
        p3CoinLabel.setFont(font);
        add(p3CoinLabel);

        p4CoinLabel = new JLabel("", SwingConstants.CENTER);
        p4CoinLabel.setSize(125, 56);
        p4CoinLabel.setLocation(620, 600);
        p4CoinLabel.setFont(font);
        add(p4CoinLabel);


        System.out.println("생성자 실행됨");

    }

    public void setStateLabel(int target, String name) {
        switch (target) {
            case 1:
                p1StateLabel.setText(name);
                break;
            case 2:
                p2StateLabel.setText(name);
                break;
            case 3:
                p3StateLabel.setText(name);
                break;
            case 4:
                p4StateLabel.setText(name);
                break;
        }
        repaint();
    }

    public void setCoinLabel(int target, int coin) {
        String text = "";
        if (coin > 0) {
            text = coin + "코인";
        }
        switch (target) {
            case 1:
                p1CoinLabel.setText(text);
                break;
            case 2:
                p2CoinLabel.setText(text);
                break;
            case 3:
                p3CoinLabel.setText(text);
                break;
            case 4:
                p4CoinLabel.setText(text);
                break;
        }
        repaint();
    }


    public void setTitleBorder(int target, String name) {
        switch (target) {
            case 1:
                p1TitleBorder.setTitle(name);
                break;
            case 2:
                p2TitleBorder.setTitle(name);
                break;
            case 3:
                p3TitleBorder.setTitle(name);
                break;
            case 4:
                p4TitleBorder.setTitle(name);
                break;
        }
        repaint();
    }

    private void paintDrawnCards() {

        for (int i = 0; i < drawnCards.size(); i++) {
            remove(drawnCards.get(i));
        }

        for (int i = drawnCards.size(); i > 0; i--) {
            add(drawnCards.get(i - 1));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        System.out.println("다시 그려짐");
    }

    public void paintScore(int target, int score) {
        String text = String.valueOf(score);
        switch (target) {
            case 0:
                dealerScoreLabel.setText(text);
                break;
            case 1:
                p1ScoreLabel.setText(text);
                break;
            case 2:
                p2ScoreLabel.setText(text);
                break;
            case 3:
                p3ScoreLabel.setText(text);
                break;
            case 4:
                p4ScoreLabel.setText(text);
                break;
        }
    }

    // Card 객체가 들어감, 0 = dealer , 1~4 = player
    public void paintCard(Card card, int target, int pos) {
        Point point;
        switch (target) {
            case 0: // dealer
                point = new Point(58, 162 + pos * 40);
                break;
            case 1: // p1
                point = new Point(254, 162 + pos * 40);
                break;
            case 2: // p2
                point = new Point(384, 162 + pos * 40);
                break;
            case 3: // p3
                point = new Point(514, 162 + pos * 40);
                break;
            case 4: // p4
                point = new Point(644, 162 + pos * 40);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + target);
        }

        JLabel imageLabel = new JLabel(card.getImage());
        imageLabel.setSize(78, 108);
        imageLabel.setLocation(point);
        System.out.println("필드 이미지 생성 좌표 x : " + point.x + " y : " + point.y);
        drawnCards.add(imageLabel);
        paintDrawnCards();
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // timer paint
        int start = 30;
        for (int i = 0; i < 9; i++) {
            if (i >= timer) {
                g.setColor(Color.CYAN);
            } else {
                g.setColor(Color.RED);

            }
            g.fillOval(start, 35, 60, 60);
            g.setColor(Color.black);
            g.drawOval(start, 35, 60, 60);
            g.drawOval(start, 35, 59, 59);
            start = start + 80;
        }
        // card panel paint
//        g.drawRect(20, 140, 150, 500);
//        g.drawRect(230, 140, 125, 500);
//        g.drawRect(360, 140, 125, 500);
//        g.drawRect(490, 140, 125, 500);
//        g.drawRect(620, 140, 125, 500);

//        paintDrawnCards();
    }

    public void setTimer(int timer) {
        this.timer = timer;
        repaint();
    }

    public void resetEnv() {
        dealerScoreLabel.setText("0");
        p1ScoreLabel.setText("0");
        p2ScoreLabel.setText("0");
        p3ScoreLabel.setText("0");
        p4ScoreLabel.setText("0");
        // clear all drawn cards
        for (int i = 0; i < drawnCards.size(); i++) {
            remove(drawnCards.get(i));
        }
        drawnCards.clear();
        repaint();
    }

    public void setCoinLabelX2(int idx) {
        String text;
        switch (idx) {
            case 1:
                text = p1CoinLabel.getText();
                p1CoinLabel.setText(text + "x2");
                break;
            case 2:
                text = p2CoinLabel.getText();
                p2CoinLabel.setText(text + "x2");
                break;
            case 3:
                text = p3CoinLabel.getText();
                p3CoinLabel.setText(text + "x2");
                break;
            case 4:
                text = p4CoinLabel.getText();
                p4CoinLabel.setText(text + "x2");
                break;
        }
        repaint();
    }

}

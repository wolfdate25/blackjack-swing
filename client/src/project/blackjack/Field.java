package project.blackjack;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.Vector;

public class Field extends JPanel {

    private int timer = 0;

    Vector<JLabel> drawnCards = new Vector<>(52); // 필드에 올라갈 카드
    Font font = new Font("Arial", Font.PLAIN, 24); // 사용할 폰트
    JLabel dealerScoreLabel;
    JLabel p1ScoreLabel;
    JLabel p2ScoreLabel;
    JLabel p3ScoreLabel;
    JLabel p4ScoreLabel;
    TitledBorder dealerTitleBorder;
    TitledBorder p1TitleBorder;
    TitledBorder p2TitleBorder;
    TitledBorder p3TitleBorder;
    TitledBorder p4TitleBorder;


    public Field() {
        setLayout(null);
        // Width = 700, Height = 569
        setBackground(Color.GREEN);

//        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/2c.jpg")));
//        imageLabel.setSize(78, 108);
//        imageLabel.setLocation(58,162);
//        add(imageLabel);

        // 사용할 폰트
        Font font = new Font("Arial", Font.PLAIN, 24);

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
        p1Panel.setBounds(230, 140, 125, 500);
        p1Panel.setBorder(p1TitleBorder);
        add(p1Panel);

        JLabel p2Panel = new JLabel();
        p2TitleBorder = new TitledBorder("Empty");
        p2TitleBorder.setBorder(lineBorder);
        p2Panel.setBounds(360, 140, 125, 500);
        p2Panel.setBorder(p2TitleBorder);
        add(p2Panel);

        JLabel p3Panel = new JLabel();
        p3TitleBorder = new TitledBorder("Empty");
        p3TitleBorder.setBorder(lineBorder);
        p3Panel.setBounds(490, 140, 125, 500);
        p3Panel.setBorder(p3TitleBorder);
        add(p3Panel);

        JLabel p4Panel = new JLabel();
        p4TitleBorder = new TitledBorder("Empty");
        p4TitleBorder.setBorder(lineBorder);
        p4Panel.setBounds(620, 140, 125, 500);
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
        p1ScoreLabel.setLocation(246, 597);
        p1ScoreLabel.setFont(font);
        add(p1ScoreLabel);

        p2ScoreLabel = new JLabel("0", SwingConstants.CENTER);
        p2ScoreLabel.setSize(94, 38);
        p2ScoreLabel.setLocation(376, 597);
        p2ScoreLabel.setFont(font);
        add(p2ScoreLabel);

        p3ScoreLabel = new JLabel("0", SwingConstants.CENTER);
        p3ScoreLabel.setSize(94, 38);
        p3ScoreLabel.setLocation(506, 597);
        p3ScoreLabel.setFont(font);
        add(p3ScoreLabel);

        p4ScoreLabel = new JLabel("0", SwingConstants.CENTER);
        p4ScoreLabel.setSize(94, 38);
        p4ScoreLabel.setLocation(636, 597);
        p4ScoreLabel.setFont(font);
        add(p4ScoreLabel);


        System.out.println("생성자 실행됨");

    }

    public void setTitleBorder(int target, String name) {
        switch (target) {
            case 0:
                p1TitleBorder.setTitle(name);
                break;
            case 1:
                p2TitleBorder.setTitle(name);
                break;
            case 2:
                p3TitleBorder.setTitle(name);
                break;
            case 3:
                p4TitleBorder.setTitle(name);
                break;
        }
        repaint();
    }

    public void reset() {
        drawnCards.clear();
    }

    private void paintDrawnCards() {

        for (int i = drawnCards.size(); i > 0; i--) {
            remove(drawnCards.get(i - 1));
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

        paintDrawnCards();
    }

    public void setTimer(int timer) {
        this.timer = timer;
        repaint();
    }
}

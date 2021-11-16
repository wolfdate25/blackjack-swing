package project.blackjack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lobby extends JFrame{
    public JPanel rootPanel;
    private JButton refreshRoom;
    private JButton makeRoom;
    private JLabel userLabel;
    private JLabel coinLabel;
    private JList roomList;
    private JButton enterRoom;

    Player player;

    public Lobby(Player player) {
        setTitle("Lobby");
        setContentPane(rootPanel);

        // setup Player
        this.player = player;

        userLabel.setText("username: "+ player.getName());
        coinLabel.setText("coin: " + player.getCoin());


        pack();
        setVisible(true);


        enterRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Room room = new Room(player);
                dispose();
            }
        });
    }
}

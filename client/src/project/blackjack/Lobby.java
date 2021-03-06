package project.blackjack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lobby extends JFrame {
    public JPanel rootPanel;
    Player player;
    DefaultListModel model;
    GameThread game;
    private JButton refreshRoom;
    private JButton makeRoom;
    private JLabel userLabel;
    private JLabel coinLabel;
    private JList roomList;
    private JButton enterRoom;
    private JButton loginButton;

    public Lobby(GameThread game) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {

        }
        setTitle("로비");
        setContentPane(rootPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.game = game;

        // setup Player
//        this.player = player;

        // setup room list
//        roomList = new JList(new DefaultListModel());


        if (player == null) {
            userLabel.setText("먼저 로그인을 하세요");
        } else {
            userLabel.setText("username: " + player.getName());
            coinLabel.setText("coin: " + player.getCoin());
        }
        model = new DefaultListModel();
        roomList.setModel(model);

        pack();
        setVisible(true);

        enterRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Room room = (Room) roomList.getSelectedValue();
                if (room == null) {
                    JOptionPane.showMessageDialog(null, "먼저 입장할 방을 선택해야합니다.");
                    return;
                }
                // 서버에 방 입장 요청 패킷 전송
                game.requestEnterRoom(room.name);
//                Room room = new Room(player);
//                dispose();
            }
        });
        refreshRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.removeAllElements();
                // 서버에 방 목록 요청 패킷 전송
                game.requestRoomList();
            }
        });
        makeRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomName = JOptionPane.showInputDialog("생성할 방 이름을 입력하세요");

                if (roomName != null) {
                    if (roomName.equals("")) {
                        JOptionPane.showMessageDialog(null, "생성할 방 이름을 한 글자 이상 입력해야합니다.");
                        return;
                    }
                    // 방 생성 요청 패킷 전송
                    game.requestCreateRoom(roomName);
                }
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login(game);
            }
        });
    }

    public void addRoom(String roomName, int players) {
        model.addElement(new Room(roomName, players));
    }

    public void setLabel(String name, int coin) {
        userLabel.setText("이름: " + name);
        coinLabel.setText("코인: " + coin);
    }

    class Room {
        private final String name;
        private final int players;

        public Room(String name, int players) {
            this.name = name;
            this.players = players;
        }

        @Override
        public String toString() {
            return name + " [" + players + "/4]";
        }
    }
}

package project.blackjack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Connect extends JFrame {
    public JPanel rootPanel;
    private JTextField portField;
    private JButton connectButton;
    private JTextField addressField;

    public Connect() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {

        }
        setTitle("접속");
        setContentPane(rootPanel);

        pack();
        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String address = addressField.getText();
                    int port = Integer.parseInt(portField.getText());
                    if (address.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "서버 주소를 입력해야 합니다.");
                    } else if (port == 0) {
                        JOptionPane.showMessageDialog(null, "포트를 입력해야 합니다.");
                    } else {
                        try {
//                            loginServer(username, password);
//                            Packet packet = (Packet) ois.readObject();
//                            if (packet.name.equals("true")) { // 로그인 성공
//                                Player player = new Player(username);
//                                player.setCoin(Integer.parseInt(packet.action));
                            GameThread thread = new GameThread(address, port);
                            thread.start();

                            dispose();
//                            } else { // 로그인 실패
//                                JOptionPane.showMessageDialog(null, "해당 계정 정보가 잘못되었거나 서버에 등록되어 있지 않습니다.");
                        } catch (IOException ioe) {
                            JOptionPane.showMessageDialog(null, "서버와의 연결에 실패했습니다.");

                        }
//                        } catch (IOException unknownHostException) {
//                            JOptionPane.showMessageDialog(null, "서버와의 연결에 실패했습니다.");
//                        } catch (ClassNotFoundException classNotFoundException) {
//                            classNotFoundException.printStackTrace();
//                        }
                    }
                } catch (NullPointerException npe) {
                    JOptionPane.showMessageDialog(null, "접속하기 위한 올바른 정보를 입력해야 합니다.");

                }

            }
        });
    }

}
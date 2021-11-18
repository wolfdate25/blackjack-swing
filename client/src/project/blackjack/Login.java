package project.blackjack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    public JPanel rootPanel;
    private JTextField portField;
    private JButton loginButton;
    private JTextField addressField;
    private JTextField nameField;


    public Login() {
        setTitle("Login");
        setContentPane(rootPanel);
        pack();
        setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = nameField.getText();
                    String address = addressField.getText();
                    int port = Integer.parseInt(portField.getText());
                    if (address.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "서버 주소를 입력해야 합니다.");
                    } else if (port == 0) {
                        JOptionPane.showMessageDialog(null, "포트를 입력해야 합니다.");
                    } else if (username.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "이름을 입력해야 합니다.");
                    } else {
                        GameThread thread = new GameThread(username,address,port);
                        thread.start();
                        dispose();
                    }
                } catch (NullPointerException npe) {
                    JOptionPane.showMessageDialog(null, "접속하기 위한 올바른 정보를 입력해야 합니다.");

                }

            }
        });
    }
}
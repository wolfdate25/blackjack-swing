package project.blackjack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    public JPanel rootPanel;
    private JTextField nameField;
    private JButton loginButton;


    public Login() {
        setTitle("Login");
        setContentPane(rootPanel);
        pack();
        setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = nameField.getText();
                if (!username.isEmpty()) {
                    Player player = new Player(nameField.getText());
                    Lobby room = new Lobby(player);
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "이름을 입력해야 합니다.");
                }

            }
        });
    }
}
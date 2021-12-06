package project.blackjack;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class Launcher extends JFrame {
    public JPanel rootPanel;
    private JTextField portField;
    private JButton startButton;

    public Launcher() {
        setContentPane(rootPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int port = Integer.parseInt(portField.getText());
                    if (port > 0) {
                        ServerThread thread = new ServerThread(port);
                        thread.start();
                        dispose();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "올바른 포트 번호를 입력해야 합니다.");
                }

            }
        });
    }

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
    }

}
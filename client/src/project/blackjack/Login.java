package project.blackjack;

import javax.swing.*;
import java.awt.event.*;

public class Login extends JDialog {
    GameThread game;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPasswordField passwordField;
    private JTextField nameField;

    public Login(GameThread game) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {

        }
        setTitle("로그인");
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        pack();

        this.game = game;

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("취소버튼 눌림");
                onCancel();
            }
        });
        setModalityType(ModalityType.APPLICATION_MODAL);

        setVisible(true);
    }

    private void onOK() {
        // add your code here
        String name = nameField.getText();
        if (name.equals("")) {
            JOptionPane.showMessageDialog(null, "이름을 입력해야 합니다.");
            return;
        }
        String password = String.valueOf(passwordField.getPassword());
//        System.out.println(String.valueOf(password));
        if (password.equals("")) {
            JOptionPane.showMessageDialog(null, "비밀번호를 입력해야 합니다.");
            return;
        }
        // 로그인 요청 전송
        game.login(name, password);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}

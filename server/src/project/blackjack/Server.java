package project.blackjack;

import javax.swing.*;
import java.net.ServerSocket;

public class Server extends JFrame {
    ServerSocket socket;
    private JPanel rootPanel;
    private JTextArea logArea;
    private JLabel userCount;
    private JLabel roomCount;

    public Server() {
        setContentPane(rootPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("서버 로그");
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {

        }
        pack();
        setVisible(true);


    }

    public void appendText(String str) {
        // textArea.append("사용자로부터 들어온 메세지 : " + str+"\n");
        logArea.append(str + "\n");
        logArea.setCaretPosition(logArea.getText().length());
    }

    protected void setUserCount(int count) {
        userCount.setText("접속자 수: " + count + "명");
    }

    public void setRoomCount(int count) {
        roomCount.setText("개설된 방: " + count + "개");

    }

}
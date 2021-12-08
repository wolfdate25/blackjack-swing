package project.blackjack;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Locale;

public class Server extends JFrame {
    private JPanel rootPanel;
    private JTextArea logArea;
    private JLabel userCount;
    private JLabel roomCount;

    ServerSocket socket;

    public Server() {
        setContentPane(rootPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Server Logger");
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
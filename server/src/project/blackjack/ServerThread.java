package project.blackjack;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
    /* 메인 서버 스레드 */

    ServerSocket socket;
    Server frame;
    Socket client_socket;

    LobbyService playerService;

    public ServerThread(int port) {
        try {
            socket = new ServerSocket(port); // 서버 생성
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame = new Server();
        frame.appendText("Server initializing");
        playerService = new LobbyService(frame);
    }

    public void run() {
        frame.appendText("Server Running");
        while (true) { // 사용자의 접속을 받기 위한 while 문
            try {
                frame.appendText("Waiting new clients...");
                client_socket = socket.accept();

                playerService.addPlayer(client_socket);
//                playerService.checkPlayerConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

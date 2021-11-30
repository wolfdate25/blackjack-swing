package project.blackjack;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameThread extends Thread {
    private static final long serialVersionUID = 1L;
    Socket socket;
    Player player = null;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private static Lobby lobby;
    private static Room room;

    private Packet packet;

    public GameThread(String address, int port) throws IOException {
//        try {
        socket = new Socket(address, port);
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.flush();
        ois = new ObjectInputStream(socket.getInputStream());

//        player = player;

//            Packet packet = new Packet("100", username, "");
//            oos.writeObject(packet);

        lobby = new Lobby(this);
        packet = new Packet("", "", "");

//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        requestRoomList();
    }

    protected void login(String name, String password) {
        // 1. 로그인 정보를 담은 패킷을 생성한다.
        Packet packet = new Packet("100", name, password);
        try {
            // 2. 서버에 패킷을 보낸다.
            oos.writeObject(packet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            Packet packet = readPacket();
            if (packet == null) break;
            if (packet.code == "") break;
            String msg = String.format("Code : %s, Username: %s, action: %s", packet.code, packet.name, packet.action);
            System.out.println(msg);

            lobbyAction(packet);
            if (room != null && room.isShowing()) {
                gameAction(packet);
            }
        }
    }

    public static void setLobbyVisible(boolean set) {
        lobby.setVisible(set);
    }

    private void lobbyAction(Packet packet) {
        switch (packet.code) {
            case "100": // 계정 정보 수신
                // 로그인이 성공하면
                if (packet.code.equals("100")) {
                    // 플레이어를 생성한다.
                    player = new Player(packet.name);
                    // 서버로부터 받은 코인을 입력한다.
                    player.setCoin(Integer.parseInt(packet.action));
                    lobby.setLabel(player.getName(), player.getCoin());
                }
                break;
            case "101": // 로그인 실패
                JOptionPane.showMessageDialog(null, "로그인 실패하였습니다.");
                break;
            case "102": // 방 목록 수신
                lobby.addRoom(packet.name, Integer.parseInt(packet.action));
                break;
            case "104": // 방 개설 실패
                break;
            case "103": // 방 개설 승인
            case "105": // 방 접속 승인
                String roomName = packet.name;
                room = new Room(this, roomName, player);
                lobby.setVisible(false);
                break;
            case "106": // 방 접속 실패
                JOptionPane.showMessageDialog(null, "게임이 이미 진행 중인 방에 입장할 수 없습니다.");
                break;
        }
    }

    private void gameAction(Packet packet) {
        switch (packet.code) {
            case "201": // 방 환경 정보 수신
                receiveRoomEnv(packet);
                break;
            case "203": // 코인 배팅 액션 수신
                receiveBetPlayer(packet.name, Integer.parseInt(packet.action));
                break;
            case "204": // 카드 드로우 액션 수신
                receiveDrawPlayer(packet.name, packet.action);
                break;
            case "205": // 플레이어 상태 수신
                receivePlayerState(packet.name, packet.action);
                break;
        }
    }

    private void receivePlayerState(String name, String action) {
        room.setPlayerState(name, action);
    }

    private void receiveDrawPlayer(String name, String card) {
        room.drawCard(name, card);
    }

    private void receiveRoomEnv(Packet packet) {
        switch (packet.name) {
            case "nplayer":
                room.addPlayer(new Player(packet.action));
                break;
            case "rplayer":
                room.removePlayer(packet.action);
                break;
            case "timer":
                int timer = Integer.parseInt(packet.action);
                room.setTimer(timer);
                break;
        }
    }

    private void receiveBetPlayer(String username, int coin) {
        room.setBetCoin(username, coin);
    }

    private Packet readPacket() {
        Packet packet;
        try {
            packet = (Packet) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            packet = null;
            e.printStackTrace();
        }
        return packet;
    }

    public void requestRoomList() {
        Packet packet = new Packet("102", "", "");
        try {
            oos.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestCreateRoom(String name) {
        Packet packet = new Packet("103", name, "");
        try {
            oos.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestBetCoin(String coin) {
        Packet packet = new Packet("203", coin, "");
        try {
            oos.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestLeaveRoom() {
        Packet packet = new Packet("202", "", "");
        room = null;
        try {
            oos.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestEnterRoom(String roomName) {
        Packet packet = new Packet("105", roomName, "");
        try {
            oos.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestRoomEnv() {
        Packet packet = new Packet("201", "", "");
        try {
            oos.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestDrawCard() {
        Packet packet = new Packet("204", "", "");
        try {
            oos.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

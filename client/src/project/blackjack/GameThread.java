package project.blackjack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class GameThread extends Thread {
    private static final long serialVersionUID = 1L;
    String username;
    Socket socket;
    Player player;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private static Lobby lobby;
    private static Room room;

    public GameThread(String username, String address, int port) {
        this.username = username;
        try {
            socket = new Socket(address, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());

            Packet packet = new Packet("100", username, "");
            oos.writeObject(packet);

            player = new Player(username);
            lobby = new Lobby(player, this);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        requestRoomList();
    }

    public void run() {
        while (true) {
            Packet packet = readPacket();
            if (packet == null) break;
            if (packet.code == "") break;
            String msg = String.format("Code : %s, Username: %s, action: %s", packet.code, packet.name, packet.action);
            System.out.println(msg);

            codeAction(packet);
        }
    }

    public static void setLobbyVisible(boolean set) {
        lobby.setVisible(set);
    }

    private void codeAction(Packet packet) {
        switch (packet.code) {
            case "102": // 방 목록 수신
                lobby.addRoom(packet.name, Integer.parseInt(packet.action));
                break;
            case "103": // 방 개설 승인
            case "200": // 방 접속 승인
                String roomName = packet.name;
                room = new Room(this, roomName, player);
                lobby.setVisible(false);
                break;
            case "201": // 방 환경 정보 수신
                receiveRoomEnv(packet);

        }
    }

    private void receiveRoomEnv(Packet packet) {
        switch (packet.name) {
            case "nplayer":
                room.addPlayer(new Player(packet.action));
                break;
            case "rplayer":
                room.removePlayer(packet.action);
            case "timer":
                int timer = Integer.parseInt(packet.action);
                room.setTimer(timer);
        }
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
        Packet packet = new Packet("200", roomName, "");
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
}

package project.blackjack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;

public class Player extends Thread {
    private Socket client_socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private LobbyService service;
    private Room room;

    private String username;

    public Player(Socket client_socket, LobbyService service) {
        this.client_socket = client_socket;
        this.service = service;
        try {
            oos = new ObjectOutputStream(client_socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(client_socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Packet packet;
        while (!client_socket.isClosed()) { // 사용자 접속을 계속 받는 while문
//                (client_socket == null) break;
            packet = readPacket();
            if (packet == null) {
//                logout();
                break;
            }
            service.frame.appendText(packet.code + " " + packet.name + " " + packet.action);
            codeAction(packet);

        }

    }

    // 동작 코드 실행
    private void codeAction(Packet packet) {
        if (packet.code.equals("100")) { // 로그인 요청 코드
            if (service.checkDuplicateUserName(packet.name)) {
                this.username = packet.name;
                service.frame.appendText(username + "님이 로그인하였습니다.");
            } else { // 중복된 이름으로 로그인 시도한 사용자 처리

            }
            return;
        }
        if (username == null || username.equals("")) { // 로그인 안한 사용자 감지
            return;
        }
        switch (packet.code) {
            case "102": // 방 목록 요청 코드
                service.frame.appendText("방목록 요청 동작 실행");
                sendRoomList();
                break;
            case "103": // 방 개설 요청 코드
                service.frame.appendText("방개설 요청 동작 실행");
                // packet.name - 입장할 방 이름
                if (!packet.name.equals("")) {
                    // 개설자는 자동으로 입장됨.
                    if (service.createRoom(packet.name, this)) {
                        // 방 개설 성공
                        sendPacket("103", room.getRoomName(), "");
                    } else {
                        // 방 개설 실패
                    }

                } else {
                    // 방 이름 없이 패킷이 왔을 때 동작
                }
                break;
            case "200": // 방 입장 요청 코드
                service.frame.appendText("방입장 요청 동작 실행");
                String name = packet.name;
                if (!name.equals("")) {

                    if (service.enterRoom(name, this)) {
                        // 방에 입장하였을 때 플레이어에게 전송할 패킷
                        sendPacket("200",room.getRoomName(),"");
                    } else {
                        // 방에 입장하지 못하였을 때 플레이어에게 전송할 패킷

                    }
                }
                break;
            case "201": // 방의 초기 환경 요청 코드
                room.sendNewPlayer(this);
                break;
            case "202": // 방 퇴장 요청 코드
                room.removePlayer(this);
                break;
        }
    }

    public void sendRoomList() {
        Iterator<Room> itr = service.getRoomIterator();
        while (itr.hasNext()) {
            Room room = itr.next();
            sendPacket("102", room.getRoomName(), String.valueOf(room.getPlayerCount()));
        }
    }

    private void sendPacket(String code, String name, String action) {
        Packet packet = new Packet(code, name, action);
        try {
            oos.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Packet readPacket() {
        Packet packet = null;
        try {
            packet = (Packet) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("패킷 읽기 실패");
            logout();
            return null;
        }
        return packet;
    }

    // 플레이어가 접속중인지 확인하기 위한 패킷 전송
    public boolean sendKnockPacket() {
        Packet packet = new Packet("1", "", "");
        try {
            oos.writeObject(packet);
        } catch (IOException e) {
            logout();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void logout() {
        try {
            oos.close();
            ois.close();
            client_socket.close();
            service.removePlayer(this);
            service.frame.appendText(username + "님이 로그아웃하였습니다.");
            service.refreshPlayerCount();

            if (room != null) {
                room.removePlayer(this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isClosed() {
        return client_socket.isClosed();
    }

    public String getUsername() {
        return username;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void sendNewPlayer(String username) {
        sendPacket("201","nplayer",username);
    }

    public void removePlayer(String username) {
        sendPacket("201","rplayer",username);
    }

    public void sendTimer(int timer) {
        sendPacket("201","timer",String.valueOf(timer));
    }
}
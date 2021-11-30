package project.blackjack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

public class Player extends Thread {
    private Socket client_socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private LobbyService service;
    private Room room;

    // parameters
    private String username;
    private int coin = 0;
    private int betCoin = 0;
    private Vector<Card> drawnCards;
    private boolean canEnterTheRoom = true;
    private int state = 0; // 0 = none(lose), 1 = win, 2 = blackjack, 3 = burst

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
        drawnCards = new Vector<>(6);
    }

    public void initParameters() {
        coin = 0;
        betCoin = 0;
        drawnCards.clear();
        state = 0;
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
            if (!service.checkDuplicateUserName(packet.name)) {
                if (service.db.playerLogin(packet.name, packet.action)) {
                    this.username = packet.name;
                    coin = service.db.getPlayerCoin(packet.name);
                    service.frame.appendText(username + "님이 로그인하였습니다.");
                    sendPacket("100", username, String.valueOf(coin));
                } else {
                    // 비밀번호가 틀리거나 가입되지 않은 경우
                    sendPacket("101", "", "");
                }
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
                if (!packet.name.equals("") && room == null) { // 패킷 오작동 & 중복 개설 검사
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
            case "105": // 방 입장 요청 코드
                service.frame.appendText("방입장 요청 동작 실행");
                String name = packet.name;
                if (!name.equals("") && room == null) { // 패킷 오작동 & 중복 입장 검사
                    if (canEnterTheRoom) {
                        if (service.enterRoom(name, this)) {
                            // 방에 입장하였을 때 플레이어에게 전송할 패킷
                            sendPacket("105", room.getRoomName(), "");
                        } else {
                            // 방에 입장하지 못하였을 때 플레이어에게 전송할 패킷(106)
                            sendPacket("106", "reject", "");
                        }
                    } else {
                        // 이미 입장중인 방이 있을 때 플레이어에게 전송할 패킷(106)
                        sendPacket("106", "already", "");
                    }
                }
                break;
            case "201": // 방의 초기 환경 요청 코드
                // 기존에 입장된 플레이어들을 전송
                room.sendOldPlayers(this);
                // 기존에 드로우된 카드들을 전송
                room.sendDrawnCards(this);

                break;
            case "202": // 방 퇴장 요청 코드
                // service.leaveRoom(roomcode, player);
                room.removePlayer(this);
                room = null;
                break;
            case "203": // 배팅 코드
                try {
                    betCoin(Integer.parseInt(packet.name));
                } catch (NumberFormatException e) {
                    betCoin(1);
                }
                break;
            case "204": // 드로우 코드
                if(room.canDraw() && state == 0) {
                    room.drawCard(this);
                }
                break;
        }
    }

    public void addCard(Card card) {
        drawnCards.add(card);
//        room.sendCard(username,card.getName());
    }

    public Vector<Card> getDrawnCards() {
        return drawnCards;
    }

    public int getScore() {
        Iterator<Card> itr = drawnCards.iterator();
        int sum = 0;
        while (itr.hasNext()) {
            Card card = itr.next();
            sum += card.getValue();
        }
        return sum;
    }

    private void betCoin(int coin) {
        // 게임이 시작중이지 않을 때
        if (!room.isPlaying()) {
            // 베팅할 코인이 가진 코인보다 적으면
            if (betCoin < this.coin) {
                // 배팅 금액을 늘린다
                betCoin = betCoin + coin;
                room.sendBetCoin(this);
            } else {
                // 베팅할 코인이 적을 때

            }
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
            logout();
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
/*    public boolean sendKnockPacket() {
        Packet packet = new Packet("1", "", "");
        try {
            oos.writeObject(packet);
        } catch (IOException e) {
            logout();
            e.printStackTrace();
            return false;
        }
        return true;
    }*/

    public int getBetCoin() {
        return betCoin;
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
                room = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isReady() {
        if (betCoin > 0) {
            return true;
        } else {
            return false;
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
        sendPacket("201", "nplayer", username);
    }

    public void sendRemovePlayer(String username) {
        sendPacket("201", "rplayer", username);
    }

    public void sendTimer(int timer) {
        sendPacket("201", "timer", String.valueOf(timer));
    }

    public void sendBetCoin(String username, int coin) {
        sendPacket("203", username, String.valueOf(coin));
    }

    public void sendCard(String playerName, String cardName) {
        sendPacket("204", playerName, cardName);

    }

    public boolean canEnterTheRoom() {
        return canEnterTheRoom;
    }

    public boolean setCanEnterTheRoom(boolean canEnterTheRoom) {
        return this.canEnterTheRoom = canEnterTheRoom;
    }

    public void sendEnvReset() {
        sendPacket("209", "", "");
    }

    public void setPlayerState(int state) {
        this.state = state;
        room.sendStatus(this);
    }

    public int getPlayerState() {
        return state;
    }

    public int getCoin() {
        return coin;
    }

    public int addCoin(int coin) {
        return this.coin += coin;
    }
    public int subCoin(int coin) {
        return this.coin -= coin;
    }

    public void sendStatus(String username, int status) {
        sendPacket("205", username, String.valueOf(status));
    }
}

package project.blackjack;

import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

public class LobbyService {
    private Vector<Player> playerList;
    private Vector<Room> roomList;
    Server frame;

    int codeIndex = 0;

    public LobbyService(Server frame) {
        playerList = new Vector<>(50);
        roomList = new Vector<>(10);
        this.frame = frame;

//        PlayerServiceThread thread= new PlayerServiceThread(this);
//        thread.start();
    }

    public void addPlayer(Socket client_socket) {
        Player newPlayer = new Player(client_socket, this);
        playerList.add(newPlayer);
        newPlayer.start();
//        frame.appendText("접속자 변경 : " + playerList.size());
        refreshPlayerCount();
    }

    public void removePlayer(Player player) {
        playerList.remove(player);
    }

    public void refreshPlayerCount() {
        frame.setUserCount(playerList.size());
    }

    public void refreshRoomCount() {
        frame.setRoomCount(roomList.size());
    }



    public boolean createRoom(String roomName,Player player) {
        // 방 이름 중복 검사
        Iterator<Room> itr = roomList.iterator();
        while(itr.hasNext()) {
            if(itr.next().getRoomName().equals(roomName)) {
                return false;
            }
        }
        // 방 생성
        Room room = new Room(roomName, this, player);
        roomList.add(room);
        refreshRoomCount();

        // 스레드 실행
        room.start();
        return true;
    }

    public boolean enterRoom(String roomCode, Player player) {
        Iterator<Room> itr = roomList.iterator();
        while (itr.hasNext()) {
            Room room = itr.next();
            if (room.getRoomName().equals(roomCode)) {
                room.addPlayer(player);
                return true;
            }
        }
        return false;
    }

    public void leaveRoom(String roomCode, Player player) {
        Iterator<Room> itr = roomList.iterator();
        while (itr.hasNext()) {
            Room room = itr.next();
            if (room.getRoomName().equals(roomCode)) {
                room.removePlayer(player);
            }
        }
    }


    public void removeRoom(Room room) {
        roomList.remove(room);
        refreshRoomCount();
    }

    public Boolean checkDuplicateUserName(String name) {
        Iterator<Player > itr = playerList.iterator();
        while(itr.hasNext()) {
            if (itr.next().getUsername() == name) {
                return false;
            }
        }
        return true;
    }

    public Iterator<Room> getRoomIterator() {
        return roomList.iterator();
    }
}

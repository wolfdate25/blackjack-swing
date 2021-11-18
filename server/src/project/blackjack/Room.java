package project.blackjack;

import java.util.Iterator;
import java.util.Vector;

public class Room extends Thread {
    private Vector<Player> players;
    private LobbyService service;
    private String roomName;

    private boolean beEnd = false;

    public Room(String roomName, LobbyService lobbyService, Player player) {
        this.roomName = roomName;
        this.service = lobbyService;
        players = new Vector<>(4);

        addPlayer(player);
    }

    public boolean addPlayer(Player player) {
        player.setRoom(this);
        if (players.size() < 4) {
            // 모든 플레이어에게 새로운 플레이어를 추가
            Iterator<Player> itr = players.iterator();
            while (itr.hasNext()) {
                Player otherPlayer = itr.next();
                otherPlayer.sendNewPlayer(player.getUsername());
            }
            // 백터에 새로운 플레이어를 추가
            players.add(player);
            return true;
        } else {
            return false;
        }
    }

    public int getPlayerCount() {
        return players.size();
    }

    public void removePlayer(Player player) {
        players.remove(player);
        if (players.size() == 0) {
//            service.removeRoom(this);
//            System.out.println("방이 닫힙니다.");
            beEnd = true;
        } else {
            // 플레이어 제거 정보 보내기
            Iterator<Player> itr = players.iterator();
            while (itr.hasNext()) {
                Player otherPlayer = itr.next();
                otherPlayer.removePlayer(player.getUsername());
            }
        }
    }

    public String getRoomName() {
        return roomName;
    }

    // 스레드
    public void run() {
        int timer = 0;
        try {
            while (!beEnd) {
                Thread.sleep(1000);
                sendTimer(timer++);
                timer = timer % 10;

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.removeRoom(this);
    }

    private void sendTimer(int timer) {
        Iterator<Player> itr = players.iterator();
        Player player;
        while (itr.hasNext()) {
            player = itr.next();
            player.sendTimer(timer);
        }
    }

    public void sendNewPlayer(Player player) {
        Iterator<Player> itr = players.iterator();
        Player otherPlayer;
        while (itr.hasNext()) {
            otherPlayer = itr.next();
            if (!otherPlayer.getUsername().equals(player.getUsername())) {
                player.sendNewPlayer(otherPlayer.getUsername());

            }
        }
    }
}

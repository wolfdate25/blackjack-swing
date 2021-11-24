package project.blackjack;

import java.util.Iterator;
import java.util.Vector;

public class Room extends Thread {
    private Vector<Player> players;
    private LobbyService service;
    private String roomName;
    private int timer = 0;

    private boolean keepRunning = true;

    private boolean isStart = false;

    Deck deck;
    Dealer dealer;


    public Room(String roomName, LobbyService lobbyService, Player player) {
        this.roomName = roomName;
        this.service = lobbyService;
        players = new Vector<>(4);
        deck = new Deck();

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
            keepRunning = false;
        } else {
            // 플레이어 제거 정보 보내기
            Iterator<Player> itr = players.iterator();
            while (itr.hasNext()) {
                Player otherPlayer = itr.next();
                otherPlayer.sendRemovePlayer(player.getUsername());
            }
        }
    }

    public String getRoomName() {
        return roomName;
    }

    // 스레드
    public void run() {
//        boolean keepRunning = true;
        try {
            while (keepRunning) {
                if (beforeReady()) {
                    dealer = new Dealer(this);
                    // 한명이라도 게임에 준비한 사람이 존재할 때
                    startGame();
                } else {
                    // 아무도 게임에 준비하지 않았을 때
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.removeRoom(this);
        service.frame.appendText(roomName +" 방이 정리됩니다");
    }

    private void startGame() throws InterruptedException {
        // 딜러와 레디한 플레이어에게 카드를 2개씩 던진다.
        for(int i = 0; i <2;i++) {
            Thread.sleep(1000);

            dealer.drawCard();
            for(int p = 0; p<players.size();p++) {
                Thread.sleep(1000);

                Player player = players.get(p);
                if(player.isReady()) {
                    player.drawCard();
                }
            }
        }

        // 타이머가 종료될 때까지 플레이어가 추가로 할 액션을 결정한다.

        // 플레이어가 취할 액션을 정의한다.
    }

    public boolean isStart() {
        return isStart;
    }

    // 플레이어가 한명 이상 준비하지 않을 경우 동작하는 타이머
    // 10초 타이머가 끝난 후 준비된 플레이어가 있을 경우 true 반환
    private boolean beforeReady() throws InterruptedException {
        Thread.sleep(1000);
        sendTimer(timer++);
        // 타이머가 10에 도달할 때
        if ((timer = timer % 10) == 0) {
            // 준비(배팅)된 플레이어가 있는지 검사
            Iterator<Player> itr = players.iterator();
            while (itr.hasNext()) {
                Player player = itr.next();
                if (player.isReady()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void sendTimer(int timer) {
        Iterator<Player> itr = players.iterator();
        Player player;
        while (itr.hasNext()) {
            player = itr.next();
            player.sendTimer(timer);
        }
    }

    public void sendBetCoin(Player player) {
        Iterator<Player> itr = players.iterator();
        Player allPlayer;
        while (itr.hasNext()) {
            allPlayer = itr.next();
            allPlayer.sendBetCoin(player.getUsername(), player.getBetCoin());
        }
    }

    public void sendCard(String playerName, String cardName) {
        Iterator<Player> itr = players.iterator();
        Player allPlayer;
        while (itr.hasNext()) {
            allPlayer = itr.next();
            allPlayer.sendCard(playerName,cardName);
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

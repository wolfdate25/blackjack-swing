package project.blackjack;

import java.util.Iterator;
import java.util.Vector;

public class Room extends Thread {
    private Vector<Player> players;
    private Vector<Player> beRemovePlayers;
    private LobbyService service;
    private String roomName;
    private int timer = 0;

    private boolean keepRunning = true;
    private boolean isPlaying = false;
    private boolean canDraw = false;

    Deck deck;
    Dealer dealer;


    public Room(String roomName, LobbyService lobbyService, Player player) {
        this.roomName = roomName;
        this.service = lobbyService;
        players = new Vector<>(4);
        beRemovePlayers = new Vector<>(4);
        deck = new Deck();
        dealer = new Dealer(this);

        addPlayer(player);
    }

    public boolean addPlayer(Player player) {
        player.initParameters();
        if (players.size() < 4) {
            player.setRoom(this);
            player.setCanEnterTheRoom(false);
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
        // 게임이 진행중이면 나중에 제거함
        if (isPlaying) {
            // 제거 목록에 추가
            beRemovePlayers.add(player);
        } else {
            players.remove(player);
            player.setCanEnterTheRoom(true);
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
                    // 한명이라도 게임에 준비한 사람이 존재할 때
                    // 게임을 시작한다.
                    startGame();

                    // 게임이 종료되었을 때
                    isPlaying = false;
                    Thread.sleep(1000);
                    // 보상을 지급한다
                    sendReward();

                    // 딜러의 파라미터를 초기화한다
                    dealer.reset();
                    // 플레이어의 파라미터를 초기화한다
                    for (Player player : players) {
                        player.initParameters();
                    }
                    // 미뤄둔 플레이어를 제거한다.
                    for (Player player : beRemovePlayers) {
                        removePlayer(player);
                    }
                    beRemovePlayers.clear();
                    // 남아있는 플레이어에게 환경 초기화 패킷을 보낸다.
                    Iterator<Player> itr = players.iterator();
                    while (itr.hasNext()) {
                        Player otherPlayer = itr.next();
                        otherPlayer.sendEnvReset();
                    }
                } else {
                    // 아무도 게임에 준비하지 않았을 때
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.removeRoom(this);
        service.frame.appendText(roomName + " 방이 정리됩니다");
    }

    private void startGame() throws InterruptedException {
        // 게임을 시작한다
        isPlaying = true;
        Iterator<Player> itr;
        // 딜러와 레디한 플레이어에게 카드를 2개씩 던진다.
        for (int i = 0; i < 2; i++) {
            Thread.sleep(1000);

            dealer.drawCard();
            itr = players.iterator();
            while (itr.hasNext()) {
                Thread.sleep(1000);

                Player player = itr.next();
                if (player.isReady()) {
                    drawCard(player);
                }
            }
        }

        // 타이머가 종료될 때까지 플레이어의 드로우를 허용한다.
        canDraw = true;
        // 타이머를 재생한다.
        while (isPlaying) {
            Thread.sleep(1000);
            sendTimer(timer++);
            // 타이머가 10에 도달 했을 때
            if ((timer = timer % 10) == 0) {
                // 플레이어의 드로우를 모두 막는다.
                canDraw = false;
                break;
            }
        }

        // 딜러의 점수가 16 이하면 무조건 히트, 17 이상이면 무조건 스테이
        while (dealer.getScore() < 17) {
            Thread.sleep(1000);
            dealer.drawCard();
        }

        // 플레이어의 점수를 비교해 이겼는지 졌는지 판단한다.
        Thread.sleep(1000);
        itr = players.iterator();
        while (itr.hasNext()) {
            Player player = itr.next();
            if (player.isReady()) {
                if (player.getPlayerState() == 0 && player.getScore() > dealer.getScore()) {
                    player.setPlayerState(1);
                }
            }
        }
    }

    // 카드를 뽑아 플레이어에게 추가한다
    public void drawCard(Player player) {
        timer = 0; // 카드를 뽑으면 타이머가 초기화
        Card card = deck.getRandomCard();
        player.addCard(card);
        sendCard(player.getUsername(), card.getName());

        // 플레이어의 점수를 보고 버스트, 블랙잭 여부를 판단한다.
        if (player.getScore() == 21) {
            player.setPlayerState(2); // blackjack
        } else if (player.getScore() > 21) {
            player.setPlayerState(3); // burst
        }
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

    // 타이머를 전송하는 메소드
    private void sendTimer(int timer) {
        Iterator<Player> itr = players.iterator();
        Player player;
        while (itr.hasNext()) {
            player = itr.next();
            player.sendTimer(timer);
        }
    }

    // 방에 입장된 플레이어에게 배팅된 금액을 전송하는 메소드
    public void sendBetCoin(Player player) {
        Iterator<Player> itr = players.iterator();
        Player allPlayer;
        while (itr.hasNext()) {
            allPlayer = itr.next();
            allPlayer.sendBetCoin(player.getUsername(), player.getBetCoin());
        }
    }

    // 방에 입장된 플레이어에게 카드를 전송하는 메소드
    public void sendCard(String playerName, String cardName) {
        Iterator<Player> itr = players.iterator();
        Player allPlayer;
        while (itr.hasNext()) {
            allPlayer = itr.next();
            allPlayer.sendCard(playerName, cardName);
        }
    }

    // 기존 플레이어의 이름을 새로운 플레이어에게 전송하는 메소드
    public void sendOldPlayers(Player player) {
        Iterator<Player> itr = players.iterator();
        Player otherPlayer;
        while (itr.hasNext()) {
            otherPlayer = itr.next();
            if (!otherPlayer.getUsername().equals(player.getUsername())) {
                player.sendNewPlayer(otherPlayer.getUsername());

            }
        }
    }

    // 기존에 드로우된 카드들을 새로운 플레이어에게 전송하는 메소드
    public void sendDrawnCards(Player player) {
        // 딜러의 카드 전송
        for (Card dealerCard : dealer.getDrawnCards()) {
            player.sendCard("dealer", dealerCard.getName());
        }

        // 플레이어들의 카드 전송
        Iterator<Player> itr = players.iterator();
        Player otherPlayer;
        while (itr.hasNext()) {
            otherPlayer = itr.next();
            // 자신은 제외
            if (!otherPlayer.getUsername().equals(player.getUsername())) {
                Vector<Card> cards = otherPlayer.getDrawnCards();
                Iterator<Card> cardItr = cards.iterator();
                while (cardItr.hasNext()) {
                    Card card = cardItr.next();
                    player.sendCard(otherPlayer.getUsername(), card.getName());
                }
            }
        }

    }

    // send player status to all players
    public void sendStatus(Player player) {
        Iterator<Player> itr = players.iterator();
        while(itr.hasNext()) {
            Player otherPlayer  = itr.next();
            otherPlayer.sendStatus(player.getUsername(),player.getPlayerState());
        }
    }

    // 보상을 지급하는 함수
    private void sendReward() {
        Iterator<Player> itr = players.iterator();
        while (itr.hasNext()) {
            Player player = itr.next();
            if (player.getPlayerState() == 1) { // 승리
                player.addCoin(player.getBetCoin() * 2);
//                service.db.setPlayerCoin(player.getUsername(),player.getCoin());
            } else if (player.getPlayerState() == 2) { // blackjack
                player.addCoin(player.getBetCoin() * 3);
            } else if (player.getPlayerState() == 3) { // burst
                player.subCoin(player.getBetCoin());
            } else { // lose
                // ready 안한 플레이어는 반영 x
                if(player.isReady()) {
                    player.subCoin(player.getBetCoin());
                }
            }
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean canDraw() {
        return canDraw;
    }
}

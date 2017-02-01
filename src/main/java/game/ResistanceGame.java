package main.java.game;

import main.java.exceptions.PlayerNotFoundException;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ResistanceGame {

    public enum GameStatus {
        AWAITING_PLAYERS, AWAITING_MISSION, AWAITING_MISSION_VOTE, AWAITING_MISSION_RESULT
    }

    private enum GameSettings{

        FIVE    (3, 2,  new int[]{2,3,2,3,3},   new int[]{1,1,1,1,1}),
        SIX     (4, 2,  new int[]{2,3,4,3,4},   new int[]{1,1,1,1,1}),
        SEVEN   (4, 3,  new int[]{2,3,3,4,4},   new int[]{1,1,1,2,1}),
        EIGHT   (5, 3,  new int[]{3,4,4,5,5},   new int[]{1,1,1,2,1}),
        NINE    (6, 3,  new int[]{3,4,4,5,5},   new int[]{1,1,1,2,1}),
        TEN     (6, 4,  new int[]{3,4,4,5,5},   new int[]{1,1,1,2,1});

        private final int numResistance;
        private final int numSpies;
        private final int[] numPlayersOnMission;
        private final int[] numSkullsNeeded;

        GameSettings(int numResistance, int numSpies, int[] numPlayersOnMission, int[] numSkullsNeeded){

            this.numSpies = numSpies;
            this.numResistance = numResistance;
            this.numPlayersOnMission = numPlayersOnMission;
            this.numSkullsNeeded = numSkullsNeeded;

        }

    }

    private GameStatus currentStatus;

    private User[] players;
    private ArrayList<User> playerQueue;

    private TextChannel channel;

    private GameSettings gameSettings;

    private int numMissionsCompleted;

    private User[] spies;
    private User[] resistance;

    private User commander;
    private int nextCommander;

    private User[] currentMission;
    private HashMap<User, Boolean> voteTally;
    private HashMap<User, Boolean> missionResult;

    private int numSuccesses;
    private int numFailures;

    public ResistanceGame(User host, TextChannel channel){

        playerQueue = new ArrayList<>();

        playerQueue.add(host);

        currentStatus = GameStatus.AWAITING_PLAYERS;

        this.channel = channel;

        this.numMissionsCompleted = 0;
        this.numSuccesses = 0;
        this.numFailures = 0;

    }

    public GameStatus getCurrentStatus(){

        return currentStatus;

    }

    public void addPlayer(User player){

        playerQueue.add(player);

    }

    /**
     *
     * Removes the specified player from the list of players
     *
     * @param player    The player to remove
     * @return          If the player was in the list
     */
    public boolean removePlayer(User player){

        if(userIsHost(player) && playerQueue.size() != 1){

            GameMessages.sendMessageToGame(this.channel, player.getName() + " has left the game, " + playerQueue.get(1).getName() + " is now the host.");

        }

        return playerQueue.remove(player);

    }

    public boolean hasPlayer(User player){

        return playerQueue.contains(player);

    }

    public boolean hasPlayer(String playerName){

        for(User u : players){

            if(u.getName().toLowerCase().equals(playerName.toLowerCase())){

                return true;

            }

        }

        return false;

    }

    public boolean playerIsSpy(User player){

        for(User u : spies){

            if(u.equals(player)){

                return true;

            }

        }

        return false;

    }

    public User getPlayer(String playerName) throws PlayerNotFoundException{

        for(User u : players){

            if(u.getName().toLowerCase().equals(playerName.toLowerCase())){

                return u;

            }

        }

        throw new PlayerNotFoundException();

    }

    public boolean userIsHost(User player){

        return playerQueue.get(0).equals(player);

    }

    public User getCommander() {

        return this.commander;

    }

    public int getNumPlayers(){

        return playerQueue.size();

    }

    public int getNumPlayersThisMission() {

        return this.gameSettings.numPlayersOnMission[this.numMissionsCompleted];

    }

    public boolean isOnCurrentMission(User player) {

        for (User other : this.players) {
            if (player == other) {
                return true;
            }
        }

        return false;

    }

    public void startGame(){

        switch(getNumPlayers()){

            case 5:
                this.gameSettings = GameSettings.FIVE;
                break;
            case 6:
                this.gameSettings = GameSettings.SIX;
                break;
            case 7:
                this.gameSettings = GameSettings.SEVEN;
                break;
            case 8:
                this.gameSettings = GameSettings.EIGHT;
                break;
            case 9:
                this.gameSettings = GameSettings.NINE;
                break;
            case 10:
                this.gameSettings = GameSettings.TEN;
                break;

        }

        this.setRoles();

        this.nextRound();

    }

    private void setRoles() {

        int numPlayers = this.getNumPlayers();

        this.nextCommander = (int) (Math.random() * numPlayers);

        this.players = this.playerQueue.toArray(new User[numPlayers]);

        ArrayList<User> remainingPlayers = new ArrayList<>(this.playerQueue);

        this.spies = new User[this.gameSettings.numSpies];

        for (int i = 0; i < this.gameSettings.numSpies; i++) {

            int spy = (int) (Math.random() * remainingPlayers.size());
            this.spies[i] = remainingPlayers.remove(spy);

        }

        this.resistance = remainingPlayers.toArray(new User[this.gameSettings.numResistance]);

        GameMessages.sendSpyIntro(this.spies);
        GameMessages.sendResistanceIntro(this.resistance);

    }

    private void nextRound() {

        this.currentStatus = GameStatus.AWAITING_MISSION;

        this.commander = this.players[this.nextCommander];
        this.nextCommander = (this.nextCommander + 1) % this.players.length;
        this.currentMission = new User[this.getNumPlayersThisMission()];

        this.voteTally = new HashMap<>();

        GameMessages.sendNewRoundMessage(this.channel, this.commander, this.getNumPlayersThisMission());

    }

    public void pushToVote(User[] members) {

        this.currentMission = members;
        this.currentStatus = GameStatus.AWAITING_MISSION_VOTE;

    }

    public void addVote(User player, boolean vote) {

        this.voteTally.put(player, vote);

        if (this.voteTally.size() == this.players.length) {
            this.runMission();
        }

    }

    private void runMission() {

        this.currentStatus = GameStatus.AWAITING_MISSION_RESULT;

        this.missionResult = new HashMap<>();

        GameMessages.sendVoteResults(this.channel, this.voteTally);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (this.voteResult(this.voteTally)) {
            GameMessages.sendMissionIntro(this.channel, this.currentMission);
        } else {
            this.nextRound();
        }

    }

    private boolean voteResult(HashMap<User, Boolean> votes) {

        int passVotes = 0;
        for (User player : this.players) {

            passVotes += (votes.get(player) ? 1 : 0);

        }

        return passVotes > (this.players.length / 2);

    }

    public void addMissionResult(User player, boolean result) {

        this.missionResult.put(player, result);

        if (this.missionResult.size() == this.getNumPlayersThisMission()) {
            this.completeMission();
        }

    }

    private void completeMission() {

        int numFails = 0;

        for (User player : this.missionResult.keySet()) {

            numFails += (this.missionResult.get(player) ? 0 : 1);

        }

        if (numFails <= this.gameSettings.numSkullsNeeded[this.numMissionsCompleted]) {

            this.missionSucceeded(this.getNumPlayersThisMission() - numFails, numFails);

        } else {

            this.missionFailed(this.getNumPlayersThisMission() - numFails, numFails);

        }

    }

    private void missionSucceeded(int passes, int fails) {

        GameMessages.sendMissionSuccess(this.channel, passes, fails);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        numSuccesses++;
        if (numSuccesses == 3) {
            this.resistanceWin();
        }

    }

    private void missionFailed(int passes, int fails) {

        GameMessages.sendMissionFail(this.channel, passes, fails);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        numFailures++;
        if (numFailures == 3) {
            this.spiesWin();
        }

    }

    private void resistanceWin() {

        

    }

    private void spiesWin() {



    }


}

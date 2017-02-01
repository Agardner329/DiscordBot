package main.java.game;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.impl.MessageImpl;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Alex on 1/27/2017.
 */
public class ResistanceGame {

    public enum GameStatus {
        WAITING_FOR_PLAYERS, AWAITING_MISSION, AWAITING_MISSION_VOTE, AWAITING_FLAGS;
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
    private User[] currentMission;

    public ResistanceGame(User host, TextChannel channel){

        playerQueue = new ArrayList<>();

        playerQueue.add(host);

        currentStatus = GameStatus.WAITING_FOR_PLAYERS;

        this.channel = channel;

        this.numMissionsCompleted = 0;

    }

    public GameStatus getCurrentStatus(){

        return currentStatus;

    }

    /**
     * Adds a player to the list of players
     *
     * @param player
     */
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

    public boolean userIsHost(User player){

        return playerQueue.get(0).equals(player);

    }

    public int getNumPlayers(){

        return playerQueue.size();

    }

    public void startGame(){

        this.currentStatus = GameStatus.AWAITING_MISSION;

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

    }

    private void setRoles() {

        this.players = this.playerQueue.toArray(new User[this.playerQueue.size()]);

        ArrayList<User> remainingPlayers = new ArrayList<>(this.playerQueue);

        this.spies = new User[this.gameSettings.numSpies];

        for (int i = 0; i < this.gameSettings.numSpies; i++) {

            int spy = (int) (Math.random() * remainingPlayers.size());
            this.spies[i] = remainingPlayers.remove(spy);

        }

        this.resistance = remainingPlayers.toArray(new User[this.gameSettings.numResistance]);

    }

    private void nextRound() {

    }

    public void setMission(User[] players){

        this.currentStatus = GameStatus.AWAITING_MISSION_VOTE;

        currentMission = players;

    }

    public void addVote(User player, boolean pass){



    }

}

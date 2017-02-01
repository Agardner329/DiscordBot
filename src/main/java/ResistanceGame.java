package main.java;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.io.File;
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

    private static final String SPY_ICON = "https://i.gyazo.com/b6beda8a8045e9d933ebdd1323d45690.png";
    private static final String TOWN_ICON = "https://i.gyazo.com/a944cefaf424b9838630b7ee34d88c31.png";
    private static final String PASS_ICON = "https://i.gyazo.com/817dd13225d03360f66a5c2c688747c9.png";
    private static final String FAIL_ICON = "https://i.gyazo.com/a241757e5cf4c93780576893c87a512f.png";

    private GameStatus currentStatus;

    private ArrayList<User> players;

    private TextChannel channel;

    private GameSettings gameSettings;

    private int numMissionsCompleted;

    private User[] spies;
    private User[] resistance;

    private User commander;
    private User[] currentMission;

    public ResistanceGame(User host, TextChannel channel){

        players = new ArrayList<>();

        players.add(host);

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

        players.add(player);

    }

    /**
     *
     * Removes the specified player from the list of players
     *
     * @param player    The player to remove
     * @return          If the player was in the list
     */
    public boolean removePlayer(User player){

        if(userIsHost(player) && players.size() != 1){

            sendMessageToGame(player.getName() + " has left the game, " + players.get(1).getName() + " is now the host.");

        }

        return players.remove(player);

    }

    public boolean hasPlayer(User player){

        return players.contains(player);

    }

    public boolean userIsHost(User player){

        return players.get(0).equals(player);

    }

    public int getNumPlayers(){

        return players.size();

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

        this.spies = new User[gameSettings.numSpies];
        this.resistance = new User[gameSettings.numResistance];

        this.setRoles(this.players, gameSettings.numSpies);





    }

    private void setRoles(ArrayList<User> players, int numSpies) {
        ArrayList<User> remainingPlayers = new ArrayList<>(players);
        for (int i = 0; i < numSpies; i++) {
            int spy = (int) (Math.random() * remainingPlayers.size());
            this.spies[i] = remainingPlayers.remove(spy);
        }
        for (int i = 0; i < remainingPlayers.size(); i++) {
            this.resistance[i] = remainingPlayers.get(i);
        }
    }

    private void nextRound() {

    }

    public void setMission(User[] players){

        this.currentStatus = GameStatus.AWAITING_MISSION_VOTE;

        currentMission = players;

    }

    public void addVote(User player, boolean pass){



    }

    private void sendMessageToResistance(String message){

        for(User u : resistance){

            sendMessageToPlayer(u, message);

        }

    }

    private void sendMessageToSpies(String message){

        for(User u : spies){

            sendMessageToPlayer(u, message);

        }

    }

    private void sendMessageToPlayer(User player, String message){

        player.getPrivateChannel().sendMessage(message).queue();

    }

    private void sendMessageToGame(String message){

        channel.sendMessage(message).queue();

    }

}

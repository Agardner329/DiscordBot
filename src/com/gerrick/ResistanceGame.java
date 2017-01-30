package com.gerrick;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;

/**
 * Created by Alex on 1/27/2017.
 */
public class ResistanceGame {

    public enum gameStatus{
        WAITING_FOR_PLAYERS, AWAITING_MISSION, AWAITING_MISSION_VOTE, AWAITING_FLAGS;
    }

    private enum roundNumbers{

    }

    private enum numFactions{

    }

    private static final String SPY_ICON = "https://i.gyazo.com/b6beda8a8045e9d933ebdd1323d45690.png";
    private static final String TOWN_ICON = "https://i.gyazo.com/a944cefaf424b9838630b7ee34d88c31.png";
    private static final String PASS_ICON = "https://i.gyazo.com/817dd13225d03360f66a5c2c688747c9.png";
    private static final String FAIL_ICON = "https://i.gyazo.com/a241757e5cf4c93780576893c87a512f.png";

    private gameStatus currentStatus;

    private ArrayList<User> players;

    private TextChannel channel;

    public ResistanceGame(User host, TextChannel channel){

        players = new ArrayList<>();

        players.add(host);

        currentStatus = gameStatus.WAITING_FOR_PLAYERS;

        this.channel = channel;

    }

    public gameStatus getCurrentStatus(){

        return currentStatus;

    }

    /**
     * Adds a player to the list of players
     *
     * @param player
     */
    public void addPlayer(User player){
        if(!players.contains(player) && players.size() < 10){
            players.add(player);
        }
    }

    /**
     *
     * Removes the specified player from the list of players
     *
     * @param player    The player to remove
     * @return          If the player was in the list
     */
    public boolean removePlayer(User player){

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

        this.currentStatus = gameStatus.AWAITING_MISSION;

    }

}

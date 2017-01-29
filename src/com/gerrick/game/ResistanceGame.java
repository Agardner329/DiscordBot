package com.gerrick.game;

import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;

/**
 * Created by Alex on 1/27/2017.
 */
public class ResistanceGame {

    private ArrayList<User> players;

    public ResistanceGame(User host){

        players = new ArrayList<>();

        players.add(host);

    }

    /**
     * Adds a player to the list of players
     *
     * @param player
     */
    public void addPlayer(User player){
        if(!players.contains(player)){
            players.add(player);
        }
    }

    public boolean removePlayer(User player){
        return players.remove(player);
    }



}

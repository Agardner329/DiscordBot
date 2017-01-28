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

    public void addPlayer(User player){
        players.add(player);
    }

    public void removePlayer(User player){
        players.remove(player);
    }



}

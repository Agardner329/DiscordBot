package com.gerrick;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.HashMap;

/**
 * Created by Alex on 1/29/2017.
 */
public class GameManager {

    private static HashMap<TextChannel, ResistanceGame> GAMES = new HashMap<>();

    public static boolean hasGameOnChannel(TextChannel channel){

        return GAMES.containsKey(channel);

    }

    public static boolean playerIsInGame(User player){

        for(TextChannel channel : GAMES.keySet()){

            if(GAMES.get(channel).hasPlayer(player)){

                return true;

            }

        }

        return false;

    }

    public static ResistanceGame getGameOf(User player){

        for(TextChannel channel : GAMES.keySet()){

            if(GAMES.get(channel).hasPlayer(player)){

                return GAMES.get(channel);

            }

        }

        return null;

    }

    public static ResistanceGame getGame(TextChannel channel){

        return GAMES.get(channel);

    }

    public static boolean removeGame(TextChannel channel){

        try {

            GAMES.remove(channel);

        } catch(NullPointerException e){

            return false;

        }

        return true;

    }

    public static void addGame(TextChannel channel, ResistanceGame game){

        GAMES.put(channel, game);

    }

}

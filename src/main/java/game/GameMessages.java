package main.java.game;

import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.io.File;
import java.io.IOException;

/**
 * Created by Alex Gardner on 1/31/2017.
 */
class GameMessages {

    protected static void sendMessageToPlayers(User[] players, String message){

        for(User player : players){

            sendMessageToPlayer(player, message);

        }

    }

    protected static void sendMessageToPlayer(User player, String message){

        player.getPrivateChannel().sendMessage(message).queue();

    }

    protected static void sendMessageToGame(TextChannel channel, String message){

        channel.sendMessage(message).queue();

    }

    protected static void sendImageToPlayer(User player, File file){

        try {

            player.getPrivateChannel().sendFile(file, null).queue();

        } catch (IOException e){

        }


    }

    protected static void sendImageToGame(TextChannel channel, File file){

        try {

            channel.sendFile(file, null).queue();

        } catch (IOException e){

        }

    }

}

package main.java.game;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.io.File;
import java.io.IOException;

/**
 * Created by Alex Gardner on 1/31/2017.
 */
class GameMessages {

    private static final File SPY = new File("resources/images/Spy.png");
    private static final File TOWN = new File("resources/images/Resistance.png");
    private static final File YES = new File("resources/images/Yes.png");
    private static final File NO = new File("resources/images/No.png");
    private static final File PASS = new File("resources/images/Pass.png");
    private static final File FAIL = new File("resources/images/Fail.png");

    protected static void sendNewRoundMessage(TextChannel channel, User commander, int numMembers){

        sendMessageToGame(channel, commander.getName() + " is now the commander.\nThis mission will have " + numMembers + "members.");

    }

    protected static void sendMessageToPlayers(User[] players, String message){

        for(User player : players){

            sendMessageToPlayer(player, message);

        }

    }

    protected static void sendSpyIntro(User[] spies){

        for(User u : spies){

            sendImageToPlayer(u, SPY);

            String message = "You are a spy! \nThe other ";

            if(spies.length > 2){
                message += "spies are: ";
            }else{
                message += "spy is: ";
            }

            for(User v : spies){

                int numSpiesLeft = spies.length - 1;

                if(!v.equals(u)){

                    message += v.getName();

                    numSpiesLeft --;

                    if(numSpiesLeft > 1){
                        message += ", ";
                    }else if(numSpiesLeft == 1 && spies.length > 3){
                        message += ", and ";
                    }else if(numSpiesLeft == 1){
                        message += " and ";
                    }

                }

            }

            sendMessageToPlayer(u, message);

        }

    }

    private static void sendMessageToPlayer(User player, String message){

        player.getPrivateChannel().sendMessage(message).queue();

    }

    private static void sendMessageToGame(TextChannel channel, String message){

        channel.sendMessage(message).queue();

    }

    private static void sendImageToPlayer(User player, File file){

        try {

            player.getPrivateChannel().sendFile(file, null).queue();

        } catch (IOException e){

        }


    }

    private static void sendImageToGame(TextChannel channel, File file){

        try {

            channel.sendFile(file, null).queue();

        } catch (IOException e){

        }

    }

}

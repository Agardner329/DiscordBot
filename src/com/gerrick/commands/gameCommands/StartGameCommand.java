package com.gerrick.commands.gameCommands;

import com.gerrick.GameManager;
import com.gerrick.MisusedCommandException;
import com.gerrick.commands.Command;
import com.gerrick.commands.CommandData;

/**
 * Created by Alex on 1/28/2017.
 */
public class StartGameCommand extends Command{

    public StartGameCommand(){
        this.help = "Usage: !start";
        this.canUseThroughDM = false;
        this.canUseThroughServer = true;
    }

    @Override
    protected void action(CommandData command) throws MisusedCommandException {

        if (!GameManager.hasGameOnChannel(command.channel)) {

            command.channel.sendMessage("There is no game on this channel to start.").queue();

        }else if(!GameManager.getGame(command.channel).userIsHost(command.author)) {

            command.channel.sendMessage("You are not the host of this game.").queue();

        }else if(GameManager.getGame(command.channel).getNumPlayers() > 10 || GameManager.getGame(command.channel).getNumPlayers() < 5){

            command.channel.sendMessage("The game does not have the necessary 5 to 10 players.\nThe game currently has " + GameManager.getGame(command.channel).getNumPlayers() + " players.").queue();

        }else{

                GameManager.getGame(command.channel).startGame();

                command.channel.sendMessage("The game on this channel has begun!").queue();


        }

    }

}

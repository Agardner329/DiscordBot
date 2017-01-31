package com.gerrick.commands.gameCommands;

import com.gerrick.GameManager;
import com.gerrick.MisusedCommandException;
import com.gerrick.commands.Command;
import com.gerrick.commands.CommandData;
import com.gerrick.ResistanceGame;

/**
 * Created by Alex on 1/28/2017.
 */
public class JoinGameCommand extends Command{

    public JoinGameCommand(){
        this.help = "Usage: !join";
        this.canUseThroughDM = false;
        this.canUseThroughServer = true;
    }


    @Override
    protected void action(CommandData command) throws MisusedCommandException, IllegalArgumentException {

        if(!GameManager.hasGameOnChannel(command.channel)){

            command.channel.sendMessage("There is no active game in this channel, to create one type !create").queue();

        }else if(GameManager.playerIsInGame(command.author)){

            command.channel.sendMessage(command.author.getName() + " is already in a game").queue();

        }else if(!GameManager.getGame(command.channel).getCurrentStatus().equals(ResistanceGame.GameStatus.WAITING_FOR_PLAYERS)) {

            command.channel.sendMessage("The game in this channel is not currently accepting players").queue();

        }else{

            GameManager.getGame(command.channel).addPlayer(command.author);

            command.channel.sendMessage("You have joined the game on this channel.").queue();

        }

    }

}

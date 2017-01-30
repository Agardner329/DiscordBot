package com.gerrick.commands.gameCommands;

import com.gerrick.GameManager;
import com.gerrick.MisusedCommandException;
import com.gerrick.commands.Command;
import com.gerrick.commands.CommandData;
import com.gerrick.ResistanceGame;

/**
 * Created by Alex on 1/28/2017.
 */
public class LeaveGameCommand extends Command{

    public LeaveGameCommand(){
        this.help = "Usage: !leave";
        this.canUseThroughDM = false;
        this.canUseThroughServer = true;
    }

    @Override
    protected void action(CommandData command) throws MisusedCommandException {

        try {
            if (GameManager.getGame(command.channel).removePlayer(command.author) && GameManager.getGame(command.channel).getCurrentStatus().equals(ResistanceGame.gameStatus.WAITING_FOR_PLAYERS)) {

                command.channel.sendMessage("You have successfully left this game.").queue();

                if (GameManager.getGame(command.channel).getNumPlayers() == 0) {

                    GameManager.removeGame(command.channel);

                }

            } else {

                command.channel.sendMessage("You were not in a game on this channel.").queue();

            }
        } catch(NullPointerException e){

            command.channel.sendMessage("There is no game on this channel to leave.").queue();

        }

    }

}

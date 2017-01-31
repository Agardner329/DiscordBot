package com.gerrick.commands.gameCommands;

import com.gerrick.GameManager;
import com.gerrick.MisusedCommandException;
import com.gerrick.ResistanceGame;
import com.gerrick.commands.Command;
import com.gerrick.commands.CommandData;

/**
 * Created by Alex on 1/30/2017.
 */
public class YesCommand extends Command {

    public YesCommand(){

        this.help = "Usage: !Yes";
        this.canUseThroughDM = true;
        this.canUseThroughServer = false;

    }

    @Override
    protected void action(CommandData command) throws MisusedCommandException, IllegalArgumentException {

        if(!GameManager.playerIsInGame(command.author)){

            command.author.getPrivateChannel().sendMessage("You are not currently in a game.").queue();

        }else if(GameManager.getGameOf(command.author).getCurrentStatus() != ResistanceGame.GameStatus.AWAITING_MISSION_VOTE){

            command.author.getPrivateChannel().sendMessage("You are not currently in a game.").queue();

        }else{



        }

    }

}

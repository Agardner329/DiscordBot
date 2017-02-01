package main.java.commands.gameCommands;

import main.java.GameManager;
import main.java.exceptions.MisusedCommandException;
import main.java.game.ResistanceGame;
import main.java.commands.Command;
import main.java.commands.CommandData;

public class YesCommand extends Command {

    public YesCommand(){

        this.help = "Usage: !Yes";
        this.canUseThroughDM = true;
        this.canUseThroughServer = false;

    }

    @Override
    protected void action(CommandData command) throws MisusedCommandException, IllegalArgumentException {

        if(!GameManager.playerIsInGame(command.author)){

            command.author.getPrivateChannel().sendMessage("You are not currently in a game").queue();

        }else if(GameManager.getGameOf(command.author).getCurrentStatus() != ResistanceGame.GameStatus.AWAITING_MISSION_VOTE){

            command.author.getPrivateChannel().sendMessage("Your game is not currently waiting for a mission vote").queue();

        }else{

            GameManager.getGameOf(command.author).addVote(command.author, true);

        }

    }

}

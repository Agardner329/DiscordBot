package main.java.commands.gameCommands;

import main.java.GameManager;
import main.java.commands.Command;
import main.java.commands.CommandData;
import main.java.exceptions.MisusedCommandException;
import main.java.game.ResistanceGame;

public class FailCommand extends Command {

    public FailCommand(){

        this.help = "Usage: !fail";
        this.canUseThroughDM = true;
        this.canUseThroughServer = false;

    }

    @Override
    protected void action(CommandData command) throws MisusedCommandException, IllegalArgumentException {


        if(!GameManager.playerIsInGame(command.author)){

            command.author.getPrivateChannel().sendMessage("You are not currently in a game").queue();

        }else if(GameManager.getGameOf(command.author).getCurrentStatus() != ResistanceGame.GameStatus.AWAITING_MISSION_RESULT){

            command.author.getPrivateChannel().sendMessage("Your game is not currently waiting for mission flags").queue();

        }else if(GameManager.getGameOf(command.author).isOnCurrentMission(command.author)){

            command.author.getPrivateChannel().sendMessage("You are not on the current mission").queue();

        }else if(GameManager.getGameOf(command.author).playerIsSpy(command.author)) {

            command.author.getPrivateChannel().sendMessage("You are not a spy!").queue();

        }else{

                GameManager.getGameOf(command.author).addMissionResult(command.author, false);

        }

    }

}

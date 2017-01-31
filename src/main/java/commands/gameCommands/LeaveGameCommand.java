package main.java.commands.gameCommands;

import main.java.GameManager;
import main.java.MisusedCommandException;
import main.java.commands.Command;
import main.java.commands.CommandData;
import main.java.ResistanceGame;

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
            if (GameManager.getGame(command.channel).removePlayer(command.author) && GameManager.getGame(command.channel).getCurrentStatus().equals(ResistanceGame.GameStatus.WAITING_FOR_PLAYERS)) {

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

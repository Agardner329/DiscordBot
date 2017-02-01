package main.java.commands.gameCommands;

import main.java.GameManager;
import main.java.game.ResistanceGame;
import main.java.commands.Command;
import main.java.commands.CommandData;
import main.java.MisusedCommandException;

/**
 * Created by Alex on 1/28/2017.
 */
public class JoinGameCommand extends Command {

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

        }else if(!GameManager.getGame(command.channel).getCurrentStatus().equals(ResistanceGame.GameStatus.AWAITING_PLAYERS)) {

            command.channel.sendMessage("The game in this channel is not currently accepting players").queue();

        }else{

            GameManager.getGame(command.channel).addPlayer(command.author);

            command.channel.sendMessage("You have joined the game on this channel.").queue();

        }

    }

}

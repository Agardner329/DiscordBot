package main.java.commands.gameCommands;

import main.java.GameManager;
import main.java.MisusedCommandException;
import main.java.ResistanceGame;
import main.java.commands.Command;
import main.java.commands.CommandData;

/**
 * Created by Alex on 1/28/2017.
 */
public class CreateGameCommand extends Command {

    public CreateGameCommand(){
        this.help = "Usage: !create";
        this.canUseThroughDM = false;
        this.canUseThroughServer = true;
    }

    @Override
    protected void action(CommandData command) throws MisusedCommandException, IllegalArgumentException {

        if(GameManager.hasGameOnChannel(command.channel)){

            command.channel.sendMessage("There is already a game in this channel.").queue();

        }else if(GameManager.playerIsInGame(command.author)){

            command.channel.sendMessage(command.author.getName() + " is already in a game").queue();

        }else{

            GameManager.addGame(command.channel, new ResistanceGame(command.author, command.channel));

            command.channel.sendMessage("There is now an active game on this channel").queue();

        }

    }

}

package main.java.commands.gameCommands;

import main.java.GameManager;
import main.java.MisusedCommandException;
import main.java.commands.Command;
import main.java.commands.CommandData;

public class CancelGameCommand extends Command {

    public CancelGameCommand(){
        this.help = "Usage: !cancel";
        this.canUseThroughDM = false;
        this.canUseThroughServer = true;
    }

    @Override
    protected void action(CommandData command) throws MisusedCommandException, IllegalArgumentException {

        if(GameManager.hasGameOnChannel(command.channel) && GameManager.getGame(command.channel).userIsHost(command.author)){

            GameManager.removeGame(command.channel);

            command.channel.sendMessage("The game on this channel has been removed.").queue();

        }

    }

}

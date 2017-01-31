package java.commands.gameCommands;

import java.GameManager;
import java.MisusedCommandException;
import java.commands.Command;
import java.commands.CommandData;

/**
 * Created by Alex on 1/29/2017.
 */
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

package java.commands.gameCommands;

import java.MisusedCommandException;
import java.commands.Command;
import java.commands.CommandData;

/**
 * Created by Alex on 1/30/2017.
 */
public class LaunchCommand extends Command {

    public LaunchCommand(){

        this.help = "Usage: !launch member1 member2 ...";
        this.canUseThroughServer = true;
        this.canUseThroughDM = false;

    }

    @Override
    protected void action(CommandData command) throws MisusedCommandException, IllegalArgumentException {

    }
}

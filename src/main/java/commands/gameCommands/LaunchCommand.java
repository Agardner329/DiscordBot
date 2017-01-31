package main.java.commands.gameCommands;

import main.java.commands.Command;
import main.java.commands.CommandData;
import main.java.MisusedCommandException;

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

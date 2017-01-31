package com.gerrick.commands.gameCommands;

import com.gerrick.MisusedCommandException;
import com.gerrick.commands.Command;
import com.gerrick.commands.CommandData;

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

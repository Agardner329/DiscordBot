package com.gerrick.commands.gameCommands;

import com.gerrick.MisusedCommandException;
import com.gerrick.commands.Command;
import com.gerrick.commands.CommandData;

/**
 * Created by Alex on 1/28/2017.
 */
public class LeaveGameCommand extends Command{

    private static final String HELP = "Usage: !leave";

    @Override
    public void action(CommandData command) throws MisusedCommandException {

    }

    @Override
    public String help() {
        return HELP;
    }

}

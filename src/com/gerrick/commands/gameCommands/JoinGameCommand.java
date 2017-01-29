package com.gerrick.commands.gameCommands;

import com.gerrick.MisusedCommandException;
import com.gerrick.commands.Command;
import com.gerrick.commands.CommandData;

/**
 * Created by Alex on 1/28/2017.
 */
public class JoinGameCommand extends Command{

    private static final String HELP = "Usage: !join";

    public void action(CommandData command) throws MisusedCommandException {



    }

    public String help() {
        return HELP;
    }

}

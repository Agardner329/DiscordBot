package com.gerrick.commands;

import com.gerrick.MisusedCommandException;

/**
 * Created by Alex Gardner on 1/27/2017
 */
public abstract class Command {

    /**
     * Executes the given subcommand based on the info specified in the commandData object
     *
     * @param command The data on how to execute the command
     * @throws MisusedCommandException If the command is given improper args
     */
    public abstract void action(CommandData command) throws MisusedCommandException;

    /**
     * Returns the info on how to use the specific subtype
     *
     * @return the help String for the type
     */
    public abstract String help();

}

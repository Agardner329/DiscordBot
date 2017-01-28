package com.gerrick.commands;

import com.gerrick.MisusedCommandException;

/**
 * Created by Alex Gardner on 1/27/2017.
 */
public interface Command {

    void action(CommandData command) throws MisusedCommandException;

    String help();

}

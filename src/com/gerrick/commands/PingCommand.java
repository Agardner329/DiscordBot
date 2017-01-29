package com.gerrick.commands;


/**
 * Created by Alex Gardner on 1/27/2017
 */
public class PingCommand extends Command {

    //The help string
    private static final String HELP = "Usage: !ping";

    /**
     * Executes the PingCommand as specified in command
     *
     * @param command the data about the command to be executed
     */
    @Override
    public void action(CommandData command) {
        command.event.getTextChannel().sendMessage("Pong!").queue();
    }

    /**
     * Returns the help string for the PingCommand
     *
     * @return the help string for PingCommand
     */
    @Override
    public String help() {
        return HELP;
    }

}

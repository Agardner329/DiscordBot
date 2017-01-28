package com.gerrick.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by Alex Gardner on 1/27/2017.
 */
public class PingCommand implements Command {

    private static final String HELP = "Usage: !ping";

    @Override
    public void action(CommandData command) {
        command.event.getTextChannel().sendMessage("Pong!").queue();
    }

    @Override
    public String help() {
        return HELP;
    }

}

package com.gerrick.commands;

import com.gerrick.DiscordBot;
import com.gerrick.MisusedCommandException;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * Created by Alex Gardner on 1/27/2017.
 */
public class HelpCommand implements Command {

    private static final String HELP = "Usage: !help or !help (command)";

    @Override
    public void action(CommandData command) throws MisusedCommandException {

        if(command.args.length == 0){
            TextChannel channel = command.event.getTextChannel();
            channel.sendMessage("Available Commands:");
            for(String key : DiscordBot.commands.keySet()){
                channel.sendMessage(key + ": " + DiscordBot.commands.get(key).help());
            }
        }

    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public boolean isSafe(CommandData command) {

        if(command.args.length == 0 || DiscordBot.commands.containsKey(command.args[0])){
            return true;
        }

        return false;

    }
}

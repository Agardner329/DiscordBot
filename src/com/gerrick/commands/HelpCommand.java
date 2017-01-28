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

        TextChannel channel = command.event.getTextChannel();

        if(command.args.length == 0){

            channel.sendMessage("Available Commands:").queue();

            for(String key : DiscordBot.commands.keySet()){
                channel.sendMessage(key + ": " + DiscordBot.commands.get(key).help()).queue();
            }
        }else{

            if(!DiscordBot.commands.containsKey(command.args[0])){

                DiscordBot.log("Debug", command.args[0]);
                throw new MisusedCommandException(command.author.getName() + " gave a bad command");

            }else{

                channel.sendMessage(DiscordBot.commands.get(command.args[0]).help()).queue();

            }

        }

    }

    @Override
    public String help() {
        return HELP;
    }
}

package com.gerrick.commands;

import com.gerrick.DiscordBot;
import com.gerrick.MisusedCommandException;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * Created by Alex Gardner on 1/27/2017
 */
public class HelpCommand implements Command {

    private static final String HELP = "Usage: !help or !help (command)";

    /**
     * Prints either the possible commands, or the help string about a requested command
     *
     * @param command The data about the given command
     * @throws MisusedCommandException If the requested command does not exist
     */
    @Override
    public void action(CommandData command) throws MisusedCommandException {

        //Acquires the channel of the message
        TextChannel channel = command.event.getTextChannel();


        if(command.args.length == 0){//Tests to see if args contains anything

            channel.sendMessage("Available Commands:").queue();

            for(String key : DiscordBot.commands.keySet()){//Prints the usage info for all possible commands
                channel.sendMessage(key).queue();
            }
        }else{

            if(!DiscordBot.commands.containsKey(command.args[0])){//Tests to see if the help requested if for a valid command

                throw new MisusedCommandException(command.author.getName() + " gave a bad command");

            }else{

                channel.sendMessage(DiscordBot.commands.get(command.args[0]).help()).queue();//Sends the help text about the requested command

            }

        }

    }

    /**
     * Returns the help string for this command
     *
     * @return The help string for this command
     */
    @Override
    public String help() {
        return HELP;
    }
}

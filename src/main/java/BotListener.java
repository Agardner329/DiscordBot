package main.java;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by Alex Gardner on 1/27/2017
 */
public class BotListener extends ListenerAdapter{

    /**
     * Tests all incoming messages from servers to ensure they were not sent by the bot, and begin with the command character,
     *  then sends them to main
     *
     * @param event the event object specific to the message received
     */
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){

        //Ignore messages from self
        if(event.getMessage().getAuthor().getId().equals(event.getJDA().getSelfUser().getId())){
            return;
        }

        //Check that message is command
        if(event.getMessage().getContent().substring(0, 1).equals("!")){

            CommandManager.handleCommand(event);//Sends back to main to handle

        }

    }

    /**
     * Tests all incoming private messages to ensure they begin with the command character, then sends them to main
     *
     * @param event the event object specific to the message received
     */
    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event){

        //Check that message is command
        if(event.getMessage().getContent().substring(0, 1).equals("!")){

            CommandManager.handleCommand(event);//Sends back to main to handle

        }

    }

    /**
     * Logs the status of the bot upon initial connection
     *
     * @param event The information of the event
     */
    @Override
    public void onReady(ReadyEvent event){
        DiscordBot.log("Status", "Logged in as: " + event.getJDA().getSelfUser().getName());
    }

}

package com.gerrick;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by Alex Gardner on 1/27/2017.
 */
public class BotListener extends ListenerAdapter{

    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        //Ignore messages from self
        if(event.getMessage().getAuthor().getId().equals(event.getJDA().getSelfUser().getId())){
            return;
        }

        //Check that message is type
        if(event.getMessage().getContent().startsWith("!")){

            DiscordBot.handleCommand(event);

        }

    }

    @Override
    public void onReady(ReadyEvent event){
        DiscordBot.log("Status", "Logged in as: " + event.getJDA().getSelfUser().getName());
    }

}

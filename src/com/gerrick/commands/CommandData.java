package com.gerrick.commands;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by Alex Gardner on 1/27/2017.
 */
public class CommandData {

    public final String raw;
    public final String beheaded;
    public final String[] splitBeheaded;
    public final String type;
    public final String[] args;
    public final User author;
    public final MessageReceivedEvent event;

    public CommandData(MessageReceivedEvent event){
        this.raw = event.getMessage().getContent().toLowerCase();
        this.beheaded = raw.substring(1);
        this.splitBeheaded = beheaded.split(" ");
        this.type = splitBeheaded[0];
        this.args = new String[splitBeheaded.length - 1];
        for(int i = 1; i < splitBeheaded.length; i++){
            args[i-1] = splitBeheaded[i];
        }
        this.author = event.getAuthor();
        this.event = event;
    }

}

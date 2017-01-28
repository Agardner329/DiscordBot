package com.gerrick.commands;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by Alex Gardner on 1/27/2017
 */
public class CommandData {

    //The full lowercase version of the entered string
    public final String raw;
    //The raw string without the head character
    public final String beheaded;
    //The beheaded string split into substrings at each space character
    public final String[] splitBeheaded;
    //The type of command (derived from splitBeheaded[0])
    public final String type;
    //The arguments for the command (splitBeheaded[1-end])
    public final String[] args;
    //The author of the message
    public final User author;
    //The original event object
    public final MessageReceivedEvent event;

    /**
     * Fills in all of the instance variables based on the data contained within event
     *
     * @param event The data on the message sent
     */
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

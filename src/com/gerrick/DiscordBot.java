package com.gerrick;

import com.gerrick.commands.Command;
import com.gerrick.commands.CommandData;
import com.gerrick.commands.HelpCommand;
import com.gerrick.commands.PingCommand;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Alex Gardner on 1/27/2017.
 */
public class DiscordBot {

    private static final String TOKEN = "Mjc0NjQyMDI1NDY1NTExOTM2.C21D4g.MuX7gTzksD1VIjGmUGenGhW7UzI";

    private static JDA bot;

    public static HashMap<String, Command> commands = new HashMap<>();

    public static void main(String[] args){

        setupCommands();

        try{

            bot = new JDABuilder(AccountType.BOT).addListener(new BotListener()).setToken(TOKEN).buildBlocking();

            bot.setAutoReconnect(true);

        } catch(Exception e){

            e.printStackTrace();

        }

    }

    private static void setupCommands(){

        commands.put("ping", new PingCommand());
        commands.put("help", new HelpCommand());

    }

    public static void handleCommand(MessageReceivedEvent event){

        CommandData command = new CommandData(event);

        if(commands.containsKey(command.type)){

            try {

                commands.get(command.type).action(command);

            } catch(MisusedCommandException e){
                log("Command Error", e.getMessage());
                command.event.getTextChannel().sendMessage(e.getMessage()).queue();
            }

        }else{

            String error = command.author.getName() + " gave a bad command";
            
            log("Command Error", error);
            command.event.getTextChannel().sendMessage(error).queue();

        }

    }

    public static void log(String type, String message){

        SimpleDateFormat time = new SimpleDateFormat("[HH:mm:ss]");
        String timeString = time.format(new Date());

        System.out.println(timeString + " [" + type + "]: " + message);
    }

}

package com.gerrick;

import com.gerrick.commands.*;
import com.gerrick.commands.gameCommands.*;
import com.gerrick.game.ResistanceGame;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.HashMap;

/**
 * Created by Alex Gardner on 1/27/2017
 */
public class DiscordBot {

    private static final String TOKEN = "Mjc0NjQyMDI1NDY1NTExOTM2.C21D4g.MuX7gTzksD1VIjGmUGenGhW7UzI";

    private static JDA bot;

    public static HashMap<String, Command> COMMANDS = new HashMap<>();
    public static HashMap<TextChannel, ResistanceGame> GAMES = new HashMap<>();

    /**
     * Sets up a Discord Bot
     *
     * @param args not used
     */
    public static void main(String[] args){

        setupCommands();

        try{

            bot = new JDABuilder(AccountType.BOT).addListener(new BotListener()).setToken(TOKEN).buildBlocking();

            bot.setAutoReconnect(true);

        } catch(Exception e){

            e.printStackTrace();

        }

    }

    /**
     * Sets up all COMMANDS to be put in the COMMANDS hash map
     */
    private static void setupCommands(){

        COMMANDS.put("ping", new PingCommand());
        COMMANDS.put("help", new HelpCommand());
        COMMANDS.put("create", new CreateGameCommand());
        COMMANDS.put("join", new JoinGameCommand());
        COMMANDS.put("leave", new LeaveGameCommand());
        COMMANDS.put("start", new StartGameCommand());

    }

    /**
     *
     * Takes a MessageReceivedEvent that was intended as a command, and executes it if possible,
     *      printing error statements in both the log and channel from which the message came if
     *      it cannot be executed
     *
     * @param event Command given
     */
    public static void handleCommand(MessageReceivedEvent event){

        CommandData command = new CommandData(event);

        if(COMMANDS.containsKey(command.type)){//Checks to see if a valid command was entered

            try {//Executes the command, catching errors

                COMMANDS.get(command.type).action(command);

            } catch(MisusedCommandException e){//Prints the specifics of the error into the log and text channel
                log("Command Error", e.getMessage());
                command.event.getTextChannel().sendMessage(e.getMessage()).queue();
            }

        }else{//Prints out the specifics of the error to the log and text channel

            String error = command.author.getName() + " gave a bad command";

            log("Command Error", error);
            command.event.getTextChannel().sendMessage(error).queue();

        }

    }


    /**
     * Prints to the log the type of message, message, and timestamp of the call
     *      in the format [HH:MM:SS] [type]: message
     *
     * @param type      The type of message to log
     * @param message   The message
     */
    public static void log(String type, String message){

        java.text.SimpleDateFormat time = new java.text.SimpleDateFormat("[HH:mm:ss]");
        String timeString = time.format(new java.util.Date());

        System.out.println(timeString + " [" + type + "]: " + message);
    }

}

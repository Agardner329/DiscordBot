package main.java;

import main.java.commands.gameCommands.*;
import main.java.commands.Command;
import main.java.commands.CommandData;
import main.java.commands.HelpCommand;
import main.java.commands.PingCommand;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

/**
 * Created by Alex on 1/29/2017.
 */
public class CommandManager {

    public enum commands {

        PING(new PingCommand()),
        HELP(new HelpCommand()),
        CREATE(new CreateGameCommand()),
        JOIN(new JoinGameCommand()),
        LEAVE(new LeaveGameCommand()),
        START(new StartGameCommand()),
        CANCEL(new CancelGameCommand()),
        YES(new YesCommand()),
        NO(new NoCommand()),
        PASS(new PassCommand()),
        FAIL(new FailCommand());

        public final Command command;

        commands(Command command){

            this.command = command;

        }

    }

    /**
     *
     * Takes a MessageReceivedEvent, converts it into a CommandData, and runs it
     *
     * @param event Command given
     */
    public static void handleCommand(GuildMessageReceivedEvent event){

        runCommand(new CommandData(event));

    }

    /**
     *
     * Takes a PrivateMessageReceivedEvent, converts it into a CommandData, and runs it
     *
     * @param event Command given
     */
    public static void handleCommand(PrivateMessageReceivedEvent event){

        runCommand(new CommandData(event));

    }

    /**
     *
     * Takes a CommandData, and executes it if possible
     *
     * @param command Command given
     */
    private static void runCommand(CommandData command){

        try{//Checks to see if a valid command was entered

            CommandManager.commands.valueOf(command.type.toUpperCase()).command.execute(command);

        }catch(IllegalArgumentException e){

            String error = command.author.getName() + " gave a bad command: !" + command.type;

            DiscordBot.log("Command Error", error);
        }catch(MisusedCommandException e){//Prints out the specifics of the error to the log

            DiscordBot.log("Command Error", e.getMessage());

        }

    }

}

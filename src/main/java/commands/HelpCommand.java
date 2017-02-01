package main.java.commands;

import main.java.CommandManager;
import main.java.MisusedCommandException;
import net.dv8tion.jda.core.entities.PrivateChannel;

public class HelpCommand extends Command {

    public HelpCommand(){
        this.help = "Usage: !help or !help (command)";
        this.canUseThroughDM = true;
        this.canUseThroughServer = true;
    }

    /**
     * Prints either the possible COMMANDS, or the help string about a requested command
     *
     * @param command The data about the given command
     * @throws MisusedCommandException If the requested command does not exist
     */
    @Override
    protected void action(CommandData command) throws MisusedCommandException, IllegalArgumentException {

        //Acquires the channel of the message
        if(!command.author.hasPrivateChannel()) {

            command.author.openPrivateChannel().queue();

            try {
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
                System.exit(1);
            }

        }

        PrivateChannel channel = command.author.getPrivateChannel();


        if(command.args.length == 0){//Tests to see if args contains anything

            String message = "Available Commands: \n";

            for(CommandManager.commands key : CommandManager.commands.values()){//Prints the usage info for all possible COMMANDS
                message += key.toString().toLowerCase() + "\n";
            }

            channel.sendMessage(message).queue();

        }else{

            if(CommandManager.commands.valueOf(command.type.toUpperCase()) != null){//Tests to see if the help requested if for a valid command

                throw new MisusedCommandException(command.author.getName() + " gave bad args for: !help");

            }else{

                channel.sendMessage(CommandManager.commands.valueOf(command.args[0].toUpperCase()).command.help()).queue();//Sends the help text about the requested command

            }

        }

    }

}

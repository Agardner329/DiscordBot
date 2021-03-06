package main.java.commands;

public class PingCommand extends Command {

    public PingCommand(){
        this.help = "Usage: !ping";
        this.canUseThroughDM = false;
        this.canUseThroughServer = true;
    }

    /**
     * Executes the PingCommand as specified in command
     *
     * @param command the data about the command to be executed
     */
    @Override
    protected void action(CommandData command) {
        command.channel.sendMessage("Pong!").queue();
    }


}

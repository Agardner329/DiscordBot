package main.java.commands;

import main.java.exceptions.MisusedCommandException;

public abstract class Command {

    protected String help;
    protected boolean canUseThroughDM, canUseThroughServer;

    /**
     * Executes the given action of an object if the command was received from the proper location
     *
     * @param command The data on how to execute the command
     * @throws MisusedCommandException If the command is given improper args
     * @throws
     */
    public void execute(CommandData command) throws MisusedCommandException, IllegalArgumentException{

        if(command.isFromPublic){
            if(!canUseThroughServer){
                throw new MisusedCommandException(command.author.getName() + " tried to use " + this.getClass().getSimpleName() + " from a public channel.");
            }
        }else{
            if(!canUseThroughDM){
                throw new MisusedCommandException(command.author.getName() + " tried to use " + this.getClass().getSimpleName() + " from a private channel.");
            }
        }

        this.action(command);

    }

    /**
     *
     * Defines the action that each subclass should take
     *
     * @param command   The data on the command to be executed
     * @throws MisusedCommandException  If an improper argument is put in for a command
     * @throws IllegalArgumentException If a non-existing command is entered
     */
    protected abstract void action(CommandData command) throws MisusedCommandException, IllegalArgumentException;

    /**
     * Returns the info on how to use the specific subtype
     *
     * @return the help String for the type
     */
    public String help(){
        return help;
    }

}

package main.java.commands.gameCommands;

import main.java.GameManager;
import main.java.commands.Command;
import main.java.commands.CommandData;
import main.java.exceptions.MisusedCommandException;
import main.java.exceptions.PlayerNotFoundException;
import main.java.game.ResistanceGame;
import net.dv8tion.jda.core.entities.User;

public class LaunchCommand extends Command {

    public LaunchCommand(){

        this.help = "Usage: !launch member1 member2 ...";
        this.canUseThroughServer = true;
        this.canUseThroughDM = false;

    }

    @Override
    protected void action(CommandData command) throws MisusedCommandException, IllegalArgumentException {

        if(!GameManager.playerIsInGame(command.author)){

            command.channel.sendMessage("You are not currently in a game").queue();

        }else if(GameManager.getGameOf(command.author).getCurrentStatus() != ResistanceGame.GameStatus.AWAITING_MISSION){

            command.channel.sendMessage("Your game is not currently waiting for a mission").queue();

        }else if(!GameManager.getGameOf(command.author).getCommander().equals(command.author)){

            command.channel.sendMessage("You are not the current commander").queue();

        }else if(GameManager.getGameOf(command.author).getNumPlayersThisMission() != command.args.length){

            command.channel.sendMessage("That is not the correct number of members\nThe number of members on this mission should be " + GameManager.getGameOf(command.author).getNumPlayersThisMission()).queue();

        }else{

            for(String player : command.args){

                if(!GameManager.getGameOf(command.author).hasPlayer(player)){

                    command.channel.sendMessage(player + " is not in your game").queue();

                    return;

                }

            }

            User[] players = new User[command.args.length];

            for(int i = 0; i < command.args.length; i++){

                try {

                    players[i] = GameManager.getGameOf(command.author).getPlayer(command.args[i]);

                } catch(PlayerNotFoundException e){

                    e.printStackTrace();

                }

            }

            GameManager.getGameOf(command.author).pushToVote(players);

        }

    }
}

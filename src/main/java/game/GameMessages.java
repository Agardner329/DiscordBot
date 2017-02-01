package main.java.game;

import main.java.DiscordBot;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

class GameMessages {

    private static final File SPY = new File("resources/images/Spy.png");
    private static final File TOWN = new File("resources/images/Resistance.png");
    private static final File YES = new File("resources/images/Yes.png");
    private static final File NO = new File("resources/images/No.png");
    private static final File PASS = new File("resources/images/Pass.png");
    private static final File FAIL = new File("resources/images/Fail.png");

    static void sendVoteResults(TextChannel channel, HashMap<User, Boolean> votes){

        BufferedImage resultImage = new BufferedImage(816 * votes.size(), 1110, BufferedImage.TYPE_INT_RGB);
        Graphics g = resultImage.getGraphics();
        int currentX = 0;

        int numYes = 0;
        int numNo = 0;

        try {
            for (Boolean vote : votes.values()) {

                if(vote){

                    numYes++;
                    g.drawImage(ImageIO.read(YES), currentX, 0, null);

                }else{

                    numNo++;
                    g.drawImage(ImageIO.read(NO), currentX, 0, null);

                }

                currentX += 816;

            }

            File tempFile = new File("temp.png");

            ImageIO.write(resultImage, "png", tempFile);

            sendImageToGame(channel, tempFile);

            DiscordBot.log("Deletion Success", "" + tempFile.delete());

        }catch (IOException e){

            e.printStackTrace();

        }

        String message = "";

        if(numNo > numYes){

            message += "The mission proposal fails!\n";

        }else{

            message += "The mission proposal passes!\n";

        }

        message += "The votes for this mission were:\n";

        for(User u : votes.keySet()){

            message += u.getName() + ": ";

            if(votes.get(u)){

                message += "yes";

            }else{

                message += "no";

            }

            message += "\n";

        }

        sendMessageToGame(channel, message);

    }

    static void sendMissionIntro(TextChannel channel, User[] currentMission){

        String message = "The proposed mission is: ";

        for(User u : currentMission){

            message += u.getName() + " + ";

        }

        message = message.substring(0, message.length() - 3);

        message += "\nCast your votes by messaging me now";

        sendMessageToGame(channel, message);

    }

    static void sendMissionSuccess(TextChannel channel, int numPass, int numFail){

        BufferedImage resultImage = new BufferedImage(816 * (numPass + numFail), 1110, BufferedImage.TYPE_INT_RGB);
        Graphics g = resultImage.getGraphics();
        int currentX = 0;

        try {
            for (int i = 0; i < numPass; i++) {

                g.drawImage(ImageIO.read(PASS), currentX, 0, null);
                currentX += 816;

            }

            for (int i = 0; i < numFail; i++) {

                g.drawImage(ImageIO.read(FAIL), currentX, 0, null);
                currentX += 816;

            }

            File tempFile = new File("temp.png");

            ImageIO.write(resultImage, "png", tempFile);

            sendImageToGame(channel, tempFile);

            DiscordBot.log("Deletion Success", "" + tempFile.delete());

        }catch (IOException e){

            e.printStackTrace();

        }

        sendMessageToGame(channel, "The mission passes with flag count: " + numPass + " passes, and " + numFail + "fails");

    }

    static void sendMissionFail(TextChannel channel, int numPass, int numFail){

        BufferedImage resultImage = new BufferedImage(816 * (numPass + numFail), 1110, BufferedImage.TYPE_INT_RGB);
        Graphics g = resultImage.getGraphics();
        int currentX = 0;

        try {
            for (int i = 0; i < numPass; i++) {

                g.drawImage(ImageIO.read(PASS), currentX, 0, null);
                currentX += 816;

            }

            for (int i = 0; i < numFail; i++) {

                g.drawImage(ImageIO.read(FAIL), currentX, 0, null);
                currentX += 816;

            }

            File tempFile = new File("temp.png");

            ImageIO.write(resultImage, "png", tempFile);

            sendImageToGame(channel, tempFile);

            DiscordBot.log("Deletion Success", "" + tempFile.delete());

        }catch (IOException e){

            e.printStackTrace();

        }

        sendMessageToGame(channel, "The mission fails with flag count: " + numPass + " passes, and " + numFail + "fails");

    }

    static void sendNewRoundMessage(TextChannel channel, User commander, int numMembers){

        sendMessageToGame(channel, commander.getName() + " is now the commander.\nThis mission will have " + numMembers + "members.");

    }

    protected static void sendSpyIntro(User[] spies){

        for(User u : spies){

            sendImageToPlayer(u, SPY);

            String message = "You are a spy! \nThe other ";

            if(spies.length > 2){
                message += "spies are: ";
            }else{
                message += "spy is: ";
            }

            for(User v : spies){

                int numSpiesLeft = spies.length - 1;

                if(!v.equals(u)){

                    message += v.getName();

                    numSpiesLeft --;

                    if(numSpiesLeft > 1){
                        message += ", ";
                    }else if(numSpiesLeft == 1 && spies.length > 3){
                        message += ", and ";
                    }else if(numSpiesLeft == 1){
                        message += " and ";
                    }

                }

            }

            sendMessageToPlayer(u, message);

        }

    }

    protected static void sendResistanceIntro(User[] resistance){

        for(User u : resistance){

            sendImageToPlayer(u, TOWN);

            sendMessageToPlayer(u, "You are on the Resistance");

        }

    }

    private static void sendMessageToPlayer(User player, String message){

        player.getPrivateChannel().sendMessage(message).queue();

    }

    static void sendMessageToGame(TextChannel channel, String message){

        channel.sendMessage(message).queue();

    }

    private static void sendImageToPlayer(User player, File file){

        try {

            player.openPrivateChannel().queue();

            try {
                Thread.sleep(100);
            } catch(InterruptedException e){

                e.printStackTrace();

            }

            player.getPrivateChannel().sendFile(file, null).queue();

        } catch (IOException e){

            e.printStackTrace();

        }


    }

    private static void sendImageToGame(TextChannel channel, File file){

        try {

            channel.sendFile(file, null).queue();

        } catch (IOException e){

            e.printStackTrace();

        }

    }

}

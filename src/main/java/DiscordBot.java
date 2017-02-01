package main.java;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Alex Gardner on 1/27/2017
 */
public class DiscordBot {

    private static final String CREDENTIALS = readConfig().getProperty("Token");

    private static final String configFilePath = "resources/config.properties";

    private static JDA bot;

    /**
     * Sets up a Discord Bot
     *
     * @param args not used
     */
    public static void main(String[] args){

        try{

            bot = new JDABuilder(AccountType.BOT).setToken(CREDENTIALS).addListener(new BotListener()).buildBlocking();

            bot.setAutoReconnect(true);

        } catch(Exception e){

            e.printStackTrace();

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

    private static Properties readConfig(){

        Properties prop = new Properties();

        try {

            prop.load(new FileInputStream(configFilePath));

        } catch(IOException e){

            System.out.print("Cannot find config.properties file.");

            System.exit(1);

        }

        return prop;

    }

}

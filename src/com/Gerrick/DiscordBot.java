package com.Gerrick;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

/**
 * Created by Alex Gardner on 1/27/2017.
 */
public class DiscordBot {

    private static final String TOKEN = "274642025465511936";

    private static JDA jda;

    public static void Main(String[] args){

        try{

            jda = new JDABuilder(AccountType.BOT).setToken(TOKEN).buildBlocking();

        } catch(Exception e){

            e.printStackTrace();

        }


    }

}

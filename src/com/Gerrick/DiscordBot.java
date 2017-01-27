package com.Gerrick;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

/**
 * Created by Alex Gardner on 1/27/2017.
 */
public class DiscordBot {

    private static final String TOKEN = "Mjc0NjQyMDI1NDY1NTExOTM2.C21D4g.MuX7gTzksD1VIjGmUGenGhW7UzI";

    private static JDA bot;

    public static void main(String[] args){

        try{

            bot = new JDABuilder(AccountType.BOT).setToken(TOKEN).buildBlocking();

        } catch(Exception e){

            e.printStackTrace();

        }

    }

}

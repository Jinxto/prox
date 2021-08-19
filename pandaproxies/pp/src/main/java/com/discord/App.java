package com.discord;



import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws LoginException
    {
    	
       JDA jda = JDABuilder.createDefault("ODc3ODQ2NDIzMzgxNjM5MjA5.YR4kMA.Q6_0MURPdYHemSxTEQa44vUpN90").build();
       jda.addEventListener(new bot());
       jda.getPresence().setActivity(Activity.listening("$help in DMs	"));

    }
}


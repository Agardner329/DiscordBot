package main.resources;

/**
 * Usage - Change config to your liking, then change file name and class name to "Config" to activate settings
 */
public class BaseConfig {

    private static final String TOKEN = "Give me a token!";


    public static String getCredentials() {
        return TOKEN;
    }
}

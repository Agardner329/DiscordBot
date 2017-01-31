package main.java;

/**
 * Created by Alex Gardner on 1/27/2017
 */
public class MisusedCommandException extends Exception {

    /**
     * Supers the String error to the Exception class
     *
     * @param error The details of the error
     */
    public MisusedCommandException(String error){
        super(error);
    }

}

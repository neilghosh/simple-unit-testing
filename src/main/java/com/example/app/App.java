package com.example.app;

import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {

    Logger logger;

    public static void main(String[] args) {
        System.out.println((new App(null)).add(2, 3));
    }
    
    int add(int x, int y) {
        logger.info("Adding numbers ");
        int z = x + y;
        logger.info("Result is "+x);
        return z;
    }

    int addSafely(int x, int y) {
        return Math.addExact(x, y);
    }

    public App(Logger logger) {
        this.logger =  logger == null ? Logger.getLogger(App.class.getName()) : logger;
    }
}

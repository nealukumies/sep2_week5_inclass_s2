package org.example;

import java.util.Scanner;
import java.util.logging.Logger;

public class App {
    public static final Logger logger = Logger.getLogger(App.class.getName());
    public static void main(String[] args) {

        logger.info("Hello and welcome! \n Enter number to start loop:");
        int number = askInt();
        logger.info(()-> "You entered: " + number);
        runLoop(number);
    }

    public static int askInt() {
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }

    public static void runLoop(int number) {
        for (int i = 1; i <= number; i++) {
            int finalI = i;
            logger.info(() -> "x = " + finalI);
        }
    }
}
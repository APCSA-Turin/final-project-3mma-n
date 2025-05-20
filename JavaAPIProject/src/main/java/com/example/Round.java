package com.example;
import org.json.JSONObject;
import java.util.*;

public class Round {
    // instance variables
    private JSONObject names;
    private JSONObject rates;
    private ArrayList<Object> keyArray;
    private Scanner sc;
    private String[] keys;
    private int numAnswers;
    private int[] maxes;
    private double conversion;

    public Round(Game game, int n, int[] m) {
        // initializes instance variables associated with the game that created it
        names = game.getNames();
        rates = game.getRates();
        keyArray = game.getKeyArray();
        sc = game.getScanner();

        // initializes variables provided in the constructor
        numAnswers = n;
        maxes = m;

        // generates the 2 random keys for the 2 currencies
        keys = randomKeys();
        // calculates the conversion rate
        conversion = getConversion(keys);
        // plays the round
        play();
    }

    public void play() {
        // gets the names of the 2 currencies
        String name1 = names.getString(keys[0]);
        String name2 = names.getString(keys[1]);
        // gets the conversion rate
        double conversion = getConversion(keys);
        // sets the number of hints and answers
        int hints = 3;
        int numAnswers = 4;
        // creates the list of answers
        double[] choices = answers();

        // initializes the variable guess to a dummy value that will make the loop below run
        int guess = 0;

        // runs until an answer choice is entered
        while (guess <= 0 || guess > numAnswers) {
            // clears the screen
            App.clearScreen();
            // prints out the question and answer choices
            System.out.println("1000 " + name1 + " converts to how many " + name2 + "?");
            for (int i = 0; i < numAnswers; i++) {
                System.out.println((i + 1) + ") " + choices[i] + " " + name2);
            }

            // offers a hint if they player has any left
            if (hints > 0) {
                System.out.println((numAnswers + 1) + ") Recieve a Hint: " + hints + " remaining");
            }

            // allows the player to enter a guess
            System.out.print("Guess: ");
            guess = sc.nextInt();
            sc.nextLine();

            // runs if the player doesn't guess one of the answers
            if (guess > numAnswers) {
                if (guess == numAnswers + 1 && hints > 0) {
                    // provides the player with a hint
                    hint();
                    hints--;
                } else {
                    // prints if an invalid number is entered
                    System.out.println("Invalid Input.");
                }
                // allows the user to read the output before the screen will be cleared
                System.out.println("Press Enter to Continue.");
                sc.nextLine();
            }
        }
        // prints based on whether or not the answer is correct
        if (choices[guess - 1] == conversion) {
            System.out.print("Correct!! ");
        } else {
            System.out.print("Wrong!! ");
        }
        // provides the correct answer
        System.out.println("1000 " + name1 + " converts to " + conversion + " " + name2);
    }

    public String[] randomKeys() {
        // selects the first currency
        String[] keys = new String[2];
        keys[0] = (String) keyArray.get((int) (Math.random() * maxes[0]));

        // selects the second currency
        keys[1] = keys[0];
        while (keys[0].equals(keys[1])) {
            keys[1] = (String) keyArray.get((int) (Math.random() * maxes[1]));
        }
        return keys;
    }

    public double getConversion(String[] keys) {
        return (int) (rates.getDouble(keys[1]) / rates.getDouble(keys[0]) * 100000) / 100.0;
    }

    public double[] answers() {
        double[] list = new double[4];
        for (int i = 0; i < list.length; i++) {
            list[i] = conversion;
            while(list[i] == conversion) {
                String[] keys = randomKeys();
                list[i] = getConversion(keys);
            } 
        }
        list[(int) (Math.random() * numAnswers)] = conversion;
        return list;
    }

    public void hint() {
        // gets the rate of one of the two currencies at random
        String thisKey = keys[(int) Math.random() * 2];
        double thisVal = rates.getDouble(thisKey);
        String thisName = names.getString(thisKey);
        // selects another different currency to compare it to
        String otherKey = (String) keyArray.get((int) (Math.random() * maxes[1]));
        double otherVal = rates.getDouble(otherKey);
        String otherName = names.getString(otherKey);

        // prints how the first rate compares to the new one
        System.out.print("1 " + thisName + " is worth ");
        if (thisVal < otherVal) {
            System.out.print("more than");
        } else if (thisVal > otherVal) {
            System.out.print("less than");
        } else {
            System.out.print("the same as");
        }
        System.out.println(" 1 " + otherName);
    }
}

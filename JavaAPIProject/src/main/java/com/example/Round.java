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
    }

    public int play() {
        // gets the names of the 2 currencies
        String name1 = names.getString(keys[0]);
        String name2 = names.getString(keys[1]);
        // gets the conversion rate
        double conversion = getConversion(keys);
        // initializes new variables to be used during the round
        int hints = 3;
        int guesses = 2;
        int numAnswers = 4;
        String hintStr = "";
        
        // creates the list of answers
        double[] choices = answers();

        // initializes the variable guess to a dummy value that will make the loop below run
        int guess = 0;

        // runs until an answer choice is entered
        while (guesses > 0) {
            // clears the screen
            App.clearScreen();
            // prints out the question and answer choices
            System.out.println("-----------------------------------------------------");
            System.out.println("Guesses Remaining: " + guesses + "    Hints Remaining: " + hints);
            if (!hintStr.equals("")) {
                System.out.print("-----------------------------------------------------");
                System.out.println(hintStr);
            }
            System.out.println("-----------------------------------------------------");
            System.out.println("1000 " + name1 + " converts to how many " + name2 + "?");
            for (int i = 0; i < numAnswers; i++) {
                System.out.println((i + 1) + ") " + choices[i] + " " + name2);
            }

            // offers a hint if they player has any left
            if (hints > 0) {
                System.out.println((numAnswers + 1) + ") Recieve a Hint");
            }
            System.out.println("-----------------------------------------------------");

            // allows the player to enter a guess
            System.out.print("Answer: ");
            guess = sc.nextInt();
            sc.nextLine();

            // clears the screen
            App.clearScreen();
            System.out.println("-----------------------------------------------------");

            if (guess <= numAnswers && guess > 0) {
                // prints the player's guess
                System.out.println("Guessed " + choices[guess - 1] + " " + name2);
                // prints based on whether or not the answer is correct
                if (choices[guess - 1] == conversion) {
                    System.out.print("Correct!! ");
                    break;
                } else {
                    guesses--;
                    System.out.println("Wrong!! " + guesses + " guesses left.");
                    hintStr += "\nThe answer is not " + choices[guess - 1] + " " + name2;
                }
            } else if (guess == numAnswers + 1 && hints > 0) {
                System.out.println("Hint Requested:");
                // provides the player with a hint
                hintStr += "\n" + hint();
                hints--;
            } else {
                // prints if an invalid number is entered
                System.out.println("Invalid Input.");
            }
            // allows the user to read the output before the screen will be cleared
            System.out.println("-----------------------------------------------------");
            System.out.print("Press Enter to Continue. ");
            sc.nextLine();
        }
        // provides the correct answer
        System.out.println("1000 " + name1 + " converts to " + conversion + " " + name2);
        if (guesses > 0) {
            return 3 + (guesses * 2) + hints;
        }
        return 0;
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

    // returns the approximate value of 1000 of one currency converted to the second currency
    public double getConversion(String[] keys) {
        return (int) (rates.getDouble(keys[1]) / rates.getDouble(keys[0]) * 100000) / 100.0;
    }

    // generates a number of fake answers (with one being the real answer)
    public double[] answers() {
        double[] list = new double[numAnswers];
        // creates only the fake answers
        for (int i = 0; i < list.length; i++) {
            list[i] = conversion;
            // ensures that none of the fake answers can be the same as the real one
            while(list[i] == conversion) {
                String[] keys = randomKeys();
                list[i] = getConversion(keys);
            } 
        }
        // replaces a random fake answer with the real answer
        list[(int) (Math.random() * numAnswers)] = conversion;
        return list;
    }

    public String hint() {
        // gets the rate of one of the two currencies at random
        String thisKey = keys[(int) (Math.random() * 2)];
        double thisVal = rates.getDouble(thisKey);
        String thisName = names.getString(thisKey);
        // selects another different currency to compare it to
        String otherKey = (String) keyArray.get((int) (Math.random() * maxes[1]));
        double otherVal = rates.getDouble(otherKey);
        String otherName = names.getString(otherKey);

        // builds a string based on how the first rate compares to the new one
        String str = "";
        str += "1 " + thisName + " is worth ";
        if (thisVal < otherVal) {
            str += "more than";
        } else if (thisVal > otherVal) {
            str += "less than";
        } else {
            str += "the same as";
        }
        str += " 1 " + otherName;
        System.out.println(str);
        return str;
    }
}

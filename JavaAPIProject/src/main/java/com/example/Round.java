package com.example;
import org.json.JSONObject;
import java.util.*;

public class Round {
    // instance variables
    private JSONObject names;
    private JSONObject rates;
    private ArrayList<String> keyArray;
    private Scanner sc;
    private String[] keys;
    private int numAnswers;
    private int[] maxes;
    private double conversion;
    private int baseNum;

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
        // chooses the amount of currency to be converted (random multiple of 50 from 50 to 1000)
        baseNum = ((int) (Math.random() * 20) + 1) * 50; 
        // calculates the conversion rate
        conversion = getConversion(keys);
    }

    public int play() {
        // gets the names of the 2 currencies
        String name1 = names.getString(keys[0]);
        String name2 = names.getString(keys[1]);
        // gets the conversion rate
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
            System.out.println("-----------------------------------------------------");
            // pritns remaining guesses and hints
            System.out.println("Guesses Remaining: " + guesses + "    Hints Remaining: " + hints);
            // prints every hint already given to the player
            if (!hintStr.equals("")) {
                System.out.print("-----------------------------------------------------");
                System.out.println(hintStr);
            }
            System.out.println("-----------------------------------------------------");
            // prints the question and answer choices
            System.out.println(baseNum + " " + name1 + " converts to how many " + name2 + "?");
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
        System.out.println(baseNum + " " + name1 + " converts to " + conversion + " " + name2);
        if (guesses > 0) {
            return 3 + (guesses * 2) + hints;
        }
        return 0;
    }

    // generates a random pair of keys
    public String[] randomKeys() {
        // selects the first currency
        String[] keys = new String[2];
        keys[0] = keyArray.get((int) (Math.random() * maxes[0]));

        // selects the second currency
        keys[1] = keys[0];
        while (keys[0].equals(keys[1])) {
            keys[1] = keyArray.get((int) (Math.random() * maxes[1]));
        }
        return keys;
    }

    // returns the approximate value of the baseNum of one currency converted to the second currency
    public double getConversion(String[] keys) {
        return (int) (rates.getDouble(keys[1]) / rates.getDouble(keys[0]) * (baseNum * 100)) / 100.0;
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

    /* allows the player to recieve a hint:
     * first, the player chooses one of the two currencies from the question to compare
     * then, they choose an option out of 3 other currencies to compare it to
     * after this, they are told the conversion rate between those two
     */
    public String hint() {
        // allows the player to pick the first currency
        System.out.println("Which currency would you like to compare?");
        System.out.println("1) " + names.getString(keys[0]));
        System.out.println("2) " + names.getString(keys[1]));
        System.out.println("-----------------------------------------------------");
        System.out.print("Select: ");
        String thisKey = keys[sc.nextInt() - 1];
        sc.nextLine();
        String thisName = names.getString(thisKey);

        App.clearScreen();

        // allows the player to pick the second currency
        System.out.println("-----------------------------------------------------");
        System.out.println("Which currency will you compare " + thisName + " to?");
        String[] compareKeys = new String[3];
        for (int i = 0; i < 3; i++) {
            // randomly generates a list of 3 keys to compare the currency to
            compareKeys[i] = keyArray.get((int) (Math.random() * maxes[1]));
            System.out.println((i + 1) + ") " + names.getString(compareKeys[i]));
        }
        System.out.println("-----------------------------------------------------");
        System.out.print("Select: ");
        String otherKey = compareKeys[sc.nextInt() - 1];
        sc.nextLine();
        String otherName = names.getString(otherKey);

        // builds a string based on how the first rate compares to the new one
        String[] tempKeys = {thisKey, otherKey};
        String str = "";
        str += baseNum + " " + thisName + " is worth ";
        str += getConversion(tempKeys);
        str += " " + otherName;
        System.out.println(str);
        return str;
    }
}

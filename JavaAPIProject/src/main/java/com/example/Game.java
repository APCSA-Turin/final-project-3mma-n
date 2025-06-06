package com.example;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class Game {
    private JSONObject names;
    private JSONObject rates;
    private ArrayList<String> fullArray;
    private ArrayList<String> keyArray;
    private JSONObject countries;
    private Scanner sc;
    private int score;

    public Game(JSONObject n, JSONObject r, ArrayList<String> k, Scanner s) throws IOException {
        names = n;
        rates = r;
        fullArray = k;
        sc = s;
        score = 0;
        countries = new JSONObject(CurrencyAPI.getFile("/workspaces/final-project-3mma-n/JavaAPIProject/src/main/java/com/example/CurrencyList.JSON"));
        removeBadKeys();
        setCountryOnly(true);
    }


    // removes all keys with empty names from the array
    public void removeBadKeys() {
        for (int i = 1; i < fullArray.size(); i++) {
            if (names.getString(fullArray.get(i)).equals("")) {
                fullArray.remove(i);
                i--;
            }
        }
    }

    // true: sets keyArray to only contain currencies of real countries
    // false: sets keyArray to contain all currencies
    public void setCountryOnly(boolean bool) {
        keyArray = new ArrayList<String>();
        if (bool) {
            for (String item : fullArray) {
                try {
                    JSONObject country = countries.getJSONObject(item.toUpperCase());
                    keyArray.add(item);
                } catch (Exception JSONException) {
                }
            }
        } else {
            for (String item : fullArray) {
                keyArray.add(item);
            }
        }
    }

    // sorts the keys in an order based on the inputted method using insertion sort
    public void orderKeys(int method) {
        for (int i = 1; i < keyArray.size(); i++) {
            int j = i;
            boolean bool = true;
            while(bool) {
                keyArray.set(j - 1, keyArray.set(j, keyArray.get(j - 1)));
                j--;
                if (j <= 0) {
                    break;
                }
                // from closest to the value of one euro to furthest
                if (method == 1) {
                    bool = (distToOne(rates.getDouble(keyArray.get(j))) < distToOne(rates.getDouble(keyArray.get(j - 1))));
                // from A - Z by name
                } else if (method == 2) {
                    bool = (names.getString(keyArray.get(j)).toUpperCase().compareTo(names.getString(keyArray.get(j - 1)).toUpperCase()) < 0);
                }
            }
            
        }
    }

    // starts the game
    public void playGame(int numAnswers) {
        // orders the keys based on reciprocal distance to the value of one euro
        orderKeys(1);

        // allows the player to play rounds until they choose to stop or lose once
        String input = "y";
        while (input.equals("y")) {
            // creates a new round and runs it
            Round r = new Round(this, numAnswers);
            int earned = r.play();
            // ends the game if the player fails to guess correctly
            if (earned == 0) {
                break;
            }
            // adds the earned score to the total
            score += earned;
            System.out.println("Round over! " + earned + " points earned.");
            System.out.println("Current Score: " + score);
            System.out.print("Continue Playing? (y/n) ");
            input = sc.nextLine();
        }
        // prints a game over message and allows the player to return to the main menu of the app
        System.out.println("Game Over! Score: " + score);
        System.out.print("Press Enter to Return to Menu. ");
        sc.nextLine();
    }


    public void learnConversions() {
        int input = 1;
        String baseKey = "eur";
        while (input < 4) {
            orderKeys(2);
            // clears screen and prints the user's primary options
            App.clearScreen();
            System.out.println("-----------------------------------------------------");
            System.out.println("Welcome to the Currency Conversion Learning Tool!");
            System.out.println("Select an option below: ");
            System.out.println("1) See all rates A-Z compared to the Base Currency");
            System.out.println("2) See rates of currencies starting with a certain string");
            System.out.println("3) Change Base Currency (Currently " + names.getString(baseKey) + ")");
            System.out.println("4) Return to Menu");
            System.out.print("Select Option: ");
            input = sc.nextInt();
            sc.nextLine();
            if (input == 1) {
                int i = 0;
                while (i < keyArray.size()) {
                    // prints a divider to organize by starting letter
                    String letter = names.getString(keyArray.get(i)).substring(0, 1);
                    System.out.println(letter + " ---------------------------------------------------");
                    // prints all currencies starting with that letter
                    while (i < keyArray.size() && names.getString(keyArray.get(i)).substring(0, 1).equals(letter)) {
                        printFromIDX(i, baseKey);
                        i++;
                    }
                }
            } else if (input == 2) {
                System.out.print("Enter the String that the currency must start with (eg. 'E' or 'Al') ");
                printInRange(sc.nextLine(), baseKey);
            } else if (input == 3) {
                // allows the user to change the base currency
                System.out.print("Enter the currency symbol to make the base currency: ");
                String temp = sc.nextLine();
                // ensures that the entered key is actually valid
                try {
                    double testVal = rates.getDouble(temp);
                    baseKey = temp;
                    System.out.println("Base currency changed to " + names.getString(baseKey));
                } catch (Exception JSONException) {
                    System.out.println("Invalid Key Entered");
                }
            } else {
                break;
            }
            System.out.println("-----------------------------------------------------");
            System.out.print("Press Enter to Continue. ");
            sc.nextLine();
        }
    }

    // necessary getters for major instance variables
    public JSONObject getNames() {
        return names;
    }
    public JSONObject getRates() {
        return rates;
    }
    public ArrayList<String> getKeyArray() {
        return keyArray;
    }
    public Scanner getScanner() {
        return sc;
    }

    public int getScore() {
        return score;
    }

    // returns the reciprocal distance to 1 (eg. 0.5 and 2 both return 2)
    public static double distToOne(double num) {
        if (num < 1) {
            return 1 / num;
        }
        return num;
    }

    // prints data for all currencies starting with the string sub
    public void printInRange(String sub, String baseKey) {
        sub = sub.toUpperCase();
        int i = 0;
        while (i < keyArray.size()) {
            String current = names.getString(keyArray.get(i));
            if (current.length() >= sub.length() && current.substring(0, sub.length()).toUpperCase().equals(sub)) {
                printFromIDX(i, baseKey);
            }
            i++;
        }
    }

    // prints data for a currency given its index
    public void printFromIDX(int idx, String baseKey) {
        String key = keyArray.get(idx);
        double num = rates.getDouble(baseKey) / rates.getDouble(key);
        System.out.println("  " + names.getString(key) + " (" + key.toUpperCase() + ") - " + num + " " + names.getString(baseKey));
    }

}

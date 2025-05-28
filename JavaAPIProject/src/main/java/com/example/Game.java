package com.example;
import org.json.JSONObject;
import java.util.*;

public class Game {
    private JSONObject names;
    private JSONObject rates;
    private ArrayList<Object> keyArray;
    private Scanner sc;
    private int score;

    public Game(JSONObject n, JSONObject r, ArrayList<Object> k, Scanner s) {
        names = n;
        rates = r;
        keyArray = k;
        sc = s;
        score = 0;
        removeBadKeys();
    }


    // removes all keys with empty names from the array
    public void removeBadKeys() {
        for (int i = 1; i < keyArray.size(); i++) {
            if (names.getString((String) keyArray.get(i)).equals("")) {
                keyArray.remove(i);
                i--;
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
                    bool = (distToOne(rates.getDouble((String) keyArray.get(j))) < distToOne(rates.getDouble((String) keyArray.get(j - 1))));
                // from A - Z by name
                } else if (method == 2) {
                    bool = (names.getString((String) keyArray.get(j)).toUpperCase().compareTo(names.getString((String) keyArray.get(j - 1)).toUpperCase()) < 0);
                }
            }
            
        }
    }

    // starts the game
    public void playGame(int numAnswers, int[] maxes) {
        // orders the keys based on reciprocal distance to the value of one euro
        orderKeys(1);

        // allows the player to play rounds until they choose to stop or lose once
        String input = "y";
        while (input.equals("y")) {
            // creates a new round and runs it
            Round r = new Round(this, numAnswers, maxes);
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
        while (input < 3) {
            orderKeys(2);
            // clears screen and prints the user's primary options
            App.clearScreen();
            System.out.println("-----------------------------------------------------");
            System.out.println("Welcome to the Currency Conversion Learning Tool!");
            System.out.println("Select an option below: ");
            System.out.println("1) See all rates A-Z compared to one Euro");
            System.out.println("2) See rates of currencies starting with a certain string");
            System.out.println("3) Return to Menu");
            System.out.print("Select Option: ");
            input = sc.nextInt();
            sc.nextLine();
            if (input == 1) {
                int i = 0;
                while (i < keyArray.size()) {
                    String letter = names.getString((String) keyArray.get(i)).substring(0, 1);
                    System.out.println(letter + " ---------------------------------------------------");
                    while (i < keyArray.size() && names.getString((String) keyArray.get(i)).substring(0, 1).equals(letter)) {
                        System.out.println("  " + names.getString((String) keyArray.get(i)) + " - " + rates.getDouble((String) keyArray.get(i)));
                        i++;
                    }
                }
            } else if (input == 2) {
                System.out.print("Enter the String that the currency must start with (eg. 'E' or 'Al') ");
                printInRange(sc.nextLine());
            } else {
                break;
            }
            System.out.println("-----------------------------------------------------");
            System.out.print("Press Enter to Continue. ");
            sc.nextLine();
        }
    }

    // necessary getters and setters for major instance variables
    public JSONObject getNames() {
        return names;
    }
    public JSONObject getRates() {
        return rates;
    }
    public ArrayList<Object> getKeyArray() {
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

    public void printInRange(String sub) {
        sub = sub.toUpperCase();
        int i = 0;
        while (i < keyArray.size()) {
            String current = names.getString((String) keyArray.get(i));
            if (current.length() >= sub.length() && current.substring(0, sub.length()).toUpperCase().equals(sub)) {
                System.out.println("  " + current + " - " + rates.getDouble((String) keyArray.get(i)));
            }
            i++;
        }
    }
}

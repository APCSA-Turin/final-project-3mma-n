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

    public void removeBadKeys() {
        for (int i = 1; i < keyArray.size(); i++) {
            if (names.getString((String) keyArray.get(i)).equals("")) {
                keyArray.remove(i);
                i--;
            }
        }
    }

    // sorts the keys in an order based on the inputted method
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
                    bool = (names.getString((String) keyArray.get(j)).compareTo(names.getString((String) keyArray.get(j - 1))) < 0);
                }
            }
            
        }
    }

    public void playGame(int numAnswers, int[] maxes) {
        orderKeys(1);
        String input = "y";
        while (input.equals("y")) {
            Round r = new Round(this, numAnswers, maxes);
            int earned = r.play();
            if (earned == 0) {
                break;
            }
            score += earned;
            System.out.println("Round over! " + earned + " points earned.");
            System.out.println("Current Score: " + score);
            System.out.print("Continue Playing? (y/n) ");
            input = sc.nextLine();
        }
        System.out.println("Game Over! Score: " + score);
        System.out.print("Press Enter to Return to Menu. ");
        sc.nextLine();
    }

    public void learnConversions() {
        System.out.println("Welcome to the Currency Conversion Learning Tool!");
        System.out.println("Please Select an option below: ");
        orderKeys(2);
        int i = 0;
        while (i < keyArray.size()) {
            String letter = names.getString((String) keyArray.get(i)).substring(0, 1);
            System.out.println(letter + " ---------------------------------------------------");
            while (i < keyArray.size() && names.getString((String) keyArray.get(i)).substring(0, 1).equals(letter)) {
                System.out.println("  " + names.getString((String) keyArray.get(i)) + " - " + rates.getDouble((String) keyArray.get(i)));
                i++;
            }
        }
        System.out.println("-----------------------------------------------------");
    }

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

    // returns the exponential distance to 1 (eg. 0.5 and 2 both return 2)
    public static double distToOne(double num) {
        if (num < 1) {
            return 1 / num;
        }
        return num;
    }
}

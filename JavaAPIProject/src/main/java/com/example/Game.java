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
        setupKeys();
    }

    public void setupKeys() {
        // sorts the keys based on which is closest to the value of one Euro using insertion sort
        for (int i = 1; i < keyArray.size(); i++) {
            if (names.getString((String) keyArray.get(i)).equals("")) {
                keyArray.remove(i);
                i--;
            } else {
                int j = i;
                while(j > 0 && (distToOne(rates.getDouble((String) keyArray.get(j))) < distToOne(rates.getDouble((String) keyArray.get(j - 1))))) {
                    keyArray.set(j - 1, keyArray.set(j, keyArray.get(j - 1)));
                    j--;
                }
            }
        }
    }
    public void playRound(int numAnswers, int[] maxes) {
        String input = "y";
        while (input.equals("y")) {
            Round r = new Round(this, numAnswers, maxes);
            int earned = r.play();
            score += earned;
            System.out.println("Round over! " + earned + " points earned.");
            System.out.println("Current Score: " + score);
            System.out.print("Play Again? (y/n) ");
            input = sc.nextLine();
        }
        System.out.println("Goodbye!");
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

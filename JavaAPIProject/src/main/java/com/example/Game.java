package com.example;
import org.json.JSONObject;
import java.util.*;

public class Game {
    private JSONObject names;
    private JSONObject rates;
    private ArrayList<Object> keyArray;
    private Scanner sc;

    public Game(JSONObject n, JSONObject r, ArrayList<Object> k, Scanner s) {
        names = n;
        rates = r;
        keyArray = k;
        sc = s;
        setupKeys();
    }

    public void setupKeys() {
        // sorts the keys based on which is closest to the value of one Euro
        for (int i = 1; i < keyArray.size(); i++) {
            if (names.getString((String) keyArray.get(i)).equals("")) {
                keyArray.remove(i);
                i--;
            } else {
                int j = i;
                while(j > 0 && (Math.abs(1 - rates.getDouble((String) keyArray.get(j))) < Math.abs(1 - rates.getDouble((String) keyArray.get(j - 1))))) {
                    keyArray.set(j - 1, keyArray.set(j, keyArray.get(j - 1)));
                    j--;
                }
            }
        }
    }
    public void playRound(int numAnswers, int[] maxes) {
        Round r = new Round(this, numAnswers, maxes);
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

    // public void runRound(int firstMax, int secondMax) {
    //     String[] keys = randomKeys(firstMax, secondMax);
    //     String name1 = names.getString(keys[0]);
    //     String name2 = names.getString(keys[1]);
    //     double rate1 = rates.getDouble(keys[0]);
    //     double rate2 = rates.getDouble(keys[1]);

    //     double conversion = (int) (rate2 / rate1 * 100000) / 100.0;
    //     int guess = 5;
    //     int hints = 3;
    //     int numAnswers = 4;
    //     double[] choices = fakeAnswers(numAnswers, conversion, firstMax, secondMax);
    //     while (guess <= 0 || guess > numAnswers) {
    //         App.clearScreen();
    //         System.out.println("1000 " + name1 + " converts to how many " + name2 + "?");
    //         for (int i = 0; i < numAnswers; i++) {
    //             System.out.println((i + 1) + ") " + choices[i] + " " + name2);
    //         }

    //         if (hints > 0) {
    //             System.out.println((numAnswers + 1) + ") Recieve a Hint: ");
    //         }
    //         System.out.print("Guess: ");
    //         guess = sc.nextInt();
    //         if (guess > numAnswers) {
    //             if (guess == numAnswers + 1 && hints > 0) {

    //             } else {
    //                 System.out.println("Invalid Input.");
    //             }
    //             System.out.println("Press Enter to Continue.");
    //             sc.nextLine();
    //         }
    //     }
    //     if (choices[guess - 1] == conversion) {
    //         System.out.print("Correct!! ");
    //     } else {
    //         System.out.print("Wrong!! ");
    //     }
    //     System.out.println("1000 " + name1 + " converts to " + conversion + " " + name2);
    // }

    // public String[] randomKeys(int firstMax, int secondMax) {
    //     // selects the first currency
    //     String[] keys = new String[2];
    //     keys[0] = (String) keyArray.get((int) (Math.random() * firstMax));

    //     // selects the second currency
    //     keys[1] = keys[0];
    //     while (keys[0].equals(keys[1])) {
    //         keys[1] = (String) keyArray.get((int) (Math.random() * secondMax));
    //     }
    //     return keys;
    // }

    // public double getConversion(String[] keys) {
    //     return (int) (rates.getDouble(keys[1]) / rates.getDouble(keys[0]) * 100000) / 100.0;
    // }

    // public double[] fakeAnswers(int numChoices, double realAns, int firstMax, int secondMax) {
    //     double[] list = new double[4];
    //     for (int i = 0; i < list.length; i++) {
    //         list[i] = realAns;
    //         while(list[i] == realAns) {
    //             String[] keys = randomKeys(firstMax, secondMax);
    //             list[i] = getConversion(keys);
    //         } 
    //     }
    //     list[(int) (Math.random() * numChoices)] = realAns;
    //     return list;


}

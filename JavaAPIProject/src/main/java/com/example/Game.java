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

    public void runRound(int firstMax, int secondMax) {
        String[] keys = randomKeys(firstMax, secondMax);
        String name1 = names.getString(keys[0]);
        String name2 = names.getString(keys[1]);
        double rate1 = rates.getDouble(keys[0]);
        double rate2 = rates.getDouble(keys[1]);

        // System.out.println(num);
        // double rate1 = rates.getDouble((String) keyArray.get(num));
        // System.out.println(name1 + "--- " + rate1 + " (" + keyArray.get(num) + ")\n");



        
        // System.out.println(num);
        // double rate2 = rates.getDouble((String) keyArray.get(num));
        // System.out.println(name2 + "--- " + rate2 + " (" + keyArray.get(num) + ")\n");

        double conversion = (int) (rate2 / rate1 * 100000) / 100.0;
        System.out.println("1000 " + name1 + " converts to how many " + name2 + "?");
        System.out.print("Guess: ");
        double guess = sc.nextDouble();
        System.out.println("1000 " + name1 + " converts to " + conversion + " " + name2);
    }

    public String[] randomKeys(int firstMax, int secondMax) {
        // selects the first currency
        String[] keys = new String[2];
        keys[0] = (String) keyArray.get((int) (Math.random() * firstMax));

        // selects the second currency
        keys[1] = keys[0];
        while (keys[0].equals(keys[1])) {
            keys[1] = (String) keyArray.get((int) (Math.random() * secondMax));
        }
        return keys;
    }

    public double getConversion(String[] keys) {
        return (int) (rates.getDouble(keys[1]) / rates.getDouble(keys[0]) * 100000) / 100.0;
    }

    public double[] fakeAnswers(int numChoices, double realAns, int firstMax, int secondMax) {
        double[] list = new double[4];
        for (int i = 0; i < list.length; i++) {
            double conversion = realAns;
            while(conversion == realAns) {
                String[] keys = randomKeys(firstMax, secondMax);
                conversion = getConversion(keys);
            } 
        }
        list[(int) (Math.random() * numChoices)] = realAns;
        return list;
    }
}

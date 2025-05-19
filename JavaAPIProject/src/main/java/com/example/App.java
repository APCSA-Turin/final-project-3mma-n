package com.example;
import org.json.JSONObject;
import java.util.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static boolean nameValid(String name) {
        if (name.equals("")) return false;
        return true;
    }

    public static void main(String[] args) throws Exception {
        String ratesEndpoint = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/eur.json";
        String ratesString = CurrencyAPI.getData(ratesEndpoint);
        // JSONArray obj = new JSONArray(ratesString);
        // JSONArray rates = obj.getJSONArray(0);
        JSONObject obj = new JSONObject(ratesString);
        JSONObject rates = obj.getJSONObject("eur");

        String namesEndpoint = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies.json";
        String namesString = CurrencyAPI.getData(namesEndpoint);
        JSONObject names = new JSONObject(namesString);
        
        Set<String> keySet = rates.keySet();
        ArrayList<Object> keyArray = new ArrayList<Object>(keySet);

        Scanner sc = new Scanner(System.in);
        Game g = new Game(names, rates, keyArray, sc);
        g.runRound(20, 200);
        g.runRound(0, 50);

        // System.out.println(keyArray[0]);
        // for (Object key : keyArray) {
        //     System.out.print(names.getString((String) key) + " -- ");
        // }



        // int count = 0;
        // for (int i = 0; i < keyArray.length; i++) {
        //     String name = names.getString((String)keyArray[i]);
        //     double rate = rates.getDouble((String) keyArray[i]);
        //     if (nameValid(name)) {
        //         System.out.println(name + "--- " + rate + " (" + keyArray[i] + ")");
        //         count++;
        //     }
        // }
        // System.out.println(count);

        sc.close();
    }
}

// INFO LINK: https://github.com/fawazahmed0/exchange-api#endpoints
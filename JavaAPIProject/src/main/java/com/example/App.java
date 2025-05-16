package com.example;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Set;
/**
 * Hello world!
 *
 */
public class App 
{
    public static boolean nameValid(String name) {
        if (!name.equals("")) return true;
        return false;
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
        Object[] keyArray = keySet.toArray();
        for (int i = 1; i < keyArray.length; i++) {
            int j = i - 1;
            while(j >= 0 && rates.getDouble((String) keyArray[j]) > rates.getDouble((String) keyArray[i])) {
                Object temp = keyArray[i];
                keyArray[i] = keyArray[j];
                keyArray[j] = temp;
            }
            
        }
        System.out.println(keyArray[0]);


        int num = 0;
        String name1 = "";
        while (!nameValid(name1)) {
            num = (int) (Math.random() * keyArray.length);
            name1 = names.getString((String)keyArray[num]);
        }
        System.out.println(num);
        double rate1 = rates.getDouble((String) keyArray[num]);
        System.out.println(name1 + "--- " + rate1 + " (" + keyArray[num] + ")\n");

        num = 0;
        String name2 = "";
        while (!nameValid(name2)) {
            num = (int) (Math.random() * keyArray.length);
            name2 = names.getString((String)keyArray[num]);
        }
        System.out.println(num);
        double rate2 = rates.getDouble((String) keyArray[num]);
        System.out.println(name2 + "--- " + rate2 + " (" + keyArray[num] + ")\n");
        System.out.println("1000 " + name1 + " converts to " + ((int) (rate2 / rate1 * 100000) / 100.0) + " " + name2);

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

    }

}

// INFO LINK: https://github.com/fawazahmed0/exchange-api/blob/main/MIGRATION.md
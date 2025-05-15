package com.example;
import org.json.JSONObject;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws Exception {
        String endpoint = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/eur.json";
        String jsonString = CurrencyAPI.getData(endpoint);
        JSONObject obj = new JSONObject(jsonString);
        //you can get the value of the key by calling the getString(key) method of JSON Object
        JSONObject eur = obj.getJSONObject("eur");
        double usd = eur.getDouble("usd");
        System.out.println(usd);
        // System.out.println(eur);
        // String name = obj.getString("name");
        // String type = obj.getString("type");
        // int level = obj.getInt("level");            
        // System.out.println(name + " is an " + type + " type at level " + level);
    }

}

// LINK: https://github.com/fawazahmed0/exchange-api/blob/main/MIGRATION.md
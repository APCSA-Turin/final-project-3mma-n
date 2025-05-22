package com.example;
import org.json.JSONObject;
import java.util.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
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

        JSONObject validList = new JSONObject();
        
        Set<String> keySet = rates.keySet();
        ArrayList<Object> keyArray = new ArrayList<Object>(keySet);

        Scanner sc = new Scanner(System.in);
        Game g = new Game(names, rates, keyArray, sc);
        int[] maxes = {20, 200};
        g.playRound(4, maxes);

        sc.close();
    }
}

// INFO LINK: https://github.com/fawazahmed0/exchange-api#endpoints
// OTHER LINKS: https://api.nbp.pl/en.html https://frankfurter.dev/
// https://stackoverflow.com/questions/7463414/what-s-the-best-way-to-load-a-jsonobject-from-a-json-text-file


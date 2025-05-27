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

    public static void instructions() {
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("You will be given a random amount of a currency");
        System.out.println("You must guess the conversion rate to a second currency out of provided choices.");
        System.out.println("Example: 100 US Dollar converts to how many Euro?");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("You will have 2 chances to guess the correct answer, and 3 available hints.");
        System.out.println("Using a hint will allow you to compare one of the two currencies to a third one.");
        System.out.println("Each hint used or incorrect guess will decrease the number of points you earn (max 10, min 5).");
        System.out.println("However, if you guess wrong twice, your game will end!");
        System.out.println("Good LucK!!");
        System.out.println("-------------------------------------------------------------------------------------");
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

        int input = 1;
        while (input < 4) {
            App.clearScreen();
            System.out.println("-----------------------------------------------------");
            System.out.println("Welcome to the Currency Conversion Game!");
            System.out.println("Select an Option Below:");
            System.out.println("1) Play Game");
            System.out.println("2) View Instructions");
            System.out.println("3) Learn Conversion Rates");
            System.out.println("4) Quit");
            input = sc.nextInt();
            sc.nextLine();
            if (input == 1) {
                int[] maxes = {20, 200};
                g.playGame(4, maxes);
            } else if (input == 2) {
                instructions();
            } else if (input == 3) {
                g.learnConversions();
            } else {
                System.out.println("Goodbye!");
            }
            System.out.print("Press Enter to Continue. ");
            sc.nextLine();
        }
        sc.close();
    }
}

// INFO LINK: https://github.com/fawazahmed0/exchange-api#endpoints
// OTHER LINKS: https://api.nbp.pl/en.html https://frankfurter.dev/
// https://stackoverflow.com/questions/7463414/what-s-the-best-way-to-load-a-jsonobject-from-a-json-text-file


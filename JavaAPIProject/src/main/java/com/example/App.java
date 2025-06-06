package com.example;
import org.json.JSONObject;
import java.util.*;
/**
 * Hello world!
 *
 */
public class App 
{
    // clears the screen
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    // prints out instructions for the player
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
        // retrieves the conversion rates of each currency
        String ratesEndpoint = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/eur.json";
        String ratesString = CurrencyAPI.getData(ratesEndpoint);
        JSONObject obj = new JSONObject(ratesString);
        JSONObject rates = obj.getJSONObject("eur");

        // retrieves the names of each currency
        String namesEndpoint = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies.json";
        String namesString = CurrencyAPI.getData(namesEndpoint);
        JSONObject names = new JSONObject(namesString);
        
        // creates an ArrayList to store all keys
        Set<String> keySet = rates.keySet();
        ArrayList<Object> objArray = new ArrayList<Object>(keySet);
        ArrayList<String> keyArray = new ArrayList<String>();
        for (Object item : objArray) {
            keyArray.add((String) item);
        }

        String fileStr = CurrencyAPI.getFile("/workspaces/final-project-3mma-n/JavaAPIProject/src/main/java/com/example/CurrencyList.JSON");

        Scanner sc = new Scanner(System.in);

        // creates a new game
        Game g = new Game(names, rates, keyArray, sc);
        boolean countryOnly = true;
        // runs until the user chooses to quit
        int input = 1;
        while (input < 5) {
            // clears screen and prints the user's primary options
            App.clearScreen();
            System.out.println("-----------------------------------------------------");
            System.out.println("Welcome to the Currency Conversion Game!");
            System.out.println("Select an Option Below:");
            System.out.println("1) Play Game");
            System.out.println("2) View Instructions");
            System.out.println("3) Learn Conversion Rates");
            // displays differently based on wheter countryOnly is true
            System.out.print("4) Include Non-Country Currencies (Currently ");
            if (countryOnly) {
                System.out.println("Off)");
            } else {
                System.out.println("On)");
            }
            System.out.println("5) Quit");
            // allows the user to select a choice from the menu
            System.out.print("Select Option: ");
            input = sc.nextInt();
            sc.nextLine();
            if (input == 1) {
                // runs the game
                g.playGame(4);
            } else if (input == 2) {
                // prints the instructions
                instructions();
                System.out.print("Press Enter to Continue. ");
                sc.nextLine();
            } else if (input == 3) {
                // runs the learning tool
                g.learnConversions();
            } else if (input == 4) {
                // toggles non-Country currencies on/off
                countryOnly = !countryOnly;
                g.setCountryOnly(countryOnly);
            } else {
                System.out.println("Goodbye!");
            }
        }
        sc.close();
    }
}

// INFO LINK: https://github.com/fawazahmed0/exchange-api
// OTHER LINKS: https://api.nbp.pl/en.html https://frankfurter.dev/
// https://stackoverflow.com/questions/7463414/what-s-the-best-way-to-load-a-jsonobject-from-a-json-text-file


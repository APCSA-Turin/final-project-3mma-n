# **1\. Project Overview \- Currency Conversion Game**

This project allows the user to play rounds of a currency conversion game and get points for each round based on how well they perform. The user will have to guess the conversion rate of one randomly selected currency to another randomly selected currency out of 4 options. The user has 2 guesses and 3 available hints, but each one used will decrease the points earned.

# **2\. Code Breakdown**

## **App.java:**

Imports JSON Files and runs the main menu

### main(String\[\] args)

\- Entry point of the program.  
\- Calls the methods that retrieve the names and rates lists  
\- Creates an ArrayList of every key  
\- Creates an instance of the game class  
\- Initializes a Scanner object  
\- Runs a while loop allowing the user to select options  
\- The inclusion of non-Country currencies can be enabled or disabled

### instructions()

\- Prints all the instructions for playing the game

### clearScreen()

\- Clears the terminal

## **Game.java:** 

Responsible for managing the keyArray and running Rounds of the Game

### **int score:** 

\- Stores the accumulated score for the session

### playGame(int, numAnswers, int\[\] maxes)

\- Starts a while loop allowing the user to play until they lose or choose to stop  
\- Stores the score after each Round is run, and breaks the loop if it's zero  
\- Initializes a Round object on each iteration and runs the play method

### learnConversions

\- Starts a while loop allowing the user to choose how to display the conversion rates  
\- Starts with Euros as the base currency but allows the user to change it  
\- Organizes list Alphabetically

### removeBadKeys()

\- Removes all currencies with empty names from the keyArray

### public void setCountryOnly(boolean bool)

\- If set to true:   
  Determines which currencies are from a real country  
  Removes all other currencies from the keyArray  
\- If set to false:  
  Returns all currencies to the keyArray

### orderKeys(int method)

\- Uses insertion sort to organize the keys in an order based on the given method  
\- Method 1 is reciprocal distance to one (eg. 0.5 and 2 are both 2x away)  
\- Method 2 is alphabetical order

## 

### double distToOne(double num)

\- Returns the reciprocal distance between num and one

### public void printInRange(String sub, String baseKey)

\- Prints the conversion rates of all strings containing the substring at the beginning  
\- Calls printFromIDX() to print

### public void printFromIDX(int idx, String baseKey)

\- Prints the conversion rate of the currency at idx in keyArray  
\- Conversion rate is based on the entered base currency

### JSONObject getNames()

\- Returns the JSONObject storing currency names

### JSONObject getRates()

\- Returns the JSONObject storing exchange rates for 1 euro

### ArrayList\<Object\> getKeyArray()

\- Returns the ArrayList\<Object\> with all keys (currency symbols)

### Scanner getScanner()

\- Returns the Scanner initialized in App

### int getScore()

\- Returns the player's total score

## **Round.java:** 

Runs a single round of the Currency Conversion Game

### String\[\] keys: 

\- Stores the keys of the 2 random currencies

### int\[\] maxes: 

\- Stores the max indices for the first and second random currencies

### int numAnswers: 

\- Determines the number of answer choices

### double conversion: 

\- The conversion rate between the 2 currencies

### int baseNum:

\- The amount of the first currency used in the question, randomized from 50 to 1000

### int play()

\- Plays a round of the game and returns the earned score  
\- Runs a while loop allowing the player to guess or use a hint  
\- Given hints and incorrect guesses are printed to the selection screen after they are used  
\- Ends the loop when the correct answer is guessed or all the guesses have been used up  
\- Returns zero if the player fails to guess the answer correctly  
\- Otherwise, returns a base score of 5, \+1 for each hint left and \+2 for each guess left

### String\[\] randomKeys()

\- Returns a set of two random keys within the values of the maxes\[\] list

### double getConversion(String\[\] keys)

\- Returns the conversion between the 2 keys in the provided list

### double\[\] answers()

\- Returns a list of fake answers, with a random slot replaced by the real conversion rate

### String hint()

\- Prompts the user to compare one of the currencies from the question to another  
\- Prompts the user to choose between 3 other random currencies to compare it to  
\- Prints the conversion rate between the 2 selected currencies

## **CurrencyAPI.java:**

Responsible for retrieving JSON Files

### String getData(String endpoint)

\- Returns a String retrieved using the given endpoint

## **CurrencyList.JSON**

Contains the symbol of the currency from every real country

# **3\. Features Implemented (Rubric Aligned)**

✔ Base Project (88%)  
 \- Uses an external API (Currency Conversion API)  
 \- Provides interactive user experience using a Scanner object that reads typed inputs  
 \- Has 4 java classes and many outputs  
✔ Statistics / Basic Computation (6%)  
 \- Computes conversion rates between many different currencies based on the retrieved data  
✔ Filter/Sort Data (+2%)  
 \- Has the ability to sort currencies in multiple different orders  
✔ Written Walkthrough (Separate 5pts)  
 \- This document fulfills the walkthrough requirement  
**Total \= 96%**

# **4\. Output Example**

\[Images attatched to Walkthrough version on google classroom\]

# **5\. What I Learned**

\- How to retrieve JSON files from the internet using URLs  
\- How to use JSONObjects to get meaningful data from JSON Strings
\- How to use FileReader to read JSON Files in the repository
\- How to logically organize multiple classes to create a fluid game experience
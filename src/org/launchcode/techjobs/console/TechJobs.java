package org.launchcode.techjobs.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by LaunchCode
 */

/* ****** WHAT DOES THIS CODE DO (ALTOGETHER)? WHAT IS IT'S PURPOSE? ******

*/


//remember- a public class means it's accessable from outside this file
public class TechJobs {

    /*
    because the scanner (input) is private, it will only be visable in the class that declares it!
    creating a new scanner
    */

    private static Scanner in = new Scanner(System.in);

    /*
    main statement (main def statement in Python)
    */

    public static void main(String[] args) {

        /* Initialize our field map with key/name pairs
        This is the hash map that matches variable names from the data file to variable names displayed to the user
        in the app; we are creating a columnChoices HashMap (with the key and name values both being strings) and adding the variables to it as choices
        */

        HashMap<String, String> columnChoices = new HashMap();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        /*
        Top-level menu options
        A second HashMap is used to store the top level menu options of search and list that are presented to the user, also both strings
        */

        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        /*
        Allow the user to search until they manually quit
         */

        while (true) {

            /*
            create a variable called actionChoice use the getUserSelection function and actionChoices
            HashMap (search/list) to display the choices to the user and receive their choice back
            */

            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            /*
            if the user chose to "list" the jobs
            */

            if (actionChoice.equals("list")) {

                /*
                user the getUserSelection function again, to display the column choices and recieve the user's input
                */

                String columnChoice = getUserSelection("List", columnChoices);

                /*
                if the user chooses "all" than that's when we need the PRINT JOBS function, which hasn't been built yet!!
                */

                if (columnChoice.equals("all")) {
                    ArrayList allJobs = JobData.findAll();
                    ArrayList cloneList = (ArrayList<HashMap<String, String>>)allJobs.clone();
                    printJobs(cloneList);

                /*
                otherwise, the user chose a specific column to list
                 */

                } else {

                    /*
                    We create a new ArrayList with the type string named Results
                    And set it equal to a search within the JobData function that
                    finds all results that match the user's column choice
                     */

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    /*
                    Then it prints out a heading:
                    columnChoices.get(columnChoice) will return the value of the key!
                    and then we print all the results in the ArrayList!
                     */

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    Collections.sort(results);

                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            /*
            else they must have chosen to "search" the jobs
             */

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {
                    //Call function, pass user's input string
                    //if the results are empty, display a message
                    //else, print the results
                    ArrayList<HashMap<String, String>> jobList = JobData.findByValue(searchTerm);
                    if (jobList.isEmpty()) {
                        System.out.println("There are no results that match '" + searchTerm + "'. Please try again.");
                    } else {
                        printJobs(jobList);
                    }
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary

    /*

    Create a function called getUserSelection which takes in a menuHeader(string),
    and a HashMap (the variable name here is "choices" but this function will take either of the
    HashMaps columnChoices or actionChoices at different points; they are both comprised of two
    string data types
     */


    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {
        /*
        function: getUserSelection
        What does it do?
        It takes a HashMap and a header, and displays the menu choices (from the HashMap) to the user,
        then prompts the user to make a selection from the menu, and returns the user's choice
        */

        /*
        Initialize the variable we will later use to store the user's input
        */

        Integer choiceIdx;

        /*
        Initializing a boolean variable that will be used to represent whether the user has
        made a valid choice or not; it will default to false
        */

        Boolean validChoice = false;

        /*
        - String [] this is creating a new Array!!!! and setting it's size to 5 because the size of arrays
         must be determined when they are initiated!!
        - At this point it is an array with five "null" values
         */

        String[] choiceKeys = new String[choices.size()];

        /*
        - Put the choices in an ordered structure so we can
        - Associate an integer with each one
        */

        Integer i = 0;


        /*
        - The keySet method returns a Set view of the keys contained in this map
        - For eachChar in "string": This is the Python version of for (String choiceKey : choices.keySet()) {}
        - choices.keySet = [all, position type, employer, location, core competency] or [search, list]
        - So this code is creating an empty array of the hashmap keys!
        - choiceKey = search (etc); choiceKey is equal to each string within that array
        */

        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {
            /*
            print out the header (View Jobs By)
             */

            System.out.println("\n" + menuHeader);

            /*
            - Print available choices
            - Print each choice and it's corresponding number choice (where the 'j' is printed!)
            - Starting: 0; while j is less than the length of the menu, increment by 1 each time
            - Break it down: choices.get(choiceKeys[j])
            - if j = 0 and the choices (the HashMap) = actionChoices, then:
            -       choiceKeys[0] = search
            -       choices.get(search) = Search
            -       and System.out.println("" + j + " - " + choices.get(choiceKeys[j])) = "0 - Search"
            */

            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            /*
            get the user's input- should be a 0 or 1
            */

            choiceIdx = in.nextInt();
            in.nextLine();

            /*
            Validate user's input
            */

            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while (!validChoice);

        /*
        choiceKeys[choiceIdx] will return the option that the user selected
        */

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {
        //need a contional: if there are results, print results; if results are empty, print empty message

        //ArrayList allJobs = JobData.findAll();

        /*
        allJobs is this data type: JobArrayList<HashMap<String, String>> An array list of HashMaps (they each contain two strings)
         */

        //TODO: Iterate through allJobs
        //TODO: Iterate through each "job" HashMap to print each key + value (for the length of the HashMap)
        if (!someJobs.isEmpty()) {
            for (HashMap<String, String> eachJob : someJobs) {
                System.out.println("*****");
                for (String key : eachJob.keySet()) {
                    System.out.println(key + ":  " + eachJob.get(key));
                }
                System.out.println("*****");
            }
        } else {
            System.out.println("There are no results to display");
        }
    }
}

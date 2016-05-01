/**
 * NLP Project Part 2
 * Use this as the main class for your project.
 * Implement the logic to generate the SQL query and the query answer.
 * Create additional methods, variables, and classes as needed.
 *
 */

import java.util.Scanner;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;

public class CS421_Project2
{

    public static String currentQuery;
    public static String sqlQuery = null;
    public static DataBaseTier db = new DataBaseTier();
    public static QueryProcessor processor = new QueryProcessor();



    public static void main(String[] args)
    {

//----------------------------------------------------------------------------------
        System.out.println("Welcome! This is MiniWatson.");
        System.out.println("Please ask a question. Type 'q' when finished.");
        System.out.println();
        String input;
        Scanner keyboard = new Scanner(System.in);
        do{
            System.out.print(">");
            input =keyboard.nextLine().trim();

            if(!input.equalsIgnoreCase("q")){
                currentQuery = input;
                System.out.println("<QUERY>\n" + currentQuery);
                //TODO perform any query processing
                processor.determineQuestionType(currentQuery);

                //-----------------------------------------------
                printSQL(); //TODO implement method below
                printAnswer(); //TODO implement method below
                System.out.println();

            }
        }while(!input.equalsIgnoreCase("q"));

        keyboard.close();
        System.out.println("Goodbye.");
    //    System.exit(0);

    }

    public static void printSQL(){
        //TODO update this to get and print appropriate SQL
        sqlQuery = "SELECT COUNT(*) FROM Actor;"; // map currentQuery to sqlQuery
        System.out.println("<SQL>\n" + sqlQuery);
    }
    public static void printAnswer(){
        String answer = "";
        if(processor.getYesnoFlag() == 1 && processor.getWhFlag() ==0){
            if(db.executeYesNoQuery(sqlQuery)) answer = "Yes";
            else answer = "No";
        }

        System.out.println("<ANSWER>\n" + answer);
    }

}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Rakesh on 4/30/2016.
 */
/*
    This class is used to provide methods that access the database. The plan was to have it like a 3-tier architecture design, where
    one tier is solely for the database.
 */
public class DataBaseTier {

    public DataBaseTier(){
        //default constructor
    }


    /*
        This is used to execute queries to the database for questions that require a Yes/No answer
     */

    public boolean executeYesNoQuery(String sqlQuery){
        Statement stmt = null;
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+"oscar-movie_imdb.sqlite");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            e.printStackTrace();
            System.exit(0);
        }

        try {
            ResultSet rs = stmt.executeQuery(sqlQuery);
            System.out.println("Table created successfully");
            if(rs.getInt(1) > 0){
                stmt.close();
                c.close();
                return true;
            }
            stmt.close();
            c.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
    /*
        This is used to execute queries where the quesiton begins with a WH*
     */
    public String executeWhQuery(String sqlQuery){
        Statement stmt = null;
        Connection c = null;
        String answer = "";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+"oscar-movie_imdb.sqlite");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            e.printStackTrace();
            System.exit(0);
        }

        try {
            ResultSet rs = stmt.executeQuery(sqlQuery);
            System.out.println("Table created successfully");
            answer = rs.getString(1);
            stmt.close();
            c.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return answer;

    }
}

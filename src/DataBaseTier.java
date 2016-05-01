import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Rakesh on 4/30/2016.
 */
public class DataBaseTier {

    public DataBaseTier(){
        //default constructor
    }

    public boolean executeYesNoQuery(String sqlQuery){
        Statement stmt = null;
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+"C:/Users/Rakesh/Desktop/SqliteDatabases/oscar-movie_imdb.sqlite");
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
}

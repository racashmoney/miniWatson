import java.util.Hashtable;
/*
 *  Word bank class is used for translating nationality to country if asked a question such as
 * "Which French actor won the oscar in 2013?"
 */
public class Wordbank
{

    Hashtable<String, String> h;
    Hashtable<String, String> countries; //This set contains all the keys which are type of people, and countries associated with them
    public Wordbank() //Constructor for WordBank
    {
        h = new Hashtable<String, String>();
//        h.put("by", "Director");
//        h.put("director", "Director");
//        h.put("born", "Person");
//        h.put("star", "Actor");
//        h.put("oscar", "Oscar");
//        h.put("movie", "Movie");
//        h.put("film","Movie");

        countries = new Hashtable<String,String>();
        countries.put("French", "France");
        countries.put("Italian", "Italy");
        countries.put("German", "Germany");
        countries.put("Mexican", "Mexico");
        countries.put("Canadian", "Canada");
        countries.put("American", "USA");
        countries.put("Swedish", "Sweden");
        countries.put("Japanese", "Japan");
        countries.put("English", "England");
        countries.put("Italian", "Italy");
        countries.put("Indian", "Indian");
        countries.put("Irish", "Ireland");
        countries.put("Italian", "Italy");
        countries.put("Romanian", "Romania");
        countries.put("Scottish", "Scotland");
        countries.put("Chinese", "China");
        countries.put("Australian", "Australia");
        countries.put("Russian", "Russia");


    }
    /*
        Method to add any potential items to another hashtable, dynamically.
     */
    public void add(String key, String value)
    {
        h.put(key, value);
    }
}
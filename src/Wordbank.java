import java.util.Hashtable;

public class Wordbank
{

    Hashtable<String, String> h;
    Hashtable<String, String> countries;
    public Wordbank()
    {
        h = new Hashtable<String, String>();
        h.put("by", "Director");
        h.put("director", "Director");
        h.put("born", "Person");
        h.put("star", "Actor");
        h.put("oscar", "Oscar");
        h.put("movie", "Movie");
        h.put("film","Movie");

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

    public void add(String key, String value)
    {
        h.put(key, value);
    }
}
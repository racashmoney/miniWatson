package Part1;

import java.util.Hashtable;

public class Wordbank 
{
	
	Hashtable<String, String> h;
	
	public Wordbank()
	{
		h = new Hashtable<String, String>();
		h.put("capital", "Geography");
		h.put("deeper", "Geography");
		h.put("continent", "Geography");
		h.put("countries", "Geography");
		h.put("country", "Geography");
		h.put("border", "Geography");
		h.put("mountain", "Geography");
		h.put("world", "Geography");
		h.put("ocean", "Geography");
		h.put("deepest", "Geography");

		h.put("sing", "Music");
		h.put("song", "Music");
		h.put("sings", "Music");
		h.put("album", "Music");
		h.put("artist", "Music");
		h.put("born", "Music");
		
		h.put("star", "Movie");
		h.put("oscar", "Movie");
		h.put("actor", "Movie");
		h.put("movie", "Movie");
		h.put("film", "Movie");
		h.put("directed", "Movie");
		h.put("actress", "Movie");
		h.put("by", "Movie");
	}
	
	public void add(String key, String value)
	{
		h.put(key, value);
	}
}

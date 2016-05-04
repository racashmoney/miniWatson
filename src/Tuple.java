//package Part2;
/*
    This class is used to represent a sentence or question as two different strings.
    One string is in the given language. The second string as POS tags.
    A tuple stores one word and one tag.
 */
public class Tuple{

    private String word; // word of the sentence
    private String pos; // pos representing the word

    public Tuple(String word, String pos)
    {
        this.word = word;
        this.pos = pos;
    }

    public String getWord()
    {
        return word;
    }

    public String getPos()
    {
        return pos;
    }

    public void setWord(String word)
    {
        this.word = word;
    }
}
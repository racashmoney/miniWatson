//package Part2;

import java.util.ArrayList;

/**
 * Created by Rakesh on 4/30/2016.
 */
public class QueryProcessor {

    public int Flag = 0;

    public String sqlQuery = "";

    public QueryProcessor(){

    }


    //Function creates a flag depending on what type of question it's being asked.
    public void determineQuestionType(String currentQuery){
        if(currentQuery.startsWith("Is") || currentQuery.startsWith("Was")) {
            Flag = 1;
        }
        else if(currentQuery.startsWith("Did")){
            Flag = 2;
        }
        else if(currentQuery.startsWith("Who") || currentQuery.startsWith("When")|| currentQuery.startsWith("Which")){
            Flag = 3;
        }

    }

    public String buildSQLQuery(String currentQuery){
        determineQuestionType(currentQuery);
        Wordbank hash = new Wordbank();
        sqlQuery = "";
        String tree = Parsing.print_tree(currentQuery);
        ArrayList<Tuple> pos_list = Parsing.POS(tree);


        if(Flag == 1){
            sqlQuery = "SELECT COUNT(*) ";
            for(int i =0; i< pos_list.size(); i++)
            {
                //If the query contains by we know we are looking for a movie's director
                if (pos_list.get(i).getWord().equals("by"))
                {
                    sqlQuery += "From Person "
                            + "INNER JOIN Director ON Person.id = "
                            + "Director.director_id "
                            + "INNER JOIN Movie On Director.movie_id = Movie.id "
                            + "WHERE Person.name LIKE \"%" + pos_list.get(i+1).getWord() + "%\" AND Movie.name "
                            + "LIKE \"%" + pos_list.get(i - 1).getWord() + "%\"";
                }
                //If the query contains director then we know we are looking for a person
                if(pos_list.get(i).getWord().equals("director")){
                    String director_name = "";
                    for(Tuple t: pos_list){
                        if(t.getPos().equals("NNP")){
                            director_name = t.getWord();
                        }
                    }
                    sqlQuery += "From Person "
                            + "INNER JOIN Director ON Person.id = "
                            + "Director.director_id "
                            + "WHERE Person.name LIKE \"%" + director_name + "%\"";
                }
                //Checking for if an actor was in this movie
                if(pos_list.get(i).getWord().equals("born") && pos_list.get(i+1).getWord().equals("in")){
                    sqlQuery += "From Person "
                            + "INNER JOIN Actor ON Person.id = Actor.actor_id "
                            + "WHERE Person.name LIKE \"%" + pos_list.get(i-1).getWord() + "%\" AND Person.pob "
                            + "LIKE \"%" + pos_list.get(i+2).getWord() + "%\"";
                }
                //Checking if this movie won the best movie of that year
                if(pos_list.get(i).getWord().equals("best") && pos_list.get(i+1).getWord().equals("movie")){
                    String movie_name = "";
                    String year = "";
                    for(Tuple t: pos_list){
                        if(t.getPos().equals("NNP")){
                            movie_name = t.getWord();
                        }
                        if(t.getPos().equals("CD")){
                            year = t.getWord();
                        }
                    }
                    sqlQuery += "From Oscar "
                            + "INNER JOIN Movie ON Movie.id = Oscar.movie_id "
                            + "WHERE Movie.name LIKE \"%" + movie_name + "%\" AND Oscar.type = "
                            + "\"BEST PICTURE\" AND OSCAR.year = " + year;
                }
            }

        }
        else if (Flag == 2){
            sqlQuery += "SELECT COUNT(*) ";
            for(int i =0; i< pos_list.size(); i++)
            {
                //Checks if an actor was in a movie
                if(pos_list.get(i).getWord().equals("star") && pos_list.get(i+ 1).getWord().equals("in")){
                    String actor_name = pos_list.get(i - 1).getWord();
                    String movie_name = pos_list.get(i + 2).getWord();

                    sqlQuery += "From Movie "
                            + "INNER JOIN Actor ON Movie.id = Actor.movie_id "
                            + "INNER JOIN PERSON ON Actor.actor_id = Person.id "
                            + "WHERE Movie.name LIKE \"%" + movie_name + "%\" AND "
                            + "Person.name LIKE \"%" + actor_name + "%\"";

                }
                //Returns a person in the queury or a movie based on the question
                if(pos_list.get(i).getWord().equals("win") && pos_list.get(i + 2).getWord().equals("oscar"))
                {
                    Wordbank countries = new Wordbank();
                    String actor_name = "";
                    String year = "";
                    int bestflag = 0;
                    for(Tuple t: pos_list){
                        if(t.getPos().equals("NNP")){
                            actor_name = t.getWord();
                        }
                        if(t.getPos().equals("CD")){
                            year = t.getWord();
                        }
                        if(t.getPos().equals("JJ")){
                            actor_name = t.getWord();
                        }
                        if(t.getWord().equals("best"))
                        {
                            bestflag = 1;
                        }
                    }
                    if(countries.countries.containsKey(actor_name))
                    {
                        String country = countries.countries.get(actor_name);
                        sqlQuery += "From Person "
                                + "INNER JOIN Oscar ON Person.id = Oscar.person_id "
                                + "WHERE Person.pob LIKE \"%" + country + "%\" AND Oscar.year = "
                                + year;
                    }
                    else if(bestflag == 1)
                    {
                        sqlQuery+="FROM Person "
                                + "INNER JOIN Actor ON Actor.actor_id = Person.id "
                                + "INNER JOIN Movie ON Movie.id = Actor.movie_id "
                                + "INNER JOIN Oscar ON Oscar.movie_id = Movie.id "
                                + "WHERE Person.name LIKE \"%" + actor_name + "%\" AND Oscar.type = "
                                + "\"BEST-PICTURE\"";

                    }

                }
            }
        }
        else if (Flag == 3){

            if(pos_list.get(0).getWord().equals("Who")){
                for(int j = 0; j< pos_list.size(); j++){
                    //Checks who directed a movie
                    if(pos_list.get(j).getWord().equals("directed"))
                    {
                        int bestflag = 0;
                        String year = "";
                        for (Tuple t: pos_list)
                        {

                            if(t.getPos().equals("CD")){
                                year = t.getWord();
                            }
                            if(t.getWord().equals("best"))
                            {
                                bestflag = 1;
                            }
                        }
                        if(bestflag == 1)
                        {
                            sqlQuery += "SELECT Person.name "
                                    + "FROM Person "
                                    + "INNER JOIN Director ON Director.director_id = "
                                    + "Person.id "
                                    + "INNER JOIN Oscar ON Oscar.movie_id = "
                                    + "Director.movie_id "
                                    + "WHERE Oscar.year = " + year + " AND Oscar.type = "
                                    + "\"BEST-PICTURE\"";
                        }
                        else{
                            String movie = pos_list.get(j+1).getWord();
                            sqlQuery+= "SELECT Person.name "
                                    + "FROM Person "
                                    + "INNER JOIN Director ON Director.director_id = Person.id "
                                    + "INNER JOIN Movie ON Movie.id = Director.movie_id "
                                    + "WHERE Movie.name LIKE \"%" + movie + "%\"";
                        }

                    }
                    //checks who won an oscar in which category actress or actor etc.
                    if(pos_list.get(j).getWord().equals("oscar")){
                        String category = "";
                        String year = "";
                        category += pos_list.get(j+3).getWord().toUpperCase();
                        for(Tuple t : pos_list){
                            if(t.getPos().equals("CD"))
                            {
                                year += t.getWord();
                            }
                        }
                        sqlQuery += "SELECT Person.name "
                                + "FROM Person "
                                + "INNER JOIN Oscar ON Oscar.person_id = Person.id "
                                + "WHERE Oscar.year = " + year + " AND Oscar.type = "
                                + "\"BEST-" + category+ "\"";
                    }
                }
            }//end if
            else if(pos_list.get(0).getWord().equals("Which")){
                String type = "";
                int movie_flag = 0;
                String year = "";
                for(int i = 1; i < pos_list.size(); i++){
                    //Checks if a movie won in a category or actor
                    if(pos_list.get(i).getWord().equals("won")){
                        type += pos_list.get(i - 1).getWord().toUpperCase();
                        if(pos_list.get(i - 1).getWord().equals("movie")){
                            movie_flag = 1;
                        }
                        for(Tuple t: pos_list){
                            if(t.getPos().equals("CD")){
                                year = t.getWord();
                            }
                        }
                        if(movie_flag == 1){
                            sqlQuery += "SELECT Movie.name "
                                    + "From Movie "
                                    + "INNER JOIN Oscar ON Oscar.movie_id = Movie.id "
                                    + "WHERE Oscar.year = " + year + " AND Oscar.type = "
                                    + "\"BEST-PICTURE\"";
                        }
                        else{
                            sqlQuery += "SELECT Person.name "
                                    + "FROM Person "
                                    + "INNER JOIN Oscar ON Oscar.person_id = Person.id "
                                    + "WHERE Oscar.year = " + year + " AND Oscar.type = "
                                    + "\"BEST-" + type + "\"";
                        }


                    }
                }

            }//end else if
            else if(pos_list.get(0).getWord().equals("When")){
                String name = "";
                String category = "";
                for(int i = 0; i<pos_list.size(); i++){
                    if(pos_list.get(i).getPos().equals("NNP"))
                    {
                        name = pos_list.get(i).getWord();
                    }
                    if(pos_list.get(i).getWord().equals("best"))
                    {
                        category = pos_list.get(i + 1).getWord().toUpperCase();
                    }
                }

                sqlQuery += "SELECT Oscar.year "
                        + "FROM Oscar "
                        + "INNER JOIN Person ON Person.id = Oscar.person_id "
                        + "WHERE Person.name LIKE \"%" + name + "%\" AND Oscar.type = "
                        + "\"BEST-" + category + "\"";
            }
        }

        return sqlQuery;
    }


    public int getFlag()
    {
        return Flag;
    }
}
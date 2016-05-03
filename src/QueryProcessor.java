//package Part2;

import java.util.ArrayList;

/**
 * Created by Rakesh on 4/30/2016.
 */
public class QueryProcessor {

    public int flag = 0;

    public String sqlQuery = "";

    public QueryProcessor(){

    }



    public void determineQuestionType(String currentQuery){
        if(currentQuery.startsWith("Is") || currentQuery.startsWith("Was")) {
            flag = 1;
        }
        else if(currentQuery.startsWith("Did")){
            flag = 2;
        }
        else if(currentQuery.startsWith("Who") || currentQuery.startsWith("When")|| currentQuery.startsWith("Which")){
            flag = 3;
        }

    }

    public String buildSQLQuery(String currentQuery){
        determineQuestionType(currentQuery);
        Wordbank hash = new Wordbank();
        sqlQuery = "SELECT COUNT(*)";
        String tree = Parsing.print_tree(currentQuery);
        ArrayList<Tuple> pos_list = Parsing.POS(tree);


        if(flag == 1){
            for(int i =0; i< pos_list.size(); i++)
            {

                if (pos_list.get(i).getWord().equals("by"))
                {

                    sqlQuery += "From Person "
                            + "INNER JOIN Director ON Person.id = "
                            + "Director.director_id "
                            + "INNER JOIN Movie On Director.movie_id = Movie.id "
                            + "WHERE Person.name LIKE \"%" + pos_list.get(i+1).getWord() + "%\" AND Movie.name "
                            + "LIKE \"%" + pos_list.get(i - 1).getWord() + "%\"";
                }
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
                if(pos_list.get(i).getWord().equals("born") && pos_list.get(i+1).getWord().equals("in")){
                    sqlQuery += "From Person "
                            + "INNER JOIN Actor ON Person.id = Actor.actor_id "
                            + "WHERE Person.name LIKE \"%" + pos_list.get(i-1).getWord() + "%\" AND Person.pob "
                            + "LIKE \"%" + pos_list.get(i+2).getWord() + "%\"";
                }
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
        else if (flag == 2){
            for(int i =0; i< pos_list.size(); i++)
            {
                if(pos_list.get(i).getWord().equals("star") && pos_list.get(i+ 1).getWord().equals("in")){
                    String actor_name = pos_list.get(i - 1).getWord();
                    String movie_name = pos_list.get(i + 2).getWord();

                    sqlQuery += "From Movie "
                            + "INNER JOIN Actor ON Movie.id = Actor.movie_id "
                            + "INNER JOIN PERSON ON Actor.actor_id = Person.id "
                            + "WHERE Movie.name LIKE \"%" + movie_name + "%\" AND "
                            + "Person.name LIKE \"%" + actor_name + "%\"";

                }

                if(pos_list.get(i).getWord().equals("win") && pos_list.get(i + 2).getWord().equals("oscar"))
                {
                    String actor_name = "";
                    String year = "";
                    String country = "";
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
                    }
    //                if(actor_name.equals("French"))
                    if(hash.countries.containsKey(actor_name))
                    {
                        country = hash.countries.get(actor_name);
                        sqlQuery += "From Person "
                                + "INNER JOIN Oscar ON Person.id = Oscar.person_id "
                                + "WHERE Person.pob LIKE \"%" + country + "%\" AND Oscar.year = " + year;
                    }
                    else{
                        if(year.equals("")){
                            sqlQuery += "From Person "
                                    + "INNER JOIN Oscar ON Person.id = Oscar.person_id "
                                    + "WHERE Person.name LIKE \"%" + actor_name + "%\"";
                        }
                        else {
                            sqlQuery += "From Person "
                                    + "INNER JOIN Oscar ON Person.id = Oscar.person_id "
                                    + "WHERE Person.name LIKE \"%" + actor_name + "%\" AND Oscar.year = " + year;
                        }
                    }

                }
            }
        }
        else if (flag == 3){

        }

        return sqlQuery;
    }


    public int getFlag()
    {
        return flag;
    }
}
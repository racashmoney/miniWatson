/**
 * Created by Rakesh on 4/30/2016.
 */
public class QueryProcessor {

    public int yesnoFlag = 0;
    public int whFlag = 0;

    public QueryProcessor(){

    }

    public void determineQuestionType(String currentQuery){
        if(currentQuery.startsWith("Is") || currentQuery.startsWith("Was") || currentQuery.startsWith("Did")) {
            yesnoFlag = 1;
            whFlag = 0;
        }
        else if(currentQuery.startsWith("Where")|| currentQuery.startsWith("What") || currentQuery.startsWith("Who") || currentQuery.startsWith("When")|| currentQuery.startsWith("Which")){
            yesnoFlag = 0;
            whFlag = 1;
        }
        else{

        }
    }

    public String buildSQLQuery(String currentQuery){
        String sqlQuery = "";
        if(yesnoFlag == 1 && whFlag == 0){
            sqlQuery = "SELECT COUNT(*)";
        }

        return sqlQuery;
    }

    public int getYesnoFlag(){
        return yesnoFlag;
    }

    public int getWhFlag() {
        return whFlag;
    }

    public void setYesnoFlag(int yesnoFlag) {
        this.yesnoFlag = yesnoFlag;
    }

    public void setWhFlag(int whFlag) {
        this.whFlag = whFlag;
    }
}

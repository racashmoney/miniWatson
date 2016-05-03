//package Part2;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class Parsing {

    public static List<Tree> parse(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        List<Tree> result = new ArrayList<Tree>();
        for (CoreMap sentence : sentences) {
            Tree tree = sentence.get(TreeAnnotation.class);
            result.add(tree);
        }

        return result;
    }


    public static String print_tree(String t) {
        String text = t;
        String tree_string = "";
        List<Tree> trees = parse(text);
        for (Tree tree : trees) {
            tree_string += tree.toString();
            System.out.println(tree);
        }
        //System.out.println("=====================================================");
        return tree_string;
    }


    public static ArrayList<Tuple> POS(String tree){

        ArrayList<Tuple> list = new ArrayList<Tuple>();
        String word = "";
        String pos = "";
        for(int i = 0; i < tree.length(); i++)
        {

            if(tree.charAt(i) == '('){
                i++;
                while(tree.charAt(i) != ' '){
                    pos += tree.charAt(i);
                    i++;
                }
                if(tree.charAt(i + 1) == '('){
                    pos = "";
                    continue;
                }
                else{
                    while(tree.charAt(i) != ')'){
                        pos += tree.charAt(i);
                        i++;
                    }
                    Tuple tup = new Tuple(word,pos);
                    System.out.println(pos + " " + word);
                    word = "";
                    pos = "";
                    list.add(tup);
                    continue;
                }
            }
        }
        return list;
    }

//    public static void main(String args[])
//    {
//        String s = "Who won the oscar for best actor in 2005?";
//        String tree = print_tree(s);
//        ArrayList<Tuple> posTags = POS(tree);
//        Tuple t = posTags.get(0);
//        if(t.getWord().toUpperCase().equals("IS")||t.getWord().toUpperCase().equals("WAS"))
//        {
//            //yes to-be category
//        }
//        else if(t.getWord().toUpperCase().equals("DID"))
//        {
//            //yes do question
//        }
//        else if(t.getWord().toUpperCase().equals("WHO") ||
//                t.getWord().toUpperCase().equals("WHICH")||
//                t.getWord().toUpperCase().equals("WHEN"))
//        {
//            //Wh-question
//        }
//        else{
//            //unknown question
//        }
//        //System.out.println(tree);
//    }
}
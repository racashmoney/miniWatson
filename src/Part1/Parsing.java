package Part1;

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

    List<Tree> trees = parse(text);
    for (Tree tree : trees) {
      System.out.println(tree);
    }
    System.out.println("=====================================================");
	return text;

  }
}
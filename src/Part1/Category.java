package Part1;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;


public class Category {

	public static ArrayList<Tuple> labelText(String text) {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(text);
		ArrayList<Tuple> list = new ArrayList<Tuple>();

		// run all Annotators on this text
		pipeline.annotate(document);

		// these are all the sentences in this document
		// a CoreMap is essentially a Map that uses class objects as keys and
		// has values with custom types
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		for (CoreMap sentence : sentences) {
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				// this is the text of the token
				String word = token.get(TextAnnotation.class);
				// this is the NER label of the token
				String ne = token.get(NamedEntityTagAnnotation.class);

				// Adds tuple of word and NER label to the ArrayList
				Tuple t = new Tuple(word, ne);
				list.add(t);
				//System.out.println("word: " + t.getWord() + " ne:" + t.getLabel());
			}
		}
		return list;
	}

	public static String[] readFile(String file) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(file));
		String str;

		List<String> list = new ArrayList<String>();
		while ((str = in.readLine()) != null) {
			list.add(str);
		}

		String[] stringArr = list.toArray(new String[0]);
		return stringArr;
	}

	public static void main(String[] args) throws IOException {
		String fileName = "sentences.txt";
		String[] sentences = readFile(fileName);
		/*String[] sentences = {"Is the Pacific deeper than the Atlantic?", "Did Swank win the oscar in 2000?",
				"Is the Shining by Kubrik?","Does the album Thriller include the track BeatIt?", "Who directed Hugo?",
				"Which is the scary movie by Kubrik with Nicholson?", "In which continent does Canada lie?",
				"With which countries does France have a border?", "Where was Gaga born?", "In which album does Aura appear?",
				"Which album by Swift was released in 2014?"};*/
		ArrayList<Tuple> list = new ArrayList<Tuple>();
		Wordbank hash = new Wordbank();

		for (String s : sentences) {
			list = labelText(s);
			System.out.println(s);
			for (Tuple t : list) {
				if (t.getLabel().toUpperCase().equals("LOCATION")) {
					System.out.println("Belongs to Geography");
					break;
				}
				else if (hash.h.containsKey(t.getWord())&& list.get(0).getWord().equals("Who") ) {
					System.out.println("Belongs to " + hash.h.get(t.getWord()));
					break;
				}
				else if (hash.h.containsKey(t.getWord())) {
					System.out.println("Belongs to " + hash.h.get(t.getWord()));
					break;
				}
				else if (t.getLabel().toUpperCase().equals("PERSON"))
				{
					for (Tuple t1: list)
					{
						if (t1.getLabel().toUpperCase().equals("LOCATION"))
						{
							System.out.println("Belongs to Music");
							break;
						}
					}
					break;
				}
				/*else if (t.getLabel().toUpperCase().equals("LOCATION"))
				{
					int flag = 0;
					for (Tuple t1: list)
					{
						if(t1.getLabel().toUpperCase().equals("PERSON"))
						{
							System.out.println("Belongs to Muisc");
							flag++;
						}
						else if(t1.getLabel().toUpperCase().equals("ORGANIZATION"))
						{
							flag++;
						}
					}
					if (flag == 0)
					{
						System.out.println("Belongs to Geography");
						break;
					}
					
				}*/
			}
			Parsing.print_tree(s);
		}
		
	}
}


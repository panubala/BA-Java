import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

//import java.util.Date;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.Weight;
import org.apache.lucene.store.FSDirectory;
import org.xml.sax.SAXException;

public class Searcher {
	IndexSearcher indexSearcher;
	Query query;
	QueryParser parser;
	IndexReader reader;
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
//		UtilsQuery queryProcessor = new UtilsQuery();
//		ArrayList <String> query = queryProcessor.read("/Users/panuyabalasuntharam/Documents/BA/topics2016.xml", "note");
//		System.out.println(query.toString());
//		for (int i = 1; i < query.size(); i++) {
//			System.out.println("Query number: " + Integer.toString(i));
//	    	System.out.println(giveAllLongForm(query.get(i)));
//	    	System.out.println(giveAllAcronymsOfTheQuery(query.get(i)).toString());
//	    }
		UtilsQuery queryProcessor = new UtilsQuery();
		ArrayList <String> query = queryProcessor.read("/Users/panuyabalasuntharam/Documents/BA/topics2016.xml", "note");
		 for (int i = 1; i < query.size(); i++) {
			  giveAllLongForm(query.get(i));
	     }
		
	}

	public Searcher(String index, String field) throws IOException{
		reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		indexSearcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer() ;
		
		parser = new QueryParser(field, analyzer);

	}
	
	//This is the normal search. Don't change it.
	public TopDocs search(String searchQuery) throws ParseException, IOException{
		//We need this for weird characters
		searchQuery = QueryParser.escape(searchQuery);
		query = parser.parse(searchQuery);
		
		TopDocs topDocs = indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
		return topDocs;
	}
	
	//TODO: Take all longForms into query and give it weights
	public TopDocs search2(String searchQuery) throws ParseException, IOException{
		String longForm = giveAllLongForm(searchQuery);
		
		Query sQuery, aQuery;
		
		searchQuery = QueryParser.escape(searchQuery);
		sQuery = parser.parse(searchQuery);
		sQuery = new BoostQuery(sQuery, 1f);
		
		Builder bQuery = new BooleanQuery.Builder()
				.add(sQuery, BooleanClause.Occur.SHOULD);
				
		if (longForm != "") {
			longForm = QueryParser.escape(longForm);
			aQuery = parser.parse(longForm);
			aQuery = new BoostQuery(aQuery, 0.2f);
			bQuery.add(aQuery, BooleanClause.Occur.SHOULD);
		}
		
		
		TopDocs topDocs = indexSearcher.search(bQuery.build(), LuceneConstants.MAX_SEARCH);
		return topDocs;
	}
	
	public TopDocs search(Query query) throws IOException, ParseException{
		TopDocs topDocs = indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
		return topDocs;
	}
	
	public TopDocs search(Query query,Sort sort) throws IOException, ParseException{
		TopDocs topDocs = indexSearcher.search(query, LuceneConstants.MAX_SEARCH,sort);
		return topDocs;
	}
	
	public Document getDocument(ScoreDoc scoreDoc) throws CorruptIndexException, IOException{
		return indexSearcher.doc(scoreDoc.doc);
	}
	
	public void close() throws IOException{
		reader.close();	
	}
	
	//This gives a list with all acronyms from the query
	public static ArrayList<String> giveAllAcronymsOfTheQuery(String query) throws IOException {
		UtilsQuery utils = new UtilsQuery();
		ArrayList<String> acronymList  = utils.createAbbArray();
		ArrayList<String> acronymsOfQuery = new ArrayList<>();
		
		for (int i = 0; i < acronymList.size(); i++) {
			if (query.contains(acronymList.get(i))){
				acronymsOfQuery.add(acronymList.get(i));
			}
		}
		
		return acronymsOfQuery;
	}
	
	//This gives a String with all longForm
	public static String giveAllLongForm(String query) throws IOException {
		ArrayList<String> acronymsOfQuery = giveAllAcronymsOfTheQuery(query);
		
		String addQuery = "";
		String abb;
		String abbReplaced;
		
		for (int i = 0; i < acronymsOfQuery.size(); i++) {
			 abb = acronymsOfQuery.get(i);
			 System.out.println(abb);
			 abbReplaced = abb.replaceAll("/", "_");
			 
			 File file = new File("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abb/" + abbReplaced +".txt");
			if (file.exists()){
				BufferedReader br1= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abb/" + abbReplaced +".txt"));	
				String line = br1.readLine();
				
				int r = 0;
				
				while(line!=null && r<5){
					
					//We don't want the ranking
					if (line != ""){
//						System.out.println(line);
						String arr1 [] = line.split(" ",2);
						
						addQuery = addQuery + " " + arr1[1] ;
					}
					line= br1.readLine();
					r++;
				}
				br1.close();

			}
			
		}
		
		return addQuery;
	
	}
	
	
}


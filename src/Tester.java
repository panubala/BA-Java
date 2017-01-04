

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
//import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.Weight;

import org.xml.sax.SAXException;

public class Tester {
	
   String indexDir = "/Users/panuyabalasuntharam/Documents/BA/pmc/Index/";
   Searcher searcher;
   static Writer output;
   static Writer output2;
   
  
   public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
    
      score("description", "Test", true);
      
//      getfilepath("summary");
      
         		
   }
   
   
   public static void score(String key, String fileID, Boolean replace) throws IOException, ParserConfigurationException, SAXException, org.apache.lucene.queryparser.classic.ParseException {
	   Tester tester = new Tester();
	   UtilsQuery queryProcessor = new UtilsQuery();
	   
	   ArrayList<String> query;
	   
	   
	   if (replace == true){
		   output = new BufferedWriter(new FileWriter("/Users/panuyabalasuntharam/Documents/BA/resultFile_added_"+key +fileID+".txt"));
		   query = ReplaceQuery.replace(key);
	   }else{
		   query = queryProcessor.read("/Users/panuyabalasuntharam/Documents/BA/topics2016.xml", key);
	   }
	   
	   
	   for (int i = 1; i < query.size(); i++) {
	    	  tester.findQuery(query.get(i), i, true);
	      }
	   output.close();
   }

   public static void getfilepath(String key) throws ParserConfigurationException, SAXException, IOException, org.apache.lucene.queryparser.classic.ParseException {
	
	   Tester tester = new Tester();
	   UtilsQuery queryProcessor = new UtilsQuery();
	   
	   
	   output2 = new BufferedWriter(new FileWriter("/Users/panuyabalasuntharam/Documents/BA/filePathList_"+ key +"2.txt"));
	   
	   ArrayList<String> query = queryProcessor.read("/Users/panuyabalasuntharam/Documents/BA/topics2016.xml", key);
	   
	   for (int i = 1; i < query.size(); i++) {
	    	  tester.getScoreFilePaths(query.get(i), i);
	      }
	   output2.close();
   }
   
   private void findQuery(String searchQuery, int queryID, Boolean search) throws IOException, org.apache.lucene.queryparser.classic.ParseException{
      // Boolean search says whether search or search2
	   
	   searcher = new Searcher(indexDir, "content");
      long startTime = System.currentTimeMillis();
      
      // search2: takes 5 different longForm and gives 1/5 weight
      TopDocs result;
      if (search == true){
    	   result= searcher.search(searchQuery);
      }else {
    	  result = searcher.search2(searchQuery);
      }
      
   
      ScoreDoc[] hits = result.scoreDocs;
      long endTime = System.currentTimeMillis();

      System.out.println(hits.length + " documents found for Query: "+ Integer.toString(queryID)+"\nTime :" + (endTime - startTime) + "ms");
      
      ArrayList <Tuple> filePaths = new ArrayList<>();
      
      int r = 1; 
      for(ScoreDoc scoreDoc : result.scoreDocs) {
    	  
         Document doc = searcher.getDocument(scoreDoc);
         System.out.print("Score: "+ scoreDoc.score + " ");
         System.out.println("File: "+doc.get("filepath"));
         
         String [] temp = doc.get("docid").split(" ");
         String docid = temp[2];
       
         
         output.append(Integer.toString(queryID) + " Q0 " + docid +"  " +Integer.toString(r)+" " +scoreDoc.score + " STANDARD\n");
                  
         r++;
      }
     
      
      searcher.close();
   }
   
   private void getScoreFilePaths(String searchQuery, int queryID) throws IOException, org.apache.lucene.queryparser.classic.ParseException{
	    // 1. get the score
	   	// 2. put the filepath of it in a document
	   	searcher = new Searcher(indexDir, "content");
	      long startTime = System.currentTimeMillis();

	      TopDocs result = searcher.search(searchQuery);
	      ScoreDoc[] hits = result.scoreDocs;
	      long endTime = System.currentTimeMillis();

	      System.out.println(hits.length + " documents found for Query: "+ Integer.toString(queryID)+"\nTime :" + (endTime - startTime) + "ms");
	      
	      ArrayList <Tuple> filePaths = new ArrayList<>();
	      
	      int r = 1; 
	      for(ScoreDoc scoreDoc : result.scoreDocs) {
	    	  
	         Document doc = searcher.getDocument(scoreDoc);
	         System.out.print("Score: "+ scoreDoc.score + " ");
	         System.out.println("File: "+doc.get("filepath"));
	         filePaths.add(new Tuple(queryID, doc.get("filepath")));
	         
	         String [] temp = doc.get("docid").split(" ");
	         String docid = temp[2];
	       
	         output2.append(Integer.toString(queryID) + " " + doc.get("filepath") + "\n");
	         
	         r++;
	      }
	     
	      searcher.close();
	   }
}

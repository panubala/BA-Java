

import java.io.IOException;
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
	
   String indexDir = "/Users/neptun/Desktop/BA/pmc/Index/";
   Searcher searcher;
   public static int queryID;
   
  
   public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
      Tester tester = new Tester();
      ArrayList <String> query = tester.preprocess("/Users/neptun/Desktop/BA/topics2016.xml", "topic");
      //tester.sort(query.get(2));
//      for (int i = 1; i < query.size(); i++) {
//    	  tester.sort(query.get(i));
////    	  System.out.println(query.get(i));
//      }
      
//      ArrayList <String> query2016 = ReadFiles.readTopic("/Users/neptun/Desktop/BA/topics2016.xml", "topic");
//      System.out.println(query2016.get(2).replaceAll("[*:,.;#%$!?\\-/\\[\\]]+", ""));
//      queryID = 1;
//    
//      tester.sort(query2016.get(2).replaceAll("[*:,.;#%$!?\\-/\\[\\]]+", ""));

      
    		
   }
   
   public ArrayList <String> preprocess(String path, String key) throws ParserConfigurationException, SAXException, IOException{
	   ArrayList <String> query = ReadFiles.read(path, key);
	   
//	   for (int i = 1; i < query.size(); i++) {
//		   query.add(i, query.get(i).replaceAll("[*:,.;#%$!?\\-/\\[\\]]+", "")); 
//	   }
	   System.out.println(query.size());
	   return query;
   }
   
   
   private void sort(String searchQuery) throws IOException, org.apache.lucene.queryparser.classic.ParseException{
      searcher = new Searcher(indexDir, "content");
      long startTime = System.currentTimeMillis();


      TopDocs result = searcher.search(searchQuery);
      ScoreDoc[] hits = result.scoreDocs;
      long endTime = System.currentTimeMillis();

      System.out.println(hits.length + " documents found. Time :" + (endTime - startTime) + "ms");
      ArrayList <String> resultList = new ArrayList<String>();
      int r = 1; 
      for(ScoreDoc scoreDoc : result.scoreDocs) {
    	  
         Document doc = searcher.getDocument(scoreDoc);
         System.out.print("Score: "+ scoreDoc.score + " ");
         System.out.println("File: "+doc.get("filepath"));
         //TODO
         resultList.add(Integer.toString(queryID) + " Q0 " + doc.get("docid")+"  " +Integer.toString(r)+" " +scoreDoc.score + " STANDARD");
         Path file = Paths.get("/Users/neptun/Desktop/BA/resultFile.txt");
         Files.write(file, resultList, Charset.forName("UTF-8"));
         r++;
      }
     
      
      searcher.close();
   }
}

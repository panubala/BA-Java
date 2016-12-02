

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
	
   String indexDir = "/Users/neptun/Desktop/BA/pmc/Index/";
   Searcher searcher;
   static Writer output;
   
  
   public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
      Tester tester = new Tester();
      QueryPreprocess queryProcessor = new QueryPreprocess();
       
//      FileWriter writer = new FileWriter("/Users/neptun/Desktop/BA/resultFile.txt");
//      writer.write("");
//      writer.close();
      
      output = new BufferedWriter(new FileWriter("/Users/neptun/Desktop/BA/resultFile.txt"));
      
      ArrayList <String> query = queryProcessor.read("/Users/neptun/Desktop/BA/topics2016.xml", "topic");
      for (int i = 1; i < query.size(); i++) {
    	  tester.find(query.get(i), i);
      }
      output.close();
         		
   }
   

   
   
   private void find(String searchQuery, int queryID) throws IOException, org.apache.lucene.queryparser.classic.ParseException{
      searcher = new Searcher(indexDir, "content");
      long startTime = System.currentTimeMillis();


      TopDocs result = searcher.search(searchQuery);
      ScoreDoc[] hits = result.scoreDocs;
      long endTime = System.currentTimeMillis();

      System.out.println(hits.length + " documents found for Query: "+ Integer.toString(queryID)+"\nTime :" + (endTime - startTime) + "ms");
      ArrayList <String> resultList = new ArrayList<String>();
      int r = 1; 
      for(ScoreDoc scoreDoc : result.scoreDocs) {
    	  
         Document doc = searcher.getDocument(scoreDoc);
         System.out.print("Score: "+ scoreDoc.score + " ");
         System.out.println("File: "+doc.get("filepath"));
         
         output.append(Integer.toString(queryID) + " Q0 " + doc.get("docid")+"  " +Integer.toString(r)+" " +scoreDoc.score + " STANDARD\n");
         
         r++;
      }
     
      
      searcher.close();
   }
}

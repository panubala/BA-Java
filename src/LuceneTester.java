

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

public class LuceneTester {
	
   String indexDir = "/Users/neptun/Desktop/BA/pmc/Index/";
   static String dataDir = "/Users/neptun/Desktop/BA/pmc/pmc-00/00/13900.nxml";
   Searcher searcher;
   
   


   public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
      LuceneTester tester;
      /*
      ReadPMC readPMC = new ReadPMC();
      String query2016 = ReadPMC.readFile16All("/Users/neptun/Desktop/BA/topics2016.xml");
      String query2015 = ReadPMC.readFile16All("/Users/neptun/Desktop/BA/topics2015A.xml");
      String query2014 = ReadPMC.readFile16All("/Users/neptun/Desktop/BA/topics2014.xml");
      String queryAll = query2016 + query2015 + query2014;
      */
      
	ArrayList <String> query2016 = ReadFiles.readTopic("/Users/neptun/Desktop/BA/topics2016.xml");
      System.out.println(query2016.get(1).replaceAll("[*:,.;#%$!?\\-/\\[\\]]+", ""));
      queryID = 1;
    try {
          tester = new LuceneTester();
          tester.sortUsingRelevance(query2016.get(1).replaceAll("[*:,.;#%$!?\\-/\\[\\]]+", ""));
   	} catch (IOException e) {
          e.printStackTrace();
    }
      
    		
   }
   public static int queryID;
   
   private void sortUsingRelevance(String searchQuery) throws IOException, org.apache.lucene.queryparser.classic.ParseException{
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

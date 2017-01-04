

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
//   static Writer output2;
   
  
   public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
      Tester tester = new Tester();
      UtilsQuery queryProcessor = new UtilsQuery();
      
      output = new BufferedWriter(new FileWriter("/Users/panuyabalasuntharam/Documents/BA/resultFile_added_noteTest.txt"));
//      output2 = new BufferedWriter(new FileWriter("/Users/panuyabalasuntharam/Documents/BA/filePathList_description2.txt"));
     
      
//      ArrayList <String> query = queryProcessor.read("/Users/panuyabalasuntharam/Documents/BA/topics2016.xml", "description");
      
      
      ArrayList<String> query = ReplaceQuery.replace("note");
      
      for (int i = 1; i < query.size(); i++) {
    	  tester.findQuery(query.get(i), i);
      }
      output.close();
//      output2.close();
         		
   }
   
   
   

   
   private void findQuery(String searchQuery, int queryID) throws IOException, org.apache.lucene.queryparser.classic.ParseException{
      searcher = new Searcher(indexDir, "content");
      long startTime = System.currentTimeMillis();

      //TopDocs result = searcher.search2(searchQuery);
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
         //filePaths.add(new Tuple(queryID, doc.get("filepath")));
         
         String [] temp = doc.get("docid").split(" ");
         String docid = temp[2];
       
         
         output.append(Integer.toString(queryID) + " Q0 " + docid +"  " +Integer.toString(r)+" " +scoreDoc.score + " STANDARD\n");
         
//         output2.append(Integer.toString(queryID) + " " + doc.get("filepath") + "\n");
         
         r++;
      }
     
      
      searcher.close();
   }
}

import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
import java.nio.file.Paths;
//import java.util.Date;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.Weight;
import org.apache.lucene.store.FSDirectory;

public class Searcher {
	IndexSearcher indexSearcher;
	Query query;
	QueryParser parser;
	IndexReader reader;

	public Searcher(String index, String field) throws IOException{
		reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		indexSearcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer() ;
		
		parser = new QueryParser(field, analyzer);

	}
	
	public TopDocs search(String searchQuery) throws ParseException, IOException{
		query = parser.parse(searchQuery);
		TopDocs topDocs = indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
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
	
	
}


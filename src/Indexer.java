import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.xml.sax.SAXException;
//import org.apache.lucene.util.Version;
import org.apache.lucene.index.IndexWriterConfig;

public class Indexer {
	
	private IndexWriter writer;
	Directory dir;
	static TextFileFilter fileFilter = new TextFileFilter(); 
	
	 public static void main(String args[]) throws IOException, ParserConfigurationException, SAXException {
		 ArrayList<File> filepaths = createArray();
		
		String indexDirectory = "/Users/neptun/Desktop/BA/pmc/Index/";
	        
		long start = System.currentTimeMillis();
		Indexer indexer = new Indexer(indexDirectory);
	        
		int numIndexed = indexer.createIndex(filepaths, fileFilter);
	    	
	    indexer.close();
	    	 
	    long end = System.currentTimeMillis();
	    System.out.println("Indexing " + numIndexed + " files took " + (end - start) + " milliseconds");
	        
	    }
	public static ArrayList<File> createArray(){
		
		FileSystem fileSys = new FileSystem();
		ArrayList<File> filepaths = null;
		
		//pmc-00
		for (int i = 0; i < 10; i++) {
			filepaths = fileSys.addDir(new File("/Users/neptun/Desktop/BA/pmc/pmc-00/0"+i), fileFilter);
		}
		for (int i = 10; i < 53; i++) {
			filepaths = fileSys.addDir(new File("/Users/neptun/Desktop/BA/pmc/pmc-00/"+i), fileFilter);
		}
		

		
		//pmc-01
		for (int i = 0; i < 10; i++) {
			filepaths = fileSys.addDir(new File("/Users/neptun/Desktop/BA/pmc/pmc-01/0"+i), fileFilter);
		}
		for (int i = 10; i < 49; i++) {
			filepaths = fileSys.addDir(new File("/Users/neptun/Desktop/BA/pmc/pmc-01/"+i), fileFilter);
		}
		
		
		//pmc-02
		for (int i = 0; i < 10; i++) {
			filepaths = fileSys.addDir(new File("/Users/neptun/Desktop/BA/pmc/pmc-02/0"+i), fileFilter);
		}
		for (int i = 10; i < 78; i++) {
			filepaths = fileSys.addDir(new File("/Users/neptun/Desktop/BA/pmc/pmc-02/"+i), fileFilter);
		}
		
		
		//pmc-03
		for (int i = 0; i < 10; i++) {
			filepaths = fileSys.addDir(new File("/Users/neptun/Desktop/BA/pmc/pmc-03/0"+i), fileFilter);
		}
		for (int i = 10; i < 73; i++) {
			filepaths = fileSys.addDir(new File("/Users/neptun/Desktop/BA/pmc/pmc-03/"+i), fileFilter);
		}
		
		return filepaths;
	} 
	 
	public Indexer(String indexDir) throws IOException{
		
		StandardAnalyzer analyzer = new StandardAnalyzer();
		
		dir = FSDirectory.open(Paths.get(indexDir));

		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		writer = new IndexWriter(dir, config);
	}
	
	public void close() throws IOException, CorruptIndexException{
		writer.close();
	}
	
	private Document getDocument(File file) throws IOException, ParserConfigurationException, SAXException, FileSystemException{
		System.out.println("Indexing File: " + file.toString());
		UtilsPMC parser = new UtilsPMC();
		
		String content =  parser.getContent(file.getPath());		
		String title=  parser.getTitle(file.getPath());
		String docID=  parser.getdocID(file.getPath());
		Document document = new Document();
		
		document.add(new TextField("content", content,Field.Store.NO));
		document.add(new TextField("alltitle", title, Field.Store.YES));
		document.add(new TextField("docid", docID, Field.Store.YES));
		
		document.add(new TextField("filename",file.getName(),Field.Store.YES));
		
		
		document.add(new StringField("filepath", file.getCanonicalPath(), Field.Store.YES));
	
		
		return document;
	}
	
	private void indexFile(File file) throws IOException, ParserConfigurationException, SAXException{
		Document document = getDocument(file);
		
		writer.addDocument(document);	
	}

	public int createIndex(ArrayList<File> dataDirPath, FileFilter filter) throws IOException, ParserConfigurationException, SAXException{
		
		for(File file : dataDirPath){
			
			if(!file.isDirectory() 
					&&!file.isHidden()
					&& file.exists()
					&& file.canRead()){
				
				indexFile(file);
			}
		}
		return writer.numDocs();
	}
	
	
}

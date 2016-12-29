import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UtilsQuery {
	

		
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		UtilsQuery query = new UtilsQuery();
		System.out.println(query.read("/Users/neptun/Desktop/BA/topics2016.xml", "note").get(1));
	}
	
	public UtilsQuery(){}
	
	
	//key = topic, note, summary, description
	public static ArrayList<String> read(String input, String key) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		File file = new File(input);

		Document doc = dBuilder.parse(file);
		
		ArrayList <String> output = new ArrayList<String>();
		NodeList nList = doc.getElementsByTagName(key);
		
		// Keep the first empty. Otherwise would have a problem with indexing.
		output.add("**********");
		
		for(int temp = 0; temp < nList.getLength(); temp++){
			Node nNode = nList.item(temp);
			 output.add(nNode.getTextContent());
		}
		return output;	
	}
	
	//We don't need this anymore
	public String readContent(String input) throws ParserConfigurationException, SAXException, IOException{
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		File file = new File(input);

		Document doc = dBuilder.parse(file);
		
		NodeList nListNote = doc.getElementsByTagName("note");
		NodeList nListDescr = doc.getElementsByTagName("description");
		NodeList nListSum = doc.getElementsByTagName("summary");
		
		String output = "";
		
		for(int temp = 0; temp < nListDescr.getLength(); temp++){
			Node nNodeNote = nListNote.item(temp);
			Node nNodeDescr = nListDescr.item(temp);
			Node nNodeSum = nListSum.item(temp);
			
			output = output + nNodeNote.getTextContent();
			output = output + nNodeDescr.getTextContent();
			output = output + nNodeSum.getTextContent();	
		}
		

		return output;
	}
	

	//Creates an array of Medical Acronym
	public static ArrayList<String> createAbbArray() throws IOException{
		BufferedReader br= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/acronymMedical.txt"));
		ArrayList<String> acronyms = new ArrayList<>();
		String line = br.readLine();
		while(line!=null){
			acronyms.add(line);
			line = br.readLine();
		}
		
		br.close();
		return acronyms;
	}

	
}

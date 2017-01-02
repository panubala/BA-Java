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
		query.giveAllAcronymList("/Users/panuyabalasuntharam/Documents/BA/topics2016.xml", "note");
//		query.giveAllLongForm("/Users/panuyabalasuntharam/Documents/BA/topics2016.xml", "description");
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
	
	public void giveAllAcronymList(String path, String key) throws ParserConfigurationException, SAXException, IOException {
		UtilsQuery query = new UtilsQuery();
		ArrayList <String> allQuery = query.read(path, key);
		
		int size = 0; 
		
		for (int i = 0; i < allQuery.size(); i++) {
			ArrayList <String> acronymList = giveAllAcronymsOfTheQuery(allQuery.get(i));
			size = size + acronymList.size();
			System.out.println("Query "+Integer.toString(i)+": " + acronymList.toString());
		}
		
		System.out.println("\n" + Integer.toString(size));
	}
	
	
	public static String giveAllLongFormOfTheQuery(String query) throws IOException {
		ArrayList<String> acronymsOfQuery = giveAllAcronymsOfTheQuery(query);
		
		String addQuery = "";
		String abb;
		String abbReplaced;
		
		for (int i = 0; i < acronymsOfQuery.size(); i++) {
			 abb = acronymsOfQuery.get(i);
			 abbReplaced = abb.replaceAll("/", "_");
			 
			 File file = new File("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abb/" + abbReplaced +".txt");
			if (file.exists()){
				BufferedReader br1= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abb/" + abbReplaced +".txt"));	
				String line = br1.readLine();
				
				int r = 0;
				
				while(line!=null && r<5 ){
					
					//We don't want the ranking
					String arr1 [] = line.split(" ",2);
					addQuery = addQuery + " " + arr1[1] ;
					line= br1.readLine();
					r++;
				}
				br1.close();

			}
			
		}
		
		return addQuery;
	
	}

	public void giveAllLongForm(String path, String key) throws ParserConfigurationException, SAXException, IOException{
		UtilsQuery query = new UtilsQuery();
		ArrayList <String> allQuery = query.read(path, key);
		
		int size = 0; 
		
		String longForm = "";
		
		for (int i = 0; i < allQuery.size(); i++) {
			longForm = longForm + giveAllLongFormOfTheQuery(allQuery.get(i));
			
		}
		System.out.println(longForm);
		
	}

}

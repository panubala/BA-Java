import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

import com.sun.org.apache.xerces.internal.parsers.*;

public class ReadFiles {
	

		
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		System.out.println(read("/Users/neptun/Desktop/BA/topics2016.xml", "note").get(1));
	}
	
	public ReadFiles(){}
	
	public static Document readFile(String input) throws ParserConfigurationException, SAXException, IOException{

		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		File file = new File(input);

		Document doc = dBuilder.parse(file);
		return doc;
	      
	}
	
	
	
//	public String readFile16All(String input) throws ParserConfigurationException, SAXException, IOException{
//		
//		Document doc = readFile(input);
//		
//		NodeList nListNote = doc.getElementsByTagName("note");
//		NodeList nListDescr = doc.getElementsByTagName("description");
//		NodeList nListSum = doc.getElementsByTagName("summary");
//		
//		String output = "";
//		
//		for(int temp = 0; temp < nListDescr.getLength(); temp++){
//			Node nNodeNote = nListNote.item(temp);
//			Node nNodeDescr = nListDescr.item(temp);
//			Node nNodeSum = nListSum.item(temp);
//			
//			output = output + nNodeNote.getTextContent();
//			output = output + nNodeDescr.getTextContent();
//			output = output + nNodeSum.getTextContent();	
//		}
//		
//
//		return output;
//	}
	
	//Array starts at 1
	//key = topic, note, summary, description
	public static ArrayList<String> read(String input, String key) throws ParserConfigurationException, SAXException, IOException{
		Document doc = readFile(input);
		ArrayList <String> output = new ArrayList();
		String text = "";
		NodeList nList = doc.getElementsByTagName(key);
		output.add("There is no content.");
		for(int temp = 0; temp < nList.getLength(); temp++){
			Node nNode = nList.item(temp);
			 output.add(nNode.getTextContent().replaceAll("[*:,.;#%$!?\\-/\\[\\]]+", ""));
		}
		return output;	
	}
	

//	public static String readFileAll(String input) throws ParserConfigurationException, SAXException, IOException{
//		
//		Document doc = readFile(input);
//		
//		NodeList nListDescr = doc.getElementsByTagName("description");
//		NodeList nListSum = doc.getElementsByTagName("summary");
//		
//		String output = "";
//		
//		for(int temp = 0; temp < nListDescr.getLength(); temp++){
//
//			Node nNodeDescr = nListDescr.item(temp);
//			Node nNodeSum = nListSum.item(temp);
//			
//			output = output + nNodeDescr.getTextContent();
//			output = output + nNodeSum.getTextContent();	
//		}
//		
//		return output;
//	}
	
	
//	
//	//This gives just a part of the text. e.g all descriptions
//	public static String readFilePart(String input, String key) throws ParserConfigurationException, SAXException, IOException{
//
//		Document doc = readFile(input);
//	
//		NodeList nList = doc.getElementsByTagName(key);
//		String output = "";
//	
//		for(int temp = 0; temp < nList.getLength(); temp++){
//			Node nNode = nList.item(temp);
//			output = output + nNode.getTextContent();	
//		}
//		
//		output = output.replaceAll("/", "");
//		
//		return output;
//	}


	
}

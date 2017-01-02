import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class SearchInScore {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		search();
	}
	
	public static void search() throws ParserConfigurationException, SAXException, IOException {
		UtilsQuery utils = new UtilsQuery();
		ArrayList <Tuple> acronyms = utils.giveAllAcronymList("/Users/panuyabalasuntharam/Documents/BA/topics2016.xml", "note");
		
		
		
		for (int i = 0; i < acronyms.size(); i++) {
			System.out.println(acronyms.get(i).queryNo + ": " + acronyms.get(i).list.toString());
		}
		
		
	}
	
	public void giveFilePaths() throws IOException {
		BufferedReader br1= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/filePathList_note.txt"));
		String line = br1.readLine();
		line = br1.readLine();
		ArrayList <String> filePaths;
		
		String arr [] = line.split(" ", 2);
		
		while(arr[0] == "1"){
			
		}
	}

}

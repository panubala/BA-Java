import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ReplaceQuery {
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		
		Writer output = new BufferedWriter(new FileWriter("/Users/neptun/Desktop/BA/replaced_Text.txt"));
		output.append(replace().toString());
		output.close();
	}
	
	public static ArrayList <String> replace() throws IOException, ParserConfigurationException, SAXException {
		ArrayList<String> acronyms = read();
		ArrayList<Tuple> list = new ArrayList<>();
		
		String abb;
		String abb1;

		for (int i = 0; i < acronyms.size(); i++) {
			abb = acronyms.get(i);
			
			abb1 = abb.replaceAll("/", "_");
			
			File file = new File("/Users/neptun/Desktop/BA/abbreviations/abb/" + abb1 +".txt");
			
			if (file.exists()){
				BufferedReader br1= new BufferedReader(new FileReader("/Users/neptun/Desktop/BA/abbreviations/abb/" + abb1 +".txt"));
				Scanner sc = new Scanner("/Users/neptun/Desktop/BA/abbreviations/abb/" + abb1 +".txt");
				
				String line = br1.readLine();
			
				int rank = 0;
				
				String longForm = "";
				
				while(line!=null ){
				
					String arr1 [] = line.split(" ", 2);
					int tempRank= Integer.parseInt(arr1 [0]);
					if (tempRank > rank){
						rank = tempRank;
						longForm = arr1 [1];
					}
					line= br1.readLine();
				}
				
				if (rank!=0){
					list.add(new Tuple(abb, longForm, i));
				}
				br1.close();
			}
			
			
		}
		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i).abb + " " + list.get(i).longForm);
		}
		
		UtilsQuery processor = new UtilsQuery();
		String query = processor.readContent("/Users/neptun/Desktop/BA/topics2016.xml");
		
		ArrayList <String> query1 = processor.read("/Users/neptun/Desktop/BA/topics2016.xml", "note");
		//System.out.println(query1.toString());
		
		for (int j = 0; j < query1.size(); j++) {
			
		
			for (int i = 0; i < list.size(); i++) {
			
			String replaced = query1.get(j).replaceAll("[\\s\\[(]{1}"+list.get(i).abb + "[\\s\\]),;]{1}", " "+list.get(i).abb+list.get(i).longForm + " " );
			query1.set(j, replaced);
			
			}
			
		}
		
//		System.out.println(query1.get(1));
		
		return query1;
	}
	
	public static ArrayList<String> read() throws IOException {
		BufferedReader br= new BufferedReader(new FileReader("/Users/neptun/Desktop/BA/acronymMedical.txt"));
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

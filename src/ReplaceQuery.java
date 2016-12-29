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
		
		replace("note");
//		Writer output = new BufferedWriter(new FileWriter("/Users/panuyabalasuntharam/Documents/BA/replaced_Text_note2.txt"));
//		output.append(replace("note").toString());
//		output.close();
	
	}
	
	public static ArrayList <String> replace(String key) throws IOException, ParserConfigurationException, SAXException {
		
		ArrayList <Tuple> acronymList=findLongForm();
		
		ArrayList <String> query = UtilsQuery.read("/Users/panuyabalasuntharam/Documents/BA/topics2016.xml", key);
		
		//Query-loop
		for (int j = 1; j < query.size(); j++) {
			
			//Acronym-loop
			for (int i = 0; i < acronymList.size(); i++) {
			
			String replaced = query.get(j).replaceAll("[\\s\\[(]{1}"+acronymList.get(i).abb + "[\\s\\]),;]{1}", 
														" "+ acronymList.get(i).longForm + " " 
														+acronymList.get(i).abb + " " );
			query.set(j, replaced);
			}
			
		}

		return query;
	}
	
	//for the first medical acronyms
	public static ArrayList<Tuple> findLongForm() throws IOException, ParserConfigurationException, SAXException {
		
		ArrayList<String> acronyms = UtilsQuery.createAbbArray();
		ArrayList<Tuple> list = new ArrayList<>();
		
		String abb;
		String abbReplaced;

		for (int i = 0; i < acronyms.size(); i++) {
			abb = acronyms.get(i);
			abbReplaced = abb.replaceAll("/", "_");
			
			File file = new File("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abb/" + abbReplaced +".txt");
			
			if (file.exists()){
				BufferedReader br1= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abb/" + abbReplaced +".txt"));
				
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
					list.add(new Tuple(abb, longForm, i, rank));
				}
				br1.close();
			}
		}
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).abb + " " + list.get(i).longForm + " " + list.get(i).rank);
			}
			
			return list;
			
			
		}
		
	//for second medical acronyms
	public static ArrayList<Tuple> findLongForm2() throws IOException, ParserConfigurationException, SAXException {
		ArrayList<String> acronyms = UtilsQuery.createAbbArray();
		ArrayList<Tuple> list = new ArrayList<>();
			
		String abb;
		String abbReplaced;

		for (int i = 0; i < acronyms.size(); i++) {
			abb = acronyms.get(i);
			abbReplaced = abb.replaceAll("/", "_");
				
			File file = new File("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abb2/" + abbReplaced +".txt");
				
			if (file.exists()){
				BufferedReader br1= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abb2/" + abbReplaced +".txt"));
					
				String longForm= br1.readLine();
				
				if (longForm == null){
						longForm = "";
				}
					list.add(new Tuple(abb, longForm, i));
					br1.close();
			}else{
				list.add(new Tuple(abb, "", i));
			}
				
				
		}
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).abb + " " + list.get(i).longForm );
		}
		
		return list;
	}
	
	
}

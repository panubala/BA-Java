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
		
		UtilsQuery processor = new UtilsQuery();
		
		
		ArrayList <String> query = processor.read("/Users/panuyabalasuntharam/Documents/BA/topics2016.xml", key);
		
		for (int j = 1; j < query.size(); j++) {
			
		
			for (int i = 0; i < acronymList.size(); i++) {
			
			String replaced = query.get(j).replaceAll("[\\s\\[(]{1}"+acronymList.get(i).abb + "[\\s\\]),;]{1}", 
					" "+ acronymList.get(i).longForm + " " );
							//+acronymList.get(i).abb + " " );
			query.set(j, replaced);
			
			}
			
		}
		//System.out.println(query);
		
		return query;
	}
	
	public static ArrayList<Tuple> findLongForm() throws IOException, ParserConfigurationException, SAXException {
		ArrayList<String> acronyms = createMedicalAcronymArray();
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
		
		public static ArrayList<Tuple> findLongForm2() throws IOException, ParserConfigurationException, SAXException {
			ArrayList<String> acronyms = createMedicalAcronymArray();
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
	
		public static ArrayList<Tuple> findLongForm3() throws IOException, ParserConfigurationException, SAXException {
			ArrayList<String> acronyms = createMedicalAcronymArray();
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
					
					br1.close();
				File file2 = new File("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abb2/" + abbReplaced +".txt");
						
						if (file2.exists()){
							BufferedReader br2= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abb2/" + abbReplaced +".txt"));
							
							String longForm2= br2.readLine();
							if (longForm2 == null){
								longForm2 = "";
							}
							list.add(new Tuple(abb, longForm,longForm2, i));
							br2.close();
						}else{
							
							list.add(new Tuple(abb,longForm, "", i));
						}
						
						
					}
				

					
				}
			
				for (int i = 0; i < list.size(); i++) {
					System.out.println(list.get(i).abb + " " + list.get(i).longForm + " " + list.get(i).longForm2);
				}
				
				return list;
				
				
			}		
	
	
	
	
	public static ArrayList<String> createMedicalAcronymArray() throws IOException {
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

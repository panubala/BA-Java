import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ReplaceQuery {
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		
		replace("description", 1);
//		getResult("note", "Test");
		
//		deleteRank();
	
//		findLongForm5(2);
//		findLongFormComparison();
		
//		compareResult("note", "2", "3");
		
//		Writer output = new BufferedWriter(new FileWriter("/Users/panuyabalasuntharam/Documents/BA/replaced_Text_note2.txt"));
//		output.append(replace("note").toString());
//		output.close();
	
	}
	
	public static ArrayList <String> replace(String key, int num) throws IOException, ParserConfigurationException, SAXException {
		
		ArrayList <Tuple> acronymList=findLongForm2();
		
		ArrayList <String> query = UtilsQuery.read("/Users/panuyabalasuntharam/Documents/BA/topics2016.xml", key);
		
//		ArrayList <Boolean> resList = compareResult(key, "2", "3");
		
		//Query-loop
		for (int j = 1; j < query.size(); j++) {
			
			
			String replaced = query.get(j);
		
			//Acronym-loop
			for (int i = 0; i < acronymList.size(); i++) {

				

//				if(resList.get(j)){
					replaced = replaced.replaceAll("[\\s\\[(]{1}"+acronymList.get(i).abb + "[\\s\\]).,;]{1}", 
						" "+ acronymList.get(i).longForm + " " 
							+acronymList.get(i).abb + " " );
//				}else{
//					replaced = replaced.replaceAll("[\\s\\[(]{1}"+acronymList.get(i).abb + "[\\s\\]),;]{1}",
//						" "+ acronymList.get(i).longForm2 + " " 
//						+acronymList.get(i).abb + " " );
//					
//					Writer output = new BufferedWriter(
//							new FileWriter("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abb/" 
//							+ acronymList.get(i).abb.replaceAll("/", "_") + ".txt", true));
//					
//					if (acronymList.get(i).longForm2 != null){
//						output.append("99"+" " + acronymList.get(i).longForm2 + "\n");
//					}
//					
//					output.close();
//				}
				
			
			
				}
			query.set(j, replaced);
			
		}
		for (int i = 0; i < query.size(); i++) {
			System.out.println(query.get(i));
		}
//		}
		return query;
	}
	
	public static ArrayList <String> replace00(String key, int num, int num2) throws IOException, ParserConfigurationException, SAXException {
		
		ArrayList <Tuple> acronymList=findLongForm5(num);
		
		
		ArrayList <String> query = UtilsQuery.read("/Users/panuyabalasuntharam/Documents/BA/topics2016.xml", key);
		int j = num2;

		String replaced = query.get(j);
		
		//Acronym-loop
		for (int i = 0; i < acronymList.size(); i++) {

					replaced = replaced.replaceAll("[\\s\\[(]{1}"+acronymList.get(i).abb + "[\\s\\]),;]{1}", 
						" "+ acronymList.get(i).longForm + " " 
							+acronymList.get(i).abb + " " );

							
		}
		query.set(j, replaced);

		return query;
	}
	
	public static ArrayList <String> replace4(String key, String specialAbb) throws IOException, ParserConfigurationException, SAXException {
		
		ArrayList <Tuple> acronymList=findLongForm4(specialAbb);
		ArrayList <String> query = UtilsQuery.read("/Users/panuyabalasuntharam/Documents/BA/topics2016.xml", key);
		ArrayList <String> longForms = new ArrayList<>();
		
		String abbReplaced = specialAbb.replaceAll("/", "_");
		File file = new File("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abb/" + abbReplaced +".txt");
		
		if (file.exists()){
			BufferedReader br1= new BufferedReader(new FileReader(file.getAbsolutePath()));
			
			String line = br1.readLine();
			
			while(line!=null ){
				String arr1 [] = line.split(" ", 2);

				longForms.add(arr1[1]);

				line= br1.readLine();
			}
			
			br1.close();
		}
		
		//Query-loop
		for (int j = 1; j < query.size(); j++) {
			String replaced = query.get(j);
			//Acronym-loop
			for (int i = 0; i < acronymList.size(); i++) {
					replaced = replaced.replaceAll("[\\s\\[(]{1}"+acronymList.get(i).abb + "[\\s\\]),;]{1}", 
						" "+ acronymList.get(i).longForm + " " 
							+acronymList.get(i).abb + " " );
				}
			query.set(j, replaced);
		}
		
		ArrayList <String> replacedQueries = new ArrayList<>();
		System.out.println("Size of longForm: " + Integer.toString(longForms.size()));
		for (int i = 0; i < longForms.size(); i++) {
			for (int j = 0; j < query.size(); j++) {
				String replaced = query.get(j).replaceAll("[\\s\\[(]{1}"+ specialAbb + "[\\s\\]),;]{1}", 
						" "+ longForms.get(i) + " " 
								+specialAbb + " " );
				
				replacedQueries.add(replaced);
			}
		}
		
		return replacedQueries;
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
				
				System.out.println(abbReplaced);
				String line = br1.readLine();
			
				int rank = 0;
				String longForm = "";
				
				while(line!=null ){
//					System.out.println(line);
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
	
	public static ArrayList<Tuple> findLongForm5(int num) throws IOException, ParserConfigurationException, SAXException {
		
		ArrayList<String> acronyms = UtilsQuery.createAbbArray();
		ArrayList<Tuple> list = new ArrayList<>();
		
		String abb;
		String abbReplaced;
		
		int r=0;

		for (int i = 0; i < acronyms.size(); i++) {
			abb = acronyms.get(i);
			abbReplaced = abb.replaceAll("/", "_");
			
			File file = new File("/Users/panuyabalasuntharam/Documents/BA/abbreviations/myabb/" + abbReplaced +".txt");
			
			if (file.exists()){
				BufferedReader br1= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/abbreviations/myabb/" + abbReplaced +".txt"));
				
				String line = br1.readLine();
			
				ArrayList <String> longFormList = new ArrayList<>();
				
				while(line!=null ){
					r++;
					String arr1 [] = line.split(" ", 2);
					longFormList.add(arr1[1]);
					

					line= br1.readLine();
				}
				
				if (!longFormList.isEmpty()){
					if(longFormList.size() >=  num ){
						list.add(new Tuple(abb, longFormList.get(num-1), i));
					}
				}
				br1.close();
			}
		}
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).abb + " " + list.get(i).longForm );
			}
			
	
			System.out.println(Integer.toString(r) + " " + Integer.toString(r/acronyms.size()));
			
			return list;
			
			
		}

	public static ArrayList<Tuple> findLongForm4(String specialAbb) throws IOException, ParserConfigurationException, SAXException {
		
		ArrayList<String> acronyms = UtilsQuery.createAbbArray();

		acronyms.remove(specialAbb);
	
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
//					System.out.println(line);
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
	
	public static void deleteRank() throws IOException, ParserConfigurationException, SAXException {
		
		ArrayList<String> acronyms = UtilsQuery.createAbbArray();
		ArrayList<Tuple> list = new ArrayList<>();
		
		String abb;
		String abbReplaced;

		for (int i = 0; i < acronyms.size(); i++) {
			abb = acronyms.get(i);
			abbReplaced = abb.replaceAll("/", "_");
			
			File file = new File("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abb/" + abbReplaced +".txt");
			
			if (file.exists()){
				BufferedReader br1= new BufferedReader(new FileReader(file.getAbsolutePath()));
				
//				System.out.println(abbReplaced);
				String line = br1.readLine();
			
				int rank = 0;
				String longForm = "";
				
				while(line!=null ){
//					System.out.println(line);
					String arr1 [] = line.split(" ", 2);
					
					int tempRank= Integer.parseInt(arr1 [0]);
					if (tempRank > rank){
						rank = tempRank;
						longForm = arr1 [1];
					}
					line= br1.readLine();
				}
				
				br1.close();
				File file2 = new File("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abbNORank/"+ abbReplaced +".txt");
				Writer output = new BufferedWriter(new FileWriter(file2.getAbsolutePath()));
				output.append(longForm);
				output.close();
				
			}
		}
			
			
			
			
			
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
				
//			File file = new File("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abbPMC/" + abbReplaced +".txt");
			File file = new File("/Users/panuyabalasuntharam/Documents/BA/abbTensorFlow2/" + abbReplaced +".txt");
				
			if (file.exists()){
//				BufferedReader br1= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abbPMC/" + abbReplaced +".txt"));
				BufferedReader br1= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/abbTensorFlow2/" + abbReplaced +".txt"));
				
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
	
	public static ArrayList<Tuple> findLongFormComparison() throws IOException, ParserConfigurationException, SAXException {
		
		ArrayList<String> acronyms = UtilsQuery.createAbbArray();
		ArrayList<Tuple> list = new ArrayList<>();
		
		String abb;
		String abbReplaced;

		for (int i = 0; i < acronyms.size(); i++) {
			abb = acronyms.get(i);
			System.out.println(abb);
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
				
				
				
				File file2 = new File("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abb2/" + abbReplaced +".txt");
				
				if (file2.exists()){
					BufferedReader br2= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/abbreviations/abb2/" + abbReplaced +".txt"));
						
					String longForm2= br2.readLine();
					
					
						list.add(new Tuple(abb, longForm, longForm2, i));
						
				}else{
					list.add(new Tuple(abb, longForm, "", i));
				}
				
				br1.close();
			}
		}
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).abb + " " + list.get(i).longForm + " "+list.get(i).longForm2);
			}
			
			return list;
			
			
		}
	
	public static ArrayList<String> getResult(String key, String key2) throws IOException {
		BufferedReader br1= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/result_added_"+ key +key2+".txt"));
		ArrayList<String> list2 = new ArrayList();
		for (int i = 0; i < 32; i++) {
			list2.add("0");
		}
		String line = br1.readLine();
		
		
		while(line!=null ){
	
			if(line.contains("P_10 ")){
				line = line.replace(" ", "")
						.replaceAll("P_10", "")
						.replaceAll("all", "31");
//						.replaceAll("	", "");
				
				String arr [] = line.split("	", 3);
				
				list2.set(Integer.parseInt(arr[1]), arr[2]);
			}
			line= br1.readLine();
		}
		
		//list: last element is 'all'
		//index: 0 has no meaning
		System.out.println(list2.toString());
		return list2;
	}
	
	public static ArrayList <Boolean> compareResult(String key1, String key2, String key3 ) throws IOException {
		ArrayList <String>list2 = getResult(key1, key2);
		ArrayList <String>list3 = getResult(key1, key3);
		ArrayList <Boolean>listComp = new ArrayList();
		
		for (int i = 0; i < list2.size(); i++) {
			if (Double.parseDouble(list2.get(i)) < Double.parseDouble(list3.get(i))){
				listComp.add(i, false);
			}else{
				listComp.add(i, true);
			}
		}
		
		System.out.println(listComp.toString());
		return listComp;
	}
}

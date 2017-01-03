import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class SearchInScore {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		search();
	}
	
	public static void search() throws ParserConfigurationException, SAXException, IOException {
		UtilsQuery utilsQ = new UtilsQuery();
		
		//acronyms = queryNo + list of acronyms from the query
		ArrayList <Tuple> acronyms = utilsQ.giveAllAcronymList("/Users/panuyabalasuntharam/Documents/BA/topics2016.xml", "note");
		
		
		
//		for (int i = 0; i < acronyms.size(); i++) {
//			System.out.println(acronyms.get(i).queryNo + ": " + acronyms.get(i).list.toString());
//		}
		
		ArrayList <Tuple> longFormList = new ArrayList<>();
		ArrayList <String> list = new ArrayList <>();
				
		BufferedReader br= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/acronymMedical.txt"));
		String line = br.readLine();
		while(line!=null){
			longFormList.add(new Tuple(line,list));
			line = br.readLine();
		}
		br.close();
		
		ArrayList<String> acronymList = acronyms.get(1).list;
		
		ArrayList<String> filePaths = giveFilePaths();
		ArrayList<String> content = new ArrayList<>();
		
		UtilsPMC utilsP = new UtilsPMC();
		
		
		for (int i = 0; i < filePaths.size(); i++) {
	
			content.add(utilsP.getContent(filePaths.get(i)));
		}
		
	
		
		String acr;
		String tag;
		
		String longForm;
		ArrayList <Tuple> list1 = new ArrayList <>();
		
		for (int i = 0; i < content.size(); i++) {
			for (int j = 0; j < acronymList.size(); j++) {
				if(content.get(i).contains(acronymList.get(j))){
//					System.out.println(acronymList.get(j));
					
					acr = acronymList.get(j);
					ArrayList <String> listTemp = new ArrayList <>();
					
					
					
					tag = "\\s[A-Za-z]*";
					
					for (int j2 = 1; j2 < acr.length(); j2++) {
						tag = tag + "\\s[A-Za-z]*";
					}
					
					Pattern regex = Pattern.compile(tag + "[\\[(\\s]+"+acr +"[\\])\\s.,;:]+");
					Matcher matcher = regex.matcher(content.get(i));
					while (matcher.find()) {
				       longForm = matcher.group()
				    		   .replaceAll("\\(", "")
				    		   .replaceAll("\\)", "")
				    		   .replaceAll(acr, "")
				    		   .replaceFirst("\\s", "");
				       longForm = longForm.trim();
				       
				       //check if the first character is the same
				       if (acr.substring(0, 1).toLowerCase().equals(longForm.substring(0, 1).toLowerCase())){
				       		listTemp.add(longForm);
				       }
					}
					
					Pattern regex2 = Pattern.compile(tag + "\\s"+acr +"\\s[\\[(]+.*[\\])]");
					Matcher matcher2 = regex2.matcher(content.get(i));
					while (matcher2.find()) {
				       longForm = matcher2.group();
				       listTemp.add(longForm);
					}
					
					
					if (! listTemp.isEmpty()){
						list1.add(new Tuple(acr, listTemp));
					}
					
				}
			}
		}
		
		
		for (int i = 0; i < list1.size(); i++) {
			Boolean bool = false;
			int j= 0;
			while(bool == false){
				
				if(list1.get(i).acr.equals(longFormList.get(j).acr)){
					
					ArrayList <String> listTemp = new ArrayList<>();

					for (int k = 0; k < longFormList.get(j).list.size(); k++) {
						listTemp.add(longFormList.get(j).list.get(k));
					}
					for (int k = 0; k < list1.get(i).list.size(); k++) {
						listTemp.add(list1.get(i).list.get(k));
					}
					
					longFormList.set(j, new Tuple (longFormList.get(j).acr,listTemp));

					bool = true;
				}
				j++;
			}
		}
		
		
		for (int i = 0; i < longFormList.size(); i++) {
			System.out.println(longFormList.get(i).acr + " " + longFormList.get(i).list.toString());
		}

		
	}
	
	public static ArrayList <String> giveFilePaths() throws IOException {
		BufferedReader br1= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/filePathList_note.txt"));
		String line = br1.readLine();
		
		ArrayList <String> filePaths = new ArrayList<>() ;
		
		String arr [] = line.split(" ", 2);
		
		//Ouery 1
		for (int i = 0; i < 9; i++) {
			filePaths.add(arr[1].replaceAll("/Users/neptun/Desktop/", "/Users/panuyabalasuntharam/Documents/"));
			line = br1.readLine();
			arr = line.split(" ", 2);
		}
		
		return filePaths;
	}

}

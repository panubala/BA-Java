import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class SearchInScore {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		search();
//		ArrayList<Tuple> list = giveFilePaths();
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i).queryNo + " " + list.get(i).filePath);
//		}
		
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
		
//		ArrayList<String> acronymList = acronyms.get(1).list;
		
		ArrayList<Tuple> filePaths = giveFilePaths();
		ArrayList<String> content = new ArrayList<>();
		
		UtilsPMC utilsP = new UtilsPMC();
		
		
		for (int i = 0; i < filePaths.size(); i++) {
			content.add(utilsP.getContent(filePaths.get(i).filePath));
		}
		
	
		
		String acr;
		String tag;
		
		String longForm;
		ArrayList <Tuple> list1 = new ArrayList <>();
//		System.out.println(content.size());
//		System.out.println(filePaths.size());
		// for each query
		for (int k = 1; k < 31; k++) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < acronyms.get(k).list.size(); j++) {
					if(content.get(10*(k-1) + i).contains(acronyms.get(k).list.get(j))){
////						System.out.println(acronymList.get(j));
						longForm = "";
						acr = acronyms.get(k).list.get(j);
						ArrayList <String> listTemp = new ArrayList <>();
						
						
						
						tag = "\\s[^\\s]*";
						
						for (int j2 = 1; j2 < acr.length(); j2++) {
							tag =  "\\s[^\\s]*" + tag;	
						}
						
						Pattern regex = Pattern.compile(tag + "\\s[\\[(]+" + acr +  "[\\])\\s.,;:]*");
						Matcher matcher = regex.matcher(content.get(i));
						while (matcher.find()) {
					       longForm = matcher.group()
					    		   .replaceAll("[\\(\\),.;:]", "")
					    		   .replaceFirst("\\s", "")
					    		   .replaceAll(acr, "");

					       longForm = longForm.trim();
					       
					       //check if the first character is the same
//					       if (acr.substring(0, 1).toLowerCase().equals(longForm.substring(0, 1).toLowerCase())){
					       		listTemp.add(longForm);
//					       }
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
		
		Writer output;
		output = new BufferedWriter(new FileWriter("/Users/panuyabalasuntharam/Documents/BA/acronymLongForm_added_note.txt"));
		for (int i = 0; i < longFormList.size(); i++) {
			if (!longFormList.get(i).list.isEmpty()){
				System.out.println(longFormList.get(i).acr + " " + longFormList.get(i).list.toString());
				output.append(longFormList.get(i).acr + " " + longFormList.get(i).list.toString() + "\n");
			}
		}
		
		output.close();
		
//		for (int i = 0; i < list1.size(); i++) {
//			System.out.println(list1.get(i).acr + " " + list1.get(i).list.toString());
//		}
		
	}
	
	public static ArrayList <Tuple> giveFilePaths() throws IOException {
		BufferedReader br1= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/filePathList_note2.txt"));
		String line = br1.readLine();
		
		ArrayList <Tuple> filePaths = new ArrayList<>() ;
		
		String arr [];
		
		while(line != null) {
			arr = line.split(" ", 2);
			filePaths.add(new Tuple(Integer.parseInt(arr[0]),arr[1].replaceAll("/Users/neptun/Desktop/", "/Users/panuyabalasuntharam/Documents/")));
			line = br1.readLine();
			
		}
		
		return filePaths;
	}

}

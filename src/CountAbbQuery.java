import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.util.fst.PairOutputs;
import org.xml.sax.SAXException;

public class CountAbbQuery {
	public static void main(String[] args) throws ParserConfigurationException,NullPointerException, SAXException, IOException{
		CountAbbQuery count = new CountAbbQuery();
		count.findAbbQuery();
	}
	
	static ArrayList<String> abbArray; 
	static ArrayList<Integer> abbCountArray;
	static String content;
	static int r;
	
	
	
	public CountAbbQuery() {
		abbArray = new ArrayList<>();
		abbCountArray = new ArrayList<>();
		r = 0;
	}
	
	public void findAbbQuery() throws ParserConfigurationException, SAXException, IOException{

		UtilsQuery processor = new UtilsQuery();
		content = processor.readContent("/Users/panuyabalasuntharam/Documents/BA/topics2016.xml");
		

		this.upper();
		this.slashUpper();
		this.slashLower();
		this.upperAndLower();
		//this.withNumbers();
		//this.metrics();
		//this.metrics2();
		
		
		System.out.println("There are in total " + r +" acronyms." );
		System.out.println("There are " + Integer.toString(abbArray.size())+" different kind of acronyms."+"\n");
		System.out.println(abbArray.toString());
		
//		Writer writer = new BufferedWriter(new FileWriter("/Users/neptun/Desktop/BA/acronymTableQuery.txt"));
//		
//		Writer writer2 = new BufferedWriter(new FileWriter("/Users/neptun/Desktop/BA/allAcronymsQuery.txt"));
//		writer2.append(abb);
//		writer2.close();
//		
//	
//		for (int i = 0; i < abbCountArray.size(); i++) {
//			writer.append(abbArray.get(i)+ " " + abbCountArray.get(i) + "\n");
//		}
//		
//		writer.close();
//		System.out.println(abbArray.size());
//		Writer writer3 = new BufferedWriter(new FileWriter("/Users/neptun/Desktop/BA/acronymMedical.txt"));
//		
//		for (int i = 0; i < abbArray.size(); i++) {
//			writer3.append(abbArray.get(i)+"\n");
//		}
//		
//		writer3.close();
				
		System.out.println("Query has "+Integer.toString(content.length())+ " words.");
		
		
	}
	
	public void upper() {
		//Grossbuchstaben
		//Anzahl 575
		Pattern p1 = Pattern.compile("\\s[A-Z]{1,6}[\\s,.:?!;]+");
		Matcher m1 = p1.matcher(content);
		
		
		String [] blackListSingleUp ={"A", "I"};
		
		while (m1.find()){
			String s1 = m1.group().replaceAll("[\\s,.:?!;]", "");
			
			if (!Arrays.asList(blackListSingleUp).contains(s1)) {
				
		
				r++;
				
				if (!abbArray.contains(s1)){
					abbArray.add(s1);
					abbCountArray.add(1);
				}else{
					int index = getIndex(abbArray, s1);
					abbCountArray.set(index, abbCountArray.get(index) + 1);
				}
				
			}	
		}
	}
	
	public void slashUpper() {
		// FEN/GI
		//Anzahl 644
		Pattern p2 = Pattern.compile("\\s[A-Z]+/[A-Z]+[\\s,.:?!;]");
		Matcher m2 = p2.matcher(content);
		
		while (m2.find()){
			String s2 = m2.group().replaceAll("[\\s,.:?!;]", "");
		
			r++;
			
			if (!abbArray.contains(s2)){
				abbArray.add(s2);
				abbCountArray.add(1);
			}else{
				int index = getIndex(abbArray, s2);
				abbCountArray.set(index, abbCountArray.get(index) + 1);
			}
		
		}
	}
	
	public void slashLower() {
		// w/ or h/o
		//Anzahl 723
		Pattern p3 = Pattern.compile("\\s[a-z]{1,4}/[a-z]{0,4}[\\s,.:?!;]");
		Matcher m3 = p3.matcher(content);
		
		while (m3.find()){
			String s3 = m3.group().replaceAll("[\\s,.:?!;]", "");

			r++;
			
			if (!abbArray.contains(s3)){
				abbArray.add(s3);
				abbCountArray.add(1);
			}else{
				int index = getIndex(abbArray, s3);
				abbCountArray.set(index, abbCountArray.get(index) + 1);
			}
		}
	}
	
	public void upperAndLower() {
		//Gross + Kleinbuchstabe
				//Anzahl 1026
				Pattern p7 = Pattern.compile("\\s[A-Z]+[a-z]{1}[\\s,.:?!;]");
				Matcher m7 = p7.matcher(content);
				
				String [] blackListUpLow = {"He", "An", "It", "Of", "If", "Is", "On", "In", "At", 
											"He\n", "An\n", "It\n", "Of\n", "If\n", "Is\n", "On\n", "In\n", "At\n"};
				while (m7.find()){
					String s7 = m7.group().replaceAll("[\\s,.:?!;]", "");
					if (!Arrays.asList(blackListUpLow).contains(s7)){

						r++;
						
						if (!abbArray.contains(s7)){
							abbArray.add(s7);
							abbCountArray.add(1);
						}else{
							int index = getIndex(abbArray, s7);
							abbCountArray.set(index, abbCountArray.get(index) + 1);
						}
					}
				}
	}
	
	public void withNumbers() {
		//mg cm    mit Zahlen
		//Anzahl 837
		Pattern p5 = Pattern.compile("[0-9]+[a-zA-Z]+[\\s,.:?!;]");
		Matcher m5 = p5.matcher(content);

		while (m5.find()){
			String s5 = m5.group().replaceAll("[\\s,.:?!;0-9]", "");
			r++;
			
			if (!abbArray.contains(s5)){
				abbArray.add(s5);
				abbCountArray.add(1);
			}else{
				int index = getIndex(abbArray, s5);
				abbCountArray.set(index, abbCountArray.get(index) + 1);
			}
		}
	}
	
	public void metrics() {
		//zwei Kleinbuchstaben
		//Anzahl 941
		Pattern p6 = Pattern.compile("\\s[a-z]{2}[\\s,.:?!;]");
		Matcher m6 = p6.matcher(content);
		
		String[] blackListTwoLow = {"he", "an", "it", "of", "if", "is", "on", "in", "to", "as", "by",
				"up",  "at" , "or","be","no","so","he\n", "an\n", "it\n", "of\n", "if\n", "is\n", "on\n", 
				"in\n", "to\n", "as\n", "by\n","up\n",  "at\n",  "or\n","be\n", "no\n","so\n"};
		while (m6.find()){
			String s6 = m6.group().replaceAll("[\\s,.:?!;]", "");
			if (!Arrays.asList(blackListTwoLow).contains(s6)){

					r++;
					
					if (!abbArray.contains(s6)){
						abbArray.add(s6);
						abbCountArray.add(1);

					}else{
						int index = getIndex(abbArray, s6);
						abbCountArray.set(index, abbCountArray.get(index) + 1);

					}
			}
		}
	}
	
	public void metrics2() {
		// mmHg
		//Anzahl: 728
		Pattern p4 = Pattern.compile("[\\s0-9]+[a-z]+[A-Z]+[a-z]+[\\s,.:?!;]");
		Matcher m4 = p4.matcher(content);

		while (m4.find()){
			String s4 = m4.group().replaceAll("[\\s,.:?!;0-9]", "");
			r++;
			
			if (!abbArray.contains(s4)){
				abbArray.add(s4);
				abbCountArray.add(1);
			}else{
				int index = getIndex(abbArray, s4);
				abbCountArray.set(index, abbCountArray.get(index) + 1);
			}
		}
	}
	
	//Achtung 0!
	public static int getIndex(ArrayList<String> array, String abb){
		for (int i = 0; i < array.size() ; i++) {
			if ((array.get(i).equals(abb))){
				return i;
			}
		}
		return 0;
		
	}
	
	
}


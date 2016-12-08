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
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException{
		findAbbQuery();
	}
	
	public static void findAbbQuery() throws ParserConfigurationException, SAXException, IOException{
		QueryPreprocess processor = new QueryPreprocess();
		String content = processor.readContent("/Users/neptun/Desktop/BA/topics2016.xml");
		//System.out.println(content);
		
		ArrayList<String> abbArray = new ArrayList<>();
		ArrayList<Integer> abbCountArray = new ArrayList<>();
				
		//Grossbuchstaben
		//Anzahl 575
		Pattern p1 = Pattern.compile("\\s[A-Z]{1,6}[\\s,.:?!;]+");
		Matcher m1 = p1.matcher(content);
		String abb = "";
		
		String [] blackListSingleUp ={"A", "I"};
		int r = 0;
		while (m1.find()){
			String s1 = m1.group().replaceAll("[\\s,.:?!;]", "");
			
			if (!Arrays.asList(blackListSingleUp).contains(s1)) {
				
				abb = abb +" "+ s1;
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
		
		// FEN/GI
		//Anzahl 644
		Pattern p2 = Pattern.compile("\\s[A-Z]+/[A-Z]+[\\s,.:?!;]");
		Matcher m2 = p2.matcher(content);
		
		while (m2.find()){
			String s2 = m2.group().replaceAll("[\\s,.:?!;]", "");
			abb = abb +" "+ s2;
			r++;
			
			if (!abbArray.contains(s2)){
				abbArray.add(s2);
				abbCountArray.add(1);
			}else{
				int index = getIndex(abbArray, s2);
				abbCountArray.set(index, abbCountArray.get(index) + 1);
			}
		
		}
		
		
		// w/ or h/o
		//Anzahl 723
		Pattern p3 = Pattern.compile("\\s[a-z]{1,4}/[a-z]{0,4}[\\s,.:?!;]");
		Matcher m3 = p3.matcher(content);
		
		while (m3.find()){
			String s3 = m3.group().replaceAll("[\\s,.:?!;]", "");
			abb = abb +" "+ s3;
			r++;
			
			if (!abbArray.contains(s3)){
				abbArray.add(s3);
				abbCountArray.add(1);
			}else{
				int index = getIndex(abbArray, s3);
				abbCountArray.set(index, abbCountArray.get(index) + 1);
			}
		}
		
		
		
		// mmHg
		//Anzahl: 728
		Pattern p4 = Pattern.compile("[\\s0-9]+[a-z]+[A-Z]+[a-z]+[\\s,.:?!;]");
		Matcher m4 = p4.matcher(content);

		while (m4.find()){
			String s4 = m4.group().replaceAll("[\\s,.:?!;0-9]", "");
			abb = abb +" "+ s4;
			r++;
			
			if (!abbArray.contains(s4)){
				abbArray.add(s4);
				abbCountArray.add(1);
			}else{
				int index = getIndex(abbArray, s4);
				abbCountArray.set(index, abbCountArray.get(index) + 1);
			}
		}
		
		//mg cm    mit Zahlen
		//Anzahl 837
		Pattern p5 = Pattern.compile("[0-9]+[a-zA-Z]+[\\s,.:?!;]");
		Matcher m5 = p5.matcher(content);

		while (m5.find()){
			String s5 = m5.group().replaceAll("[\\s,.:?!;0-9]", "");
			abb = abb +" "+ s5;
			r++;
			
			if (!abbArray.contains(s5)){
				abbArray.add(s5);
				abbCountArray.add(1);
			}else{
				int index = getIndex(abbArray, s5);
				abbCountArray.set(index, abbCountArray.get(index) + 1);
			}
		}
		
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
					abb = abb +" "+ s6;
					//System.out.println(s6);
					r++;
					
					if (!abbArray.contains(s6)){
						abbArray.add(s6);
						abbCountArray.add(1);
						//System.out.println(" New element\n");
					}else{
						int index = getIndex(abbArray, s6);
						abbCountArray.set(index, abbCountArray.get(index) + 1);
						//System.out.println(" Increased Counter\n" );
					}
			}
		}
		
		//Gross + Kleinbuchstabe
		//Anzahl 1026
		Pattern p7 = Pattern.compile("\\s[A-Z]+[a-z]{1}[\\s,.:?!;]");
		Matcher m7 = p7.matcher(content);
		
		String [] blackListUpLow = {"He", "An", "It", "Of", "If", "Is", "On", "In", "At", 
									"He\n", "An\n", "It\n", "Of\n", "If\n", "Is\n", "On\n", "In\n", "At\n"};
		while (m7.find()){
			String s7 = m7.group().replaceAll("[\\s,.:?!;]", "");
			if (!Arrays.asList(blackListUpLow).contains(s7)){
				abb = abb +" "+ s7;
				r++;
//				
				if (!abbArray.contains(s7)){
					abbArray.add(s7);
					abbCountArray.add(1);
				}else{
					int index = getIndex(abbArray, s7);
					abbCountArray.set(index, abbCountArray.get(index) + 1);
				}
			}
		}
		
		
		System.out.println("There are in total " + r +" acronyms." );
		System.out.println("There are " + Integer.toString(abbArray.size())+" different kind of acronyms."+"\n");
		
		Writer writer = new BufferedWriter(new FileWriter("/Users/neptun/Desktop/BA/acronymTableQuery.txt"));
		
		Writer writer2 = new BufferedWriter(new FileWriter("/Users/neptun/Desktop/BA/allAcronymsQuery.txt"));
		writer2.append(abb);
		writer2.close();
		
	
		for (int i = 0; i < abbCountArray.size(); i++) {
			writer.append(abbArray.get(i)+ " " + abbCountArray.get(i) + "\n");
		}
		
		writer.close();
				
		System.out.println("Query has "+Integer.toString(content.length())+ " words.");
		
		
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


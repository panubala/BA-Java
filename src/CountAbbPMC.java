import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountAbbPMC {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Utils parser = new Utils();
		
		String content = parser.getContent("/Users/neptun/Desktop/BA/pmc/pmc-00/00/13917.nxml");
		
//		for (int i = 0; i < 10; i++) {
//			content = content + parser.getContent("/Users/neptun/Desktop/BA/pmc/pmc-00/0"+i);
//		}
//		for (int i = 10; i < 53; i++) {
//			content = content + parser.getContent("/Users/neptun/Desktop/BA/pmc/pmc-00/"+i);
//		}
		CountAbbPMC counter = new CountAbbPMC();
		ArrayList<String> abbArray = counter.findAllAbb(content);
		counter.findTheMeaning(content, abbArray);
	}
	
	CountAbbPMC(){}
	
	public  ArrayList<String> findAllAbb(String content){
		ArrayList<String> abbArray = new ArrayList<>();
		ArrayList<Integer> abbCountArray = new ArrayList<>();
		
		String abb = "";
		int r = 0;
				
		//Grossbuchstaben
		Pattern p1 = Pattern.compile("[\\s\\[(]{1}[A-Z]+[-/]*[1-9]*[A-Z]*[1-9]*[\\s,.:?!;\\])]+");
		Matcher m1 = p1.matcher(content);
		
		
		String [] blackListSingleUp ={"A", "I"};
		
		while (m1.find()){
			String s1 = m1.group().replaceAll("[\\s,.:?!;\\[\\]()]", "");
			
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
		
				
		// w/ or h/o
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
		Pattern p4 = Pattern.compile("[\\s0-9\\[(]{1}[a-z]+[A-Z]+[a-z]*[\\s,.:?!;]");
		Matcher m4 = p4.matcher(content);

		while (m4.find()){
			String s4 = m4.group().replaceAll("[\\s\\(\\[,.:?!;0-9]", "");
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
//		Pattern p5 = Pattern.compile("[0-9]{2,}[a-zA-Z]+[\\s,.:?!;]");
//		Matcher m5 = p5.matcher(content);
//
//		while (m5.find()){
//			String s5 = m5.group().replaceAll("[\\s,.:?!;]", "");
//			abb = abb +" "+ s5;
//			r++;
//			
//			if (!abbArray.contains(s5)){
//				abbArray.add(s5);
//				abbCountArray.add(1);
//			}else{
//				int index = getIndex(abbArray, s5);
//				abbCountArray.set(index, abbCountArray.get(index) + 1);
//			}
//		}
		
	//Aminosäurenzahlen --> kann man ignorieren
//		Pattern p8 = Pattern.compile("[0-9]+[-][0-9]+[\\s,.:?!;]");
//		Matcher m8 = p8.matcher(content);
//
//			while (m8.find()){
//				String s8 = m8.group().replaceAll("[\\s,.:?!;]", "");
//				abb = abb +" "+ s8;
//				r++;
//					
//				if (!abbArray.contains(s8)){
//					abbArray.add(s8);
//					abbCountArray.add(1);
//				}else{
//					int index = getIndex(abbArray, s8);
//					abbCountArray.set(index, abbCountArray.get(index) + 1);
//				}
//			}		
		
//		//zwei Kleinbuchstaben
//		//Anzahl 941
//		Pattern p6 = Pattern.compile("\\s[a-z]{2}[\\s,.:?!;]");
//		Matcher m6 = p6.matcher(content);
//		
//		String[] blackListTwoLow = {"he","we", "an", "it", "of", "if", "is", "on", "in", "to", "as", "by",
//				"up",  "at" , "or","be","no","so","he\n", "an\n", "it\n", "of\n", "if\n", "is\n", "on\n", 
//				"in\n", "to\n", "as\n", "by\n","up\n",  "at\n",  "or\n","be\n", "no\n","so\n","we\n"};
//		while (m6.find()){
//			String s6 = m6.group().replaceAll("[\\s,.:?!;]", "");
//			if (!Arrays.asList(blackListTwoLow).contains(s6)){
//					abb = abb +" "+ s6;
//					//System.out.println(s6);
//					r++;
//					
//					if (!abbArray.contains(s6)){
//						abbArray.add(s6);
//						abbCountArray.add(1);
//						//System.out.println(" New element\n");
//					}else{
//						int index = getIndex(abbArray, s6);
//						abbCountArray.set(index, abbCountArray.get(index) + 1);
//						//System.out.println(" Increased Counter\n" );
//					}
//			}
//		}
		
//		//Gross + Kleinbuchstabe
//		//Anzahl 1026
//		Pattern p7 = Pattern.compile("\\s[A-Z]+[a-z]{1}[\\s,.:?!;]");
//		Matcher m7 = p7.matcher(content);
//		
//		String [] blackListUpLow = {"He", "An", "It", "Of", "If", "Is", "On", "In", "At", 
//									"He\n", "An\n", "It\n", "Of\n", "If\n", "Is\n", "On\n", "In\n", "At\n"};
//		while (m7.find()){
//			String s7 = m7.group().replaceAll("[\\s,.:?!;]", "");
//			if (!Arrays.asList(blackListUpLow).contains(s7)){
//				abb = abb +" "+ s7;
//				r++;
////				
//				if (!abbArray.contains(s7)){
//					abbArray.add(s7);
//					abbCountArray.add(1);
//				}else{
//					int index = getIndex(abbArray, s7);
//					abbCountArray.set(index, abbCountArray.get(index) + 1);
//				}
//			}
//		}
		
		
		System.out.println("There are in total " + r +" acronyms." );
		System.out.println("There are " + Integer.toString(abbArray.size())+" different kind of acronyms."+"\n");
		
		System.out.println(abbArray + "\n");
		System.out.println(abbCountArray + "\n");
		
		System.out.println("These are the acronyms: " + abb + "\n");
		
		return abbArray;
	
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
	
	static public int countWords(String str){
		String trim = str.trim();
	    if (trim.isEmpty())
	        return 0;
	    return trim.split("\\s+").length;
	}
	
	//TODO: Do same with []
	public void findTheMeaning(String content, ArrayList<String> abbArray) {
		String abb = "";
		int r = 0;
		for (int i = 0; i < abbArray.size(); i++) {
			abb = abbArray.get(i);
			Pattern p1 = Pattern.compile("[\\s\\[]{1}"+ abb + "[\\s][\\(][^\\)]+[\\)]");
			Matcher m1 = p1.matcher(content);
			String s1 = "";
			if(m1.find()){
				s1 = m1.group();
						//.replaceAll("[0-9\\s,.:?!;\\[\\]]", "");
			}
			
			if (!s1.equals("")) {
//				if (abb.length() == countWords(s1) -1) {
//					System.out.println("YES " + s1 + countWords(s1) + "\n");
//				}else{
//					System.out.println("NO " + s1 + countWords(s1) + "\n");
//				}
				
				r++;
				System.out.println("Method 1: " + s1 + countWords(s1) + "\n");
			}

		}
		
		abb = "";
		int u;
		for (int j = 0; j < abbArray.size(); j++) {
			abb = abbArray.get(j);
			u = abb.length();
			String regex ="" ;
			for (int j2 = 0; j2 < u; j2++) {
				regex = regex + "[A-Za-z]*\\s";
			}
			regex = "\\s"+regex;
			Pattern p2 = Pattern.compile(regex+"[\\(]"+ abb +"[\\)]" );
			Matcher m2 = p2.matcher(content);
			String s2 = "";
			if(m2.find()){
				s2 = m2.group();
						//.replaceAll("[0-9\\s,.:?!;\\[\\]]", "");
			}
			
			if (!s2.equals("")) {
				
				r++;
				System.out.println("Method 2: " + s2 + countWords(s2) + "\n");
			}
		
		}
		System.out.println("I found "+ r + " acronyms.");
	}
}

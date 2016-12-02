import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class CountABB {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException{
		QueryPreprocess processor = new QueryPreprocess();
		String content = processor.readContent("/Users/neptun/Desktop/BA/topics2016.xml");
		System.out.println(content);
		
		//Grossbuchstaben
//		Pattern p = Pattern.compile("\\s[A-Z]{1,6}[\\s,]+");
//		Matcher m = p.matcher(content);
//		String abb = "";
//		
//		int r = 0;
//		//Eliminate A and I
//		while (m.find()){
//			if (m.group().equals(" A ")) {
//			}else if (m.group().equals(" I ")){
//			}else{
//				abb = abb +" "+ m.group().replaceAll(",", "");
//				r++;
//			}
//			
//		}
//		System.out.println("\nI found " + r +" acronyms:" );
//		System.out.println(abb);
		
		// FEN/GI
//		Pattern p = Pattern.compile("\\s[A-Z]+/[A-Z]+\\s");
//		Matcher m = p.matcher(content);
//		String abb = "";
//		
//		int r = 0;
//		while (m.find()){
//			abb = abb +" "+ m.group();
//			r++;
//		}
//		System.out.println("\nI found " + r +" acronyms:" );
//		System.out.println(abb);
		
		
		// w/ or h/o
//		Pattern p = Pattern.compile("\\s[a-z]{1,4}/[a-z]{0,4}[\\s]");
//		Matcher m = p.matcher(content);
//		String abb = "";
//		
//		int r = 0;
//		while (m.find()){
//			abb = abb +" "+ m.group();
//			r++;
//		}
//		System.out.println("\nI found " + r +" acronyms:" );
//		System.out.println(abb);
		
		
		// mmHg
//		Pattern p = Pattern.compile("\\s[a-z]+[A-Z]+[a-z]+[\\s]");
//		Matcher m = p.matcher(content);
//		String abb = "";
//		
//		int r = 0;
//		while (m.find()){
//			abb = abb +" "+ m.group();
//			r++;
//		}
//		System.out.println("\nI found " + r +" acronyms:" );
//		System.out.println(abb);
		
		
		
		
		
		//mg cm
//		Pattern p = Pattern.compile("[0-9]+[a-zA-Z]+[\\s]");
//		Matcher m = p.matcher(content);
//		String abb = "";
//		
//		int r = 0;
//		while (m.find()){
//			abb = abb +" "+ m.group().replaceAll("[0-9]", "");
//			r++;
//		}
//		System.out.println("\nI found " + r +" acronyms:" );
//		System.out.println(abb);
		
		
		
		
//		
//		Pattern p = Pattern.compile("\\s[A-Z]+[a-z]{1}[\\s]{1}");
//		Matcher m = p.matcher(content);
//		String abb = "";
//		
//		int r = 0;
//		while (m.find()){
//			if (!m.group().equals(" He ")
//					&& !m.group().equals(" He\n")
//					&& !m.group().equals(" An ")
//					&& !m.group().equals(" It ")
//					&& !m.group().equals(" Of ")
//					&& !m.group().equals(" If ")
//					&& !m.group().equals(" Is ")
//					&&  !m.group().equals(" On ") 
//					&&  !m.group().equals(" In ")
//					&&  !m.group().equals(" At ")){
//				
//					abb = abb +" "+ m.group();
//					r++;
//				}
//		}
//		System.out.println("\nI found " + r +" acronyms:" );
//		System.out.println(abb);
//		
		Pattern p = Pattern.compile("\\s[a-z]{2}[\\s]");
		Matcher m = p.matcher(content);
		String abb = "";
		
		int r = 0;
		String[] array = {" he ", " an ", " it ", " of ", " if ", " is ", " on ", " in ", " to ", " as ", " by ",
				" up ",  " at " , " or "," be "," no "," so "," he\n", " an\n", " it\n", " of\n", " if\n", " is\n", " on\n", " in\n", " to\n", " as\n", " by\n",
				" up\n",  " at\n",  " or\n"," be\n", " no\n"," so\n"};
		while (m.find()){
			if (!Arrays.asList(array).contains(m.group())){
					abb = abb +" "+ m.group();
					r++;
				}
		}
		System.out.println("\nI found " + r +" acronyms:" );
		System.out.println(abb);
	}
}


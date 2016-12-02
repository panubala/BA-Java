import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class CountABB {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException{
		ReadFiles r = new ReadFiles();
		String content = r.readFile16All("/Users/neptun/Desktop/BA/topics2016.xml");
		System.out.println(content);
		
		Pattern p = Pattern.compile("[A-Z]{2,}");
		Matcher m = p.matcher(content);
		String abb = "";
		System.out.println("I found those acronyms:" );
		while (m.find()){
			abb = abb +" "+ m.group();
		}
		System.out.println(abb);
	}
}

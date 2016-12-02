
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Utils {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Utils util = new Utils();
		System.out.println(util.getTitle("/Users/neptun/Desktop/BA/pmc/pmc-00/00/138682.nxml"));
	}

	private String content;
	
	Utils(){}
	
	public String getContent(String path) throws FileNotFoundException, IOException{
		readFile(path);
		return findTags("<p>", "</p>");
	}
	
	
	public String getdocID(String path) throws FileNotFoundException, IOException{
		readFile(path);
		String firstTag = "";
		if (content.contains("<article-id pub-id-type=\"pmc\">")){
			
			firstTag = "<article-id pub-id-type=\"pmc\">";
		}else{
			firstTag = "<article-id pub-id-type=\"pmcid\">";
		}

		String lastTag = "</article-id>";
		return findTags(firstTag, lastTag);
	}
	

	public String getTitle(String path) throws FileNotFoundException, IOException{
		readFile(path);
		
		Pattern regex = Pattern.compile("<article-title>([^<]*)</article-title>", Pattern.DOTALL);
		
		
		Matcher matcher = regex.matcher(content);
		String text = "";
		if (matcher.find()){
			text = text + " "+ matcher.group().replaceAll("<article-title>", "").replaceAll("</article-title>", "").replaceAll("<[^>]+>", "").replaceAll(">", "");
		}
		return text;

	}
	

	public void readFile(String pathString) throws FileNotFoundException, IOException{
		
		Path path = Paths.get(pathString);
		
		Scanner scanner = new Scanner(path);
		StringBuilder sb = new StringBuilder();
		

		String line = scanner.toString();
		sb.append(line);
		while (scanner.hasNextLine()){
			sb.append(System.lineSeparator());
			line= scanner.nextLine();
			sb.append(line);
		}
		
		content = sb.toString();
		scanner.close();
				
	}
	
	public String findTags(String firstTag, String lastTag){
		
		String text = "";
		Pattern regex = Pattern.compile(firstTag + "([^<]*)" + lastTag, Pattern.DOTALL);
		Matcher matcher = regex.matcher(content);
		while (matcher.find()) {
	        text = text + " "+ matcher.group().replaceAll(firstTag, "").replaceAll(lastTag, "").replaceAll("<[^>]+>", "").replaceAll(">", "");
	    }
		
		return text;	
	}

}
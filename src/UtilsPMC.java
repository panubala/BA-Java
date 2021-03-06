
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsPMC {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		UtilsPMC util = new UtilsPMC();
		System.out.println(util.getdocID("/Users/panuyabalasuntharam/Documents/BA/pmc/pmc-00/00/13901.nxml"));
		
	}

	private String content;
	
	UtilsPMC(){}
	
	public String getContent(String path) throws FileNotFoundException, IOException{
		readFile(path);
		return findTags("<p[^>]*>","<[\\s]*/[\\s]*p[\\s]*>" );
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
	        text = text + " "+ matcher.group()
	        .replaceAll(firstTag, " ")
	        .replaceAll(lastTag, " ")
	        .replaceAll("<[^>]+>", " ")
	        .replaceAll(">", " ")
	        .replaceAll("[\\s]{2,}", " ");
	    }
		
		return text;	
	}

}
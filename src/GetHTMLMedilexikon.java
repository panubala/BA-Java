import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetHTMLMedilexikon {
	public static void main(String[] args) throws IOException, InterruptedException {
		ArrayList<String> acronyms = new ArrayList<>();
		acronyms = createAbbArray(); 

		
		int r = 0;
		for (int i = 0; i < acronyms.size(); i++) {
			create(acronyms.get(i));
			TimeUnit.SECONDS.sleep(2);
			System.out.println(r);
			r++;
		}
	}
		
		//It makes an array of the acronyms
	private static ArrayList<String> createAbbArray() throws IOException {
		BufferedReader br= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/acronymMedical.txt"));
		ArrayList<String> acronyms = new ArrayList<>();
		String line = br.readLine();
		while(line!=null){
			acronyms.add(line);
			line = br.readLine();
		}
			
			return acronyms;
	}
		
	private static void create(String abb) throws IOException {
		//String html = getUrlSource("http://www.medilexicon.com/abbreviations?search="+ abb +"&target=abbreviations");
		String abb2 = abb.replaceAll("/", "_");
		String html = readDoc(abb2);
		//createFile(html, "/Users/panuyabalasuntharam/Documents/BA/abbreviations/xml2/"+ abb2 +".xml");
		createFileAbb(html, abb,"/Users/panuyabalasuntharam/Documents/BA/abbreviations/abb2/" + abb2 +".txt");

	}
		
	private static String getUrlSource(String url) throws IOException{
		URL html = new URL(url);
		URLConnection h = html.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(h.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuilder sb =new StringBuilder();
		while((inputLine = in.readLine()) != null){
			sb.append(inputLine);
		}
		in.close();
		return sb.toString();
	}
	
	private static void createFile(String text, String path) throws IOException {
		Writer output = new BufferedWriter(new FileWriter(path));
		output.append(text);
		output.close();
	}
	
	private static ArrayList<String> findLongForm(String content, String abb) {
		ArrayList<String> lFArray = new ArrayList<>();
		Pattern p1 = Pattern.compile("<strong class=\'item_text_strong\'>"+abb+"</strong><strong class=\"item_text_normal\">[^<]*<");
		Matcher m1 = p1.matcher(content);
		
		
		while (m1.find()){
			String longForm  = m1.group().replaceAll("<strong class=\'item_text_strong\'>"+abb+"</strong><strong class=\"item_text_normal\">", "").replaceAll("<", "");
			lFArray.add(longForm);	
		}
		
		return lFArray;
	}
	
	private static void createFileAbb(String html, String abb, String path) throws IOException {
		ArrayList<String> lFArray = findLongForm(html, abb);
		//ArrayList<String> ratingArray = findRating(html);
		Writer output = new BufferedWriter(new FileWriter(path));
		
		for (int j = 0; j < lFArray.size(); j++) {
			output.append(
					//ratingArray.get(j) + 
					" " + lFArray.get(j)  + "\n");
		}
		
		output.close();
	}
	
	private static String readDoc(String abb2) throws IOException {
		BufferedReader br= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/abbreviations/xml2/"+ abb2 +".xml"));
		String text = "";
		String line = br.readLine();
		while(line!=null){
			text = text + line;
			line = br.readLine();
		}
		br.close();
		
		return text;
	}
	
}

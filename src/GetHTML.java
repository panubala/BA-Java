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

public class GetHTML {
	public static void main(String[] args) throws IOException, InterruptedException {
		ArrayList<String> acronyms = new ArrayList<>();
		acronyms = createAbbArray(); 
		System.out.println(acronyms.toString());
		System.out.println(acronyms.size());
		
		//184
		int r = 0;
		for (int i = 184; i < acronyms.size(); i++) {
			create(acronyms.get(i));
			TimeUnit.SECONDS.sleep(2);
			System.out.println(r);
			r++;
		}
		
		
		
		
		
	}
	
	private static void create(String abb) throws IOException {
		String html = getUrlSource("https://www.allacronyms.com/_medical/" + abb);
		createFile(html, "/Users/neptun/Desktop/BA/abbreviations/xml/"+ abb +".xml");
		//createFileAbb(html,"/Users/neptun/Desktop/BA/abbreviations/abb/" + abb +".xml");

	}
	
	private static ArrayList<String> createAbbArray() throws IOException {
		BufferedReader br= new BufferedReader(new FileReader("/Users/neptun/Desktop/BA/acronymMedical.txt"));
		ArrayList<String> acronyms = new ArrayList<>();
		String line = br.readLine();
		while(line!=null){
			acronyms.add(line);
			line = br.readLine();
		}
		
		return acronyms;
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
	
	private static void createFileAbb(String html, String path) throws IOException {
		ArrayList<String> lFArray = findLongForm(html);
		ArrayList<String> ratingArray = findRating(html);
		Writer output = new BufferedWriter(new FileWriter(path));
		
		for (int j = 0; j < lFArray.size(); j++) {
			output.append(ratingArray.get(j) + " " + lFArray.get(j)  + "\n");
		}
		
		output.close();
	}
	
	private static ArrayList<String> findLongForm(String content) {
		ArrayList<String> lFArray = new ArrayList<>();
		Pattern p1 = Pattern.compile("<div\\sclass\\=\"pairDef\">[^<]*<");
		Matcher m1 = p1.matcher(content);
		
		
		while (m1.find()){
			String longForm  = m1.group().replaceAll("<div\\sclass\\=\"pairDef\">", "").replaceAll("<", "");
			lFArray.add(longForm);
			
		}
		
		return lFArray;
	}
	
	private static ArrayList<String> findRating(String content) {
		ArrayList<String> ratingArray = new ArrayList<>();
		Pattern p1 = Pattern.compile(" rating\">[1-9]+");
		Matcher m1 = p1.matcher(content);
		
	
		while (m1.find()){
			String rating = m1.group().replaceAll(" rating\">", "");
			ratingArray.add(rating);
			
		}

		return ratingArray;
	}
}

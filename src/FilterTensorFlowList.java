import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterTensorFlowList {
	public static void main(String[] args) throws IOException {
		ArrayList<String> acronyms = UtilsQuery.createAbbArray();
//		System.out.println(Arrays.toString(acronyms.toArray()));
		String abb;
		String abbReplaced;
		
		for (int i = 0; i < acronyms.size(); i++) {
			abb = acronyms.get(i);
			abbReplaced = abb.replaceAll("/", "_");
			
			File file = new File("/Users/panuyabalasuntharam/Documents/BA/abbTensorFlow/" + abbReplaced +".txt");
//			System.out.println(file.toString());
			if (file.exists()){
				
				BufferedReader br1= new BufferedReader(new FileReader("/Users/panuyabalasuntharam/Documents/BA/abbTensorFlow/" + abbReplaced +".txt"));
//				Writer output = new BufferedWriter(new FileWriter("/Users/panuyabalasuntharam/Documents/BA/abbTensorFlow3/" + abbReplaced +".txt"));
				String line = br1.readLine();
				System.out.println(line);
				while(line!=null ){
					line =line.replaceAll("[-///]", " ");
					if(filter(line)){
						System.out.println(line);
//						output.append(line + "\n");
					}
					line= br1.readLine();
				}
				br1.close();
//				output.close();
			}
		}
		
	}
	
	static public  Boolean filter(String input) {
		Pattern p = Pattern.compile(".*[0-9+//(//)]+.*");
		Matcher m = p.matcher(input);
		
		if (m.find()){
			return false;
		}else{
			return true;
		}
		
		
		
	}
	
	
}

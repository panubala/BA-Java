import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Word2VecPreparation {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		UtilsPMC utils = new UtilsPMC();
		ArrayList<File>listOfFiles = new ArrayList<File>();
		File folder;

		
		//pmc-00
		for (int i = 0; i < 10; i++) {
			folder = new File("/Users/panuyabalasuntharam/Documents/BA/pmc/pmc-00/0"+i);
			listOfFiles.addAll(Arrays.asList(folder.listFiles()));
		}
		for (int i = 10; i < 53; i++) {
			folder = new File("/Users/panuyabalasuntharam/Documents/BA/pmc/pmc-00/"+i);
			listOfFiles.addAll(Arrays.asList(folder.listFiles()));
		}
		
		//pmc-01
		for (int i = 0; i < 10; i++) {
			folder = new File("/Users/panuyabalasuntharam/Documents/BA/pmc/pmc-01/0"+i);
			listOfFiles.addAll(Arrays.asList(folder.listFiles()));
			
		}
		
		System.out.println("True1");
		for (int i = 10; i < 49; i++) {
			folder = new File("/Users/panuyabalasuntharam/Documents/BA/pmc/pmc-01/"+i);
			listOfFiles.addAll(Arrays.asList(folder.listFiles()));	
			
		}
		
		//pmc-02
		for (int i = 0; i < 10; i++) {
			folder = new File("/Users/panuyabalasuntharam/Documents/BA/pmc/pmc-02/0"+i);
			listOfFiles.addAll(Arrays.asList(folder.listFiles()));
		}

		for (int i = 10; i < 78; i++) {
			folder = new File("/Users/panuyabalasuntharam/Documents/BA/pmc/pmc-02/"+i);
			listOfFiles.addAll(Arrays.asList(folder.listFiles()));
		}
		
		//pmc-03
		for (int i = 0; i < 10; i++) {
			folder = new File("/Users/panuyabalasuntharam/Documents/BA/pmc/pmc-03/0"+i);
			listOfFiles.addAll(Arrays.asList(folder.listFiles()));
		}
		for (int i = 10; i < 73; i++) {
			folder = new File("/Users/panuyabalasuntharam/Documents/BA/pmc/pmc-03/"+i);
			listOfFiles.addAll(Arrays.asList(folder.listFiles()));	
		}
		
		
	
//		String content = "";
//		for (int i = 0; i < listOfFiles.length; i++) {
//			content = content + utils.getContent(listOfFiles[i].getAbsolutePath());
//		}
//		
//		System.out.println(content);
//		
//		Writer output = new BufferedWriter(new FileWriter("/Users/panuyabalasuntharam/Documents/BA/PMC.txt"));
//		output.append(content);
//		output.close();
	}
}	


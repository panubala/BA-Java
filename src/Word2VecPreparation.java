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
		
//		File[] listOfFiles = new File[]{};
		
		File folder;
		
//		File folder1 = new File("/Users/panuyabalasuntharam/Documents/BA/pmc/pmc-00/00");
//		File[] listOfFiles1 = folder1.listFiles();
//		
//		System.out.println(Arrays.toString(listOfFiles1));
		
		
		for (int i = 0; i < 10; i++) {
			folder = new File("/Users/panuyabalasuntharam/Documents/BA/pmc/pmc-00/0"+i);
//			System.out.println(Arrays.toString(folder.listFiles()));
			listOfFiles.addAll(Arrays.asList(folder.listFiles()));
//			listOfFiles = Stream.concat(Arrays.stream(listOfFiles), Arrays.stream(folder.listFiles())).toArray(File[]::new);
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


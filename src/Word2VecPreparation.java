import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

public class Word2VecPreparation {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		UtilsPMC utils = new UtilsPMC();
		File[] listOfFiles = new File[]{};
		
		File folder;
		
		for (int i = 0; i < 10; i++) {
			folder = new File("/Users/panuyabalasuntharam/Documents/BA/pmc-00/0"+i);
			listOfFiles = Stream.concat(Arrays.stream(listOfFiles), Arrays.stream(folder.listFiles())).toArray(File[]::new);
		}
		
		
		
		
		String content = "";
		for (int i = 0; i < listOfFiles.length; i++) {
			content = content + utils.getContent(listOfFiles[i].getAbsolutePath());
		}
		
		System.out.println(content);
		
		Writer output = new BufferedWriter(new FileWriter("/Users/panuyabalasuntharam/Documents/BA/PMC.txt"));
		output.append(content);
		output.close();
	}
}	


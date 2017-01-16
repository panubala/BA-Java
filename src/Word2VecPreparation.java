import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Word2VecPreparation {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		UtilsPMC utils = new UtilsPMC();
		
		
		File folder = new File("/Users/panuyabalasuntharam/Documents/BA/pmc/pmc-00/00");
		File[] listOfFiles = folder.listFiles();
		
		String content = "";
		for (int i = 0; i < listOfFiles.length; i++) {
			content = content + utils.getContent(listOfFiles[i].getAbsolutePath());
		}
		
		System.out.println(content);
		
		Writer output = new BufferedWriter(new FileWriter("/Users/panuyabalasuntharam/Documents/BA/PMCcontent.txt"));
		output.append(content);
		output.close();
	}
}	


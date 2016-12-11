import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReplaceQuery {
	public static void main(String[] args) throws IOException {
		ArrayList<String> acronyms = read();
		ArrayList<Tuple> list = new ArrayList<>();
		
		String abb;
		String abb1;

		for (int i = 0; i < acronyms.size(); i++) {
			
			System.out.println(i);
			abb = acronyms.get(i);
			System.out.println(abb);
			abb1 = abb.replaceAll("/", "_");
			
			File file = new File("/Users/neptun/Desktop/BA/abbreviations/abb/" + abb1 +".txt");
			
			if (file.exists()){
				BufferedReader br1= new BufferedReader(new FileReader("/Users/neptun/Desktop/BA/abbreviations/abb/" + abb1 +".txt"));
				Scanner sc = new Scanner("/Users/neptun/Desktop/BA/abbreviations/abb/" + abb1 +".txt");
				
				String line = br1.readLine();
			
				int rank = 0;
				
				String longForm = "";
				
				while(line!=null ){
					
					System.out.println("ok");
					String arr1 [] = line.split(" ", 2);
					int tempRank= Integer.parseInt(arr1 [0]);
					if (tempRank > rank){
						rank = tempRank;
						longForm = arr1 [1];
					}
					line= br1.readLine();
				}
				
				if (rank!=0){
					list.add(new Tuple(abb, longForm, i));
				}
				br1.close();
			}
			
			
		}
		
		System.out.println(list.toString());
		
	}
	
	public static ArrayList<String> read() throws IOException {
		BufferedReader br= new BufferedReader(new FileReader("/Users/neptun/Desktop/BA/acronymMedical.txt"));
		ArrayList<String> acronyms = new ArrayList<>();
		String line = br.readLine();
		while(line!=null){
			acronyms.add(line);
			line = br.readLine();
		}
		
		br.close();
		return acronyms;
	}
	
	
}

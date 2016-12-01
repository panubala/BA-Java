import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class FileSystem {
	
		//it removes all directories and creates an array with all files
		static ArrayList<File> createFileArray(File file, FileFilter filter){
			ArrayList<File> allFile = new ArrayList<File>();
			addPath(file, allFile, filter);
			return allFile;
			
		}
		
		static void addPath(File file, ArrayList<File> all, FileFilter filter){
			File [] children = file.listFiles();
			
			if(children != null){
				for(File child: children ){
					if(filter.accept(child)){
						all.add(child);
					}
					addPath(child, all, filter);
				}
			}
			
		}
		
		

}

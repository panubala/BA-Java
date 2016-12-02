import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class FileSystem {
		public ArrayList<File> allFile;
		
		public FileSystem(){
			allFile = new ArrayList<File>();
		}
		//it removes all directories and creates an array with all files
		public ArrayList<File> addDir(File file, FileFilter filter){
			addPath(file, allFile, filter);
			return allFile;
			
		}
		
		public void addPath(File file, ArrayList<File> all, FileFilter filter){
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

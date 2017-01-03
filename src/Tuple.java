import java.util.ArrayList;

public class Tuple {
	Tuple(String abb, String longForm, int index, int rank ){
		this.abb = abb;
		this.longForm = longForm;
		this.index = index;
		this.rank = rank;
	}

	Tuple(String abb, String longForm, int index){
		this.abb = abb;
		this.longForm = longForm;
		this.index = index;
	}
	
	Tuple(String abb, String longForm, String longForm2,  int index){
		this.abb = abb;
		this.longForm = longForm;
		this.longForm2 = longForm2;
		this.index = index;
	}
	
	Tuple(int queryNo, String filePath){
		this.queryNo = queryNo;
		this.filePath = filePath;
	}
	
	Tuple(int queryNo, ArrayList <String> list){
		this.queryNo = queryNo;
		this.list = list;
	}
	
	Tuple(String acr, ArrayList <String> list){
		this.acr = acr;
		this.list = list;
	}
	
	ArrayList <String> list;
	String filePath;
	int queryNo;
	String abb;
	String longForm;
	String longForm2;
	int index;
	int rank;
	String acr;

}


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
	
	String abb;
	String longForm;
	String longForm2;
	int index;
	int rank;

}

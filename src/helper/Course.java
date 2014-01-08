package helper;

public class Course {

	//private variables
	int id;
	String coursename;
	String shortname;
	String fullname;
	
	// Empty constructor
	public Course(){
		
	}
	// constructor
	public Course(int id, String shortname, String fullname){
		this.id = id;
		this.shortname = shortname;
		this.fullname = fullname;
	}
	
	//constructor
	public Course(int id, String coursename){
		this.id = id;
		this.coursename= coursename;
	}
	
	//constructor
	public Course(String coursename){
		this.coursename= coursename;
	}
	
	// constructor
	public Course(String shortname, String fullname){
		this.shortname = shortname;
		this.fullname = fullname;
	}
	
	// getting ID
	public int getId(){
		return this.id;
	}
	
	// setting id
	public void setId(int id){
		this.id = id;
	}
	
	// getting name
	public String getCourseName(){
		return this.coursename;
	}
	
	// setting name
	public void setCourseName(String coursename){
		this.coursename = coursename;
	}	
	
//	// getting name
//	public String getShortName(){
//		return this.shortname;
//	}
//	
//	// setting name
//	public void setShortName(String shortname){
//		this.shortname = shortname;
//	}
	
//	// getting name
//	public String getFullName(){
//		return this.fullname;
//	}
//	
//	// setting name
//	public void setFullName(String fullname){
//		this.fullname = fullname;
//	}
}

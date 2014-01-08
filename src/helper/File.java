package helper;

public class File {
	
	//private variables
	int _id;
	String _filename;
	String _location;
	String created_at;
	
	// Empty constructor
	public File(){
		
	}
	// constructor
	public File(int id, String filename, String location){
		this._id = id;
		this._filename = filename;
		this._location = location;
	}
	
	// constructor
	public File(String filename, String location){
		this._filename = filename;
		this._location = location;
	}
	
	// getting ID
	public int getId(){
		return this._id;
	}	
	
	// getting filename
	public String getFileName(){
		return this._filename;
	}

	// getting location
	public String getLocation(){
		return this._location;
	}

	// setting id
	public void setId(int id){
		this._id = id;
	}

	// setting filename
	public void setFileName(String filename){
		this._filename = filename;
	}
	
	// setting location
	public void setLocation(String location){
		this._location = location;
	}
	
	public void setCreatedAt(String created_at){
		this.created_at = created_at;
	}

}

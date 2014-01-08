package helper;

public class User {
	
	//private variables
	int id;
	String token;
	String url;
	String created_at;
	
	// Empty constructor
	public User(){
		
	}
	// constructor
	public User(int id, String token, String url){
		this.id = id;
		this.token = token;
		this.url = url;
	}
	
	// constructor
	public User(String token, String url){
		this.token = token;
		this.url = url;
	}
	
	// getting ID
	public int getId(){
		return this.id;
	}	
	
	// getting token
	public String getToken(){
		return this.token;
	}

	// getting url
	public String getUrl(){
		return this.url;
	}

	// setting id
	public void setId(int id){
		this.id = id;
	}

	// setting token
	public void setToken(String token){
		this.token = token;
	}
	
	// setting url
	public void setUrl(String url){
		this.url = url;
	}
	
	public void setCreatedAt(String created_at){
		this.created_at = created_at;
	}

}

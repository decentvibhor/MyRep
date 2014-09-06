package rough_work;

import java.util.Hashtable;

public class Hashtables_Java {

	
	public static void main(String[] args) {
		
		Hashtable<String,String> t=new Hashtable<String,String>();
		t.put("username","vibhor");
		t.put("city", "noida");
		t.put("key", "value");
		
		System.out.println(t.get("city"));

	}

}

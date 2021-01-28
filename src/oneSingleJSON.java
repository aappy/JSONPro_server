import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
public class oneSingleJSON {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception, JsonMappingException, IOException {

        JSONArray js = new JSONArray();      
		
        ArrayList<CustomerDetails> alist = new ArrayList<CustomerDetails>();
	
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = null; // to create connection
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Business?user=root&password=password");
    	Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from CustomerInfo ");
	
      
        while(rs.next()) {
//Create java object
        	CustomerDetails   obj = new CustomerDetails(); 	
        	obj.setCourseName(rs.getString(1));
            obj.setPurchaseDate(rs.getString(2));
            obj.setAmount(rs.getInt(3));
            obj.setLocation(rs.getString(4));
            alist.add(obj);  // adding new obj to alist to create json
        	System.out.println(obj.getCourseName() + " \t " + obj.getPurchaseDate()+ 
        			" \t " + obj.getAmount() + " \t " + obj.getLocation());
        }
//converting in Json  
      
        for (int i = 0; i < alist.size(); i++) {
		
        ObjectMapper o = new ObjectMapper();
       
        o.writeValue(new File("/Users/aparnachdhry/eclipse-workspace/JsonJava/customerInfo"+i+".json"), alist.get(i)); // passing obj value to the file. This is file now
	    
        Gson g = new Gson();
        
        String jsonString = g.toJson(alist.get(i));
        js.add(jsonString);
        
        }
      
        JSONObject jo  = new JSONObject();
        jo.put("data", js);
      // System.out.println(jo.toString());
        
        System.out.println(jo.toJSONString());
       
        con.close();
 /*        
        
// JSON array : We need to create JSON string from java object : for that - download gson dependency
        Gson g = new Gson();
        String jsonString = g.toJson();
        
        
        JSONObject jo  = new JSONObject();
        jo.put("data", "");
      
        JSONArray js = new JSONArray();      
        js.add(); //it accepts string used gson dependency. 

        */
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.Commands;

import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Absentium
 */
public class GetServers {
    
    JSONArray data;
    
    public GetServers() {
        
    }
    
    public String getData(){
        JSONParser parser = new JSONParser();
        
        try{
            Object object = parser.parse(new FileReader("./src/main/resources/json/Containers.json"));
            
            JSONObject obj = (JSONObject) object;
            
            data = (JSONArray) obj.get("servers");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        String dataString = data.toJSONString();
        return dataString;
    }
}

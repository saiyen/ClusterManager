/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.Json;

import java.util.Map;
import org.json.simple.JSONObject;

/**
 *
 * @author absentium
 */
public class JsonCreator {
    private Map<String, String> data;
    
    public JsonCreator(Map<String, String> data){
        this.data = data;
    }
    
    public JSONObject createJson(){
        JSONObject command = new JSONObject();
        
        for (Map.Entry<String, String> entry : data.entrySet()){
            command.put(entry.getKey(), entry.getValue());
        }
        
        return command;
    }
}

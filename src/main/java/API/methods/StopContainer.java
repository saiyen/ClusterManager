/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.methods;

import java.util.HashMap;
import java.util.Map;
import API.Json.JsonCreator;
import API.Sender.CommandSender;
import org.json.simple.JSONObject;

/**
 *
 * @author absentium
 */
public class StopContainer {
    private String cId;
    private String name;
    private String cType;
    
    public StopContainer(String name, String cId, String cType){
        this.cId = cId;
        this.name = name;
        this.cType = cType;
    }
    
    public String stopIt (){
        Map<String, String> stopMap = new HashMap<>();
        
        stopMap.put("name", name);
        stopMap.put("cId", cId);
        stopMap.put("cType", cType);
        
        JsonCreator creator = new JsonCreator(stopMap);
        JSONObject createdJson = creator.createJson();
        
        CommandSender sender = new CommandSender(createdJson);
        int result = sender.sendData();
        
        String returnText = "";
        
        if(result == 1) {
            returnText += "The container with id " + cId + " has been stopped successfully.";
        } else {
            returnText += "The container with id " + cId + " could not be stopped.";
        }
        
        return returnText;
    }
}

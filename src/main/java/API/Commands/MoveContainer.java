/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.Commands;

import java.util.HashMap;
import java.util.Map;
import API.Json.JsonCreator;
import API.Sender.CommandSender;
import org.json.simple.JSONObject;

/**
 *
 * @author absentium
 */
public class MoveContainer {
    private String cId;
    private String name;
    private String cType;
    private String destination;
    
    public MoveContainer(String name, String cId, String cType, String destination){
        this.name = name;
        this.cId = cId;
        this.cType = cType;
        this.destination = destination;
    }
    
    public String moveIt(){
        Map<String,String> moveMap = new HashMap<>();
        
        moveMap.put("name", name);
        moveMap.put("cId", cId);
        moveMap.put("cType", cType);
        moveMap.put("destination", destination);
        
        JsonCreator creator = new JsonCreator(moveMap);
        JSONObject createdJson = creator.createJson();
        
        CommandSender sender = new CommandSender(createdJson);
        int result = sender.sendData();
        
        String returnText = "";
        
        if(result == 1) {
            returnText += "The container with id " + cId + " has been moved to " + destination + " successfully.";
        } else {
            returnText += "The container with id " + cId + " could not be stopped.";
        }
        
        return returnText;
    }
}

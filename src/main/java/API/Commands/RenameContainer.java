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
public class RenameContainer {
    private String name;
    private String cId;
    private String cType;
    private String newName;
    
    public RenameContainer(String name, String cId, String cType, String newName){
        this.name = name;
        this.cId = cId;
        this.cType = cType;
        this.newName = newName;
    }
    
    public String sendName(){
        Map<String, String> renameMap = new HashMap<>();
        
        renameMap.put("name", name);
        renameMap.put("cType", cType);
        renameMap.put("cId", cId);
        renameMap.put("newName", newName);
        
        JsonCreator creator = new JsonCreator(renameMap);
        JSONObject createdJson = creator.createJson();
        
        CommandSender sender = new CommandSender(createdJson);
        int result = sender.sendData();
        
        String returnText = "";
        
        if(result == 1) {
            returnText += "The name has been changed successfully";
        } else {
            returnText += "The name could not be changed";
        }
        
        return returnText;
    }
}

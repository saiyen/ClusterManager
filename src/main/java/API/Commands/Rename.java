/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.Commands;

import java.util.Map;
import API.Interfaces.Command;
import API.Json.JsonCreator;
import API.Sender.CommandSender;
import org.json.simple.JSONObject;

/**
 *
 * @author absentium
 */
public class Rename implements Command {
    
    public Rename(){
        
    }

    @Override
    public String execute(Map data) {
        JsonCreator creator = new JsonCreator(data);
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

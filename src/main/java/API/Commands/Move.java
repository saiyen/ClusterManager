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
public class Move implements Command {

    public Move(){
        
    }

    @Override
    public String execute(Map data) {
        JsonCreator creator = new JsonCreator(data);
        JSONObject createdJson = creator.createJson();
        
        CommandSender sender = new CommandSender(createdJson);
        int result = sender.sendData();
        
        String returnText = "";
        
        if(result == 1) {
            returnText += "The container with id " + data.get("cId") + " has been moved to " + data.get("destination") + " successfully.";
        } else {
            returnText += "The container with id " + data.get("cId") + " could not be stopped.";
        }
        
        return returnText;
    }
}

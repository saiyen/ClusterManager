/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.Sender;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.hogeschool.ClusterManager.SystemAdministrator;
import org.json.simple.JSONObject;

/**
 *
 * @author absentium
 */
public class CommandSender {
    JSONObject commandData;
    
    public CommandSender(JSONObject json){
        this.commandData = json;
    }
    
    public int sendData(){
        try {
            SystemAdministrator sysAdmin = new SystemAdministrator(commandData.get("cType").toString());
            sysAdmin.containerManager.setJson(commandData);
            switch(commandData.get("name").toString()){
                case "start":
                    sysAdmin.containerManager.startContainer();
                    break;
                case "stop":
                    sysAdmin.containerManager.stopContainer();
                    break;
                case "rename":
                    sysAdmin.containerManager.renameContainer();
                    break;
                case "move":
                    sysAdmin.containerManager.moveContainer();
                    break;
                case "create":
                    sysAdmin.containerManager.createContainer();
                    break; 
                case "remove":
                    sysAdmin.containerManager.removeContainer();
                    break;
            }
            
            return 1;
        } catch (IOException ex) {
            Logger.getLogger(CommandSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
}

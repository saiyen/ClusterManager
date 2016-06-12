/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.Sender;

import ContainerManager.ContainerManagerFactory;
import Interfaces.ContainerManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            ContainerManagerFactory containerManagerFactory = new ContainerManagerFactory();
            ContainerManager containerManager = containerManagerFactory.getContainerManager(commandData.get("cType").toString());

            containerManager.setJson(commandData);
            switch(commandData.get("name").toString()){
                case "start":
                    containerManager.startContainer();
                    break;
                case "stop":
                    containerManager.stopContainer();
                    break;
                case "rename":
                    containerManager.renameContainer();
                    break;
                case "move":
                    containerManager.moveContainer();
                    break;
                case "create":
                    containerManager.createContainer();
                    break; 
                case "remove":
                    containerManager.removeContainer();
                    break;
            }
            
            return 1;
        } catch (IOException ex) {
            Logger.getLogger(CommandSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
}

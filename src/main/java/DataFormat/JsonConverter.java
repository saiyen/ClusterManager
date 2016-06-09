/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataFormat;

import Models.ContainerModel;
import Models.ServerModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chill
 */
public class JsonConverter implements Interfaces.DataFormatType {

    @Override
    public void convertToDataFormat(List<ServerModel> servers) {
        
        JsonObject jsonWrapper = new JsonObject();
        JsonArray jsonWrapperArray = new JsonArray();
        try {
            JsonObject allServersData = new JsonObject();

            int count = 0;
            for (ServerModel server : servers) {
                JsonArray serverPlusContainersArray = new JsonArray();
                JsonObject serverPLusContainer = new JsonObject();

                serverPLusContainer.addProperty("ip", server.getIPAddress());
                
                int containerCount = 0;
                for (ContainerModel container : server.getContainers()) {
                    JsonArray containerArray = new JsonArray();
                    JsonObject containerObject = new JsonObject();
                    containerObject.addProperty("cId", container.getContainerID());
                    containerObject.addProperty("name", container.getContainerName());
                    containerObject.addProperty("status", container.getContainerStatus());
                    containerObject.addProperty("image", container.getContainerImage());
                    containerObject.addProperty("command", container.getContainerCommand());
                    containerObject.addProperty("cType", container.getContainerType());
                    containerArray.add(containerObject);
                    serverPLusContainer.add("container" + containerCount, containerArray);
                    containerCount++;
                }
                serverPlusContainersArray.add(serverPLusContainer);
                allServersData.add("server" + count, serverPlusContainersArray);
                count++;
            }

            jsonWrapperArray.add(allServersData);
            jsonWrapper.add("servers", jsonWrapperArray);

        } catch (JsonParseException e) {
            e.getMessage();
        }

        try (FileWriter fileWriter = new FileWriter("./src/main/resources/json/Containers.json")) {
            fileWriter.write(jsonWrapper.toString());
            fileWriter.flush();
        } catch (IOException ex) {
            Logger.getLogger(JsonConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

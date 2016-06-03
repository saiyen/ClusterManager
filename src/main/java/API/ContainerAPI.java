/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import Interfaces.IContainerRunner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nl.hogeschool.ClusterManager.ListHelper;
import nl.hogeschool.ClusterManager.DockerContainerManager;
import static spark.Spark.*;
/**
 *
 * @author Ivan Ivanov
 */
public class ContainerAPI {
     public void requests() {
         
        JsonParser parser = new JsonParser();
         
        get("/getAllContainers", (req, res) -> {
            Gson gson = new Gson();
            IContainerRunner docker = new DockerContainerManager(new JsonObject());
            ListHelper.getListOfServersAndContainers().clear();
            docker.getAllContainers();
            return gson.toJson(ListHelper.getListOfServersAndContainers());
        });
        
        post("/startContainer", (request, response) -> {
            JsonObject containerInfo = parser.parse(request.body()).getAsJsonObject();
            DockerContainerManager docker = new DockerContainerManager(containerInfo);
            docker.getAllContainers();
            docker.startContainer();
            return "{\"status\": \"OK\"}";
        });
        
        post("/stopContainer", (request, response) -> {
            JsonObject containerInfo = parser.parse(request.body()).getAsJsonObject();
            DockerContainerManager docker = new DockerContainerManager(containerInfo);
            docker.getAllContainers();
            docker.stopContainer();
            return "{\"status\": \"OK\"}";
        });
        
        post("/createContainer", (request, response) -> {
            JsonObject containerInfo = parser.parse(request.body()).getAsJsonObject();
            DockerContainerManager docker = new DockerContainerManager(containerInfo);
            docker.getAllContainers();
            docker.createContainer();
            return "{\"status\": \"OK\"}";
        });
        
        post("/removeContainer", (request, response) -> {
            JsonObject containerInfo = parser.parse(request.body()).getAsJsonObject();
            DockerContainerManager docker = new DockerContainerManager(containerInfo);
            docker.getAllContainers();
            docker.removeContainer();
            return "{\"status\": \"OK\"}";
        });
        
        post("/moveContainer", "application/json", (request, response) -> {
            JsonObject containerInfo = parser.parse(request.body()).getAsJsonObject();
            DockerContainerManager docker = new DockerContainerManager(containerInfo);
            docker.getAllContainers();
            docker.moveContainer();
            return "{\"status\": \"OK\"}";
        });
        
        post("/renameContainer", "application/json", (request, response) -> {
            JsonObject containerInfo = parser.parse(request.body()).getAsJsonObject();
            DockerContainerManager docker = new DockerContainerManager(containerInfo);
            docker.getAllContainers();
            docker.renameContainer();
            return "{\"status\": \"OK\"}";
        });
    }
}

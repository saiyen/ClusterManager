/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import Interfaces.IContainerRunner;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import nl.hogeschool.ClusterManager.AddToList;
import nl.hogeschool.ClusterManager.Client;
import nl.hogeschool.ClusterManager.DockerManager;
import static spark.Spark.*;
/**
 *
 * @author Ivan Ivanov
 */
public class JsonAPI {
     public void requests() {
        get("/getAllContainers", (req, res) -> {
            IContainerRunner docker = new DockerManager(new JsonObject());
            docker.getAllContainers();
            Gson gson = new Gson();
            return gson.toJson(AddToList.getListOfServersAndContainers());
        });
        
        post("/startContainer", (request, response) -> {
            JsonParser parser = new JsonParser();
            JsonObject containerInfo = parser.parse(request.body()).getAsJsonObject();
            DockerManager docker = new DockerManager(containerInfo);
            docker.getAllContainers();
            docker.startContainer();
            return "{\"status\": \"OK\"}";
        });
        
        post("/stopContainer", (request, response) -> {
            JsonParser parser = new JsonParser();
            JsonObject containerInfo = parser.parse(request.body()).getAsJsonObject();
            DockerManager docker = new DockerManager(containerInfo);
            docker.getAllContainers();
            docker.stopContainer();
            return "{\"status\": \"OK\"}";
        });
        
        post("/createContainer", (request, response) -> {
            JsonParser parser = new JsonParser();
            JsonObject containerInfo = parser.parse(request.body()).getAsJsonObject();
            DockerManager docker = new DockerManager(containerInfo);
            docker.getAllContainers();
            docker.createContainer();
            return "{\"status\": \"OK\"}";
        });
        
        post("/removeContainer", (request, response) -> {
            JsonParser parser = new JsonParser();
            JsonObject containerInfo = parser.parse(request.body()).getAsJsonObject();
            DockerManager docker = new DockerManager(containerInfo);
            docker.getAllContainers();
            docker.removeContainer();
            return "{\"status\": \"OK\"}";
        });
        
        post("/moveContainer", "application/json", (request, response) -> {
            JsonParser parser = new JsonParser();
            JsonObject containerInfo = parser.parse(request.body()).getAsJsonObject();
            DockerManager docker = new DockerManager(containerInfo);
            docker.getAllContainers();
            docker.moveContainer();
            return "{\"status\": \"OK\"}";
        });
    }
     
    public static JsonObject getJSONObjectFromFile() throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(".\\src\\main\\resources\\json\\ContainersFromAPI.json"));

        JsonElement JSONelement = new JsonParser().parse(reader);
        JsonObject jObject = JSONelement.getAsJsonObject();
        String result = jObject.get("id").getAsString();
        return jObject;
    }
}

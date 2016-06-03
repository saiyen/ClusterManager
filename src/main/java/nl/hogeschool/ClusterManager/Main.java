package nl.hogeschool.ClusterManager;

import API.ContainerAPI;
import Connection.SSHConnection;
import Logger.LoggerSetup;
import ReadConfig.ReadConfig;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Main {
    
    public static void main(String[] Args) throws InterruptedException {   
        
        try {
            LoggerSetup.setup();
            ReadConfig.getConfigProperties();
            ReadConfig.getConnections();
            SSHConnection.makeConnections();
            
            ListHelper listHelper = new ListHelper();
            JsonObject container = new JsonObject();
            DockerContainerManager docker = new DockerContainerManager(container);
            docker.getAllContainers();
            Client.sendToAPI("JSONObject", listHelper.getListOfServersAndContainers());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } 
    }
    
    public static JsonObject getJSONObjectFromFile() throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(".\\src\\main\\resources\\json\\ContainersFromAPI.json"));
        String result = jobject.get("id").getAsString();
        return jobject;
    }
}

package nl.hogeschool.ClusterManager;

import Interfaces.IContainerRunner;
import Models.ServerModel;
import Connection.SSHConnection;
import Logger.MyLogger;
import ReadConfig.ReadConfig;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class Main {
    
    public static void main(String[] Args) throws InterruptedException {   
        
        try {
            MyLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }
        
        try {
            ReadConfig read = new ReadConfig();
            read.getConfigProperties();
            SSHConnection.makeConnections();
            List<ServerModel> listOfServersWithContainers = AddToList.getListOfServersAndContainers();
            // Create a JSONObject from the file by calling the getFromAPI method that's defined in the Client class
            JsonObject container = getJSONObjectFromFile();
            // Create new DockerManager Object, the object gets a JSONObject which contains information about the container and a List of servers to get it's IP address
            IContainerRunner Docker = new DockerManager(container);
            // Get each server with their containers to the server
           
            Docker.getAllContainers();
            // Docker.moveContainer();
            // Send each server with their containers to the server
            Client.sendToAPI("JSONObject", listOfServersWithContainers);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } 
    }

    public static JsonObject getJSONObjectFromFile() throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(".\\src\\main\\resources\\json\\ContainersFromAPI.json"));

        JsonElement JSONelement = new JsonParser().parse(reader);
        JsonObject jobject = JSONelement.getAsJsonObject();
        String result = jobject.get("id").getAsString();
        return jobject;
    }
}

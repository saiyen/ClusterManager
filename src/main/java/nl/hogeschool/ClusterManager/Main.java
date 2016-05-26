package nl.hogeschool.ClusterManager;

import API.GETRequests;
import Connection.SSHConnection;
import ReadConfig.ReadConfig;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    public static void main(String[] Args) {
        try {
            ReadConfig read = new ReadConfig();
            read.getConfigProperties();

            SSHConnection.makeConnections();

            Docker.getAllContainers();
            JsonObject container = getJSONObjectFromFile();
            Docker.moveContainer(container);
            Docker.renameContainer(container);
        } catch (Exception e) {
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

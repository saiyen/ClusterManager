package nl.hogeschool.ClusterManager;

import API.GETRequests;
import Connection.SSHConnection;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    public static void main(String[] Args) {
//        try {            
//            SSHConnection.makeConnections();
//
//            Docker.getAllContainers();
//            JsonObject container = getJSONObjectFromFile();
//            Docker.moveContainer(container);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
        
        //For the test Sayien http://localhost:4567/hello
        GETRequests hello = new GETRequests();
        hello.getContainers();
    }

    public static JsonObject getJSONObjectFromFile() throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader("C:\\Users\\chill\\Desktop\\JSONObjects\\GETFromAPI\\ContainersFromAPI.json"));

        JsonElement JSONelement = new JsonParser().parse(reader);
        JsonObject jobject = JSONelement.getAsJsonObject();
        String result = jobject.get("id").getAsString();
        return jobject;
    }
}

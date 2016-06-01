package nl.hogeschool.ClusterManager;

import API.JsonAPI;
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
            ReadConfig read = new ReadConfig();
            read.getConfigProperties();
            read.getConnections();
            SSHConnection.makeConnections();
            
            //API
            JsonAPI api = new JsonAPI();
            api.requests();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } 
    }
}

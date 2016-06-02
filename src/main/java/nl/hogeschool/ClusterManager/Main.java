package nl.hogeschool.ClusterManager;

import API.ContainerAPI;
import Connection.SSHConnection;
import Logger.MyLogger;
import ReadConfig.ReadConfig;
import java.io.IOException;


public class Main {
    
    public static void main(String[] Args) throws InterruptedException {   
        
        try {
            MyLogger.setup();
            ReadConfig.getConfigProperties();
            ReadConfig.getConnections();
            SSHConnection.makeConnections();
            
            //API
            ContainerAPI api = new ContainerAPI();
            api.requests();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } 
    }
}

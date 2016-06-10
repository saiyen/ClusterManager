package nl.hogeschool.ClusterManager;

import Connection.SSHConnection;
import Logger.LoggerSetup;
import Readers.ReadConfig;
import com.google.gson.JsonObject;
import java.io.IOException;
import API.APICore;


public class Main {
    
    public static void main(String[] Args) throws InterruptedException {   
        
        APICore api = new APICore();
        
        api.requests();
        
        try {
            LoggerSetup.setup();
            ReadConfig.getConfigProperties();
            ReadConfig.getConnections();
            SSHConnection.makeConnections();
            
            // API should use this to create a ContainerManager
                SystemAdministrator systemAdministrator1 = new SystemAdministrator("Docker");
                systemAdministrator1.containerManager.getAllContainers();
                systemAdministrator1.setDataFormatStrategy(new DataFormat.JsonConverter());
                systemAdministrator1.useStrategyToFormatData(ListHelper.getListOfServersAndContainers());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } 
    }
    
//    public static JsonObject getJSONObjectFromFile() throws FileNotFoundException {
//        JsonReader reader = new JsonReader(new FileReader(".\\src\\main\\resources\\json\\ContainersFromAPI.json"));
//        String result = jobject.get("id").getAsString();
//        return jobject;
//    }
}

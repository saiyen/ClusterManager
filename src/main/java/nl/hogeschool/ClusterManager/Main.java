package nl.hogeschool.ClusterManager;

import Connection.SSHConnection;
import Logger.LoggerSetup;
import Readers.ReadConfig;
import com.google.gson.JsonObject;
import java.io.IOException;
import API.APICore;
import ContainerManager.ContainerManagerFactory;
import Interfaces.ContainerManager;

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
            ContainerManagerFactory containerManagerFactory = new ContainerManagerFactory();
            ContainerManager containerManager = containerManagerFactory.getContainerManager("Docker");
            containerManager.getAllContainers();
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

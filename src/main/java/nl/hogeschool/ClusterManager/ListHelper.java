package nl.hogeschool.ClusterManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import Models.ContainerModel;
import Models.ServerModel;
import java.util.logging.Logger;

public class ListHelper {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static List<ServerModel> ALL_SERVERS = new ArrayList<>();
    private static List<ContainerModel> CONTAINERS;

    public static void addOutputToList(InputStream executedCommando, String server_IP, String containerType) throws IOException {
        CONTAINERS = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(executedCommando));
        String line;

        // Read the output given by the execute command per line
        // Skip the first line
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            // Divide each line per , or " " to split by Id, Name etc

            String[] elements = line.split("\\s{2,}");
            
            if(elements.length == 6) {
                String current_ID = elements[0];
                String current_image = elements[1];
                String current_cmd = elements[2];
                String current_runtime = elements[3];
                String current_status = elements[4];
                String current_port = "";
                String current_name = elements[5];
                String current_containerType = containerType;
                
                // Save these values in the class Server which has all the containers
                CONTAINERS.add(new ContainerModel(current_ID, current_name, current_status, current_image, current_cmd, current_containerType, current_port));
            }

            if(elements.length == 7) {
                String current_ID = elements[0];
                String current_image = elements[1];
                String current_cmd = elements[2];
                String current_runtime = elements[3];
                String current_status = elements[4];
                String current_port = elements[5];
                String current_name = elements[6];
                String current_containerType = containerType;
                
                // Save these values in the class Server which has all the containers
                CONTAINERS.add(new ContainerModel(current_ID, current_name, current_status, current_image, current_cmd, current_containerType, current_port));
            }
            
        }
        ALL_SERVERS.add(new ServerModel(server_IP, CONTAINERS));
    }
    
    public static List<ServerModel> getListOfServersAndContainers() throws IOException {
        if (ALL_SERVERS.isEmpty()) {
            LOGGER.warning("The list of servers is empty");
        }
        return ALL_SERVERS;
    }
}

package nl.hogeschool.ClusterManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import Models.ContainerModel;
import java.util.logging.Logger;

public class ListHelper {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static List<ContainerModel> CONTAINERS;

    public static void getContainers(InputStream executedCommando) throws IOException {
        CONTAINERS = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(executedCommando));
        String line;
        
        // Read the output given by the execute command per line
        // Skip the first line
        reader.readLine();
        while ((line = reader.readLine()) != null) {
        // Divide each line per , or " " to split by Id, Name etc

            String[] elements = line.split("\\s{2,}");

            // Save these values in the class Server which has all the containers
            //CONTAINER ID, IMAGE, COMMAND, CREATED, STATUS, PORTS, NAMES
            if(elements.length == 6)
                CONTAINERS.add(new ContainerModel(elements[0], elements[1], elements[2], elements[3], elements[4], "", elements[5]));
            
            if(elements.length == 7)
                CONTAINERS.add(new ContainerModel(elements[0], elements[1], elements[2], elements[3], elements[4], elements[5], elements[6]));
        }
    }
    
    public static List<ContainerModel> getListOfContainers() throws IOException{
        if(CONTAINERS.isEmpty()){
            LOGGER.warning("The list of containers is empty");
        }
        
        return CONTAINERS;
    }
}

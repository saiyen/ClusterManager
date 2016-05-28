package Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import Models.ContainerModel;
import Models.ServerModel;

public class CommandOutputReader {

    public static List<ServerModel> allServers = new ArrayList<>();
    public static List<ContainerModel> containers;

    public static void addOutputToList(InputStream is, String containerEngineType, String server_IP) throws IOException {
        containers = new ArrayList<>();
        Reader inputStreamReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;

        // Read the output given by the execute command per line
        // Skip the first line
        bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
        // Divide each line per , or " " to split by Id, Name etc

            String[] elements = line.split("\\s{2,}");
            String current_IP = server_IP;
            String current_ID = elements[0];
            String current_image = elements[1];
            String current_cmd = elements[2];
            String current_runtime = elements[3];
            String current_status = elements[4];
            String current_name = elements[5];
            String current_containerType = containerEngineType;

            // Save these values in the class Server which has all the containers
            containers.add(new ContainerModel(current_ID, current_name, current_status, current_image, current_containerType));

            allServers.add(new ServerModel(current_IP, containers));

            System.out.println("Container " + containers.toString() + " has been added to the server: " + current_IP);
        }
    }
}

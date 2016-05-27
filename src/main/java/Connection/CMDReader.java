package Connection;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import Models.ContainerModel;
import Models.ServerModel;

public class CMDReader {
    public static List<ServerModel> servers = new ArrayList<>();
    public static List<ContainerModel> containers;

    public static void addCmdToList(InputStream is, String returnOutput, String server_IP) throws IOException {
        containers = new ArrayList<>();
        Reader inputStreamReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;

        // Read the output given by the execute command per line
        switch (returnOutput) {
            case "Docker getAllContainers":
//                Skip the first line
                bufferedReader.readLine();
                while ((line = bufferedReader.readLine()) != null) {
//                  Divide each line per , or " " to split by Id, Name etc

                    int index = returnOutput.indexOf(" ");

                    String[] elements = line.split("\\s{2,}");
                    String current_IP = server_IP;
                    String current_ID = elements[0];
                    String current_image = elements[1];
                    String current_cmd = elements[2];
                    String current_runtime = elements[3];
                    String current_status = elements[4];
                    String current_name = elements[5];
                    
                    String current_containerType = returnOutput.substring(0, index);         

//                  Save these values in the class Server which has all the containers
                    containers.add(new ContainerModel(current_ID, current_name, current_status, current_image, current_containerType));       
                    
                    servers.add(new ServerModel(current_IP, containers));
                    //convertObjectsToJson(servers);
                }
                
                System.out.println("Server " + servers.get(0).getIPAddress() + " has this much containers:\n" + servers.get(0).getContainers().get(0).getContainerName());
                break;
            case "Docker start":
                
                break;
            default:
                break;
        }
    }
}

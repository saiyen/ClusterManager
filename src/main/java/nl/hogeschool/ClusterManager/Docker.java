package nl.hogeschool.ClusterManager;

import Models.ServerModel;
import Models.ContainerModel;
import Connection.Execute;
import Connection.SFTPConnection;
import Connection.SSHConnection;
import Connection.StreamReader;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import net.schmizz.sshj.SSHClient;

public class Docker {

    private static String server_IP;
    private static String container_ID;
    private static String destination_IP;
    private static List<ServerModel> listOfServersWithContainers = StreamReader.servers;

    public static void startContainer(JsonObject container) throws IOException {
        container_ID = container.get("id").getAsString();
        server_IP = getServerIPFromContainerID(container_ID);
        Execute execute = new Execute();
        execute.executeCommand(server_IP, "docker start " + container_ID, "Docker start");
    }

    public static void stopContainer(JsonObject container) throws IOException {
        container_ID = container.get("id").getAsString();
        server_IP = getServerIPFromContainerID(container_ID);
        Execute execute = new Execute();
        execute.executeCommand(server_IP, "docker stop " + container_ID, "Docker stop");
    }

    public static void moveContainer(JsonObject container) throws IOException {
        container_ID = container.get("id").getAsString();
        server_IP = getServerIPFromContainerID(container_ID);
        destination_IP = container.get("extra").getAsString();
        SFTPConnection sftpTransfer = new SFTPConnection();

        Execute execute = new Execute();
        //execute.executeCommand(server_IP, "docker export " + container_ID + " > " + container_ID + ".tar", "Docker move");
        //execute.executeCommand(server_IP, "chmod 700 " + container_ID + ".tar", "setPermission");

        sftpTransfer.downloadFile(server_IP, container_ID);
        sftpTransfer.uploadFile(destination_IP, container_ID);
    }

    public static void getAllContainers() throws IOException, InterruptedException {
        HashMap<String, SSHClient> listOfClients = SSHConnection.getListOfClients();

        for (Entry<String, SSHClient> client : listOfClients.entrySet()) {
            Execute execute = new Execute();
            execute.executeCommand(client.getKey(), "docker ps -a", "Docker getAllContainers");
            System.out.println("Docker ps -a is uitgevoerd op de volgende server: " + client.getKey());
        }
    }

    public static String getServerIPFromContainerID(String containerID) {
        for (ServerModel server : listOfServersWithContainers) {
            for (ContainerModel container : server.getContainers()) {
                if (container.getContainerID().contains(containerID)) {
                    server_IP = server.getIPAddress();
                }
            }
        }
        return server_IP;
    }
}

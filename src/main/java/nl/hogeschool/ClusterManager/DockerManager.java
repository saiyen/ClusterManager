package nl.hogeschool.ClusterManager;

import Interfaces.IContainerRunner;
import Models.ServerModel;
import Models.ContainerModel;
import Connection.Execute;
import Connection.SFTPConnection;
import Connection.SSHConnection;
import Connection.CommandOutputReader;
import Models.SSHClientWrapperModel;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class DockerManager implements IContainerRunner {
    private static String server_IP;
    private static String container_ID;
    private static String destination_IP;
    private final JsonObject container;
    private final List<ServerModel> listOfServersWithContainers = CommandOutputReader.allServers;
    
    public DockerManager(JsonObject container) {
        this.container = container;
    }

    @Override
    public void startContainer() throws IOException {
        container_ID = container.get("id").getAsString();
        server_IP = getIPFromContainerID(container_ID);
        Execute.executeCommand(server_IP, "docker start " + container_ID, "Docker start");
    }

    @Override
    public void stopContainer() throws IOException {
        container_ID = container.get("id").getAsString();
        server_IP = getIPFromContainerID(container_ID);
        Execute.executeCommand(server_IP, "docker stop " + container_ID, "Docker stop");
    }
    
    @Override
    public void removeContainer() throws IOException {
        container_ID = container.get("id").getAsString();
        server_IP = getIPFromContainerID(container_ID);
        Execute.executeCommand(server_IP, "docker rm " + container_ID, "Docker remove");
    }

    @Override
    public void moveContainer() throws IOException {
        container_ID = container.get("id").getAsString();
        server_IP = getIPFromContainerID(container_ID);
        destination_IP = container.get("extra").getAsString();
        SFTPConnection sftpTransfer = new SFTPConnection();

        sftpTransfer.downloadFile(server_IP, container_ID);
        sftpTransfer.uploadFile(destination_IP, container_ID);
    }
    
    @Override
    public void renameContainer() throws IOException{
        container_ID = container.get("id").getAsString();
        server_IP = getIPFromContainerID(container_ID);
        String newName = container.get("extra").getAsString();
        Execute execute = new Execute();
        execute.executeCommand(server_IP, "docker rename "+container_ID+" "+newName, "Docker rename"); 
    }

    public void getAllContainers() throws IOException, InterruptedException {
        HashMap<String, SSHClientWrapperModel> listOfClients = SSHConnection.getListOfClients();

        for (Entry<String, SSHClientWrapperModel> client : listOfClients.entrySet()) {
            Execute execute = new Execute();
            execute.executeCommand(client.getKey(), "docker ps -a", "Docker getAllContainers");
        }
    }

    public String getIPFromContainerID(String containerID) {
        for (ServerModel server : listOfServersWithContainers) {
            for (ContainerModel theContainer : server.getContainers()) {
                if (theContainer.getContainerID().contains(containerID)) {
                    server_IP = server.getIPAddress();
                }
            }
        }
        return server_IP;
    }
}

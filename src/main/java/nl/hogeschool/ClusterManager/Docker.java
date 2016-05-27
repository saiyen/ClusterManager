package nl.hogeschool.ClusterManager;

import Interfaces.IContainerRunner;
import Models.ServerModel;
import Models.ContainerModel;
import Connection.ExecuteCMD;
import Connection.SFTPConnection;
import Connection.SSHConnection;
import Connection.CMDReader;
import Models.SSHClientWrapperModel;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Docker implements IContainerRunner {
    private static String server_IP;
    private static String container_ID;
    private static String destination_IP;
    private final JsonObject container;
    private List<ServerModel> listOfServersWithContainers = CMDReader.servers;
    
    public Docker(JsonObject container, List<ServerModel>listOfServersWithContainers) {
        this.container = container;
        this.listOfServersWithContainers = listOfServersWithContainers;
    }

    @Override
    public void startContainer() throws IOException {
        container_ID = container.get("id").getAsString();
        server_IP = getIPFromContainerID(container_ID);
        ExecuteCMD execute = new ExecuteCMD();
        execute.executeCommand(server_IP, "docker start " + container_ID, "Docker start");
    }

    @Override
    public void stopContainer() throws IOException {
        container_ID = container.get("id").getAsString();
        server_IP = getIPFromContainerID(container_ID);
        ExecuteCMD execute = new ExecuteCMD();
        execute.executeCommand(server_IP, "docker stop " + container_ID, "Docker stop");
    }

    @Override
    public void moveContainer() throws IOException {
        container_ID = container.get("id").getAsString();
        server_IP = getIPFromContainerID(container_ID);
        destination_IP = container.get("extra").getAsString();
        SFTPConnection sftpTransfer = new SFTPConnection();

        ExecuteCMD execute = new ExecuteCMD();
        //execute.executeCommand(server_IP, "docker export " + container_ID + " > " + container_ID + ".tar", "Docker move");
        //execute.executeCommand(server_IP, "chmod 700 " + container_ID + ".tar", "setPermission");

        sftpTransfer.downloadFile(server_IP, container_ID);
        sftpTransfer.uploadFile(destination_IP, container_ID);
    }
    
    @Override
    public void renameContainer() throws IOException{
        container_ID = container.get("id").getAsString();
        server_IP = getIPFromContainerID(container_ID);
        String newName = container.get("extra").getAsString();
        ExecuteCMD execute = new ExecuteCMD();
        execute.executeCommand(server_IP, "docker rename "+container_ID+" "+newName, "Docker rename"); 
    }


    public void getAllContainers() throws IOException, InterruptedException {
        HashMap<String, SSHClientWrapperModel> listOfClients = SSHConnection.getListOfClients();

        for (Entry<String, SSHClientWrapperModel> client : listOfClients.entrySet()) {
            ExecuteCMD execute = new ExecuteCMD();
            execute.executeCommand(client.getKey(), "docker ps -a", "Docker getAllContainers");
            System.out.println("Docker ps -a is uitgevoerd op de volgende server: " + client.getKey());
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

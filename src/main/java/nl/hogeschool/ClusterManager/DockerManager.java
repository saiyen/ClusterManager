package nl.hogeschool.ClusterManager;

import Interfaces.IContainerRunner;
import Models.ServerModel;
import Models.ContainerModel;
import Connection.Execute;
import Connection.SFTPConnection;
import Connection.SSHConnection;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import net.schmizz.sshj.SSHClient;

public class DockerManager implements IContainerRunner {
    private static String server_IP;
    private static String container_ID;
    private static String imageName;
    private static String containerType;
    private static String destination_IP;
    private final JsonObject container;
    private final List<ServerModel> listOfServersWithContainers = AddToList.allServers;
    
    public DockerManager(JsonObject container) {
        this.container = container;
        this.imageName = container.get("containerImage").getAsString();
        this.containerType = container.get("type").getAsString();
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
        String newContainerLocation = Tools.searchUploadPath(destination_IP).getUploadPath().concat(container_ID+".tar");
        String containerExportLocation = "/home/DockerContainers/".concat(container_ID)+".tar";
        String containerImportLocation = "/home/ubuntu-0862420/DockerContainers/".concat(container_ID)+".tar";
        
        SFTPConnection sftpTransfer = new SFTPConnection();

        // Export container to tar file in the ...
        Execute.executeCommand(server_IP, "docker export --output=\""+containerExportLocation+"\" "+container_ID, "Docker Move");
        sftpTransfer.downloadFile(server_IP, container_ID);
        sftpTransfer.uploadFile(destination_IP, container_ID);
        Execute.executeCommand(destination_IP, "cat "+containerImportLocation+" | docker import - "+container_ID+":new", "Docker Move"); 
    }
    
     public void createContainer() throws IOException{
         
     }
    
    @Override
    public void renameContainer() throws IOException{
        container_ID = container.get("id").getAsString();
        server_IP = getIPFromContainerID(container_ID);
        String newName = container.get("extra").getAsString();
        Execute.executeCommand(server_IP, "docker rename "+container_ID+" "+newName, "Docker rename"); 
    }

    @Override
    public void getAllContainers() throws IOException, InterruptedException {
        HashMap<String, SSHClient> listOfClients = SSHConnection.getListOfClients();

        for (Entry<String, SSHClient> client : listOfClients.entrySet()) {
            String tempServerIP = client.getKey();
            InputStream resultOfExecute = Execute.executeCommand(tempServerIP, "docker ps -a", "Docker getAllContainers");
            AddToList.addOutputToList(resultOfExecute, tempServerIP, containerType);
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

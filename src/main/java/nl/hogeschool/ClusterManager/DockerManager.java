package nl.hogeschool.ClusterManager;

import Interfaces.IContainerRunner;
import Models.ServerModel;
import Models.ContainerModel;
import Connection.Execute;
import Connection.SFTPConnection;
import Connection.SSHConnection;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.schmizz.sshj.SSHClient;

public class DockerManager implements IContainerRunner {
    private static String server_IP;
    private static String container_ID;
    private static String imageName;
    private static String containerType;
    private static String destination_IP;
    private final JsonObject container;
    private List<ServerModel> listOfServersWithContainers = null;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public DockerManager(JsonObject container) {
        this.container = container;
        this.imageName = container.get("containerImage").getAsString();
        this.containerType = container.get("type").getAsString();
        
    }

    @Override
    public void startContainer() throws IOException {
        try {
            container_ID = container.get("id").getAsString();
            server_IP = getIPFromContainerID(container_ID);
            Execute.executeCommand(server_IP, "docker start " + container_ID);
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stopContainer() throws IOException {
        try {
            container_ID = container.get("id").getAsString();
            server_IP = getIPFromContainerID(container_ID);
            Execute.executeCommand(server_IP, "docker stop " + container_ID);
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void removeContainer() throws IOException {
        try {
            container_ID = container.get("id").getAsString();
            server_IP = getIPFromContainerID(container_ID);
            Execute.executeCommand(server_IP, "docker rm " + container_ID);
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void moveContainer() throws IOException {
        try {
            container_ID = container.get("id").getAsString();
            server_IP = getIPFromContainerID(container_ID);
            destination_IP = container.get("extra").getAsString();
            String containerExportLocation = "/home/DockerContainers/".concat(container_ID) + ".tar";
            String containerImportLocation = "/home/ubuntu-0862420/DockerContainers/".concat(container_ID) + ".tar";

            SFTPConnection sftpTransfer = new SFTPConnection();

            // Export container to tar file in the ...
            Execute.executeCommand(server_IP, "docker export --output=\"" + containerExportLocation + "\" " + container_ID);
            sftpTransfer.downloadFile(server_IP, container_ID);
            sftpTransfer.uploadFile(destination_IP, container_ID);
            Execute.executeCommand(destination_IP, "cat " + containerImportLocation + " | docker import - " + container_ID + ":new");
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
    }
    
     public void createContainer() throws IOException{
         
     }
    
    @Override
    public void renameContainer() throws IOException{
        container_ID = container.get("id").getAsString();
        server_IP = getIPFromContainerID(container_ID);
        String newName = container.get("extra").getAsString();
        try { 
            Execute.executeCommand(server_IP, "docker rename "+container_ID+" "+newName);
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void getAllContainers() throws IOException, InterruptedException {
        HashMap<String, SSHClient> listOfClients = SSHConnection.getListOfClients();

        for (Entry<String, SSHClient> client : listOfClients.entrySet()) {
            String tempServerIP = client.getKey();
            InputStream resultOfExecute = Execute.executeCommand(tempServerIP, "docker ps -a");
            AddToList.addOutputToList(resultOfExecute, tempServerIP);
        }
    }

    public String getIPFromContainerID(String containerID) {
        try {
            listOfServersWithContainers = AddToList.getListOfServersAndContainers();
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
        
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

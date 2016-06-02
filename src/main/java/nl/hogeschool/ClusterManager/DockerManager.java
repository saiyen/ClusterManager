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
            String oldContainerLocation;
            String newContainerLocation;
                    
            if(Tools.searchUploadPath(server_IP) == null){
                LOGGER.warning("Can not find the server");
                return;
            } else {
                oldContainerLocation = Tools.searchUploadPath(server_IP).getUploadPath().concat(container_ID +".tar");
            }
            
            if(Tools.searchUploadPath(destination_IP) == null){
                LOGGER.warning("Can not find the server");
                return;
            } else {
                newContainerLocation = Tools.searchUploadPath(destination_IP).getUploadPath().concat(container_ID +".tar");
            }

            SFTPConnection sftpTransfer = new SFTPConnection();

            // Export container to tar file in the ...
            Execute.executeCommand(server_IP, "docker export --output=\"" + oldContainerLocation + "\" " + container_ID);
            sftpTransfer.downloadFile(server_IP, container_ID);
            sftpTransfer.uploadFile(destination_IP, container_ID);
            Execute.executeCommand(destination_IP, "cat " + newContainerLocation + " | docker import - " + container_ID + ":new");
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
    }
    
    //@Override
     public void createContainer() throws IOException{
        /*String newName = "";
        container_Name = container.get("name").getAsString();
        server_IP = getIPFromContainerID(container_Name);
        
        if(container_Name.equals(newName)){
            System.out.println("This name does already exist! Insert a new name.");
        }
        
        else{
            System.out.println(container);
            //Execute.executeCommand(server_IP, "docker run" "IMAGE");
        }*/
        server_IP = getIPFromContainerID(container_ID);
        destination_IP = container.get("extra").getAsString();
        try { 
            Execute.executeCommand(server_IP, "Hello-world " + "docker run");
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        AddToList.getListOfServersAndContainers().clear();
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

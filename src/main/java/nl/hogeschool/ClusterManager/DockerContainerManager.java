package nl.hogeschool.ClusterManager;

import Interfaces.IContainerRunner;
import Models.ServerModel;
import Models.ContainerModel;
import Connection.ExecuteCommad;
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

public class DockerContainerManager implements IContainerRunner {
    private static String SERVER_IP;
    private static String CONTAINER_IP;
    private static String IMAGE_NAME;
    private static String CONTAINER_TYPE;
    private static String DESTINATION_IP;
    private final JsonObject container;
    private List<ServerModel> listOfServersWithContainers = null;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public DockerContainerManager(JsonObject container) {
        this.container = container;
    }

    @Override
    public void startContainer() throws IOException {
        try {
            CONTAINER_IP = container.get("id").getAsString();
            SERVER_IP = getIPFromContainerID(CONTAINER_IP);
            ExecuteCommad.execute(SERVER_IP, "docker start " + CONTAINER_IP);
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerContainerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stopContainer() throws IOException {
        try {
            CONTAINER_IP = container.get("id").getAsString();
            SERVER_IP = getIPFromContainerID(CONTAINER_IP);
            ExecuteCommad.execute(SERVER_IP, "docker stop " + CONTAINER_IP);
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerContainerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void removeContainer() throws IOException {
        try {
            CONTAINER_IP = container.get("id").getAsString();
            SERVER_IP = getIPFromContainerID(CONTAINER_IP);
            ExecuteCommad.execute(SERVER_IP, "docker rm " + CONTAINER_IP);
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerContainerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void moveContainer() throws IOException {
        try {
            CONTAINER_IP = container.get("id").getAsString();
            SERVER_IP = getIPFromContainerID(CONTAINER_IP);
            DESTINATION_IP = container.get("extra").getAsString();
            String oldContainerLocation;
            String newContainerLocation;
                    
            if(Tools.searchUploadPath(SERVER_IP) == null){
                LOGGER.warning("Can not find the server");
                return;
            } else {
                oldContainerLocation = Tools.searchUploadPath(SERVER_IP).getUploadPath().concat(CONTAINER_IP +".tar");
            }
            
            if(Tools.searchUploadPath(DESTINATION_IP) == null){
                LOGGER.warning("Can not find the server");
                return;
            } else {
                newContainerLocation = Tools.searchUploadPath(DESTINATION_IP).getUploadPath().concat(CONTAINER_IP +".tar");
            }

            SFTPConnection sftpTransfer = new SFTPConnection();

            // Export container to tar file in the ...
            ExecuteCommad.execute(SERVER_IP, "docker export --output=\"" + oldContainerLocation + "\" " + CONTAINER_IP);
            sftpTransfer.downloadFile(SERVER_IP, CONTAINER_IP);
            sftpTransfer.uploadFile(DESTINATION_IP, CONTAINER_IP);
            ExecuteCommad.execute(DESTINATION_IP, "cat " + newContainerLocation + " | docker import - " + CONTAINER_IP + ":new");
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
    }
    
    public void createContainer() throws IOException{

    }
    
    @Override
    public void renameContainer() throws IOException{
        CONTAINER_IP = container.get("id").getAsString();
        SERVER_IP = getIPFromContainerID(CONTAINER_IP);
        String newName = container.get("extra").getAsString();
        try { 
            ExecuteCommad.execute(SERVER_IP, "docker rename "+CONTAINER_IP+" "+newName);
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerContainerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void getAllContainers() throws IOException, InterruptedException {
        HashMap<String, SSHClient> listOfClients = SSHConnection.getListOfClients();

        ListHelper.getListOfServersAndContainers().clear();
        for (Entry<String, SSHClient> client : listOfClients.entrySet()) {
            String tempServerIP = client.getKey();
            InputStream resultOfExecute = ExecuteCommad.execute(tempServerIP, "docker ps -a");
            ListHelper.addOutputToList(resultOfExecute, tempServerIP);
        }
    }

    public String getIPFromContainerID(String containerID) {
        try {
            listOfServersWithContainers = ListHelper.getListOfServersAndContainers();
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
        
        for (ServerModel server : listOfServersWithContainers) {
            for (ContainerModel theContainer : server.getContainers()) {
                if (theContainer.getContainerID().contains(containerID)) {
                    SERVER_IP = server.getIPAddress();
                }
            }
        }

        return SERVER_IP;
        
    }
}

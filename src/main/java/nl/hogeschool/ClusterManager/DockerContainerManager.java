package nl.hogeschool.ClusterManager;

import Interfaces.IContainerRunner;
import Models.ServerModel;
import Models.ContainerModel;
import Connection.ExecuteCommand;
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
    private final JsonObject container;
    private List<ServerModel> listOfServersWithContainers = null;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public DockerContainerManager(JsonObject container) {
        this.container = container;
    }

    @Override
    public void startContainer() throws IOException {
        try {
            String container_id = container.get("id").getAsString();
            String server_ip = getIPFromContainerID(container_id);
            ExecuteCommand.execute(server_ip, "docker start " + container_id);
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerContainerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stopContainer() throws IOException {
        try {
            String container_id = container.get("id").getAsString();
            String server_ip = getIPFromContainerID(container_id);
            ExecuteCommand.execute(server_ip, "docker stop " + container_id);
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerContainerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void removeContainer() throws IOException {
        try {
            String container_id = container.get("id").getAsString();
            String server_ip = getIPFromContainerID(container_id);
            ExecuteCommand.execute(server_ip, "docker rm " + container_id);
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerContainerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void moveContainer() throws IOException {
        try {
            String container_id = container.get("id").getAsString();
            String home_ip = getIPFromContainerID(container_id);
            String destination_ip = container.get("destinationIp").getAsString();
            String oldContainerLocation;
            String newContainerLocation;
                    
            if(Tools.searchUploadPath(home_ip) == null){
                LOGGER.warning("Can not find the server");
                return;
            } else {
                oldContainerLocation = Tools.searchUploadPath(home_ip).getUploadPath().concat(container_id +".tar");
            }
            
            if(Tools.searchUploadPath(destination_ip) == null){
                LOGGER.warning("Can not find the server");
                return;
            } else {
                newContainerLocation = Tools.searchUploadPath(destination_ip).getUploadPath().concat(container_id +".tar");
            }

            SFTPConnection sftpTransfer = new SFTPConnection();

            // Export container to tar file in the ...
            ExecuteCommand.execute(home_ip, "docker export --output=\"" + oldContainerLocation + "\" " + container_id);
            sftpTransfer.downloadFile(home_ip, container_id);
            sftpTransfer.uploadFile(destination_ip, container_id);
            ExecuteCommand.execute(destination_ip, "cat " + newContainerLocation + " | docker import - " + container_id + ":new");
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
    }
    
    @Override
    public void createContainer() throws IOException {
        String destination_ip = container.get("destinationIp").getAsString();
        String image = container.get("image").getAsString();
        try { 
            ExecuteCommand.execute(destination_ip, "docker run " + image);
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerContainerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void renameContainer() throws IOException{
        String container_id = container.get("id").getAsString();
        String server_ip = getIPFromContainerID(container_id);
        String newName = container.get("extra").getAsString();
        try { 
            ExecuteCommand.execute(server_ip, "docker rename "+container_id+" "+newName);
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
            InputStream resultOfExecute = ExecuteCommand.execute(tempServerIP, "docker ps -a");
            ListHelper.addOutputToList(resultOfExecute, tempServerIP);
        }
    }

    public String getIPFromContainerID(String containerID) {
        try {
            listOfServersWithContainers = ListHelper.getListOfServersAndContainers();
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
        
        String server_ip = "";
        for (ServerModel server : listOfServersWithContainers) {
            for (ContainerModel theContainer : server.getContainers()) {
                if (theContainer.getContainerID().contains(containerID)) {
                    server_ip = server.getIPAddress();
                }
            }
        }

        return server_ip;
        
    }
}

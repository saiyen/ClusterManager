package nl.hogeschool.ClusterManager;

import Connection.Execute;
import Connection.SFTPDownload;
import Connection.SFTPUpload;
import Connection.SSHConnection;
import Models.SSHConnectionModel;
import com.sun.security.ntlm.Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import net.schmizz.sshj.SSHClient;
import static org.bouncycastle.crypto.tls.ConnectionEnd.client;

public class Docker {
    List<Container> listOfContainers = new ArrayList();

    public static void startContainer(Container container, String hostname) throws IOException {
        Execute execute = new Execute(); 
        execute.executeCommand(hostname, "docker start "+container.getContainerID());
    }

    public static void stopContainer(Container container, String hostname) throws IOException {
        Execute execute = new Execute(); 
        execute.executeCommand(hostname, "docker stop "+container.getContainerID());
    }
    
    public static void moveContainer(Container container, String sourceHost, String destinationHost) throws IOException {
        SFTPDownload fileDownloader = new SFTPDownload();
        SFTPUpload fileUploader = new SFTPUpload();

        Execute execute = new Execute(); 
        execute.executeCommand(sourceHost, "docker export "+container.getContainerID()+" > "+container.getContainerID()+".tar");
        execute.executeCommand(sourceHost, "chmod 700 "+container.getContainerID()+".tar");
        
        fileDownloader.DownloadFile(sourceHost, container.getContainerID());
        fileUploader.UploadFile(destinationHost, container.getContainerID());
    }
    
    public static void getAllContainers() throws IOException, InterruptedException {
        HashMap<String,SSHClient> listOfClients = SSHConnection.getListOfClients();
        
        for(Entry<String, SSHClient> client : listOfClients.entrySet()) {
            Execute execute = new Execute(); 
            InputStream CMD = execute.executeCommand(client.getKey(), "docker ps -a");
            BufferedReader r = new BufferedReader(new InputStreamReader(CMD));
            System.out.println("Docker ps -a is uitgevoerd op de volgende server: "+ client.getKey());
            
            // Read the output given by the execute command per line
                while (r.readLine() != null) {
                    System.out.println(r.readLine());
                                System.in.read();
                }  
                
            // Divide each line per , or " " to split by Id, Name etc

            // Create new Container object and assign these values
        }
    }
}

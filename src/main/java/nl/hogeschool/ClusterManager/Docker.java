package nl.hogeschool.ClusterManager;

import Connection.Execute;
import Connection.SFTPDownload;
import Connection.SFTPUpload;
import java.io.IOException;

public class Docker {

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
        
        fileDownloader.downloadFile(sourceHost, container.getContainerID());
        fileUploader.uploadFile(destinationHost, container.getContainerID());
    }
}

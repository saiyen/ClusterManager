package nl.hogeschool.ClusterManager;

import Connection.Execute;
import Connection.SFTPDownload;
import Connection.SFTPUpload;

public class Docker {

    static String executeCommand;

    public static void startContainer(Container container) {
        // Execute Start Container command on server
        
        executeCommand = "docker start " + container.getContainerID();
        System.out.println(executeCommand);
    }

    public static void stopContainer(Container container) {
        // Execute Stop Container command on server
            
        executeCommand = "docker stop " + container.getContainerID();
    }
    
    public static void moveContainer(Container container) {
        // Execute Move Container command on server
        SFTPUpload fileUploader = new SFTPUpload();
        SFTPDownload fileDownloader = new SFTPDownload();
        
        executeCommand = "docker export --output=\""+container.getContainerID()+".tar\""+container.getContainerID();
        
        Execute execute = new Execute(); 
    }
}

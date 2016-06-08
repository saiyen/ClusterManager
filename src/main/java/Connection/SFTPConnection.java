package Connection;

import ReadConfig.ReadConfig;
import java.io.File;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;

import java.io.IOException;
import java.util.logging.Logger;
import nl.hogeschool.ClusterManager.Tools;

public class SFTPConnection {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    String downloadPath = null;
    
    public SFTPConnection() {
        try {
            downloadPath = ReadConfig.getConfigProperties().getDownloadFolderPath();
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
    }
    
    public void downloadFile(String hostname, String fileName) throws IOException {
        SSHClient targetHost = SSHConnection.getListOfClients().get(hostname);

        try {
            final SFTPClient sftp = targetHost.newSFTPClient();
            try {
                sftp.get("/home/DockerContainers/"+fileName+".tar", new FileSystemFile(downloadPath));
                LOGGER.info("The file was successfully downloaded");
            } catch(Exception e) {
                LOGGER.warning("The file was not downloaded because: " + e.getMessage());
            } finally {
                sftp.close();
                LOGGER.info("SFTP connection closed.");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            LOGGER.warning("Can not make SFTP connection because of: " + e.getMessage());
        } 
    }
    
    public void uploadFile(String destinationHost,String fileName) throws IOException {
        SSHClient targetHost = SSHConnection.getListOfClients().get(destinationHost);
        
        try {                         
	    final String src = downloadPath + File.separator + fileName+".tar";
            final SFTPClient sftp = targetHost.newSFTPClient();
            try {
                if(Tools.searchUploadPath(destinationHost) == null){
                    LOGGER.warning("Can not find the server");
                    return;
                }
                    
                sftp.put(new FileSystemFile(src), Tools.searchUploadPath(destinationHost).getUploadPath());
                LOGGER.info("The file was successfully downloaded");
                
            } catch(Exception e) {
                LOGGER.warning("The file was not uploaded because: " + e.getMessage());
            }finally {
                sftp.close();
                LOGGER.info("SFTP connection closed.");
            }
        } catch(Exception e) {
            LOGGER.warning("Can not make SFTP connection because of: " + e.getMessage());
        } 
    }
            
}

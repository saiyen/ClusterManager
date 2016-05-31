package Connection;

import ReadConfig.ReadConfig;
import java.io.File;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;

import java.io.IOException;
import java.util.logging.Logger;

public class SFTPConnection {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    String downloadPath = ReadConfig.confData.getDownloadFolderPath();
    
    public void downloadFile(String hostname, String fileName) throws IOException {
        SSHClient targetHost = SSHConnection.getListOfClients().get(hostname).getClient();

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
        SSHClient targetHost = SSHConnection.getListOfClients().get(destinationHost).getClient();
        String uploadPath = ReadConfig.confData.getUploadFolderPath();
        try {                         
	    final String src = downloadPath + File.separator + fileName+".tar";
            final SFTPClient sftp = targetHost.newSFTPClient();
            try {
                sftp.put(new FileSystemFile(src), uploadPath);
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

package Connection;

import ReadConfig.ReadConfig;
import java.io.File;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;

import java.io.IOException;

public class SFTPConnection {
    
    public void downloadFile(String hostname, String fileName) throws IOException {
        SSHClient targetHost = SSHConnection.getListOfClients().get(hostname);
        String downloadPath = ReadConfig.confData.getDownloadFolderPath();

        try {
            final SFTPClient sftp = targetHost.newSFTPClient();
            try {
                sftp.get ("/root/"+fileName+".tar", new FileSystemFile(downloadPath));
            } finally {
                sftp.close();
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            targetHost.disconnect();
        }
    }
    
    public void uploadFile(String destinationHost, String fileName) throws IOException {
        SSHClient targetHost = SSHConnection.getListOfClients().get(destinationHost);
        String downloadPath = ReadConfig.confData.getDownloadFolderPath();
        
        try {                         
	    final String src = downloadPath + File.separator + fileName+".tar";
            final SFTPClient sftp = targetHost.newSFTPClient();
            try {
                sftp.put(new FileSystemFile(src), "/tmp/saiyen/");
            } finally {
                sftp.close();
            }
        } finally {
            targetHost.disconnect();
        }
    }

}

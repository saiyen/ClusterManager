package Connection;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;
import ReadConfig.ReadConfig;

import java.io.IOException;

public class SFTPDownload {
    
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
        } finally {
            targetHost.disconnect();
        }
    }
}

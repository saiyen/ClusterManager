package Connection;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;

import java.io.IOException;

public class SFTPDownload extends SSHConnection{
    SSHConnection connection = new SSHConnection();
    
    public void DownloadFile(String hostname, String fileName) throws IOException {
        SSHClient targetHost = connection.getListOfClients().get(hostname);
        try {
            final SFTPClient sftp = targetHost.newSFTPClient();
            try {
                sftp.get ("/root/"+fileName+".tar", new FileSystemFile("C:\\Users\\chill\\Desktop\\"));
            } finally {
                sftp.close();
            }
        } finally {
            targetHost.disconnect();
        }
    }

}

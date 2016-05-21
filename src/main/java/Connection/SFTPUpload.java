package Connection;

import java.io.File;
import java.io.IOException;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;

public class SFTPUpload {
    
    public void UploadFile(String destinationHost, String fileName) throws IOException {
        SSHClient targetHost = SSHConnection.getListOfClients().get(destinationHost);
        
        try {                         
	    final String src = "C:\\Users\\chill\\Desktop\\" + File.separator + fileName+".tar";
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
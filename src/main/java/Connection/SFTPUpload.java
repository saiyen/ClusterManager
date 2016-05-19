package Connection;

import java.io.File;
import java.io.IOException;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;

public class SFTPUpload extends SSHConnection{

    public void UploadFile(String destinationHost, String ContainerID) throws IOException {
        try {     
            setSSHClient(destinationHost);
                    
	    final String src = "C:\\Users\\chill\\Desktop\\" + File.separator + ContainerID+".tar";
            final SFTPClient sftp = getSSHClient().newSFTPClient();
            try {
                sftp.put(new FileSystemFile(src), "/tmp/saiyen/");
            } finally {
                sftp.close();
            }
        } finally {
            getSSHClient().disconnect();
        }
    }
}
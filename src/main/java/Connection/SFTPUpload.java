package Connection;

import java.io.File;
import java.io.IOException;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;

public class SFTPUpload extends SSHConnection{

    public void SFTPUpload() throws IOException {
        try {     
            setSSHClient();
                    
	    final String src = "C:\\Users\\User\\Desktop\\" + File.separator + "datascience.txt";
            final SFTPClient sftp = getSSHClient().newSFTPClient();
            try {
                sftp.put(new FileSystemFile(src), "/testFolder");
            } finally {
                sftp.close();
            }
        } finally {
            getSSHClient().disconnect();
        }
    }
}
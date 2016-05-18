package Connection;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;

import java.io.IOException;

/** This example demonstrates downloading of a file over SFTP from the SSH server. */
public class SFTPDownload extends SSHConnection{

    public void DownloadFile() throws IOException {
        try {
            setSSHClient();
            final SFTPClient sftp = getSSHClient().newSFTPClient();
            try {
                sftp.get ("/root/hello-world-test.tar", new FileSystemFile("C:\\Users\\chill\\Desktop\\"));
            } finally {
                sftp.close();
            }
        } finally {
            disconnectSSHClient();
        }
    }

}

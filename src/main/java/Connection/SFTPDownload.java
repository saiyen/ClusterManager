package Connection;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;

import java.io.IOException;

/** This example demonstrates downloading of a file over SFTP from the SSH server. */
public class SFTPDownload extends SSHConnection{

    public void DownloadFile(String Hostname, String ContainerID) throws IOException {
        try {
            setSSHClient(Hostname);
            final SFTPClient sftp = getSSHClient().newSFTPClient();
            try {
                sftp.get ("/root/"+ContainerID+".tar", new FileSystemFile("C:\\Users\\chill\\Desktop\\"));
            } finally {
                sftp.close();
            }
        } finally {
            disconnectSSHClient();
        }
    }

}

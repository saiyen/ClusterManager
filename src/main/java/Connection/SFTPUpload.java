package Connection;

import java.io.File;
import java.io.IOException;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;
import net.schmizz.sshj.xfer.FileSystemFile;

public class SFTPUpload {

    public static void main(String[] args) throws IOException {
        final SSHClient ssh = new SSHClient();
        ssh.loadKnownHosts();
        ssh.connect("Hostname");
        try {
            KeyProvider loadKey = ssh.loadKeys("Key");
            ssh.authPublickey("userName",loadKey);
            
            final String src = "C:\\Users\\User1\\Desktop\\" + File.separator + "data.txt";
            final SFTPClient sftp = ssh.newSFTPClient();
            try {
                sftp.put(new FileSystemFile(src), "/test/");
            } finally {
                sftp.close();
            }
        } finally {
            ssh.disconnect();
        }
    }
}

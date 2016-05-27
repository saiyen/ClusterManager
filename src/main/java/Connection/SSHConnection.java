package Connection;

import Interfaces.IConnection;
import Models.SSHClientWrapperModel;
import Models.SSHConnectionModel;
import ReadConfig.ReadConfig;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.DisconnectReason;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;

public class SSHConnection implements IConnection{
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static HashMap<String, SSHClientWrapperModel> listOfClients = new HashMap<>();
    
    public static void makeConnections() throws IOException {
        ReadConfig read = new ReadConfig();
        ArrayList<SSHConnectionModel> connections = read.getConnectionProperties();

        String keyPath = ReadConfig.confData.getKeyPath();
        String knownHostsPath = ReadConfig.confData.getKnownHostsPath();

        for (SSHConnectionModel currentConnection : connections) {
            SSHClient sshConnection;
            sshConnection = new SSHClient();
            sshConnection.loadKnownHosts(new File(knownHostsPath));
            
            try {
                sshConnection.connect(currentConnection.getHost());
            } catch (TransportException e) {
                if (e.getDisconnectReason() == DisconnectReason.HOST_KEY_NOT_VERIFIABLE) {
                    String msg = e.getMessage();
                    String[] split = msg.split("`");
                    String fingerPrint = split[3];
                    sshConnection = new SSHClient();
                    sshConnection.addHostKeyVerifier(fingerPrint);
                    sshConnection.connect(currentConnection.getHost());
                } else {
                    throw e;
                }
                
                LOGGER.warning(e.getMessage());
            }

            KeyProvider loadKey = sshConnection.loadKeys(keyPath, currentConnection.getPassphrase());
            sshConnection.authPublickey(currentConnection.getUser(), loadKey);
            LOGGER.info("Authenticated successfully");
            
            listOfClients.put(currentConnection.getHost(), new SSHClientWrapperModel(sshConnection, sshConnection.startSession()));
        }
    }
    
    public static HashMap<String, SSHClientWrapperModel> getListOfClients() throws IOException {
        if(listOfClients.isEmpty())
            makeConnections();
        
            return listOfClients;
    }

}

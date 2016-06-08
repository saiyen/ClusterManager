package Models;

import java.util.ArrayList;
import java.util.List;

public class ServerModel {
    private String hostname;
    private String IPAddress;
    
    public ServerModel(String IPAddress, String hostname){
        this.hostname = hostname;
        this.IPAddress = IPAddress;   
    }

    /**
     * @return the Hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * @param Hostname the Hostname to set
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * @return the IPAddress
     */
    public String getIPAddress() {
        return IPAddress;
    }

    /**
     * @param IPAddress the IPAddress to set
     */
    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }
}

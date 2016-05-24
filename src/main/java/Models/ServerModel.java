package Models;

import java.util.ArrayList;
import java.util.List;

public class ServerModel {
    private String hostname;
    private String IPAddress;
    private List<ContainerModel> containers = new ArrayList<ContainerModel>();
    
    public ServerModel(String IPAddress, List<ContainerModel> containers){
        this.hostname = hostname;
        this.IPAddress = IPAddress;
        this.containers = containers;   
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

    /**
     * @return the Containers
     */
    public List<ContainerModel> getContainers() {
        return containers;
    }

    /**
     * @param Containers the Containers to set
     */
    public void setContainers(List<ContainerModel> Containers) {
        this.containers = Containers;
    }
}

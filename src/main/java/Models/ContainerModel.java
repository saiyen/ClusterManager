package Models;

public class ContainerModel{

    private String containerID;
    private String containerImage;
    private String containerCommand;
    private String containerCreated;
    private String containerStatus;
    private String containerPort;
    private String containerName;

    public ContainerModel(String containerID, String containerImage, String containerCommand, String containerCreated, String containerStatus, String containerPort, String containerName){
        this.containerID = containerID;
        this.containerImage = containerImage;
        this.containerCommand = containerCommand;
        this.containerCreated = containerCreated;
        this.containerStatus = containerStatus;
        this.containerPort = containerPort;
        this.containerName = containerName;
    }
    
        /**
     * @return the containerID
     */
    public String getContainerID() {
        return containerID;
    }

    /**
     * @param containerID the containerID to set
     */
    public void setContainerID(String containerID) {
        this.containerID = containerID;
    }

    /**
     * @return the containerName
     */
    public String getContainerName() {
        return containerName;
    }

    /**
     * @param containerName the containerName to set
     */
    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }
    
    /**
     * @return the containerStatus
     */
    public String getContainerStatus() {
        return containerStatus;
    }

    /**
     * @param containerStatus the containerStatus to set
     */
    public void setContainerStatus(String containerStatus) {
        this.containerStatus = containerStatus;
    }

    /**
     * @return the containerImage
     */
    public String getContainerImage() {
        return containerImage;
    }

    /**
     * @param containerImage the containerImage to set
     */
    public void setContainerImage(String containerImage) {
        this.containerImage = containerImage;
    }

    /**
     * @return the containerCommand
     */
    public String getContainerCommand() {
        return containerCommand;
    }

    /**
     * @param containerCommand the containerCommand to set
     */
    public void setContainerCommand(String containerCommand) {
        this.containerCommand = containerCommand;
    }

    /**
     * @return the containerCreated
     */
    public String getContainerCreated() {
        return containerCreated;
    }

    /**
     * @param containerCreated the containerCreated to set
     */
    public void setContainerCreated(String containerCreated) {
        this.containerCreated = containerCreated;
    }

    /**
     * @return the containerPort
     */
    public String getContainerPort() {
        return containerPort;
    }

    /**
     * @param containerPort the containerPort to set
     */
    public void setContainerPort(String containerPort) {
        this.containerPort = containerPort;
    }
}
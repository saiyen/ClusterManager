package Models;

public class ContainerModel{

    private String containerID;
    private String containerName;
    private String containerStatus;
    private String containerImage;
    private String containerType;

    public ContainerModel(String containerID, String containerName, String containerStatus, String containerImage, String containerType){
        this.containerID = containerID;
        this.containerName = containerName;
        this.containerStatus = containerStatus;
        this.containerImage = containerImage;
        this.containerType = containerType;
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
     * @return the containerType
     */
    public String getContainerType() {
        return containerType;
    }

    /**
     * @param containerType the containerType to set
     */
    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }
}
package Interfaces;

import java.io.IOException;
import java.util.HashMap;
import org.json.simple.JSONObject;

public interface ContainerManager {
    void setJson(JSONObject data);
    int startContainer() throws IOException;
    int stopContainer() throws IOException;
    int removeContainer() throws IOException;
    int moveContainer() throws IOException;
    int renameContainer() throws IOException;
    int createContainer() throws IOException;
    void getAllContainers() throws IOException, InterruptedException;
    HashMap<String, String> getServerAndContainerInfoByContainerID(String containerID);
}

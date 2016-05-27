package nl.hogeschool.ClusterManager;

import java.io.IOException;

public interface IContainerRunner {
    void startContainer() throws IOException;
    void stopContainer() throws IOException;
    void moveContainer() throws IOException;
    void renameContainer() throws IOException;
    void getAllContainers() throws IOException, InterruptedException;
}

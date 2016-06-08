package Interfaces;

import java.io.IOException;

public interface IContainerRunner {
    void startContainer() throws IOException;
    void stopContainer() throws IOException;
    void removeContainer() throws IOException;
    void moveContainer() throws IOException;
    void renameContainer() throws IOException;
    void createContainer() throws IOException;
    void getAllContainers() throws IOException, InterruptedException;
}

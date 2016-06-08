package Interfaces;

import java.io.IOException;

public interface IContainerRunner {
    int startContainer() throws IOException;
    int stopContainer() throws IOException;
    int removeContainer() throws IOException;
    int moveContainer() throws IOException;
    int renameContainer() throws IOException;
    int createContainer() throws IOException;
    void getAllContainers() throws IOException, InterruptedException;
}

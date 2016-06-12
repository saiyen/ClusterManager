package ContainerManager;

import Interfaces.ContainerManager;

public class ContainerManagerFactory {
    //use getShape method to get object of type shape 

    public ContainerManager getContainerManager(String containerManagerType) {
        ContainerManager containerManager = null;
        switch (containerManagerType) {
            case "Docker":
                return new DockerContainerManager();
        }
        return containerManager;
    }
}

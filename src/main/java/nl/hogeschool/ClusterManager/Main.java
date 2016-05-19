package nl.hogeschool.ClusterManager;

import java.io.IOException;

public class Main {
    public static void main(String[] Args) throws IOException{
        Container container = new Container();
        container.setContainerID("");

        Docker.startContainer(container, "");
        //Docker.moveContainer(container,"","");
    }
}

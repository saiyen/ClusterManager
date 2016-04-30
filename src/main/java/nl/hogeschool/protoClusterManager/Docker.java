package nl.hogeschool.protoClusterManager;

public class Docker {
    
    static String executeCommand;
    
    public static void startContainer(Container container){
        // Execute Start Container command on server
        try{
            executeCommand = "docker start "+container.getContainerID();
            System.out.println(executeCommand);
        }
        catch(Exception e){
            System.err.println("The Docker start command could not be executed:" +e.getMessage()+ "");
        }
    }
    
    public static void stopContainer(Container container){
        // Execute Stop Container command on server
        try {
            executeCommand = "docker stop "+container.getContainerID();
        } catch (Exception e) {
            System.err.println("The Docker stop command could not be executed:" +e.getMessage()+ "");
        }
    }
}

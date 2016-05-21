package nl.hogeschool.ClusterManager;

public class Main {
    public static void main(String[] Args) {
        try {            
            Docker.getAllContainers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
        }
    }
}

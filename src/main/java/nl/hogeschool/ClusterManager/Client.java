package nl.hogeschool.ClusterManager;

import Models.ServerModel;
import com.google.gson.JsonObject;
import java.io.FileReader;
import java.util.List;

public class Client {
    private static DataFormatContext dataFormatContext = new DataFormatContext();
    
    public static void sendToAPI(String strategy, List<ServerModel> servers) {      
        // Context is set by preferences
        checkStrategy(strategy);

        //get the json object of each Server and their containers
        dataFormatContext.formatData(servers);
    }
    
    public static JsonObject getFromAPI(String strategy, FileReader readFile) {      
        // Context is set by preferences
        checkStrategy(strategy);
        
        return dataFormatContext.getFormattedData(readFile);
    }
    
    public static DataFormatContext checkStrategy(String strategy){
        switch(strategy){
            case "JSONObject":
                dataFormatContext.setCompressionStrategy(new JsonStrategy());
                break;        
        }
        return dataFormatContext;
    }
}

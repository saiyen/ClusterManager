package nl.hogeschool.ClusterManager;

import Models.ServerModel;
import com.google.gson.JsonObject;
import java.io.FileReader;
import java.util.List;

public class Client {
    private static final DataFormatConverter dataFormatContext = new DataFormatConverter();
    
    public static void sendToAPI(String strategy, List<ServerModel> servers) {      
        // Context is set by preferences
        chooseDataFormatStrategy(strategy);

        //get the json object of each Server and their containers
        dataFormatContext.useStrategyToFormatData(servers);
    }
    
    public static DataFormatConverter chooseDataFormatStrategy(String strategy){
        switch(strategy){
            case "JSONObject":
                dataFormatContext.setDataFormatStrategy(new JsonConverter());
                break;        
        }
        return dataFormatContext;
    }
}

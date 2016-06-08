package nl.hogeschool.ClusterManager;

import Models.ServerModel;
import java.util.List;

public class Client {
    private static final DataFormatConverter DATA_FORMAT_CONTEXT = new DataFormatConverter();
    
    public static void sendToAPI(String strategy, List<ServerModel> servers) {      
        // Context is set by preferences
        chooseDataFormatStrategy(strategy);

        //get the json object of each Server and their containers
        DATA_FORMAT_CONTEXT.useStrategyToFormatData(servers);
    }
    
    public static DataFormatConverter chooseDataFormatStrategy(String strategy){
        switch(strategy){
            case "JSONObject":
                DATA_FORMAT_CONTEXT.setDataFormatStrategy(new JsonConverter());
                break;        
        }
        return DATA_FORMAT_CONTEXT;
    }
}

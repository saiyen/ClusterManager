package nl.hogeschool.ClusterManager;

import Models.ContainerModel;
import java.util.List;

public class Client {
    private static final DataFormatConverter DATA_FORMAT_CONTEXT = new DataFormatConverter();
    
    public static<T> String sendToAPI(String strategy, List<ContainerModel> containers) {      
        // Context is set by preferences
        chooseDataFormatStrategy(strategy);

        //get the json object of each Server and their containers
        DATA_FORMAT_CONTEXT.useStrategyToFormatData(containers);
        return DATA_FORMAT_CONTEXT.toString();
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

package DataFormat;

import Models.ServerModel;
import java.util.List;
import Interfaces.DataFormatType;

public class DataFormatConverter {

    private DataFormatType strategy;

    //this can be set at runtime by the application preferences
    public void setDataFormatStrategy(DataFormatType strategy) {
        this.strategy = strategy;
    }

    //use the strategy
    public void useStrategyToFormatData(List<ServerModel> servers) {
        strategy.convertToDataFormat(servers);
    }
}

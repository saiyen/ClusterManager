package nl.hogeschool.ClusterManager;

import Models.ServerModel;
import java.util.List;
import Interfaces.IDataFormatChooser;

public class DataFormatConverter {

    private IDataFormatChooser strategy;

    //this can be set at runtime by the application preferences
    public void setDataFormatStrategy(IDataFormatChooser strategy) {
        this.strategy = strategy;
    }

    //use the strategy
    public void useStrategyToFormatData(List<ServerModel> servers) {
        strategy.convertToDataFormat(servers);
    }
}

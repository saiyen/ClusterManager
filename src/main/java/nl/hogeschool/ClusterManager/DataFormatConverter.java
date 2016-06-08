package nl.hogeschool.ClusterManager;

import java.util.List;
import Interfaces.IDataFormatChooser;
import Models.ContainerModel;

public class DataFormatConverter {

    private IDataFormatChooser strategy;

    //this can be set at runtime by the application preferences
    public void setDataFormatStrategy(IDataFormatChooser strategy) {
        this.strategy = strategy;
    }

    //use the strategy
    public void useStrategyToFormatData(List<ContainerModel> containers) {
        strategy.convertToDataFormat(containers);
    }
}

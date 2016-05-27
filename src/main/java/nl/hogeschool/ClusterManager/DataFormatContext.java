package nl.hogeschool.ClusterManager;

import Interfaces.IDataFormat;
import Models.ServerModel;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.util.List;

public class DataFormatContext {

    private IDataFormat strategy;

    //this can be set at runtime by the application preferences
    public void setCompressionStrategy(IDataFormat strategy) {
        this.strategy = strategy;
    }

    //use the strategy
    public void formatData(List<ServerModel> servers) {
        strategy.convertToDataFormat(servers);
    }

    public JsonObject getFormattedData(FileReader readFile) {
        return strategy.getFormattedData(readFile);
    }
}

package DataFormat;

import Models.ServerModel;
import java.util.List;
import Interfaces.DataFormatType;

public class DataFormatConverter {

    private DataFormatType formatType;

    //this can be set at runtime by the application preferences
    public DataFormatConverter(DataFormatType formatType) {
        this.formatType = formatType;
    }

    //use the strategy to execute the specified Data Format
    public void useStrategyToFormatData(List<ServerModel> servers) {
        formatType.convertToDataFormat(servers);
    }
}

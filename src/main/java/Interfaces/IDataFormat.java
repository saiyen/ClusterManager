package Interfaces;

import Models.ServerModel;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.util.List;

public interface IDataFormat {
    public void convertToDataFormat(List<ServerModel> servers);
    public JsonObject getFormattedData(FileReader jsonReader);
}

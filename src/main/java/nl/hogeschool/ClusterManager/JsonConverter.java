/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hogeschool.ClusterManager;

import Models.ContainerModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chill
 */
public class JsonConverter implements Interfaces.IDataFormatChooser<String> {

    @Override
    public String convertToDataFormat(List<ContainerModel> containers) {
        Gson gson = new Gson();
        return gson.toJson(containers);
    } 
}

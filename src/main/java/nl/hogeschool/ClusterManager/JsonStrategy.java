/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hogeschool.ClusterManager;

import Models.ServerModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
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
public class JsonStrategy implements Interfaces.IDataFormat {

    @Override
    public void convertToDataFormat(List<ServerModel> servers) {
        // Convert object to JSON string
        Gson gson = new Gson();

//        String json = gson.toJson(servers);
//        System.out.println(json);

        // Convert object to JSON string and save into a file directly
        try (FileWriter writer = new FileWriter("./src/main/resources/json/Containers.json")) {

            gson.toJson(servers, writer);

        } catch (IOException e) {
            e.getMessage();
        }
    } 

    @Override
    public JsonObject getFormattedData(FileReader readFile) {
        JsonObject jsonObject = new JsonObject();
        try {
            JsonReader reader = new JsonReader(readFile);
            reader = new JsonReader(readFile);
            JsonElement JSONelement = new JsonParser().parse(readFile);
            jsonObject = JSONelement.getAsJsonObject();
        }catch(JsonIOException | JsonSyntaxException e){
            e.getMessage();
        }
        return jsonObject;
    }
}

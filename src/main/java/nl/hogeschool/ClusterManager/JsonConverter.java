/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hogeschool.ClusterManager;

import Models.ServerModel;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author chill
 */
public class JsonConverter implements Interfaces.IDataFormatChooser {

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
}

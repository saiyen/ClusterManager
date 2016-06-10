/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import API.Commands.*;
import static spark.Spark.*;
import API.Cors.CorsFilter;

/**
 *
 * @author absentium
 */
public class APICore {
    public void requests(){
        CorsFilter.apply();
        
        
        get("/serverList", (req,res) -> {
            GetServers getS = new GetServers();
            String data = getS.getData();
            return data; 
        });
        
        // Start or stop a specific container
        post("/start", (req, res) -> {
            StartContainer start = new StartContainer(req.queryParams("name"), req.queryParams("cId"), req.queryParams("cType"));
            
            String result = start.startIt();
            
            return result;
        });
        post("/stop", (req, res) -> {
            StopContainer stop = new StopContainer(req.queryParams("name"), req.queryParams("cId"), req.queryParams("cType"));
            
            String result = stop.stopIt();
            
            return result;
        });
        
        // Update a specific container
        post("/rename", (req, res) -> {
            RenameContainer rename = new RenameContainer(req.queryParams("name"), req.queryParams("cId"), req.queryParams("cType"), req.queryParams("extra"));
            
            String result = rename.sendName();
            
            return result;
        });
        get("/move", (req, res) -> {
            MoveContainer move = new MoveContainer(req.queryParams("name"), req.queryParams("cId"), req.queryParams("cType"), req.queryParams("destination"));
            
            String result = move.moveIt();
            
            return result;
        });
        
        // Remove a specific container
        post("/remove", (req, res) -> {
            RemoveContainer remove = new RemoveContainer(req.queryParams("name"), req.queryParams("cId"), req.queryParams("cType"));
            
            String result = remove.removeIt();
            
            return result;
        });
        
        // Create a new container on a specific server
        post("/create", (req, res) -> {
            CreateContainer create = new CreateContainer(req.queryParams("name"), req.queryParams("cType"), req.queryParams("destination"), req.queryParams("image"));
            
            String result = create.createIt();
            
            return result;
        });
    }
}

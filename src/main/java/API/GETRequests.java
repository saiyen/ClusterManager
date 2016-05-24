/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import static spark.Spark.*;
/**
 *
 * @author Ivan Ivanov
 */
public class GETRequests {
     public void getContainers() {
        get("/hello", (req, res) -> "{\"IPAddress\":\"146.185.159.191\",\"containers\":[{\"containerID\":\"a93ca1dd4f09\",\"containerName\":\"hour\",\"containerStatus\":\"ago Exited (0) About an\",\"containerImage\":\"hello-world\",\"containerType\":\"Docker\"},{\"containerID\":\"875f643a2ac1\",\"containerName\":\"reverent_jang\",\"containerStatus\":\"Exited (0) 4 days ago\",\"containerImage\":\"hello-world\",\"containerType\":\"Docker\"}]}");
    }
}

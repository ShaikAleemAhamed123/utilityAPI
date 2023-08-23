// ONHOLD Not yet Used
package com.utility.utilityAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utility.utilityAPI.models.Connection;
import com.utility.utilityAPI.services.ConnectionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/connection")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ConnectionController {

    ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService){
        this.connectionService=connectionService;
    }
    @PostMapping("/addConnection")
    public boolean addConnection(@RequestBody String body){
        Connection connection=ConnectionJsonParser(body);
        if(connection==null) return false;
        return connectionService.addConnection(connection);
    }

    public Connection ConnectionJsonParser(String data){
        ObjectMapper objectMapper=new ObjectMapper();
        try{
            return objectMapper.readValue(data,Connection.class);
        }
        catch(Exception e){
            System.out.println("Exception while parsing json user data : "+ e.getMessage());
        }
        return null;
    }
}

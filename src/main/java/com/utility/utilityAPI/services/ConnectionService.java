// ONHOLD This Service Class is currently on hold for further implementations
package com.utility.utilityAPI.services;

import com.utility.utilityAPI.models.Connection;
import com.utility.utilityAPI.repositories.ConnectionRepo;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {
    ConnectionRepo connectionRepo;

    public ConnectionService(ConnectionRepo connectionRepo){
        this.connectionRepo=connectionRepo;
    }
    public boolean addConnection(Connection connection) {
        boolean possibility_01=connectionRepo.findBySourceAndDestination(connection.getSource(),connection.getDestination())!=null;
        boolean possibility_02=connectionRepo.findBySourceAndDestination(connection.getDestination(),connection.getSource())!=null;
        if(possibility_01 || possibility_02) return true;
        connectionRepo.save(connection);
        return true;
    }
}

// ONHOLD This Repo Class is currently on hold for further implementations
package com.utility.utilityAPI.repositories;

import com.utility.utilityAPI.models.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepo extends JpaRepository<Connection, Long> {
    Connection findBySourceAndDestination(String source, String destination);
}

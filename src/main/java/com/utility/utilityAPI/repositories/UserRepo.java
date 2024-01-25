package com.utility.utilityAPI.repositories;

import com.utility.utilityAPI.models.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserData, Long> {
    UserData findByUserHandle(String userHandle);
}

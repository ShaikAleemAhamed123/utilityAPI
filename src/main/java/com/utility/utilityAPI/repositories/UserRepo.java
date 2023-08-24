package com.utility.utilityAPI.repositories;

import com.utility.utilityAPI.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User findByUserHandle(String userHandle);
}

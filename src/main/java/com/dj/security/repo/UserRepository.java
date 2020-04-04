package com.dj.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dj.security.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
   // Optional<User> findByUserName(String userName);
    
    @Query(value = "SELECT * FROM user  WHERE user_name=:userName", nativeQuery = true)
    Optional<User> findByUser(@Param("userName") String userName);
    
    @Query(value = "SELECT * FROM user  WHERE user_name=:userName", nativeQuery = true)
    User findByUserName(@Param("userName") String userName);
}

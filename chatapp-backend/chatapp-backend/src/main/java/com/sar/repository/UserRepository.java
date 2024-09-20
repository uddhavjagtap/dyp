package com.sar.repository;

import com.sar.model.User;
import com.sar.model.UserLoginProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByEmail(String email);
boolean existsByEmail(String email);
@Query("SELECT u.id AS id, u.email AS email, u.password AS password FROM User u WHERE u.email = :email")
UserLoginProjection findUserLoginDetailsByEmail(@Param("email") String email);
}
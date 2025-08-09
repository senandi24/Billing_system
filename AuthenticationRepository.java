package com.example.Billing_System.Respository;

import com.example.Billing_System.Models.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {

    Optional<Authentication> findByUsername(String username);

    Optional<Authentication> findByUsernameAndPassword(String username, String password);

    List<Authentication> findByRole(String role);

    @Query("SELECT a FROM Authentication a WHERE a.username = :username AND a.password = :password")
    Optional<Authentication> authenticateUser(@Param("username") String username, @Param("password") String password);

    @Query("SELECT COUNT(a) FROM Authentication a WHERE a.role = :role")
    long countByRole(@Param("role") String role);

    boolean existsByUsername(String username);
}
package com.example.Billing_System.Respository;

import com.example.Billing_System.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByAccountNumber(String accountNumber);

    List<Users> findByName(String name);

    Optional<Users> findByEmail(String email);

    List<Users> findByIsActive(Boolean isActive);

    @Query("SELECT u FROM Users u WHERE u.name LIKE %:name%")
    List<Users> findByNameContaining(@Param("name") String name);

    @Query("SELECT u FROM Users u WHERE u.address LIKE %:address%")
    List<Users> findByAddressContaining(@Param("address") String address);

    @Query("SELECT u FROM Users u WHERE u.telephoneNumber = :phone")
    Optional<Users> findByTelephoneNumber(@Param("phone") String telephoneNumber);

    @Query("SELECT u FROM Users u WHERE u.isActive = true ORDER BY u.name")
    List<Users> findAllActiveUsersOrderByName();

    @Query("SELECT COUNT(u) FROM Users u WHERE u.isActive = true")
    long countActiveUsers();

    boolean existsByAccountNumber(String accountNumber);

    boolean existsByEmail(String email);

    boolean existsByTelephoneNumber(String telephoneNumber);
}
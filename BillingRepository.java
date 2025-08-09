package com.example.Billing_System.Repository;

import com.example.Billing_System.Models.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {

    Optional<Billing> findByBillNumber(String billNumber);

    List<Billing> findByAccountNumber(String accountNumber);

    List<Billing> findByAccountName(String accountName);

    List<Billing> findByStatus(String status);

    List<Billing> findByBillDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT b FROM Billing b WHERE b.accountNumber = :accountNumber AND b.status = :status")
    List<Billing> findByAccountNumberAndStatus(@Param("accountNumber") String accountNumber, @Param("status") String status);

    @Query("SELECT b FROM Billing b WHERE b.accountName LIKE %:name%")
    List<Billing> findByAccountNameContaining(@Param("name") String name);

    @Query("SELECT SUM(b.totalAmount) FROM Billing b WHERE b.status = 'PAID'")
    Double getTotalPaidAmount();

    @Query("SELECT SUM(b.totalAmount) FROM Billing b WHERE b.status = 'UNPAID'")
    Double getTotalUnpaidAmount();

    @Query("SELECT b FROM Billing b ORDER BY b.billDate DESC")
    List<Billing> findAllOrderByBillDateDesc();

    boolean existsByBillNumber(String billNumber);
}
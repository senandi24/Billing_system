package com.example.Billing_System.Repository;

import com.example.Billing_System.Models.BillingItems;
import com.example.Billing_System.Models.Billing;
import com.example.Billing_System.Models.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingItemsRepository extends JpaRepository<BillingItems, Long> {

    List<BillingItems> findByBilling(Billing billing);

    List<BillingItems> findByItem(Items item);

    List<BillingItems> findByBillingId(Long billingId);

    @Query("SELECT bi FROM BillingItems bi WHERE bi.billing.id = :billingId")
    List<BillingItems> findItemsByBillingId(@Param("billingId") Long billingId);

    @Query("SELECT SUM(bi.quantity) FROM BillingItems bi WHERE bi.item.id = :itemId")
    Integer getTotalQuantitySoldForItem(@Param("itemId") Long itemId);

    @Query("SELECT SUM(bi.totalPrice) FROM BillingItems bi WHERE bi.billing.id = :billingId")
    Double getTotalAmountForBilling(@Param("billingId") Long billingId);

    @Query("SELECT bi FROM BillingItems bi JOIN bi.billing b WHERE b.status = :status")
    List<BillingItems> findByBillingStatus(@Param("status") String status);

    void deleteByBilling(Billing billing);
}
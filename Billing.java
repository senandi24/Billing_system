package com.example.Billing_System.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "billing")
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String billNumber;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String accountName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String telephoneNumber;

    @Column(nullable = false)
    private LocalDateTime billDate;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private String status; // PAID, UNPAID, PARTIAL

    @OneToMany(mappedBy = "billing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BillingItems> billingItems;

    // Default constructor
    public Billing() {
        this.billDate = LocalDateTime.now();
        this.status = "UNPAID";
    }

    // Constructor
    public Billing(String billNumber, String accountNumber, String accountName, String address, String telephoneNumber) {
        this();
        this.billNumber = billNumber;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public LocalDateTime getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BillingItems> getBillingItems() {
        return billingItems;
    }

    public void setBillingItems(List<BillingItems> billingItems) {
        this.billingItems = billingItems;
    }
}
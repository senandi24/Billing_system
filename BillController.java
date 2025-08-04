package com.example.Billing_System.Controller;

import com.example.Billing_System.Models.Billing;
import com.example.Billing_System.Models.BillingItems;
import com.example.Billing_System.Services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin(origins = "*")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createBill(@RequestBody Map<String, String> billRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            Billing bill = billService.createBill(
                    billRequest.get("accountNumber"),
                    billRequest.get("accountName"),
                    billRequest.get("address"),
                    billRequest.get("telephoneNumber")
            );

            response.put("success", true);
            response.put("message", "Bill created successfully");
            response.put("bill", bill);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Bill creation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/{billingId}/items")
    public ResponseEntity<Map<String, Object>> addItemToBill(
            @PathVariable Long billingId,
            @RequestBody Map<String, Object> itemRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            Long itemId = Long.parseLong(itemRequest.get("itemId").toString());
            Integer quantity = Integer.parseInt(itemRequest.get("quantity").toString());

            BillingItems billingItem = billService.addItemToBill(billingId, itemId, quantity);

            if (billingItem != null) {
                response.put("success", true);
                response.put("message", "Item added to bill successfully");
                response.put("billingItem", billingItem);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.put("success", false);
                response.put("message", "Failed to add item to bill");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Add item failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<List<Billing>> getAllBills() {
        try {
            List<Billing> bills = billService.getAllBills();
            return ResponseEntity.ok(bills);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Billing> getBillById(@PathVariable Long id) {
        try {
            Billing bill = billService.getBillById(id);
            if (bill != null) {
                return ResponseEntity.ok(bill);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/bill-number/{billNumber}")
    public ResponseEntity<Billing> getBillByBillNumber(@PathVariable String billNumber) {
        try {
            Billing bill = billService.getBillByBillNumber(billNumber);
            if (bill != null) {
                return ResponseEntity.ok(bill);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<Billing>> getBillsByAccountNumber(@PathVariable String accountNumber) {
        try {
            List<Billing> bills = billService.getBillsByAccountNumber(accountNumber);
            return ResponseEntity.ok(bills);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Billing>> getBillsByStatus(@PathVariable String status) {
        try {
            List<Billing> bills = billService.getBillsByStatus(status.toUpperCase());
            return ResponseEntity.ok(bills);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Billing>> getBillsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<Billing> bills = billService.getBillsByDateRange(startDate, endDate);
            return ResponseEntity.ok(bills);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{billingId}/items")
    public ResponseEntity<List<BillingItems>> getBillItems(@PathVariable Long billingId) {
        try {
            List<BillingItems> items = billService.getBillItems(billingId);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{billingId}/status")
    public ResponseEntity<Map<String, Object>> updateBillStatus(
            @PathVariable Long billingId,
            @RequestBody Map<String, String> statusRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            String status = statusRequest.get("status");
            Billing bill = billService.updateBillStatus(billingId, status.toUpperCase());

            if (bill != null) {
                response.put("success", true);
                response.put("message", "Bill status updated successfully");
                response.put("bill", bill);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Bill not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Status update failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{billingId}")
    public ResponseEntity<Map<String, Object>> deleteBill(@PathVariable Long billingId) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean deleted = billService.deleteBill(billingId);
            if (deleted) {
                response.put("success", true);
                response.put("message", "Bill deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Bill not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Delete failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/items/{billingItemId}")
    public ResponseEntity<Map<String, Object>> removeBillItem(@PathVariable Long billingItemId) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean removed = billService.removeBillItem(billingItemId);
            if (removed) {
                response.put("success", true);
                response.put("message", "Item removed from bill successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Bill item not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Remove item failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Billing>> searchBillsByAccountName(@RequestParam String accountName) {
        try {
            List<Billing> bills = billService.searchBillsByAccountName(accountName);
            return ResponseEntity.ok(bills);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getBillStats() {
        try {
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalPaidAmount", billService.getTotalPaidAmount());
            stats.put("totalUnpaidAmount", billService.getTotalUnpaidAmount());
            stats.put("totalBills", billService.getAllBills().size());
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
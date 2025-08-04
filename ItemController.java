package com.example.Billing_System.Controller;

import com.example.Billing_System.Models.Items;
import com.example.Billing_System.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createItem(@RequestBody Items itemRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            Items item = itemService.createItem(
                    itemRequest.getItemCode(),
                    itemRequest.getItemName(),
                    itemRequest.getDescription(),
                    itemRequest.getPrice(),
                    itemRequest.getCategory()
            );

            response.put("success", true);
            response.put("message", "Item created successfully");
            response.put("item", item);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Item creation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<List<Items>> getAllItems() {
        try {
            List<Items> items = itemService.getAllItems();
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<Items>> getAllActiveItems() {
        try {
            List<Items> items = itemService.getAllActiveItems();
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Items> getItemById(@PathVariable Long id) {
        try {
            Items item = itemService.getItemById(id);
            if (item != null) {
                return ResponseEntity.ok(item);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/code/{itemCode}")
    public ResponseEntity<Items> getItemByItemCode(@PathVariable String itemCode) {
        try {
            Items item = itemService.getItemByItemCode(itemCode);
            if (item != null) {
                return ResponseEntity.ok(item);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Items>> getItemsByCategory(@PathVariable String category) {
        try {
            List<Items> items = itemService.getItemsByCategory(category);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Items>> searchItemsByName(@RequestParam String name) {
        try {
            List<Items> items = itemService.searchItemsByName(name);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search/description")
    public ResponseEntity<List<Items>> searchItemsByDescription(@RequestParam String description) {
        try {
            List<Items> items = itemService.searchItemsByDescription(description);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Items>> getItemsByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        try {
            List<Items> items = itemService.getItemsByPriceRange(minPrice, maxPrice);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        try {
            List<String> categories = itemService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateItem(@PathVariable Long id, @RequestBody Items itemRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            Items item = itemService.updateItem(id, itemRequest);
            if (item != null) {
                response.put("success", true);
                response.put("message", "Item updated successfully");
                response.put("item", item);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Item not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Update failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Map<String, Object>> deactivateItem(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Items item = itemService.deactivateItem(id);
            if (item != null) {
                response.put("success", true);
                response.put("message", "Item deactivated successfully");
                response.put("item", item);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Item not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Deactivation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Map<String, Object>> activateItem(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Items item = itemService.activateItem(id);
            if (item != null) {
                response.put("success", true);
                response.put("message", "Item activated successfully");
                response.put("item", item);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Item not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Activation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteItem(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean deleted = itemService.deleteItem(id);
            if (deleted) {
                response.put("success", true);
                response.put("message", "Item deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Item not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Delete failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
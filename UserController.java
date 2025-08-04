package com.example.Billing_System.Controller;

import com.example.Billing_System.Models.Users;
import com.example.Billing_System.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody Users userRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            Users user = userService.createUser(
                    userRequest.getAccountNumber(),
                    userRequest.getName(),
                    userRequest.getAddress(),
                    userRequest.getTelephoneNumber(),
                    userRequest.getEmail()
            );

            response.put("success", true);
            response.put("message", "User created successfully");
            response.put("user", user);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "User creation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        try {
            List<Users> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<Users>> getAllActiveUsers() {
        try {
            List<Users> users = userService.getAllActiveUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        try {
            Users user = userService.getUserById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<Users> getUserByAccountNumber(@PathVariable String accountNumber) {
        try {
            Users user = userService.getUserByAccountNumber(accountNumber);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Users> getUserByEmail(@PathVariable String email) {
        try {
            Users user = userService.getUserByEmail(email);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/phone/{telephoneNumber}")
    public ResponseEntity<Users> getUserByTelephoneNumber(@PathVariable String telephoneNumber) {
        try {
            Users user = userService.getUserByTelephoneNumber(telephoneNumber);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Users>> searchUsersByName(@RequestParam String name) {
        try {
            List<Users> users = userService.searchUsersByName(name);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search/address")
    public ResponseEntity<List<Users>> searchUsersByAddress(@RequestParam String address) {
        try {
            List<Users> users = userService.searchUsersByAddress(address);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody Users userRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            Users user = userService.updateUser(id, userRequest);
            if (user != null) {
                response.put("success", true);
                response.put("message", "User updated successfully");
                response.put("user", user);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "User not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Update failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Map<String, Object>> deactivateUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Users user = userService.deactivateUser(id);
            if (user != null) {
                response.put("success", true);
                response.put("message", "User deactivated successfully");
                response.put("user", user);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "User not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Deactivation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Map<String, Object>> activateUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Users user = userService.activateUser(id);
            if (user != null) {
                response.put("success", true);
                response.put("message", "User activated successfully");
                response.put("user", user);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "User not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Activation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                response.put("success", true);
                response.put("message", "User deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "User not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Delete failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getUserStats() {
        try {
            Map<String, Object> stats = new HashMap<>();
            stats.put("activeUserCount", userService.getActiveUserCount());
            stats.put("totalUsers", userService.getAllUsers().size());
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
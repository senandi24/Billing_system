package com.example.Billing_System.Controller;

import com.example.Billing_System.Models.Authentication;
import com.example.Billing_System.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");

            Authentication auth = authService.login(username, password);

            if (auth != null) {
                response.put("success", true);
                response.put("message", "Login successful");
                response.put("user", auth);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Invalid username or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Authentication authRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            Authentication auth = authService.createUser(
                    authRequest.getUsername(),
                    authRequest.getPassword(),
                    authRequest.getRole()
            );

            response.put("success", true);
            response.put("message", "User created successfully");
            response.put("user", auth);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<Authentication>> getAllUsers() {
        try {
            List<Authentication> users = authService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<Authentication>> getUsersByRole(@PathVariable String role) {
        try {
            List<Authentication> users = authService.getUsersByRole(role.toUpperCase());
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Authentication> getUserById(@PathVariable Long id) {
        try {
            Authentication auth = authService.getUserById(id);
            if (auth != null) {
                return ResponseEntity.ok(auth);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody Authentication authRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            Authentication auth = authService.updateUser(id, authRequest);
            if (auth != null) {
                response.put("success", true);
                response.put("message", "User updated successfully");
                response.put("user", auth);
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

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean deleted = authService.deleteUser(id);
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
            stats.put("adminCount", authService.getAdminCount());
            stats.put("cashierCount", authService.getCashierCount());
            stats.put("totalUsers", authService.getAllUsers().size());
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
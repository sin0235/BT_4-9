package vn.iotstar.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.iotstar.entity.User;
import vn.iotstar.dao.UserDAO;

import java.util.List;

public class DatabaseSetup {
    
    public static void initializeDatabase() {
        UserDAO userDAO = new UserDAO();
        
        try {
            // Check if users already exist
            List<User> existingUsers = userDAO.findAll();
            if (existingUsers != null && !existingUsers.isEmpty()) {
                System.out.println("Database already has " + existingUsers.size() + " users. Skipping initialization.");
                return;
            }
            
            System.out.println("Database is empty. Creating demo users...");
            
            // Create demo users
            User user1 = new User("user1", "password123", "user1@example.com", "Người dùng 1", 1);
            User manager1 = new User("manager1", "password123", "manager1@example.com", "Quản lý 1", 2);
            User admin1 = new User("admin1", "password123", "admin1@example.com", "Quản trị viên 1", 3);
            User user2 = new User("user2", "password123", "user2@example.com", "Người dùng 2", 1);
            User manager2 = new User("manager2", "password123", "manager2@example.com", "Quản lý 2", 2);
            
            // Insert users
            boolean success = true;
            success &= userDAO.insert(user1);
            success &= userDAO.insert(manager1);
            success &= userDAO.insert(admin1);
            success &= userDAO.insert(user2);
            success &= userDAO.insert(manager2);
            
            if (success) {
                System.out.println("Demo users created successfully!");
            } else {
                System.err.println("Failed to create some demo users!");
            }
            
        } catch (Exception e) {
            System.err.println("Error during database initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void checkDatabaseStatus() {
        UserDAO userDAO = new UserDAO();
        
        try {
            System.out.println("=== Database Status Check ===");
            
            // Test connection and entity mapping
            List<User> users = userDAO.findAll();
            if (users != null) {
                System.out.println("✓ Database connection successful");
                System.out.println("✓ User entity mapping working");
                System.out.println("✓ Found " + users.size() + " users in database");
                
                for (User user : users) {
                    Boolean isActive = user.isActive();
                    System.out.println("  - " + user.getUsername() + " (Role: " + user.getRoleId() + ", Active: " + (isActive != null ? isActive : "NULL") + ")");
                }
            } else {
                System.err.println("✗ Failed to retrieve users - null result");
            }
            
            // Test specific user lookup
            User testUser = userDAO.findByUsername("user1");
            if (testUser != null) {
                System.out.println("✓ User lookup by username working");
                System.out.println("  - Found user1: " + testUser.getPassword());
            } else {
                System.err.println("✗ Could not find user1");
            }
            
            // Test login
            User loginTest = userDAO.findByUsernameAndPassword("user1", "password123");
            if (loginTest != null) {
                System.out.println("✓ Login test successful for user1/password123");
            } else {
                System.err.println("✗ Login test failed for user1/password123");
            }
            
        } catch (Exception e) {
            System.err.println("✗ Database check failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

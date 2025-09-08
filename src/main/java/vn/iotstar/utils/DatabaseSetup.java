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
            
            System.out.println("Creating demo users...");
            
            // Create demo users
            User[] demoUsers = {
                new User("user1", "password123", "user1@example.com", "Người dùng 1", 1),
                new User("manager1", "password123", "manager1@example.com", "Quản lý 1", 2),
                new User("admin1", "password123", "admin1@example.com", "Quản trị viên 1", 3),
                new User("user2", "password123", "user2@example.com", "Người dùng 2", 1),
                new User("manager2", "password123", "manager2@example.com", "Quản lý 2", 2)
            };
            
            boolean success = true;
            for (User user : demoUsers) {
                success &= userDAO.insert(user);
            }
            
            if (success) {
                System.out.println("✓ Demo users created successfully");
            } else {
                System.err.println("✗ Failed to create some demo users");
            }
            
        } catch (Exception e) {
            System.err.println("Error during database initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void checkDatabaseStatus() {
        UserDAO userDAO = new UserDAO();
        
        try {
            // Simple database connectivity check
            List<User> users = userDAO.findAll();
            if (users != null) {
                System.out.println("✓ Database connected - found " + users.size() + " users");
            } else {
                System.err.println("✗ Database connection failed");
            }
        } catch (Exception e) {
            System.err.println("✗ Database error: " + e.getMessage());
        }
    }
}

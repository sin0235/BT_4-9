-- MySQL Database Setup Script for Category Management System with User Authentication
-- Run this script in MySQL Workbench or MySQL Command Line

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS jpast4 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Use the database
USE jpast4;

-- Create Users table
CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    role_id INT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Create Category table with user relationship
CREATE TABLE IF NOT EXISTS Category (
    cate_id INT AUTO_INCREMENT PRIMARY KEY,
    cate_name VARCHAR(255) NOT NULL,
    icon_path VARCHAR(1000),
    icon_filename VARCHAR(255),
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_users_username ON Users(username);
CREATE INDEX idx_users_role_id ON Users(role_id);
CREATE INDEX idx_category_user_id ON Category(user_id);
CREATE INDEX idx_category_name ON Category(cate_name);

-- Insert sample users with different roles
-- Password: "password123" (in production, these should be properly hashed)
INSERT INTO Users (username, password, email, full_name, role_id) VALUES 
('user1', 'password123', 'user1@example.com', 'Người dùng 1', 1),
('manager1', 'password123', 'manager1@example.com', 'Quản lý 1', 2),
('admin1', 'password123', 'admin1@example.com', 'Quản trị viên 1', 3),
('user2', 'password123', 'user2@example.com', 'Người dùng 2', 1),
('manager2', 'password123', 'manager2@example.com', 'Quản lý 2', 2)
ON DUPLICATE KEY UPDATE 
    password = VALUES(password),
    email = VALUES(email),
    full_name = VALUES(full_name),
    role_id = VALUES(role_id);

-- Insert sample categories for different users
INSERT INTO Category (cate_name, icon_path, icon_filename, user_id) VALUES 
('Electronics', NULL, NULL, 1),
('Clothing', NULL, NULL, 1),
('Books', NULL, NULL, 2),
('Home & Garden', NULL, NULL, 2),
('Sports', NULL, NULL, 3),
('Food & Beverages', NULL, NULL, 3),
('Technology', NULL, NULL, 4),
('Fashion', NULL, NULL, 4),
('Health', NULL, NULL, 5),
('Travel', NULL, NULL, 5)
ON DUPLICATE KEY UPDATE 
    cate_name = VALUES(cate_name),
    user_id = VALUES(user_id);

-- Show the created tables and data
SHOW TABLES;
SELECT * FROM Users;
SELECT c.*, u.username, u.full_name, u.role_id 
FROM Category c 
JOIN Users u ON c.user_id = u.user_id 
ORDER BY u.role_id, u.username, c.cate_name;

DESCRIBE Users;
DESCRIBE Category;

-- Display role information
SELECT 
    role_id,
    CASE 
        WHEN role_id = 1 THEN 'User'
        WHEN role_id = 2 THEN 'Manager'
        WHEN role_id = 3 THEN 'Admin'
        ELSE 'Unknown'
    END as role_name,
    COUNT(*) as user_count
FROM Users 
GROUP BY role_id 
ORDER BY role_id;

-- MySQL Database Setup Script for Category Management System
-- Run this script in MySQL Workbench or MySQL Command Line

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS jpast4 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Use the database
USE jpast4;

-- Create Category table
CREATE TABLE IF NOT EXISTS Category (
    cate_id INT AUTO_INCREMENT PRIMARY KEY,
    cate_name VARCHAR(255) NOT NULL,
    icons VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample data
INSERT INTO Category (cate_name, icons) VALUES 
('Electronics', 'fa-laptop'),
('Clothing', 'fa-tshirt'),
('Books', 'fa-book'),
('Home & Garden', 'fa-home'),
('Sports', 'fa-football-ball'),
('Food & Beverages', 'fa-utensils'),
('Health & Beauty', 'fa-heart'),
('Automotive', 'fa-car');

-- Show the created table and data
SELECT * FROM Category;
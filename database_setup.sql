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
    icon_path VARCHAR(1000),
    icon_filename VARCHAR(255)
);

-- Create Image table for file upload functionality
CREATE TABLE IF NOT EXISTS images (
    image_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_name VARCHAR(255) NOT NULL,
    file_name VARCHAR(255) NOT NULL UNIQUE,
    file_path VARCHAR(1000) NOT NULL,
    file_size BIGINT,
    content_type VARCHAR(100),
    upload_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    description TEXT
);

-- Create indexes for better performance on images table
CREATE INDEX idx_images_upload_date ON images(upload_date);
CREATE INDEX idx_images_content_type ON images(content_type);
CREATE INDEX idx_images_file_name ON images(file_name);
CREATE INDEX idx_category_name ON Category(cate_name);

-- Insert sample data into Category table
INSERT INTO Category (cate_name, icon_path, icon_filename) VALUES 
('Electronics', NULL, NULL),
('Clothing', NULL, NULL),
('Books', NULL, NULL),
('Home & Garden', NULL, NULL),
('Sports', NULL, NULL),
('Food & Beverages', NULL, NULL)
ON DUPLICATE KEY UPDATE cate_name = VALUES(cate_name);

-- Show the created tables and data
SHOW TABLES;
SELECT * FROM Category;
DESCRIBE Category;
DESCRIBE images;
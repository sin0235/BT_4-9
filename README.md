# Category Management System - Setup Guide

## Prerequisites
1. **Java 17** or higher
2. **Apache Tomcat 11.0**
3. **MySQL 8.0** or higher
4. **Maven 3.6** or higher

## Database Setup

### Step 1: Install and Start MySQL
1. Install MySQL Server 8.0+
2. Start MySQL service
3. Open MySQL Workbench or command line

### Step 2: Run Database Script
Execute the `database_setup.sql` file:
```sql
-- This will create the database and sample data
source /path/to/database_setup.sql;
```

Or manually run these commands:
```sql
CREATE DATABASE IF NOT EXISTS jpast4 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE jpast4;
-- (rest of the script content)
```

## Project Setup

### Step 1: Clean and Build Project
```bash
cd /path/to/btweb
mvn clean compile
mvn package
```

### Step 2: Deploy to Tomcat
1. Copy the generated WAR file from `target/btweb-0.0.1-SNAPSHOT.war`
2. Place it in Tomcat's `webapps` directory
3. Start Tomcat server

### Step 3: Access Application
- Main page: `http://localhost:8080/btweb-0.0.1-SNAPSHOT/`
- Category management: `http://localhost:8080/btweb-0.0.1-SNAPSHOT/admin/category`

## Troubleshooting

### Common Issues:

1. **ExceptionInInitializerError**
   - Check MySQL service is running
   - Verify database connection settings in persistence.xml
   - Ensure database `jpast4` exists

2. **ClassNotFoundException**
   - Run `mvn clean compile package` to rebuild
   - Check all dependencies are downloaded

3. **404 Errors**
   - Verify servlet mappings in CategoryServlet.java
   - Check Tomcat deployment path

4. **Database Connection Issues**
   - Verify MySQL credentials (default: root with no password)
   - Check if port 3306 is accessible
   - Test connection with MySQL Workbench

### Logs to Check:
- Tomcat logs: `$TOMCAT_HOME/logs/catalina.out`
- Application logs: Check console output for JPA initialization messages

## Database Schema
```sql
CREATE TABLE Category (
    cate_id INT AUTO_INCREMENT PRIMARY KEY,
    cate_name VARCHAR(255) NOT NULL,
    icons VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## Features
- ✅ Create new categories
- ✅ View all categories
- ✅ Edit existing categories
- ✅ Delete categories
- ✅ Responsive web interface
- ✅ Input validation
- ✅ Error handling
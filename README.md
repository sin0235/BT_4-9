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

# Web Application with Image Upload

This is a Java web application built with Jakarta EE, JPA/Hibernate, and MySQL that includes category management and image upload functionality.

## Features

### Category Management
- Add, edit, delete categories
- View category list
- Icon support for categories

### Image Upload System
- Upload multiple image formats (.jpg, .jpeg, .png, .gif, .bmp)
- File size validation (max 5MB)
- Automatic file naming to prevent conflicts
- Image metadata storage (original name, file size, content type, upload date)
- Image description support
- Image gallery with grid view
- Delete uploaded images
- Direct image serving via URL

## Technology Stack

- **Backend**: Java 17, Jakarta EE, JPA/Hibernate
- **Database**: MySQL 8.0+
- **Build Tool**: Maven
- **Server**: Apache Tomcat 11.0
- **Frontend**: JSP, JSTL, HTML5, CSS3, JavaScript

## Database Setup

1. Create MySQL database:
```sql
CREATE DATABASE jpast4 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Run the database setup script:
```bash
mysql -u root -p jpast4 < database_setup.sql
```

Or execute the SQL commands in `database_setup.sql` manually.

## Configuration

Update database connection in `src/main/resources/META-INF/persistence.xml`:
```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/jpast4" />
<property name="jakarta.persistence.jdbc.user" value="your_username" />
<property name="jakarta.persistence.jdbc.password" value="your_password" />
```

## Build and Deploy

1. Build the project:
```bash
mvn clean compile
mvn package
```

2. Deploy the generated WAR file to Tomcat:
   - Copy `target/btweb-0.0.1-SNAPSHOT.war` to Tomcat's `webapps` directory
   - Start Tomcat server

## Usage

### Access the Application
- Main page: `http://localhost:8080/btweb/`
- Upload images: `http://localhost:8080/btweb/upload`
- View image gallery: `http://localhost:8080/btweb/images`
- Category management: `http://localhost:8080/btweb/categories`

### Image Upload Process
1. Navigate to the upload page
2. Select an image file (max 5MB)
3. Add optional description
4. Click "Upload" button
5. View uploaded image in the gallery

### File Organization
- Uploaded files are stored in: `webapps/btweb/uploads/`
- Each file gets a unique UUID-based name to prevent conflicts
- Original filenames and metadata are preserved in the database

## API Endpoints

- `GET /upload` - Show upload form
- `POST /upload` - Handle file upload
- `GET /images` - Show image gallery
- `POST /images` - Delete images
- `GET /image/{filename}` - Serve individual images
- `GET /categories` - Category management

## Security Features

- File type validation (only image formats allowed)
- File size limits (5MB maximum)
- Unique filename generation to prevent conflicts
- Input sanitization and validation

## Dependencies

Key Maven dependencies:
- Jakarta Servlet API 6.0.0
- Jakarta Persistence API 3.1.0
- Hibernate Core 6.2.3.Final
- MySQL Connector 8.1.0
- Apache Commons FileUpload 1.5
- Apache Commons IO 2.11.0
- JSTL 3.0.0

## Directory Structure

```
src/
├── main/
│   ├── java/vn/iotstar/
│   │   ├── controller/          # Servlets
│   │   ├── dao/                 # Data Access Objects
│   │   ├── entity/              # JPA Entities
│   │   └── utils/               # Utility classes
│   ├── resources/META-INF/      # JPA configuration
│   └── webapp/
│       ├── WEB-INF/views/       # JSP views
│       └── uploads/             # Uploaded files (created at runtime)
```

## Troubleshooting

### Common Issues

1. **Upload directory not found**: The application automatically creates the upload directory
2. **Database connection error**: Check MySQL service and connection settings
3. **File too large**: Increase limits in `@MultipartConfig` annotation
4. **Images not displaying**: Check file permissions and servlet mapping

### File Upload Limits

To change upload limits, modify the `@MultipartConfig` in `ImageUploadServlet.java`:
```java
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB threshold
    maxFileSize = 1024 * 1024 * 5,       // 5MB max file
    maxRequestSize = 1024 * 1024 * 10    // 10MB max request
)
```
# Category Management System with Icon Upload

This is a Java web application built with Jakarta EE, JPA/Hibernate, and MySQL that provides category management with image icon upload functionality.

## Features

### Category Management with Icon Upload
- Add, edit, delete categories
- Upload image icons for each category (.jpg, .jpeg, .png, .gif, .bmp)
- Icon preview in category list
- Automatic icon file management (unique naming, cleanup on delete)
- File size validation (max 2MB for icons)
- Image preview during upload

## Technology Stack

- **Backend**: Java 17, Jakarta EE, JPA/Hibernate
- **Database**: MySQL 8.0+
- **Build Tool**: Maven
- **Server**: Apache Tomcat 11.0
- **Frontend**: JSP, JSTL, HTML5, CSS3, JavaScript

## Database Setup

### Option 1: Fresh Installation
1. Create MySQL database:
```sql
CREATE DATABASE jpast4 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Run the main database setup script:
```bash
mysql -u root -p jpast4 < database_setup.sql
```

### Option 2: Update Existing Category Table
If you already have a Category table without icon support, run the migration:
```bash
mysql -u root -p jpast4 < migration_add_icon_support.sql
```

## Configuration

Update database connection in `src/main/resources/META-INF/persistence.xml`:
```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/jpast4" />
<property name="jakarta.persistence.jdbc.user" value="your_username" />
<property name="jakarta.persistence.jdbc.password" value="your_password" />
```

## Build and Deploy

1. Build the project:
```bash
mvn clean compile
mvn package
```

2. Deploy the generated WAR file to Tomcat:
   - Copy `target/btweb-0.0.1-SNAPSHOT.war` to Tomcat's `webapps` directory
   - Start Tomcat server

## Usage

### Access the Application
- **Main page**: `http://localhost:8080/btweb/`
- **Category management**: `http://localhost:8080/btweb/admin/category`
- **Add new category**: `http://localhost:8080/btweb/admin/category/edit`

### Category Icon Upload Process
1. Navigate to category management page
2. Click "Thêm Danh Mục Mới" or edit existing category
3. Enter category name
4. Select icon image file (max 2MB)
5. Preview image will appear automatically
6. Click "Thêm Mới" or "Cập Nhật"
7. Icon will be displayed in the category list

### File Organization
- Category icons are stored in: `webapps/btweb/category-icons/`
- Each icon gets a unique UUID-based filename to prevent conflicts
- Original filenames are preserved in the database
- Icons are automatically deleted when category is removed

## API Endpoints

- `GET /admin/category` - Show category list
- `GET /admin/category/edit` - Show add/edit form
- `POST /admin/category/create` - Create new category with icon
- `POST /admin/category/update` - Update category and icon
- `POST /admin/category/delete` - Delete category and its icon
- `GET /category-icons/{filename}` - Serve category icon images

## Security Features

- File type validation (only image formats allowed)
- File size limits (2MB maximum for icons)
- Unique filename generation to prevent conflicts
- Input sanitization and validation
- Automatic cleanup of orphaned icon files

## Database Schema

### Category Table
```sql
CREATE TABLE Category (
    cate_id INT AUTO_INCREMENT PRIMARY KEY,
    cate_name VARCHAR(255) NOT NULL,
    icon_path VARCHAR(1000),
    icon_filename VARCHAR(255)
);
```

## Directory Structure

```
src/
├── main/
│   ├── java/vn/iotstar/
│   │   ├── controller/admin/     # Category management servlets
│   │   ├── controller/           # Icon serving servlet
│   │   ├── dao/                  # Data Access Objects
│   │   ├── entity/               # JPA Entities
│   │   └── utils/                # Utility classes
│   ├── resources/META-INF/       # JPA configuration
│   └── webapp/
│       ├── WEB-INF/views/        # JSP views
│       └── category-icons/       # Uploaded icons (created at runtime)
```

## Troubleshooting

### Common Issues

1. **Icon upload fails**: Check file size (max 2MB) and format (images only)
2. **Icons not displaying**: Verify Tomcat permissions and servlet mapping
3. **Database connection error**: Check MySQL service and connection settings
4. **Upload directory not found**: Application creates directory automatically

### File Upload Limits

To change upload limits, modify the `@MultipartConfig` in `CategoryServlet.java`:
```java
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, // 1MB threshold
    maxFileSize = 1024 * 1024 * 2,       // 2MB max file
    maxRequestSize = 1024 * 1024 * 5     // 5MB max request
)
```

## Dependencies

Key Maven dependencies:
- Jakarta Servlet API 6.0.0
- Jakarta Persistence API 3.1.0
- Hibernate Core 6.2.3.Final
- MySQL Connector 8.1.0
- Apache Commons FileUpload 1.5
- Apache Commons IO 2.11.0
- JSTL 3.0.0

## Features Overview

### Icon Management
- **Upload**: Support for common image formats
- **Preview**: Real-time preview during upload
- **Validation**: File type and size validation
- **Storage**: Unique filename generation
- **Cleanup**: Automatic deletion when category is removed
- **Display**: Responsive icon display in category list

### User Interface
- **Responsive Design**: Works on desktop and mobile
- **File Validation**: Client-side and server-side validation
- **Progress Feedback**: Success/error messages
- **Icon Preview**: Live preview during upload
- **Intuitive Navigation**: Clear navigation between pages
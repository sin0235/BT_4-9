# 🚀 Hướng Dẫn Triển Khai Hệ Thống Quản Lý Danh Mục

## 📋 Tổng Quan Hệ Thống

Hệ thống quản lý danh mục với 3 role người dùng:
- **User (Role ID: 1)**: Xem tất cả danh mục
- **Manager (Role ID: 2)**: Quản lý danh mục của chính mình
- **Admin (Role ID: 3)**: Quản lý tất cả danh mục và xem thống kê

## 🗄️ Cài Đặt Database

### 1. Tạo Database
```sql
CREATE DATABASE jpast4 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Chạy Script Cài Đặt
Sử dụng file `database_setup_with_users.sql`:
```bash
mysql -u root -p jpast4 < database_setup_with_users.sql
```

### 3. Cấu Trúc Database
- **Users**: Quản lý người dùng và role
- **Category**: Quản lý danh mục với mối quan hệ với user

## 👥 Tài Khoản Demo

| Username | Password | Role | Mô tả |
|----------|----------|------|-------|
| user1 | password123 | User | Người dùng thường |
| manager1 | password123 | Manager | Quản lý danh mục |
| admin1 | password123 | Admin | Quản trị viên |

## 🌐 URL Structure

### Public URLs
- `/` - Trang chủ (chuyển hướng đến login)
- `/login` - Trang đăng nhập
- `/logout` - Đăng xuất

### User URLs (Role ID: 1)
- `/user/home` - Xem tất cả danh mục

### Manager URLs (Role ID: 2)
- `/manager/home` - Xem danh mục của mình
- `/manager/category/add` - Thêm danh mục mới
- `/manager/category/edit?id=X` - Sửa danh mục
- `/manager/category/view?id=X` - Xem chi tiết danh mục
- `/manager/category/delete` - Xóa danh mục

### Admin URLs (Role ID: 3)
- `/admin/home` - Dashboard admin với thống kê
- `/admin/category/add` - Thêm danh mục mới
- `/admin/category/edit?id=X` - Sửa danh mục
- `/admin/category/view?id=X` - Xem chi tiết danh mục
- `/admin/category/delete` - Xóa danh mục

## 🔒 Security Features

### Authentication Filter
- Bảo vệ tất cả URL `/user/*`, `/manager/*`, `/admin/*`
- Kiểm tra session và role của user
- Tự động chuyển hướng nếu không có quyền

### Role-Based Access Control
- **User**: Chỉ truy cập `/user/*`
- **Manager**: Truy cập `/user/*` và `/manager/*`
- **Admin**: Truy cập tất cả URL

### Data Security
- Manager chỉ thao tác với danh mục của chính mình
- Admin có thể thao tác với tất cả danh mục
- Validation ownership trước khi thực hiện CRUD

## 📁 File Upload

### Category Icons
- Thư mục: `webapps/btweb/category-icons/`
- Định dạng: .jpg, .jpeg, .png, .gif, .bmp
- Kích thước tối đa: 2MB
- Tự động tạo tên file unique

## 🚀 Deployment Steps

### 1. Build Project
```bash
mvn clean compile
mvn package
```

### 2. Deploy WAR
```bash
# Copy WAR file to Tomcat
cp target/btweb-0.0.1-SNAPSHOT.war $TOMCAT_HOME/webapps/
```

### 3. Configuration
Cập nhật `src/main/resources/META-INF/persistence.xml`:
```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/jpast4" />
<property name="jakarta.persistence.jdbc.user" value="your_username" />
<property name="jakarta.persistence.jdbc.password" value="your_password" />
```

### 4. Start Tomcat
```bash
$TOMCAT_HOME/bin/startup.sh
```

## 🔧 Testing

### 1. Access Application
- URL: `http://localhost:8080/btweb/`
- Sẽ chuyển hướng đến `/login`

### 2. Test Role Access
1. Đăng nhập với `user1/password123` → `/user/home`
2. Đăng nhập với `manager1/password123` → `/manager/home`
3. Đăng nhập với `admin1/password123` → `/admin/home`

### 3. Test CRUD Operations
- Manager: Chỉ thấy và thao tác danh mục của mình
- Admin: Thấy và thao tác tất cả danh mục

## 🎨 UI Features

### Modern Design
- Header màu xanh nhạt `#b8daed`
- Responsive layout
- Card-based design
- Hover effects

### Search Functionality
- Real-time search trong danh sách
- Filter theo tên danh mục

### User Experience
- Role-based navigation
- Breadcrumb navigation
- Success/Error messages
- Confirmation dialogs

## 📊 Database Schema

```sql
-- Users table
CREATE TABLE Users (
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

-- Category table
CREATE TABLE Category (
    cate_id INT AUTO_INCREMENT PRIMARY KEY,
    cate_name VARCHAR(255) NOT NULL,
    icon_path VARCHAR(1000),
    icon_filename VARCHAR(255),
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);
```

## 🔍 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Kiểm tra MySQL service
   - Verify connection string trong persistence.xml

2. **Access Denied**
   - Kiểm tra role của user
   - Xem log để debug authentication filter

3. **File Upload Error**
   - Kiểm tra quyền write cho thư mục upload
   - Verify file size và format

### Logs
- Tomcat logs: `$TOMCAT_HOME/logs/catalina.out`
- Application logs: Console output

## 📈 Future Enhancements

- Password encryption (BCrypt)
- User management interface
- Category export/import
- Advanced search and filtering
- Email notifications
- API endpoints
- Multi-language support

---

🎉 **Hệ thống đã sẵn sàng để sử dụng!**

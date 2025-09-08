<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Category Management System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .nav-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        .nav-card {
            background: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 8px;
            padding: 20px;
            text-align: center;
            transition: all 0.3s ease;
        }
        .nav-card:hover {
            background: #e9ecef;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .nav-card a {
            color: #007bff;
            text-decoration: none;
            font-size: 18px;
            font-weight: bold;
        }
        .nav-card a:hover {
            color: #0056b3;
        }
        .nav-card p {
            color: #666;
            margin-top: 10px;
            font-size: 14px;
        }
        .icon {
            font-size: 48px;
            margin-bottom: 15px;
            display: block;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Hệ Thống Quản Lý Danh Mục</h1>
        
        <div class="nav-grid">
            <div class="nav-card">
                <span class="icon">📁</span>
                <a href="${pageContext.request.contextPath}/admin/category">Quản Lý Danh Mục</a>
                <p>Thêm, sửa, xóa danh mục với icon ảnh</p>
            </div>
        </div>
    </div>
</body>
</html>
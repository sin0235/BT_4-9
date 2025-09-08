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
            background-color: #f8f9fa;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
        }
        .welcome-card {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            overflow: hidden;
            margin-bottom: 30px;
        }
        .card-header {
            background-color: #b8daed;
            padding: 20px;
            text-align: center;
        }
        .card-header h1 {
            color: #2c3e50;
            margin: 0;
            font-size: 24px;
            font-weight: normal;
        }
        .card-content {
            padding: 30px;
        }
        .nav-links {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        .nav-link {
            display: block;
            padding: 15px 20px;
            background: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 6px;
            text-decoration: none;
            color: #333;
            transition: all 0.3s ease;
            text-align: center;
            font-size: 16px;
        }
        .nav-link:hover {
            background: #e9ecef;
            border-color: #007bff;
            transform: translateY(-1px);
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        .nav-link.primary {
            background: #007bff;
            color: white;
            border-color: #007bff;
        }
        .nav-link.primary:hover {
            background: #0056b3;
            border-color: #004085;
        }
        .description {
            text-align: center;
            color: #666;
            margin-bottom: 25px;
            line-height: 1.5;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="welcome-card">
            <div class="card-header">
                <h1>Hệ Thống Quản Lý Danh Mục</h1>
            </div>
            <div class="card-content">
                <div class="description">
                    Chào mừng bạn đến với hệ thống quản lý danh mục.<br>
                    Chọn chức năng bạn muốn sử dụng:
                </div>
                
                <div class="nav-links">
                    <a href="${pageContext.request.contextPath}/admin/category" class="nav-link primary">
                        📁 Quản Lý Danh Mục
                    </a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
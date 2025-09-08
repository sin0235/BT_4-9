<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Danh Mục</title>
    <style>
        body { 
            font-family: Arial, sans-serif; 
            margin: 20px; 
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .nav-links {
            margin-bottom: 20px;
        }
        .nav-links a {
            color: #007bff;
            text-decoration: none;
            margin-right: 15px;
        }
        .nav-links a:hover {
            text-decoration: underline;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .btn { 
            padding: 10px 15px; 
            background-color: #007bff; 
            color: white; 
            border: none; 
            border-radius: 4px; 
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
        }
        .btn:hover { 
            background-color: #0056b3; 
        }
        .btn-success {
            background-color: #28a745;
        }
        .btn-success:hover {
            background-color: #218838;
        }
        .btn-danger {
            background-color: #dc3545;
        }
        .btn-danger:hover {
            background-color: #c82333;
        }
        .btn-sm {
            padding: 5px 10px;
            font-size: 12px;
        }
        table { 
            width: 100%; 
            border-collapse: collapse; 
            margin-top: 20px;
        }
        th, td { 
            padding: 12px; 
            text-align: left; 
            border-bottom: 1px solid #ddd; 
        }
        th { 
            background-color: #f8f9fa; 
            font-weight: bold;
        }
        tr:hover {
            background-color: #f8f9fa;
        }
        .category-icon {
            width: 50px;
            height: 50px;
            object-fit: cover;
            border-radius: 4px;
            border: 1px solid #ddd;
        }
        .no-icon {
            width: 50px;
            height: 50px;
            background-color: #e9ecef;
            border: 1px solid #ddd;
            border-radius: 4px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #6c757d;
            font-size: 12px;
        }
        .actions {
            white-space: nowrap;
        }
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .alert-error {
            background-color: #f8d7da;
            border-color: #f5c6cb;
            color: #721c24;
        }
        .alert-success {
            background-color: #d4edda;
            border-color: #c3e6cb;
            color: #155724;
        }
        .empty-state {
            text-align: center;
            padding: 50px 20px;
            color: #666;
        }
        .stats {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/">Trang chủ</a>
        <a href="${pageContext.request.contextPath}/upload">Upload ảnh</a>
        <a href="${pageContext.request.contextPath}/images">Thư viện ảnh</a>
    </div>

    <div class="container">
        <div class="header">
            <h1>Quản Lý Danh Mục</h1>
            <a href="${pageContext.request.contextPath}/admin/category/edit" class="btn btn-success">
                + Thêm Danh Mục Mới
            </a>
        </div>
        
        <c:if test="${not empty error}">
            <div class="alert alert-error">
                ${error}
            </div>
        </c:if>
        
        <c:if test="${not empty success}">
            <div class="alert alert-success">
                ${success}
            </div>
        </c:if>
        
        <c:choose>
            <c:when test="${not empty categories}">
                <div class="stats">
                    <strong>Tổng số danh mục:</strong> ${categories.size()}
                </div>
                
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Icon</th>
                            <th>Tên Danh Mục</th>
                            <th>Thao Tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="category" items="${categories}">
                            <tr>
                                <td>${category.cateid}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty category.iconFilename}">
                                            <img src="${pageContext.request.contextPath}/category-icons/${category.iconFilename}" 
                                                 alt="Icon" class="category-icon" 
                                                 title="Icon: ${category.iconFilename}">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="no-icon">No Icon</div>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <strong>${category.catename}</strong>
                                    <c:if test="${not empty category.iconFilename}">
                                        <br><small style="color: #666;">File: ${category.iconFilename}</small>
                                    </c:if>
                                </td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/admin/category/edit?id=${category.cateid}" 
                                       class="btn btn-sm">Sửa</a>
                                    <a href="javascript:void(0)" 
                                       onclick="deleteCategory(${category.cateid}, '${category.catename}')"
                                       class="btn btn-danger btn-sm">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <h2>Chưa có danh mục nào</h2>
                    <p>Hãy <a href="${pageContext.request.contextPath}/admin/category/edit">thêm danh mục đầu tiên</a> của bạn!</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <script>
        function deleteCategory(id, name) {
            if (confirm('Bạn có chắc chắn muốn xóa danh mục "' + name + '"?\nHành động này không thể hoàn tác!')) {
                // Create form and submit
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = '${pageContext.request.contextPath}/admin/category/delete';
                
                const idInput = document.createElement('input');
                idInput.type = 'hidden';
                idInput.name = 'id';
                idInput.value = id;
                
                form.appendChild(idInput);
                document.body.appendChild(form);
                form.submit();
            }
        }
    </script>
</body>
</html>
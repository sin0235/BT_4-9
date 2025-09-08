<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Category List</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        a { text-decoration: none; color: #007bff; }
        a:hover { text-decoration: underline; }
        .btn { padding: 10px 15px; background-color: #007bff; color: white; border: none; border-radius: 4px; }
    </style>
</head>
<body>
    <h1>Category Management System</h1>
    <a href="${pageContext.request.contextPath}/admin/category/edit" class="btn">Add New Category</a>
    <br><br>
    
    <c:if test="${empty list}">
        <p>No categories found. <a href="${pageContext.request.contextPath}/admin/category/edit">Add the first category</a></p>
    </c:if>
    
    <c:if test="${not empty list}">
        <table>
            <tr>
                <th>ID</th>
                <th>Category Name</th>
                <th>Icon</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="item" items="${list}">
                <tr>
                    <td>${item.cateid}</td>
                    <td>${item.catename}</td>
                    <td>${item.icon}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/category/edit?id=${item.cateid}">Edit</a>
                        |
                        <a href="${pageContext.request.contextPath}/admin/category/delete?id=${item.cateid}"
                           onclick="return confirm('Are you sure you want to delete this category?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</body>
</html>
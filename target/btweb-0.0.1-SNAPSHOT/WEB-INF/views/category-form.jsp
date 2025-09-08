<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Category Form</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        form { max-width: 400px; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input[type="text"] { width: 100%; padding: 8px; margin: 5px 0; border: 1px solid #ddd; border-radius: 4px; }
        button { padding: 10px 15px; background-color: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; margin-right: 10px; }
        button:hover { background-color: #218838; }
        .cancel { background-color: #6c757d; }
        .cancel:hover { background-color: #5a6268; }
    </style>
</head>
<body>
    <h1>${category != null ? 'Edit Category' : 'Add New Category'}</h1>
    
    <form method="post" action="${pageContext.request.contextPath}/admin/category/${category != null ? 'update' : 'create'}">
        <c:if test="${category != null}">
            <input type="hidden" name="cateid" value="${category.cateid}" />
        </c:if>
        
        <label for="catename">Category Name:</label>
        <input type="text" id="catename" name="catename" value="${category.catename}" required 
               placeholder="Enter category name"/>
        
        <label for="icon">Icon (CSS class or icon name):</label>
        <input type="text" id="icon" name="icon" value="${category.icon}" 
               placeholder="e.g., fa-laptop, fa-book"/>
        
        <br><br>
        <button type="submit">${category != null ? 'Update' : 'Create'}</button>
        <a href="${pageContext.request.contextPath}/admin/category" class="cancel" style="padding: 10px 15px; text-decoration: none; color: white;">Cancel</a>
    </form>
</body>
</html>
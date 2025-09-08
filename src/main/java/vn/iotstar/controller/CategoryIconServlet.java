package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/category-icons/*")
public class CategoryIconServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        String fileName = pathInfo.substring(1); // Remove leading slash
        
        try {
            // Build path to icon file
            Path iconPath = Paths.get(getServletContext().getRealPath(""), "category-icons", fileName);
            
            if (!Files.exists(iconPath)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            
            // Determine content type based on file extension
            String contentType = getServletContext().getMimeType(fileName);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            
            // Set response headers
            response.setContentType(contentType);
            response.setContentLengthLong(Files.size(iconPath));
            
            // Set cache headers for better performance
            response.setHeader("Cache-Control", "public, max-age=86400"); // 1 day
            
            // Write file data to response
            Files.copy(iconPath, response.getOutputStream());
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
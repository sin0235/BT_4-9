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

@WebServlet("/uploads/*")
public class ImageServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        String fileName = pathInfo.substring(1); // Remove leading slash
        
        // Validate filename to prevent directory traversal
        if (fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        try {
            // Build path to uploaded file
            Path imagePath = Paths.get(getServletContext().getRealPath(""), "uploads", fileName);
            
            if (!Files.exists(imagePath)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            
            // Determine content type based on file extension
            String contentType = getServletContext().getMimeType(fileName);
            if (contentType == null) {
                // Set default content type for images
                String lowerFileName = fileName.toLowerCase();
                if (lowerFileName.endsWith(".jpg") || lowerFileName.endsWith(".jpeg")) {
                    contentType = "image/jpeg";
                } else if (lowerFileName.endsWith(".png")) {
                    contentType = "image/png";
                } else if (lowerFileName.endsWith(".gif")) {
                    contentType = "image/gif";
                } else if (lowerFileName.endsWith(".bmp")) {
                    contentType = "image/bmp";
                } else {
                    contentType = "application/octet-stream";
                }
            }
            
            // Set response headers
            response.setContentType(contentType);
            response.setContentLengthLong(Files.size(imagePath));
            
            // Set cache headers for better performance
            response.setHeader("Cache-Control", "public, max-age=86400"); // 1 day
            response.setHeader("Expires", String.valueOf(System.currentTimeMillis() + 86400000L));
            
            // Write file data to response
            Files.copy(imagePath, response.getOutputStream());
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

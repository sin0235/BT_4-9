package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vn.iotstar.dao.UserDAO;
import vn.iotstar.dao.CategoryDAO;
import vn.iotstar.entity.User;
import vn.iotstar.utils.FileUtils;

import java.io.IOException;

@WebServlet(urlPatterns = {"/profile", "/profile/edit", "/profile/update"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 1024 * 1024 * 5,   // 5MB
    maxRequestSize = 1024 * 1024 * 10 // 10MB
)
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();
        
        switch (path) {
            case "/profile":
                showProfile(request, response, user);
                break;
            case "/profile/edit":
                showEditProfile(request, response, user);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/profile");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();
        
        if ("/profile/update".equals(path)) {
            updateProfile(request, response, user, session);
        } else {
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }

    private void showProfile(HttpServletRequest request, HttpServletResponse response, User user) 
            throws ServletException, IOException {
        
        // Get latest user data from database
        User latestUser = userDAO.findById(user.getUserId());
        if (latestUser != null) {
            request.setAttribute("user", latestUser);
        } else {
            request.setAttribute("user", user);
        }
        
        // Get categories count for this user
        CategoryDAO categoryDAO = new CategoryDAO();
        long categoriesCount = categoryDAO.countByUserId(user.getUserId());
        request.setAttribute("categoriesCount", categoriesCount);
        
        request.getRequestDispatcher("/WEB-INF/views/profile/profile.jsp").forward(request, response);
    }

    private void showEditProfile(HttpServletRequest request, HttpServletResponse response, User user) 
            throws ServletException, IOException {
        
        // Get latest user data from database
        User latestUser = userDAO.findById(user.getUserId());
        if (latestUser != null) {
            request.setAttribute("user", latestUser);
        } else {
            request.setAttribute("user", user);
        }
        
        request.getRequestDispatcher("/WEB-INF/views/profile/edit-profile.jsp").forward(request, response);
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response, 
                             User user, HttpSession session) throws ServletException, IOException {
        
        try {
            // Get form parameters
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            
            // Validate required fields
            if (fullName == null || fullName.trim().isEmpty()) {
                request.setAttribute("error", "Full name is required");
                showEditProfile(request, response, user);
                return;
            }

            // Get latest user data from database
            User userToUpdate = userDAO.findById(user.getUserId());
            if (userToUpdate == null) {
                request.setAttribute("error", "User not found");
                showEditProfile(request, response, user);
                return;
            }

            // Update basic information
            userToUpdate.setFullName(fullName.trim());
            userToUpdate.setPhone(phone != null ? phone.trim() : null);
            
            // Update the updatedAt field manually since @PreUpdate might not trigger
            userToUpdate.setUpdatedAt(java.time.LocalDateTime.now());

            // Handle file upload
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                System.out.println("Processing file upload. File size: " + filePart.getSize() + " bytes");
                try {
                    String fileName = FileUtils.saveUploadedFile(filePart, getServletContext());
                    if (fileName != null) {
                        System.out.println("File saved successfully: " + fileName);
                        // Delete old image if exists
                        if (userToUpdate.getImage() != null && !userToUpdate.getImage().isEmpty()) {
                            System.out.println("Deleting old image: " + userToUpdate.getImage());
                            FileUtils.deleteUploadedFile(userToUpdate.getImage(), getServletContext());
                        }
                        userToUpdate.setImage(fileName);
                    } else {
                        System.out.println("Failed to save uploaded file");
                    }
                } catch (IOException e) {
                    System.err.println("Error saving file: " + e.getMessage());
                    request.setAttribute("error", "Failed to upload image: " + e.getMessage());
                    showEditProfile(request, response, userToUpdate);
                    return;
                }
            } else {
                System.out.println("No file uploaded or file is empty");
            }

            // Update user in database
            System.out.println("Updating user in database. User ID: " + userToUpdate.getUserId());
            boolean success = userDAO.update(userToUpdate);
            
            if (success) {
                System.out.println("User updated successfully in database");
                // Update session with new user data
                session.setAttribute("user", userToUpdate);
                request.setAttribute("message", "Profile updated successfully!");
                showProfile(request, response, userToUpdate);
            } else {
                System.err.println("Failed to update user in database");
                request.setAttribute("error", "Failed to update profile. Please try again.");
                showEditProfile(request, response, userToUpdate);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while updating profile: " + e.getMessage());
            showEditProfile(request, response, user);
        }
    }
}

package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.dao.UserDAO;
import vn.iotstar.entity.User;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UserDAO userDAO = new UserDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Kiểm tra nếu đã đăng nhập
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            redirectByRole(response, user.getRoleId());
            return;
        }
        
        // Hiển thị trang đăng nhập
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Validate input
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin đăng nhập");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        
        try {
            // Authenticate user
            User user = userDAO.findByUsernameAndPassword(username.trim(), password);
            
            if (user != null) {
                // Login successful
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("roleId", user.getRoleId());
                session.setAttribute("roleName", user.getRoleName());
                session.setAttribute("fullName", user.getFullName());
                
                // Set session timeout (30 minutes)
                session.setMaxInactiveInterval(30 * 60);
                
                // Redirect based on role
                redirectByRole(response, user.getRoleId());
            } else {
                // Login failed
                request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
                request.setAttribute("username", username); // Keep username in form
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi trong quá trình đăng nhập. Vui lòng thử lại.");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
    
    private void redirectByRole(HttpServletResponse response, int roleId) throws IOException {
        String contextPath = getServletContext().getContextPath();
        
        switch (roleId) {
            case 1: // User
                response.sendRedirect(contextPath + "/user/home");
                break;
            case 2: // Manager
                response.sendRedirect(contextPath + "/manager/home");
                break;
            case 3: // Admin
                response.sendRedirect(contextPath + "/admin/home");
                break;
            default:
                response.sendRedirect(contextPath + "/login");
                break;
        }
    }
}

package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;

import java.io.IOException;

@WebServlet(urlPatterns = {"", "/"})
public class IndexRedirectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Kiểm tra nếu đã đăng nhập
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            // Chuyển hướng theo role
            redirectByRole(response, user.getRoleId(), request.getContextPath());
        } else {
            // Chưa đăng nhập, chuyển đến trang login
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    private void redirectByRole(HttpServletResponse response, int roleId, String contextPath) throws IOException {
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

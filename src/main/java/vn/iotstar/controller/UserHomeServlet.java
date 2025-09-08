package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.dao.CategoryDAO;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/home")
public class UserHomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private CategoryDAO categoryDAO = new CategoryDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            // User role sees all categories
            List<Category> categories = categoryDAO.findAll();
            
            request.setAttribute("categories", categories);
            request.setAttribute("currentUser", user);
            request.setAttribute("pageTitle", "Trang Chủ - User");
            
            request.getRequestDispatcher("/WEB-INF/views/user/home.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi khi tải dữ liệu");
            request.getRequestDispatcher("/WEB-INF/views/user/home.jsp").forward(request, response);
        }
    }
}

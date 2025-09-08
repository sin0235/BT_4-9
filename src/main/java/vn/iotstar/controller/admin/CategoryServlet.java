package vn.iotstar.controller.admin;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.dao.CategoryDAO;
import vn.iotstar.entity.Category;

@WebServlet({
    "/admin/category",
    "/admin/category/edit",
    "/admin/category/create",
    "/admin/category/update",
    "/admin/category/delete"
})
public class CategoryServlet extends HttpServlet {

    private CategoryDAO dao;

    @Override
    public void init() {
        dao = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.contains("edit")) {
            // Hiển thị form để chỉnh sửa hoặc thêm mới
            String idStr = req.getParameter("id");
            if (idStr != null) {
                Integer id = Integer.valueOf(idStr);
                Category entity = dao.findById(id);
                req.setAttribute("category", entity);
            }
            // forward tới form
            req.getRequestDispatcher("/WEB-INF/views/category-form.jsp").forward(req, resp);
        } else if (uri.contains("delete")) {
            // Xóa theo id và redirect
            Integer id = Integer.valueOf(req.getParameter("id"));
            dao.remove(id);
            resp.sendRedirect(req.getContextPath() + "/admin/category");
        } else {
            // Mặc định hiển thị danh sách
            List<Category> list = dao.findAll();
            req.setAttribute("list", list);
            req.getRequestDispatcher("/WEB-INF/views/category-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();
        req.setCharacterEncoding("UTF-8");
        if (uri.contains("create")) {
            // lấy dữ liệu form
            String name = req.getParameter("catename");
            String icon = req.getParameter("icon");
            Category entity = new Category(name, icon);
            dao.create(entity);
            resp.sendRedirect(req.getContextPath() + "/admin/category");
        } else if (uri.contains("update")) {
            Integer id = Integer.valueOf(req.getParameter("cateid"));
            String name = req.getParameter("catename");
            String icon = req.getParameter("icon");
            Category entity = dao.findById(id);
            entity.setCatename(name);
            entity.setIcon(icon);
            dao.update(entity);
            resp.sendRedirect(req.getContextPath() + "/admin/category");
        }
    }
}
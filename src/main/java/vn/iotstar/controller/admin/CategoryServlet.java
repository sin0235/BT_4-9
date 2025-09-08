package vn.iotstar.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.dao.CategoryDAO;
import vn.iotstar.entity.Category;
import vn.iotstar.utils.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/category", "/admin/category/create", "/admin/category/edit", 
                          "/admin/category/update", "/admin/category/delete"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, // 1MB
    maxFileSize = 1024 * 1024 * 2,       // 2MB for icons
    maxRequestSize = 1024 * 1024 * 5     // 5MB
)
public class CategoryServlet extends HttpServlet {
    
    private CategoryDAO categoryDao = new CategoryDAO();
    private static final String CATEGORY_ICONS_DIR = "category-icons";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String path = request.getServletPath();
        
        try {
            switch (path) {
                case "/admin/category":
                    showCategoryList(request, response);
                    break;
                case "/admin/category/edit":
                    showEditForm(request, response);
                    break;
                default:
                    showCategoryList(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            showCategoryList(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String path = request.getServletPath();
        
        try {
            switch (path) {
                case "/admin/category/create":
                    createCategory(request, response);
                    break;
                case "/admin/category/update":
                    updateCategory(request, response);
                    break;
                case "/admin/category/delete":
                    deleteCategory(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/admin/category");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            showCategoryList(request, response);
        }
    }
    
    private void showCategoryList(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Category> categories = categoryDao.findAll();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/views/category-list.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null) {
            Category category = categoryDao.findById(Integer.parseInt(id));
            request.setAttribute("category", category);
        }
        request.getRequestDispatcher("/WEB-INF/views/category-form.jsp").forward(request, response);
    }
    
    private void createCategory(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String catename = request.getParameter("catename");
        
        if (catename == null || catename.trim().isEmpty()) {
            request.setAttribute("error", "Tên danh mục không được để trống!");
            request.getRequestDispatcher("/WEB-INF/views/category-form.jsp").forward(request, response);
            return;
        }
        
        // Check if category name already exists
        if (categoryDao.findByName(catename) != null) {
            request.setAttribute("error", "Tên danh mục đã tồn tại!");
            request.getRequestDispatcher("/WEB-INF/views/category-form.jsp").forward(request, response);
            return;
        }
        
        Category category = new Category(catename);
        
        // Handle icon upload
        String[] iconInfo = handleIconUpload(request);
        if (iconInfo != null) {
            category.setIconPath(iconInfo[0]);
            category.setIconFilename(iconInfo[1]);
        }
        
        categoryDao.create(category);
        request.setAttribute("success", "Thêm danh mục thành công!");
        response.sendRedirect(request.getContextPath() + "/admin/category");
    }
    
    private void updateCategory(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String id = request.getParameter("cateid");
        String catename = request.getParameter("catename");
        
        if (id == null || catename == null || catename.trim().isEmpty()) {
            request.setAttribute("error", "Thông tin không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/admin/category");
            return;
        }
        
        Category category = categoryDao.findById(Integer.parseInt(id));
        if (category == null) {
            request.setAttribute("error", "Không tìm thấy danh mục!");
            response.sendRedirect(request.getContextPath() + "/admin/category");
            return;
        }
        
        // Check if new name conflicts with existing categories (excluding current one)
        Category existingCategory = categoryDao.findByName(catename);
        if (existingCategory != null && !existingCategory.getCateid().equals(category.getCateid())) {
            request.setAttribute("error", "Tên danh mục đã tồn tại!");
            request.setAttribute("category", category);
            request.getRequestDispatcher("/WEB-INF/views/category-form.jsp").forward(request, response);
            return;
        }
        
        category.setCatename(catename);
        
        // Handle icon upload
        String[] iconInfo = handleIconUpload(request);
        if (iconInfo != null) {
            // Delete old icon if exists
            if (category.getIconPath() != null) {
                FileUtils.deleteFile(category.getIconPath());
            }
            category.setIconPath(iconInfo[0]);
            category.setIconFilename(iconInfo[1]);
        }
        
        categoryDao.update(category);
        response.sendRedirect(request.getContextPath() + "/admin/category");
    }
    
    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String id = request.getParameter("id");
        if (id != null) {
            Category category = categoryDao.findById(Integer.parseInt(id));
            if (category != null) {
                // Delete icon file if exists
                if (category.getIconPath() != null) {
                    FileUtils.deleteFile(category.getIconPath());
                }
                categoryDao.remove(Integer.parseInt(id));
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/category");
    }
    
    private String[] handleIconUpload(HttpServletRequest request) throws IOException, ServletException {
        Part iconPart = request.getPart("icon");
        
        if (iconPart == null || iconPart.getSize() == 0) {
            return null;
        }
        
        String originalFileName = getFileName(iconPart);
        
        // Validate file
        if (!FileUtils.isValidImageFile(originalFileName)) {
            throw new ServletException("Chỉ chấp nhận file ảnh (.jpg, .jpeg, .png, .gif, .bmp)!");
        }
        
        if (!FileUtils.isValidFileSize(iconPart.getSize())) {
            throw new ServletException("File quá lớn! Kích thước tối đa là 2MB cho icon.");
        }
        
        // Create upload directory
        String uploadDir = FileUtils.createUploadDirectory(
            Paths.get(getServletContext().getRealPath(""), CATEGORY_ICONS_DIR).toString());
        
        // Generate unique filename
        String uniqueFileName = FileUtils.generateUniqueFileName(originalFileName);
        String filePath = Paths.get(uploadDir, uniqueFileName).toString();
        
        // Save file to disk
        try (InputStream inputStream = iconPart.getInputStream()) {
            byte[] fileContent = inputStream.readAllBytes();
            FileUtils.saveFile(fileContent, filePath);
        }
        
        return new String[]{filePath, uniqueFileName};
    }
    
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] tokens = contentDisposition.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "";
    }
}
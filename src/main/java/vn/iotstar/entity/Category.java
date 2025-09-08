package vn.iotstar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cate_id")
    private Integer cateid;

    @Column(name = "cate_name")
    private String catename;

    @Column(name = "icon_path")
    private String iconPath;
    
    @Column(name = "icon_filename")
    private String iconFilename;

    // Constructors
    public Category() {}
    
    public Category(String catename) {
        this.catename = catename;
    }
    
    public Category(String catename, String iconPath, String iconFilename) {
        this.catename = catename;
        this.iconPath = iconPath;
        this.iconFilename = iconFilename;
    }

    // Getters and setters
    public Integer getCateid() { 
        return cateid; 
    }
    
    public void setCateid(Integer cateid) { 
        this.cateid = cateid; 
    }

    public String getCatename() { 
        return catename; 
    }
    
    public void setCatename(String catename) { 
        this.catename = catename; 
    }

    public String getIconPath() { 
        return iconPath; 
    }
    
    public void setIconPath(String iconPath) { 
        this.iconPath = iconPath; 
    }
    
    public String getIconFilename() { 
        return iconFilename; 
    }
    
    public void setIconFilename(String iconFilename) { 
        this.iconFilename = iconFilename; 
    }
    
    // Helper method to get full icon URL
    public String getIconUrl() {
        return iconFilename != null ? "/category-icons/" + iconFilename : null;
    }
}
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

    @Column(name = "icons")
    private String icon;

    // Constructors
    public Category() {}
    public Category(String catename, String icon) {
        this.catename = catename;
        this.icon = icon;
    }

    // Getters and setters
    public Integer getCateid() { return cateid; }
    public void setCateid(Integer cateid) { this.cateid = cateid; }

    public String getCatename() { return catename; }
    public void setCatename(String catename) { this.catename = catename; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}
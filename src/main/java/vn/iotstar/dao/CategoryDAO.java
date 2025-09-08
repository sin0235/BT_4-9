package vn.iotstar.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.iotstar.entity.Category;
import vn.iotstar.utils.JPAConfig;

public class CategoryDAO {

    public Category create(Category entity) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);  // thêm mới
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Category update(Category entity) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            Category result = em.merge(entity); // cập nhật
            em.getTransaction().commit();
            return result;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void remove(Integer id) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            Category entity = em.find(Category.class, id);
            if (entity != null) {
                em.remove(entity); // xóa
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Category findById(Integer id) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c LEFT JOIN FETCH c.user WHERE c.cateid = :id", 
                Category.class);
            query.setParameter("id", id);
            List<Category> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    public List<Category> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT c FROM Category c LEFT JOIN FETCH c.user ORDER BY c.catename";
            TypedQuery<Category> query = em.createQuery(jpql, Category.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Category> findByUserId(Integer userId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT c FROM Category c LEFT JOIN FETCH c.user WHERE c.userId = :userId ORDER BY c.catename";
            TypedQuery<Category> query = em.createQuery(jpql, Category.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Category findByIdAndUserId(Integer categoryId, Integer userId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c LEFT JOIN FETCH c.user WHERE c.cateid = :categoryId AND c.userId = :userId", 
                Category.class);
            query.setParameter("categoryId", categoryId);
            query.setParameter("userId", userId);
            List<Category> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }
    
    public boolean isOwner(Integer categoryId, Integer userId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(c) FROM Category c WHERE c.cateid = :categoryId AND c.userId = :userId", 
                Long.class);
            query.setParameter("categoryId", categoryId);
            query.setParameter("userId", userId);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
    
    public long countByUserId(Integer userId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(c) FROM Category c WHERE c.userId = :userId", 
                Long.class);
            query.setParameter("userId", userId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public Category findByName(String catename) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c WHERE c.catename = :catename", Category.class);
            query.setParameter("catename", catename);
            List<Category> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }
    
    public Category findByNameAndUserId(String catename, Integer userId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c WHERE c.catename = :catename AND c.userId = :userId", 
                Category.class);
            query.setParameter("catename", catename);
            query.setParameter("userId", userId);
            List<Category> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }
}
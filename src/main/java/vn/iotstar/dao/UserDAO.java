package vn.iotstar.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.iotstar.entity.User;
import vn.iotstar.utils.JPAConfig;

import java.util.List;

public class UserDAO {
    
    public User findByUsername(String username) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            List<User> users = query.getResultList();
            return users.isEmpty() ? null : users.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
    
    public User findByUsernameAndPassword(String username, String password) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.username = :username AND u.password = :password AND u.isActive = true", 
                User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            List<User> users = query.getResultList();
            return users.isEmpty() ? null : users.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
    
    public User findById(int userId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(User.class, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<User> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u ORDER BY u.createdAt DESC", User.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<User> findByRole(int roleId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.roleId = :roleId AND u.isActive = true ORDER BY u.fullName", 
                User.class);
            query.setParameter("roleId", roleId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
    
    public boolean insert(User user) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    
    public boolean update(User user) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    
    public boolean delete(int userId) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            User user = em.find(User.class, userId);
            if (user != null) {
                em.remove(user);
            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    
    public boolean deactivateUser(int userId) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            User user = em.find(User.class, userId);
            if (user != null) {
                user.setActive(false);
                em.merge(user);
            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    
    public long countByRole(int roleId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.roleId = :roleId AND u.isActive = true", 
                Long.class);
            query.setParameter("roleId", roleId);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }
}

package vn.iotstar.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

public class DatabaseFixUtil {
    
    public static void fixIsActiveNullValues() {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            
            // Update all NULL is_active values to true
            Query updateQuery = em.createNativeQuery(
                "UPDATE Users SET is_active = 1 WHERE is_active IS NULL"
            );
            int updated = updateQuery.executeUpdate();
            
            transaction.commit();
            System.out.println("✓ Fixed " + updated + " NULL is_active values in database");
            
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("✗ Failed to fix is_active NULL values: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}

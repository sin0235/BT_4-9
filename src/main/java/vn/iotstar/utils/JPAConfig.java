package vn.iotstar.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfig {
    private static EntityManagerFactory factory;
    
    static {
        try {
            factory = Persistence.createEntityManagerFactory("dataSource");
            System.out.println("JPA EntityManagerFactory initialized successfully");
            
            // Check database status and initialize if needed
            DatabaseSetup.checkDatabaseStatus();
            DatabaseSetup.initializeDatabase();
            
            // Fix any NULL is_active values
            DatabaseFixUtil.fixIsActiveNullValues();
            
        } catch (Exception e) {
            System.err.println("Failed to initialize JPA EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static EntityManager getEntityManager() {
        if (factory == null) {
            throw new IllegalStateException("EntityManagerFactory is not initialized");
        }
        return factory.createEntityManager();
    }
    
    public static void shutdown() {
        if (factory != null && factory.isOpen()) {
            factory.close();
            System.out.println("JPA EntityManagerFactory closed");
        }
    }
}
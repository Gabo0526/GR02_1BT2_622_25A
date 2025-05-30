package com.ma.reminderweb.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public static void cerrar() {
        if (emf.isOpen()) {
            emf.close();
            System.out.println("EntityManagerFactory cerrado correctamente.");
        }
    }
}

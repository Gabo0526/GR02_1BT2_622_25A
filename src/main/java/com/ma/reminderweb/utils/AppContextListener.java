package com.ma.reminderweb.utils;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Aquí puedes registrar logs o inicializaciones adicionales si se requieren.
        System.out.println("Aplicación iniciada. EntityManagerFactory listo para usar.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JpaUtil.cerrar();
    }
}

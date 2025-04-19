package com.ma.reminderweb;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import model.Recordatorio;
import model.Usuario;

public class Test {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            /*Usuario usuario = new Usuario();
            usuario.setNombre("Jairo");
            usuario.setApellido("Quispe");
            usuario.setEmail("jairo.quispe@epn.edu.ec");
            usuario.setClave("avocoders");
            entityManager.persist(usuario);*/

            Recordatorio recordatorio = new Recordatorio();
            recordatorio.setTitulo("Recordatorio de prueba");
            recordatorio.setDescripcion("Recordatorio de prueba ligado a David");
            recordatorio.setFechaCreacion(java.time.Instant.now());
            recordatorio.setUsuarioFk(entityManager.find(Usuario.class, 2)); // El entityManager asigna el recordatorio al usuario con id 2
            entityManager.persist(recordatorio);

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}

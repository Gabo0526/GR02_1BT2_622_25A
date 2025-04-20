package utils;

import jakarta.persistence.*;
import model.Usuario;

public class Autenticacion {
    public static boolean autenticar(String email, String password) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        boolean autenticado = false;

        try {
            transaction.begin();

            TypedQuery<Usuario> queryUsuario =  entityManager.createNamedQuery("Usuario.findUser", Usuario.class);
            queryUsuario.setParameter(1, email);
            queryUsuario.setParameter(2, password);

            for (Usuario usuario : queryUsuario.getResultList()) {
                System.out.println(usuario);
            }

            if (!queryUsuario.getResultList().isEmpty()) {
                autenticado = true;
            }

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }

        return autenticado;
    }

    public static boolean verificarEmail(String email) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        boolean autenticado = false;

        try {
            transaction.begin();

            Query query = entityManager.createNativeQuery("SELECT * FROM Usuario u WHERE u.email=:usuarioEmail");
            query.setParameter("usuarioEmail", email);

            if (!query.getResultList().isEmpty()) {
                autenticado = true;
                System.out.println("Ya existe un usuario con ese email");
            }

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }

        return autenticado;
    }
}

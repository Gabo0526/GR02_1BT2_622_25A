package dao;

import jakarta.persistence.*;
import model.Usuario;
import utils.GenericDAO;

public class UsuarioDAO extends GenericDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    public Usuario autenticar(String email, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Usuario> query = em.createNamedQuery("Usuario.findUser", Usuario.class);
            query.setParameter(1, email);
            query.setParameter(2, password);

            return query.getResultList().isEmpty() ? null : query.getSingleResult();

        } finally {
            em.close();
        }
    }

    public boolean verificarEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNativeQuery("SELECT * FROM Usuario u WHERE u.email = :usuarioEmail");
            query.setParameter("usuarioEmail", email);
            return !query.getResultList().isEmpty();
        } finally {
            em.close();
        }
    }
}

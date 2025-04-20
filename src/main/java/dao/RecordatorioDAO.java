package dao;

import jakarta.persistence.*;
import model.Recordatorio;
import utils.GenericDAO;

import java.util.List;

public class RecordatorioDAO extends GenericDAO<Recordatorio> {

    public RecordatorioDAO() {
        super(Recordatorio.class);
    }

    /**
     * Obtiene todos los recordatorios de un usuario específico.
     * @param userId El ID del usuario.
     * @return Lista de recordatorios asociados al usuario.
     */
    public List<Recordatorio> obtenerPorUsuario(Integer userId) {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Recordatorio> query = em.createNamedQuery("Recordatorio.findAllbyUser", Recordatorio.class);
            query.setParameter(1, userId);

            return query.getResultList();

        } finally {
            em.close();
        }
    }
}

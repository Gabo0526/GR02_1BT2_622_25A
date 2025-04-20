package servlets;

import java.io.IOException;

import jakarta.persistence.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Recordatorio;
import model.Usuario;
import utils.Autenticacion;


/**
 *
 * @author DVC
 */
@WebServlet(name = "SvUsuarios", urlPatterns = {"/SvUsuarios"})
public class SvUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Usuario usuario = Autenticacion.autenticar(email, password);

        if (usuario != null) {
            // Obtener los recordatorios del usuario
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();

            try {
                transaction.begin();

                TypedQuery<Recordatorio> namedQuery =  entityManager.createNamedQuery("Recordatorio.findAllbyUser", Recordatorio.class);
                namedQuery.setParameter(1, usuario.getId());
                request.setAttribute("recordatorios", namedQuery.getResultList());

                transaction.commit();
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                entityManager.close();
                entityManagerFactory.close();
            }

            request.setAttribute("usuario", usuario);

            RequestDispatcher dispatcher = request.getRequestDispatcher("reminders.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("index.jsp");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}


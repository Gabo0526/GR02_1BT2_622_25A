package com.ma.reminderweb.servlets;

import java.io.IOException;

import com.ma.reminderweb.dao.RecordatorioDAO;
import com.ma.reminderweb.dao.UsuarioDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.ma.reminderweb.model.Usuario;


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

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        RecordatorioDAO recordatorioDAO = new RecordatorioDAO();

        Usuario usuario = usuarioDAO.autenticar(email, password);

        if (usuario != null) {
            // Guardar el usuario en la sesión
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);

            // Obtener recordatorios del usuario y enviarlos al JSP
            request.setAttribute("recordatorios", recordatorioDAO.obtenerPorUsuario(usuario.getId()));

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


package com.ma.reminderweb.servlets;

import java.io.IOException;

import com.ma.reminderweb.dao.RecordatorioDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.ma.reminderweb.model.Recordatorio;
import com.ma.reminderweb.model.Usuario;


/**
 *
 * @author DVC
 */
@WebServlet(name = "SvRecordatorios", urlPatterns = {"/SvRecordatorios"})
public class SvReminders extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null) {
            response.sendRedirect("index.jsp");
            return; // Salimos si no hay sesión activa
        }

        RecordatorioDAO recordatorioDAO = new RecordatorioDAO();

        String accion = request.getParameter("accion");

        try {
            if ("ActualizarEstado".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String nuevoEstado = request.getParameter("estado");

                boolean actualizado = recordatorioDAO.actualizarEstado(id, nuevoEstado);

                if (actualizado) {
                    System.out.println("Estado actualizado correctamente.");
                } else {
                    System.out.println("No se encontró el recordatorio con ID: " + id);
                }

            } else if ("AgregarRecordatorio".equals(accion)) {
                Recordatorio recordatorio = new Recordatorio();
                recordatorio.setTitulo(request.getParameter("titulo"));
                recordatorio.setDescripcion(request.getParameter("descripcion"));
                recordatorio.setFechaCreacion(java.time.Instant.now());
                recordatorio.setUsuarioFk(usuario);

                recordatorioDAO.save(recordatorio);

                System.out.println("Recordatorio agregado.");
            }

            // Recargar lista de recordatorios
            request.setAttribute("usuario", usuario);
            request.setAttribute("recordatorios", recordatorioDAO.obtenerPorUsuario(usuario.getId()));

            RequestDispatcher dispatcher = request.getRequestDispatcher("reminders.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar la solicitud");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

package servlets;

import java.io.IOException;
import java.util.Objects;

import dao.RecordatorioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


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
        RecordatorioDAO recordatorioDAO = new RecordatorioDAO();

        if (Objects.equals(request.getParameter("accion"), "ActualizarEstado")) {
            try {
                // 1. Obtener parámetros del formulario
                int id = Integer.parseInt(request.getParameter("id"));
                String nuevoEstado = request.getParameter("estado");

                // 2. Actualizar el estado
                boolean actualizado = recordatorioDAO.actualizarEstado(id, nuevoEstado);

                // 3. Mensaje opcional (podrías usarlo con JS o JSTL si deseas mostrar feedback)
                if (actualizado) {
                    System.out.println("Estado actualizado correctamente.");
                } else {
                    System.out.println("No se encontró el recordatorio con ID: " + id);
                }

                // 4. Redirigir para recargar la página (patrón PRG: Post/Redirect/Get)
                response.sendRedirect("recordatorios.jsp");

            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar el estado");
            }
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

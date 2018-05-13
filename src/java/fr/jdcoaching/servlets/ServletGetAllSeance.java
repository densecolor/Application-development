package fr.jdcoaching.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.HibernateUtil;
import metier.Seancetype;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author damdam61
 */
public class ServletGetAllSeance extends HttpServlet {

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

        response.setContentType("application/xml;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /*----- Ouverture de la session et de la transaction -----*/
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction t = session.beginTransaction();

            List ListeSeancetype = session.createCriteria(Seancetype.class).list();

            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_seancetype>");

            for (Seancetype st : (List<Seancetype>) ListeSeancetype) {
                out.println("<seancetype>");
                out.println("<Codest>" + st.getCodest() + "</Codest>");
                out.println("<Descripst>" + st.getDescripst() + "</Descripst>");
                out.println("<Libellest>" + st.getLibellest() + "</Libellest>");
                out.println("</seancetype>");
            }
            out.println("</liste_seancetype>");
            t.commit();
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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

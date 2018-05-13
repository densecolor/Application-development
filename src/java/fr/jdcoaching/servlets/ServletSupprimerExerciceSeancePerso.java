package fr.jdcoaching.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.HibernateUtil;
import metier.Planifier;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author edupin
 */
public class ServletSupprimerExerciceSeancePerso extends HttpServlet {

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

        String exoSupp = request.getParameter("pExoSupp");
        String codeSeance = request.getParameter("pCodeSeance");

        //on récupère la session hibernate
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        //on débute la transaction
        Transaction t = session.beginTransaction();
        
        //on récupère l'Objet Planifier à supprimer
        Query q4 = session.createQuery("from Planifier as pla where pla.exercice.codee=" + exoSupp + " and pla.seance.codes=" + codeSeance);
        Planifier pla = (Planifier) q4.list().get(0);

        session.delete(pla);

        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<informations>");
            out.println("<message>Suppression reussie!</message>");
            out.println("<id>Suppression reussie!</id>");
            out.println("</informations>");
        }
        t.commit();

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

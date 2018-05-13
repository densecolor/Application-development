package fr.jdcoaching.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.HibernateUtil;
import metier.Utilisateur;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

/**
 *
 * @author damdam61
 */
public class ServletGetAllClients extends HttpServlet {

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

            List ListeUtilisateur = session.createCriteria(Utilisateur.class).addOrder(Order.asc("nomu")).list();

            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_utilisateur>");

            for (Utilisateur u : (List<Utilisateur>) ListeUtilisateur) {
                out.println("<utilisateur>");
                out.println("<codeU>" + u.getCodeu() + "</codeU>");
                out.println("<nomU>" + u.getNomu() + "</nomU>");
                out.println("<prenomU>" + u.getPrenomu() + "</prenomU>");
                out.println("<mailU>" + u.getMailu() + "</mailU>");
                out.println("<genreU>" + u.getGenreu() + "</genreU>");
                out.println("<datenaissanceU>" + u.getDatenaissance() + "</datenaissanceU>");
                out.println("<mdpU>" + u.getMdpu() + "</mdpU>");
                out.println("<statutU>" + u.getStatutu() + "</statutU>");
                out.println("<adresseU>" + u.getAdresseu() + "</adresseU>");
                out.println("<profilU>" + u.getProfilu() + "</profilU>");
                out.println("<poidsU>" + u.getPoids() + "</poidsU>");
                out.println("<tailleU>" + u.getTaille() + "</tailleU>");
                out.println("<telU>" + u.getTelu() + "</telU>");
                out.println("</utilisateur>");
            }
            out.println("</liste_utilisateur>");
            
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

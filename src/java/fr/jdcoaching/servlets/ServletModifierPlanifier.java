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
public class ServletModifierPlanifier extends HttpServlet {

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
        //récupération des paramètres

        int codeS = Integer.parseInt(request.getParameter("pCodeS"));
        int codeExo = Integer.parseInt(request.getParameter("pCodeExo"));
        int repetition = Integer.parseInt(request.getParameter("pRepetition"));
        int tempsE = Integer.parseInt(request.getParameter("pTempsE"));
        int tempsRepos = Integer.parseInt(request.getParameter("pTempsRepos"));

        //on récupère la session hibernate
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        //on débute la transaction
        Transaction t = session.beginTransaction();

        //on récupère l'objet la Séance
        Query q1 = session.createQuery("from Planifier as pla where pla.seance.codes=" + codeS + " and pla.exercice.codee=" + codeExo + "");
        Planifier p = (Planifier) q1.list().get(0);

        //modification de l'objet avec les différents paramètres
        p.setNbrepe(repetition);
        p.setTempse(tempsE);
        p.setTempsrepose(tempsRepos);

        //sauvegarde des modifications apportées à la Séance
        session.save(p);

        t.commit();

        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<message>Modification reussie!</message>");
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

package fr.jdcoaching.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Exercice;
import metier.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author damdam61
 */
public class ServletGetExoForSeanceT extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Récupère la session hibernate
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        //On démarre la transaction de la session
        Transaction t = session.beginTransaction();

        /*----- Récupération des paramètres -----*/
        String codeS = request.getParameter("codeS");

        // requête qui récupère la liste des exercices
        Query q = session.createQuery("select o.exercice from Organiser as o where o.seancetype.codest = " + codeS);
        List<Exercice> listeExercice = (List<Exercice>) q.list();

        //On écrit un xml où on énumère tous les attributs de l'objet Exercice
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_exercices>");

            for (Exercice ex : (List<Exercice>) listeExercice) {
                out.println("<exercice>");
                out.println("<codee>" + ex.getCodee() + "</codee>");
                out.println("<codecat>" + ex.getCategorieexo() + "</codecat>");
                out.println("<libellee>" + ex.getLibellee() + "</libellee>");
                out.println("<objectif>" + ex.getObjectife() + "</objectif>");
                out.println("<lien>" + ex.getLienvideo() + "</lien>");
                out.println("<nbre>" + ex.getNbrepexoini() + "</nbre>");
                out.println("<temps>" + ex.getTempsexoini() + "</temps>");
                out.println("<repos>" + ex.getTempsreposini() + "</repos>");
                out.println("</exercice>");
            }
            out.println("</liste_exercices>");
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

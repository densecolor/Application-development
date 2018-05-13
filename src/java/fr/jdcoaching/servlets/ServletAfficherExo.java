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
 * @author Yambroise
 */
public class ServletAfficherExo extends HttpServlet {

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
        /*Type de la réponse*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // on récupère le paramètre
        String code = request.getParameter("exo");
        //String code = "54";

        //on récupère la session hibernate
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        //on démarre la transaction de la session
        Transaction t = session.beginTransaction();

        //on entame une requête qui récupère la liste des exercices
        Query q = session.createQuery("from Exercice where codee=" + code + "");
        List<Exercice> lex = (List<Exercice>) q.list();

        //on écrit un xml où on énumère tous les attributs de l'objet Exercice 
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_exercices>");

            for (int i = 0; i < lex.size(); i++) {

                Exercice ex = (Exercice) lex.get(i);
                out.println("<exercice>");
                out.println("<code>" + ex.getCodee() + "</code>");
                out.println("<exercice_libelle>" + ex.getLibellee() + "</exercice_libelle>");
                out.println("<objectif>" + ex.getObjectife() + "</objectif>");
                out.println("<categorie>" + ex.getCategorieexo() + "</categorie>");
                out.println("<repetition>" + ex.getNbrepexoini() + "</repetition>");
                out.println("<temps>" + ex.getTempsexoini() + "</temps>");
                out.println("<repos>" + ex.getTempsreposini() + "</repos>");
                out.println("<lien>" + ex.getLienvideo() + "</lien>");
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

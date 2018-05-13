package fr.jdcoaching.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.HibernateUtil;
import metier.Seance;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author edupin
 */
public class ServletModifierSeancePerso extends HttpServlet {

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
        String libelleSeance = request.getParameter("plibelleSeance");
        String descriptionSeance = request.getParameter("pdescriptionSeance");
        int nbRepSeance = Integer.parseInt(request.getParameter("pNbRepSeance"));
        int numSemaine = Integer.parseInt(request.getParameter("pNumSemaine"));

        //on récupère la session hibernate
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        //on débute la transaction
        Transaction t = session.beginTransaction();

        //on récupère l'objet la Séance
        Query q1 = session.createQuery("from Seance as st where st.codes=" + codeS + "");
        Seance s = (Seance) q1.list().get(0);

        //modification de l'objet avec les différents paramètres
        s.setLibelles(libelleSeance);
        s.setDescrips(descriptionSeance);
        s.setNbreps(nbRepSeance);
        s.setNumsemaine(numSemaine);

        //sauvegarde des modifications apportées à la Séance
        session.save(s);

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

package fr.jdcoaching.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.HibernateUtil;
import metier.Seancetype;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author edupin
 */
public class ServletAjouterSeanceType extends HttpServlet {

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
        //on récupère les différents paramètres
        String libST = request.getParameter("plibSt");
        String desST = request.getParameter("pdesST");
        System.out.println("test");
        try {
            //on récupère la session hibernate
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();

            //on débute la transaction
            Transaction t = session.beginTransaction();

//on créé un seancetype et on lui affecte les valeurs des paramètres
            Seancetype st = new Seancetype();
            st.setLibellest(libST);
            st.setDescripst(desST);
            session.save(st);

            //on entame une requête qui récupère la dernière séance type rajoutée
            Query q = session.createQuery("select max(st.codest) from Seancetype as st");
            int maxCodeSeanceType = (int) q.list().get(0);

            t.commit();

//Création du xml de message alerte
            try (PrintWriter out = response.getWriter()) {
                /*----- Ecriture de la page XML -----*/
                out.println("<?xml version=\"1.0\"?>");
                out.println("<informations>");
                out.println("<message>Ajout reussi!</message>");
                out.println("<idSeanceType>" + maxCodeSeanceType + "</idSeanceType>");
                out.println("</informations>");
            }

        } catch (Exception e) {
            try (PrintWriter out = response.getWriter()) {
                /*----- Ecriture de la page XML -----*/
                out.println("<?xml version=\"1.0\"?>");
                out.println("<message> Erreur - " + e + "</message>");
            }
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

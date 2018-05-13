package fr.jdcoaching.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Exercice;
import metier.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Yambroise
 */
public class ServletModifierExo extends HttpServlet {

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
        String codeExo = request.getParameter("code");
        String libExo = request.getParameter("lib");
        String objExo = request.getParameter("obj");
        String repetExo = request.getParameter("repet");
        String tpsExo = request.getParameter("tps");
        String repoExo = request.getParameter("repo");
        String lienExo = request.getParameter("lien");

        try {
            //on récupère la session hibernate
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();

            //on débute la transaction
            Transaction t = session.beginTransaction();

            //on créé un exercice et on lui affecte les valeurs des paramètres
            Exercice ex = (Exercice) session.get(Exercice.class, Integer.valueOf(codeExo));

            //on saisie les données récupéres
            ex.setLibellee(libExo);
            ex.setObjectife(objExo);
            ex.setNbrepexoini(Integer.valueOf(repetExo));
            ex.setTempsexoini(Integer.valueOf(tpsExo));
            ex.setTempsreposini(Integer.valueOf(repoExo));
            ex.setLienvideo(lienExo);

//on sauvegarde l'exercice
            session.update(ex);
            t.commit();
            //session.close();

//Création du xml de message alerte
            try (PrintWriter out = response.getWriter()) {
                /*----- Ecriture de la page XML -----*/
                out.println("<?xml version=\"1.0\"?>");
                out.println("<message>Modification reussie!</message>");
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

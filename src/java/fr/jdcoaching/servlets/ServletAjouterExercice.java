package fr.jdcoaching.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Categorieexo;
import metier.Exercice;
import metier.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author edupin
 */
public class ServletAjouterExercice extends HttpServlet {

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
        String nomExo = request.getParameter("pnomexo");
        String catExo = request.getParameter("pcatExo");
        String objExo = request.getParameter("pobjExo");
        String nbRepExo = request.getParameter("pnbRepExo");
        int nbRepExoInt = Integer.parseInt(nbRepExo);
        String dureeExo = request.getParameter("pdureeExo");
        int dureeExoInt = Integer.parseInt(dureeExo);
        String dureeReposExo = request.getParameter("pdureeReposExo");
        int dureeReposExoInt = Integer.parseInt(dureeReposExo);
        String lienVideoExo = request.getParameter("plienVideoExo");

        try {
            //on récupère la session hibernate
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();

            //on débute la transaction
            Transaction t = session.beginTransaction();

            //on récupère l'objet Catégorieexo à rajouter à exercice
            Query q4 = session.createQuery("from Categorieexo as c where c.codecat=" + catExo + "");
            Categorieexo ca = (Categorieexo) q4.list().get(0);

//on créé un exercice et on lui affecte les valeurs des paramètres
            Exercice ex = new Exercice();

            ex.setLibellee(nomExo);
            ex.setCategorieexo(ca);
            ex.setObjectife(objExo);
            ex.setNbrepexoini(nbRepExoInt);
            ex.setTempsexoini(dureeExoInt);
            ex.setTempsreposini(dureeReposExoInt);
            ex.setLienvideo(lienVideoExo);

//on sauvegarde l'exercice
            session.save(ex);
            t.commit();

//Création du xml de message alerte
            try (PrintWriter out = response.getWriter()) {
                /*----- Ecriture de la page XML -----*/
                out.println("<?xml version=\"1.0\"?>");
                out.println("<message>Ajout reussi!</message>");
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

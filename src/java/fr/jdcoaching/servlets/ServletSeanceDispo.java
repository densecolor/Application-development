package fr.jdcoaching.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Exercice;
import metier.HibernateUtil;
import metier.Message;
import metier.Planifier;
import metier.Seance;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author tpereira
 */
@WebServlet(name = "ServletSeanceDispo", urlPatterns = {"/ServletSeanceDispo"})
public class ServletSeanceDispo extends HttpServlet {

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

        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<listEx>");
            String idCli = request.getParameter("idCli");
            Calendar cal = Calendar.getInstance();
            //On vérifie qu'une seance est créee lors de la semaine actuelle
            Query q = session.createQuery("from Seance as s where s.numsemaine = '" + cal.get(Calendar.WEEK_OF_YEAR) + "' and s.utilisateur = '" + idCli + "'");
            ArrayList<Seance> listSean = (ArrayList<Seance>) q.list();
            if (listSean.isEmpty()) {
                //Si il n'y a pas de seance alors on renvoie l'absence de séance
                out.println("<pasSeance>" + Message.ERRPASSEANCE + " </pasSeance>");
            } else {
                // Si il y a bien une séance, il faut vérifier que l'utilisateur ne les a pas déjà toutes réalisées.
                // Premièrement on récupère la séance en question.
                Seance seanceActuelle = null;
                for (Seance seance : listSean) {
                    // Il ne peut y avoir plusieurs seance pour un même utilisateur à une date données
                    seanceActuelle = seance;
                }
                // Deuxiemement on récupère le nombre de séance validé
                Query q2 = session.createQuery("Select count(s.calendrier2) from Realisernormal as s where s.seance.codes ='1'");
                ArrayList<Object> nbRealisSean2 = (ArrayList<Object>) q2.list();

                // On sait que la requete ne sort qu'une ligne 
                // Troisièmement on compare les seances réalisées au nombre de seance prévues
                if (!nbRealisSean2.get(0).toString().equals(seanceActuelle.getNbreps().toString())) {
                    Query q3 = session.createQuery("select p.exercice from Planifier as p where p.seance.codes = '" + seanceActuelle.getCodes() + "'order by p.ordrep");
                    ArrayList<Exercice> listeExercice = (ArrayList<Exercice>) q3.list();
                    out.println("<message>" + Message.SEANCEPRETE + "</message>");
                    out.println("<seance>" + seanceActuelle.getCodes() + "</seance>");
                    String max = "faux";
                    for (Exercice ex : listeExercice) {
                        if (listeExercice.size() == listeExercice.indexOf(ex) + 1) {
                            max = "vrai";
                        }
                        out.println("<max>" + max + "</max>");
                        out.println("<codee>" + ex.getCodee() + "</codee>");
                        out.println("<codecat>" + ex.getCategorieexo() + "</codecat>");
                        out.println("<libellee>" + ex.getLibellee() + "</libellee>");
                        out.println("<objectif>" + ex.getObjectife() + "</objectif>");
                        out.println("<lien>" + ex.getLienvideo() + "</lien>");
                        out.println("<nbre> Nombre de répétitions : " + ex.getNbrepexoini() + "</nbre>");
                        out.println("<temps> Durée : " + ex.getTempsexoini() + "</temps>");
                        out.println("<repos> Repos entre deux répétitions : " + ex.getTempsreposini() + "</repos>");
                    }
                    Query q4 = session.createQuery("from Planifier as p where p.seance.codes = '" + seanceActuelle.getCodes() + "'order by p.ordrep");
                    ArrayList<Planifier> listePlanif = (ArrayList<Planifier>) q4.list();
                    for (Planifier planifier : listePlanif) {
                        out.println("<nbreP> Nombre de répétitions : " + planifier.getNbrepe() + "</nbreP>");
                        out.println("<tempsP> Durée : " + planifier.getTempse() + "</tempsP>");
                        out.println("<reposP> Repos entre deux répétitions : " + planifier.getTempsrepose() + "</reposP>");
                        out.println("<serieP> Nombre de série : " + planifier.getSeriep() + "</serieP>");
                        out.println("<seanceType>" + planifier.getSeance().getLibelles() + "</seanceType>");

                    }
                    // Dans le cas où le nombre n'est pas égal, il reste des séances à réaliser.
                    // Il faut donc sortir la liste des exercices à réaliser

                } else {
                    // Sinon toutes les séances ont déjà été realisées

                    out.println("<pasSeance>" + Message.ERRDEJAREALISE + " </pasSeance>");
                }

               
            }
             out.println("</listEx>");
            t.commit();

        }
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
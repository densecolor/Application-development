package fr.jdcoaching.servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Exercice;
import metier.HibernateUtil;
import metier.Realiserbilan;
import metier.RealiserbilanId;
import metier.Seance;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author tpereira
 */
@WebServlet(name = "ServletSeanceRealBilan", urlPatterns = {"/ServletSeanceRealBilan"})
public class ServletSeanceRealBilan extends HttpServlet {

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

        String res = request.getParameter("diff");
        String ses = request.getParameter("sess");
        Integer exo = Integer.valueOf(request.getParameter("ex"));
        Query q = session.createQuery("select p.exercice from Planifier as p where p.seance.codes = '" + ses + "'order by p.ordrep");
        ArrayList<Exercice> listeExercice = (ArrayList<Exercice>) q.list();

        Integer codeEx = listeExercice.get(exo).getCodee();
        Integer repet = listeExercice.get(exo).getNbrepexoini();
        Integer temps = listeExercice.get(exo).getTempsexoini();

        Realiserbilan real = new Realiserbilan();
        if (!repet.equals(0)) {
            real.setMaxrep(Integer.valueOf(res));
        } else {
            real.setMaxtemps(Integer.valueOf(res));
        }

        Seance sessCour = (Seance) session.get(Seance.class, Integer.valueOf(ses));

        real.setSeance(sessCour);
        RealiserbilanId reId = new RealiserbilanId(Integer.valueOf(ses), codeEx);
        real.setId(reId);
        real.setExercice(listeExercice.get(exo));

        session.save(real);
        t.commit();

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
}
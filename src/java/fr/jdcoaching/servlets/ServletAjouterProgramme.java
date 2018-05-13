package fr.jdcoaching.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Choisir;
import metier.ChoisirId;
import metier.HibernateUtil;
import metier.Profil;
import metier.Programme;
import metier.Seancetype;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author liya
 */
public class ServletAjouterProgramme extends HttpServlet {

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
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();

        String idProfil = request.getParameter("idProfilChoisit");
        String codeP = idProfil.substring(0, idProfil.indexOf("."));
        Integer codeProfil = Integer.parseInt(codeP);
        Profil profil=(Profil)session.get(Profil.class, codeProfil);

        String NomProgramme = request.getParameter("NomProgrammeChoisit");
        String DescProgramme = request.getParameter("DescProgrammeChoisit");

        Programme p = new Programme();
        p.setLibellep(NomProgramme);

        if(request.getParameter("DescProgrammeChoisit") == "") {
            p.setDescriptionp(null);
        }else{
            p.setDescriptionp(DescProgramme);
        }


        String str = request.getParameter("listeStChoisit");
        String[] strArray = null;
        strArray = str.split(",");
        List <String> listSt = Arrays.asList(strArray);
        List <Integer> listCode = new ArrayList<Integer>();
        for (String St : listSt) {
            String codeSt=St.substring(0, St.indexOf("."));
            Integer code = Integer.parseInt(codeSt);
            listCode.add(code);
        }

        session.save(p);
      Query q= session.createQuery("from Programme");
      List<Programme> listP =  (List<Programme>) q.list();
      Integer codep = listP.get(listP.size()-1).getCodep();
            ChoisirId choisirId=new ChoisirId();            
            choisirId.setCodep(codep);
            choisirId.setCodeprofil(codeProfil);
            Choisir choisir=new Choisir();
            choisir.setId(choisirId);
            session.save(choisir);
              for (int i = 0; i < listCode.size(); i++) {
            Seancetype s =(Seancetype)session.get(Seancetype.class, listCode.get(i));
                 listP.get(listP.size()-1).getSeancetypes().add(s);
                s.getProgrammes().add( listP.get(listP.size()-1));
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

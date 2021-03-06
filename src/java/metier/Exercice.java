package metier;
// Generated 23 f�vr. 2018 05:43:28 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Exercice generated by hbm2java
 */
public class Exercice  implements java.io.Serializable {


     private int codee;
     private Categorieexo categorieexo;
     private String libellee;
     private String objectife;
     private String lienvideo;
     private Integer nbrepexoini;
     private Integer tempsexoini;
     private Integer tempsreposini;
     private Set<Planifier> planifiers = new HashSet<Planifier>(0);
     private Set<Realiserbilan> realiserbilans = new HashSet<Realiserbilan>(0);
     private Set<Organiser> organisers = new HashSet<Organiser>(0);
     private Set<Niveauexo> niveauexos = new HashSet<Niveauexo>(0);
     private Set<Realisernormal> realisernormals = new HashSet<Realisernormal>(0);

    public Exercice() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.codee;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Exercice other = (Exercice) obj;
        if (this.codee != other.codee) {
            return false;
        }
        return true;
    }

	
    public Exercice(int codee, Categorieexo categorieexo) {
        this.codee = codee;
        this.categorieexo = categorieexo;
    }
    public Exercice(int codee, Categorieexo categorieexo, String libellee, String objectife, String lienvideo, Integer nbrepexoini, Integer tempsexoini, Integer tempsreposini, Set<Planifier> planifiers, Set<Realiserbilan> realiserbilans, Set<Organiser> organisers, Set<Niveauexo> niveauexos, Set<Realisernormal> realisernormals) {
       this.codee = codee;
       this.categorieexo = categorieexo;
       this.libellee = libellee;
       this.objectife = objectife;
       this.lienvideo = lienvideo;
       this.nbrepexoini = nbrepexoini;
       this.tempsexoini = tempsexoini;
       this.tempsreposini = tempsreposini;
       this.planifiers = planifiers;
       this.realiserbilans = realiserbilans;
       this.organisers = organisers;
       this.niveauexos = niveauexos;
       this.realisernormals = realisernormals;
    }
   
    public int getCodee() {
        return this.codee;
    }
    
    public void setCodee(int codee) {
        this.codee = codee;
    }
    public Categorieexo getCategorieexo() {
        return this.categorieexo;
    }
    
    public void setCategorieexo(Categorieexo categorieexo) {
        this.categorieexo = categorieexo;
    }
    public String getLibellee() {
        return this.libellee;
    }
    
    public void setLibellee(String libellee) {
        this.libellee = libellee;
    }
    public String getObjectife() {
        return this.objectife;
    }
    
    public void setObjectife(String objectife) {
        this.objectife = objectife;
    }
    public String getLienvideo() {
        return this.lienvideo;
    }
    
    public void setLienvideo(String lienvideo) {
        this.lienvideo = lienvideo;
    }
    public Integer getNbrepexoini() {
        return this.nbrepexoini;
    }
    
    public void setNbrepexoini(Integer nbrepexoini) {
        this.nbrepexoini = nbrepexoini;
    }
    public Integer getTempsexoini() {
        return this.tempsexoini;
    }
    
    public void setTempsexoini(Integer tempsexoini) {
        this.tempsexoini = tempsexoini;
    }
    public Integer getTempsreposini() {
        return this.tempsreposini;
    }
    
    public void setTempsreposini(Integer tempsreposini) {
        this.tempsreposini = tempsreposini;
    }
    public Set<Planifier> getPlanifiers() {
        return this.planifiers;
    }
    
    public void setPlanifiers(Set<Planifier> planifiers) {
        this.planifiers = planifiers;
    }
    public Set<Realiserbilan> getRealiserbilans() {
        return this.realiserbilans;
    }
    
    public void setRealiserbilans(Set<Realiserbilan> realiserbilans) {
        this.realiserbilans = realiserbilans;
    }
    public Set<Organiser> getOrganisers() {
        return this.organisers;
    }
    
    public void setOrganisers(Set<Organiser> organisers) {
        this.organisers = organisers;
    }
    public Set<Niveauexo> getNiveauexos() {
        return this.niveauexos;
    }
    
    public void setNiveauexos(Set<Niveauexo> niveauexos) {
        this.niveauexos = niveauexos;
    }
    public Set<Realisernormal> getRealisernormals() {
        return this.realisernormals;
    }
    
    public void setRealisernormals(Set<Realisernormal> realisernormals) {
        this.realisernormals = realisernormals;
    }




}



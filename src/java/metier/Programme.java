package metier;
// Generated 23 f�vr. 2018 05:43:28 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Programme generated by hbm2java
 */
public class Programme  implements java.io.Serializable {


     private int codep;
     private String libellep;
     private String descriptionp;
     private Set<Seance> seances = new HashSet<Seance>(0);
     private Set<Choisir> choisirs = new HashSet<Choisir>(0);
     private Set<Seancetype> seancetypes = new HashSet<Seancetype>(0);

    public Programme() {
    }

	
    public Programme(int codep) {
        this.codep = codep;
    }
    public Programme(int codep, String libellep, String descriptionp, Set<Seance> seances, Set<Choisir> choisirs, Set<Seancetype> seancetypes) {
       this.codep = codep;
       this.libellep = libellep;
       this.descriptionp = descriptionp;
       this.seances = seances;
       this.choisirs = choisirs;
       this.seancetypes = seancetypes;
    }
   
    public int getCodep() {
        return this.codep;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.codep;
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
        final Programme other = (Programme) obj;
        if (this.codep != other.codep) {
            return false;
        }
        return true;
    }
    
    public void setCodep(int codep) {
        this.codep = codep;
    }
    public String getLibellep() {
        return this.libellep;
    }
    
    public void setLibellep(String libellep) {
        this.libellep = libellep;
    }
    public String getDescriptionp() {
        return this.descriptionp;
    }
    
    public void setDescriptionp(String descriptionp) {
        this.descriptionp = descriptionp;
    }
    public Set<Seance> getSeances() {
        return this.seances;
    }
    
    public void setSeances(Set<Seance> seances) {
        this.seances = seances;
    }
    public Set<Choisir> getChoisirs() {
        return this.choisirs;
    }
    
    public void setChoisirs(Set<Choisir> choisirs) {
        this.choisirs = choisirs;
    }
    public Set<Seancetype> getSeancetypes() {
        return this.seancetypes;
    }
    
    public void setSeancetypes(Set<Seancetype> seancetypes) {
        this.seancetypes = seancetypes;
    }




}


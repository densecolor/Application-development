package metier;
// Generated 23 f�vr. 2018 05:43:28 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Niveauexo generated by hbm2java
 */
public class Niveauexo  implements java.io.Serializable {


     private int coden;
     private String libellen;
     private Set<Exercice> exercices = new HashSet<Exercice>(0);

    public Niveauexo() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.coden;
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
        final Niveauexo other = (Niveauexo) obj;
        if (this.coden != other.coden) {
            return false;
        }
        return true;
    }

	
    public Niveauexo(int coden) {
        this.coden = coden;
    }
    public Niveauexo(int coden, String libellen, Set<Exercice> exercices) {
       this.coden = coden;
       this.libellen = libellen;
       this.exercices = exercices;
    }
   
    public int getCoden() {
        return this.coden;
    }
    
    public void setCoden(int coden) {
        this.coden = coden;
    }
    public String getLibellen() {
        return this.libellen;
    }
    
    public void setLibellen(String libellen) {
        this.libellen = libellen;
    }
    public Set<Exercice> getExercices() {
        return this.exercices;
    }
    
    public void setExercices(Set<Exercice> exercices) {
        this.exercices = exercices;
    }




}



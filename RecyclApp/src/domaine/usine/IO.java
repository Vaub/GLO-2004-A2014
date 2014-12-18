/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domaine.usine;

import java.io.Serializable;
import utils.Orientation;
import utils.Position;

/**
 *
 * @author Vincent
 */
public abstract class IO implements Comparable<IO>, Serializable
{   
    public static final double TAILLE = 0.3f;
    
    protected FluxDeMatiere matieres;
    protected IO relierA;
    
    public final Equipement parent;
    
    public IO(Equipement _parent)
    {
        parent = _parent;
        matieres = new FluxDeMatiere();
        
        relierA = null;
    }
    
    public FluxDeMatiere getMatieres()
    {
        return matieres;
    }
    
    public void setMatieres(FluxDeMatiere _flux)
    {
        matieres = new FluxDeMatiere(_flux);
    }
    
    public void ajouterMatiere(Matiere _matiere, double _quantite)
    {
        if (matieres == null)
            matieres = new FluxDeMatiere();
        
        matieres.setMatiere(_matiere, _quantite);
    }
    
    public boolean estDessus(Position _pos)
    {
        boolean estDessus = false;
        
        Position posIO = this.getPosition();
        
        //System.out.println((posIO.x() + TAILLE) >= _pos.x());
        //System.out.println((posIO.x() - TAILLE) <= _pos.x());
        
        if ((posIO.x() + TAILLE >= _pos.x() && posIO.x() - TAILLE <= _pos.x()) &&
            (posIO.y() + TAILLE >= _pos.y() && posIO.y() - TAILLE <= _pos.y()))
        {
            estDessus = true;
        }
        
        return estDessus;
    }
    
    public boolean relierA(IO _destination)
    {
        boolean estConnecte = false;
        
        if (_destination != null && this.parent != _destination.parent &&
            !this.getClass().equals(_destination.getClass()))
        {
            this.enleverLiaison();
            
            if (!_destination.estReliee())
            {
                _destination.enleverLiaison();
                
                _destination.relierA = this;
                this.relierA = _destination;
            }
        }
        
        return estConnecte;
    }
    
    public IO getIORelie()
    {
        return this.relierA;
    }
    
    public void enleverLiaison() 
    {
        if (relierA != null)
        {
            IO io = relierA;
            
            relierA = null;
            if (io != null)
                io.enleverLiaison();
        }
    }
    
    public boolean estReliee() 
    {
        return (relierA != null);
    }

    @Override
    public int compareTo(IO o)
    {
        return Math.round((float)this.getMatieres().getQuantiteTotale() - (float)o.getMatieres().getQuantiteTotale());
    }
    
    
    
    public abstract Convoyeur getConvoyeur();
    public abstract Position getPosition();
}

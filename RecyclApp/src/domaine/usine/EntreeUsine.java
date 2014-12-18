/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domaine.usine;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashSet;
import utils.Orientation;
import utils.Position;
import utils.Taille;

/**
 *
 * @author Vincent
 */
public class EntreeUsine extends Equipement
{
    protected Sortie sortie;
    
    public FluxDeMatiere matieres;
    
    public EntreeUsine(Position _position, Taille _dimension)
    {
        super("Entrée", _position, _dimension);
        
        sortie = new Sortie(this); 
        matieres = new FluxDeMatiere();
        
        sorties.add(sortie);
    }

    @Override
    public FluxDeMatiere getMatieres()
    {
        return matieres;
    }
    
    
    
    public void setMatieres(FluxDeMatiere _flux)
    {
        matieres = _flux;
    }
    
    @Override
    public void calculTransit(HashSet<Equipement> _dejaVisite)
    {
        Equipement suivant;
        matieres = (matieres == null) ? new FluxDeMatiere() : matieres;
        
        _dejaVisite.add(this);
        
        if (sortie != null)
        {
            sortie.setMatieres(matieres);
            suivant = sortie.transiter();
            
            if (suivant != null)
                suivant.calculTransit(_dejaVisite);
        }
    }
}

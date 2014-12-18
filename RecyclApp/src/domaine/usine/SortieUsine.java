/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domaine.usine;

import java.util.HashSet;
import utils.Orientation;
import utils.Position;
import utils.Taille;

/**
 *
 * @author Vincent
 */
public class SortieUsine extends Equipement
{
    protected Entree entree;
    
    public SortieUsine(Position _position, Taille _dimension)
    {
        super("Sortie", _position, _dimension);
        entree = new Entree(this); 
        
        entrees.add(entree);
    }
    
    @Override
    public void calculTransit(HashSet<Equipement> _dejaVisite)
    {
        _dejaVisite.add(this);
    }
}

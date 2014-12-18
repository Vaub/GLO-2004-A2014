/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domaine.usine;

import utils.Orientation;
import utils.Position;

/**
 *
 * @author Proprietaire
 */
public class Entree extends IO
{
    public Entree(Equipement _parent)
    {
        super(_parent);
    }
    
    public void setRelierA(Sortie _sortie)
    {
        relierA = _sortie;
    }

    @Override
    public Position getPosition()
    {
        return (parent != null) ? parent.getPositionEntree(this) : null;
    }

    @Override
    public Convoyeur getConvoyeur()
    {
        Convoyeur retour = null;
        
        if (relierA != null &&
            relierA instanceof Sortie)
        {
            retour = ((Sortie)relierA).getConvoyeur();
        }
        
        return retour;
    }
}

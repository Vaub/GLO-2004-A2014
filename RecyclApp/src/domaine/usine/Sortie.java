/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domaine.usine;

import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;
import utils.Orientation;
import utils.Position;

/**
 *
 * @author Proprietaire
 */
public class Sortie extends IO
{
    //protected Entree relierA;
    protected Convoyeur convoyeur;
    
    protected boolean estValide;
    
    public Sortie(Equipement _parent)
    {
        super(_parent);
        
        estValide = true;
        this.convoyeur = new Convoyeur(this, Convoyeur.CAPACITE_PAR_DEFAUT);
    }
    
    public void setValide(boolean _valide)
    {
        estValide = _valide;
    }
    
    public boolean estValide()
    {
        boolean valide = false;
        
        if (estValide)
        {
            if (convoyeur != null && relierA != null)
                valide = (matieres.getQuantiteTotale() <= convoyeur.getCapaciteMaximale());
            else if (matieres.getQuantiteTotale() == 0 &&
                     relierA != null &&
                     relierA.getIORelie() == this)
                valide = true;
        }
        
        return valide;
    }
    
    public Equipement transiter()
    {
        Equipement suivant = null;
        
        if (this.estValide() && 
            relierA != null)
        {
            relierA.setMatieres(matieres);
            
            if (convoyeur.estValide())
                suivant = relierA.parent;
        }
        
        return suivant;
    }

    public Convoyeur getConvoyeur()
    {
        return this.convoyeur;
    }
    
    public boolean convoyeurSelectionne(Position _pos)
    {
        boolean estSelectionne = false;
        
        if (relierA != null && convoyeur != null)
        {   
            Position origine = this.getPosition();
            Position destination = relierA.getPosition();

            GeneralPath cheminDuConvoyeur = new GeneralPath();
            
            cheminDuConvoyeur.moveTo(origine.x(), origine.y());
            
            List<Position> points = convoyeur.getPoints();
            for (int i = 0; i < points.size() && !estSelectionne; i++)
            {
                cheminDuConvoyeur.lineTo(points.get(i).x(), points.get(i).y());
                

                estSelectionne = cheminDuConvoyeur.intersects(_pos.x() - (0.2f / 2f), 
                                                              _pos.y() - (0.2f / 2f), 
                                                              0.2f, 
                                                              0.2f);
                
                cheminDuConvoyeur.reset();
                cheminDuConvoyeur.moveTo(points.get(i).x(), points.get(i).y());
            }
            
            if (!estSelectionne)
            {
                cheminDuConvoyeur.lineTo(destination.x(), destination.y());
                estSelectionne = cheminDuConvoyeur.intersects(_pos.x() - (0.2f / 2f), 
                                                              _pos.y() - (0.2f / 2f), 
                                                              0.2f, 
                                                              0.2f);
            }
        }
        
        
        return estSelectionne;
    }

    @Override
    public void enleverLiaison()
    {
        super.enleverLiaison();
        convoyeur.resetPoints();
    }

    @Override
    public Position getPosition()
    {
        return (parent != null) ? parent.getPositionSortie(this) : null;
    }
 
}

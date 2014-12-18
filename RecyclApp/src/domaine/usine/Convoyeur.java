/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domaine.usine;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ressources.Parametres;
import utils.Orientation;
import utils.Position;
import utils.Taille;
/**
 *
 * @author owner
 */
public class Convoyeur extends Composant
{
    public static int TAILLE_PAR_DEFAUT = 3;
    public static int CAPACITE_PAR_DEFAUT = 5000;
    
    public final Sortie parent;
    
    private int largeur;
    private float capaciteMaximale;
    private ArrayList<Position> points;
    
    protected Color couleur;
    
    public Convoyeur(Sortie _parent, float capaciteMaximale)
    {
        parent = _parent;
        
        largeur = 1;
        points = new ArrayList<>();
        
        this.couleur = Parametres.getCouleur(Parametres.COULEUR_COMPOSANTS);
        
        this.capaciteMaximale = (capaciteMaximale < 0) ? 0:capaciteMaximale;
    }
    
    
    public void ajouterPoint(Position _pointAAjouter)
    {
        GeneralPath cheminConvoyeur = new GeneralPath();
        
        Position origine = parent.getPosition(),
                 destination = (parent.relierA != null) ? parent.relierA.getPosition()
                                                        : parent.getPosition();
        
        boolean estTrouve = false;
        
        cheminConvoyeur.moveTo(origine.x(), origine.y());
        
        int index = 0;
        for (index = 0; index < points.size() && !estTrouve; index++)
        {
            cheminConvoyeur.lineTo(points.get(index).x(), points.get(index).y());
            
            estTrouve = cheminConvoyeur.intersects(_pointAAjouter.x() - (0.2f / 2f), 
                                                   _pointAAjouter.y() - (0.2f / 2f), 
                                                   0.2f, 
                                                   0.2f);
            
            if (estTrouve)
            {
                cheminConvoyeur.reset();
                cheminConvoyeur.moveTo(points.get(index).x(), 
                                       points.get(index).y());
            }
        }
        
        if (!estTrouve)
        {
            cheminConvoyeur.lineTo(destination.x(), destination.y());
            estTrouve = cheminConvoyeur.intersects(_pointAAjouter.x() - (0.2f / 2f), 
                                                   _pointAAjouter.y() - (0.2f / 2f), 
                                                   0.2f, 
                                                   0.2f);
        }
        else
            index--;
        
        if (estTrouve)
        {
            //index -= (index > Math.round(points.size()/2)) ? 1 : 0;
            points.add(index, _pointAAjouter);
        }
    }
    
    public void enleverPoint(Position _point)
    {
        if (points.contains(_point))
        {
            points.remove(_point);
        }
    }
    
    public void enleverLiaison()
    {
        if (parent != null)
            parent.enleverLiaison();
    }
    
    public List<Position> getPoints()
    {
        return Collections.unmodifiableList(points);
    }
    
    public void resetPoints()
    {
        points.removeAll(points);
    }
    
    public float getCapaciteMaximale()
    {
        return capaciteMaximale;
    }
    
    public void setCapaciteMaximale(float capaciteMaximale)
    {
        if(capaciteMaximale >= 0)
        {
            this.capaciteMaximale = capaciteMaximale;
        }
    }
    
    public int getLargeur()
    {
        return largeur;
    }
    
    public void setLargeur(int largeur)
    {
        if(largeur > 0)
        {
            this.largeur = largeur;
        }
    }
    
    public Position getPointInflexion(Position _pos)
    {
        Position position = null;
        
        for (Position p : this.points)
        {
            if ((p.x() + 0.2 >= _pos.x() && p.x() - 0.2 <= _pos.x()) &&
                (p.y() + 0.2 >= _pos.y() && p.y() - 0.2 <= _pos.y()))
                position = p;
        }
        
        return position;
    }
    
    public boolean estValide()
    {
        return (parent != null) ? parent.getMatieres().getQuantiteTotale() <= capaciteMaximale : false;
    }

    @Override
    public Color getCouleur()
    {
        return (this.estValide()) ? this.couleur : Color.RED;
    }

    @Override
    public FluxDeMatiere getMatieres()
    {
        return parent.getMatieres();
    }

    @Override
    public void setCouleur(Color _couleur)
    {
        this.couleur = _couleur;
    }

    @Override
    public String getNom()
    {
        return "Convoyeur"+ ((parent.parent != null) ? " "+parent.parent.getNom() : "");
    }

    @Override
    public void setNom(String _nom)
    {
    }
}

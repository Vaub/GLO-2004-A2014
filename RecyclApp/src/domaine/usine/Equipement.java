/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domaine.usine;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import ressources.Parametres;
import utils.Orientation;
import utils.Position;
import utils.Taille;

/**
 *
 * @author Magalie
 */
public abstract class Equipement extends Composant
{
    protected String nom;
    protected Color couleur;
    protected Position position;
    
    protected ArrayList<Entree> entrees;
    protected ArrayList<Sortie> sorties;
    
    protected Orientation orientationEntrees,
                          orientationSorties;
    
    protected Taille taille;
    
    public Equipement(String _nom, Position _position, Taille _taille)
    {
        this(_nom, _position, _taille, Parametres.getCouleur(Parametres.COULEUR_COMPOSANTS));
    }
    
    public Equipement(String _nom,
                      Position _position, 
                      Taille _taille,
                      Color _couleur)
    {
        nom = _nom;
        
        position = _position;
        taille = _taille;
        
        couleur = _couleur;
        
        entrees = new ArrayList<>();
        sorties = new ArrayList<>();
        
        orientationEntrees = Orientation.OUEST;
        orientationSorties = Orientation.EST;
    }
    
    @Override
    public FluxDeMatiere getMatieres()
    {
        FluxDeMatiere matieres = new FluxDeMatiere();
        
        for (Entree e : entrees)
            matieres.combinerFlux(e.getMatieres());
        
        return new FluxDeMatiere(matieres);
    }
    
    public void clearMatiere()
    {
        for (Entree e : entrees)
            e.getMatieres().supprimerTout();
        
        for (Sortie s : sorties)
            s.getMatieres().supprimerTout();
    }
    
    @Override
    public void setNom(String _nom)
    {
        nom = _nom;
    }
    
    @Override
    public void setCouleur(Color _couleur)
    {
        couleur = _couleur;
    }

    public void setPosition(Position _position)
    {
        this.setPosition(_position.x(), _position.y());
    }
    
    public void setPosition(double _x, double _y)
    {
        position = new Position(_x, _y);
    }
    
    public void setTaille(Taille _dimension)
    {
        this.setTaille(_dimension.largeur(), _dimension.hauteur());
    }

    public void setTaille(double _largeur, double _hauteur)
    {
        taille = new Taille(_largeur, _hauteur);
    }
    
    @Override
    public String getNom()
    {
        return nom;
    }
    
    @Override
    public Color getCouleur()
    {
        return couleur;
    }
    
    public Position getPosition()
    {
        return position;
    }
    
    public Taille getTaille()
    {
        return taille;
    }

    //public abstract FluxDeMatiere getMatieres();
    public abstract void calculTransit(HashSet<Equipement> _dejaVisite);
    
    public void deplacer(float _x, float _y)
    {
        position = new Position(_x, _y);
    }
    
    public List<Sortie> getSorties()
    {
        return Collections.unmodifiableList(sorties);
    }
    
    public List<Entree> getEntrees()
    {
        return Collections.unmodifiableList(entrees);
    }
    
    public void modifierParametres(String _nom, Color _couleur)
    {
        nom = _nom;
        couleur = _couleur;
    }
    
    public Position getPositionEntree(Entree _entree)
    {
        Position pos = null;

        for (int i = 0; i < entrees.size() && pos == null; i++)
        {
            pos = (entrees.get(i) == _entree) ? getPositionIO(i + 1, entrees.size(), orientationEntrees, true) 
                                              : pos;
        }
        
        return pos;
    }
    
    public Position getPositionSortie(Sortie _sortie)
    {
        Position pos = null;

        for (int i = 0; i < sorties.size() && pos == null; i++)
        {
            pos = (sorties.get(i) == _sortie) ? getPositionIO(i + 1, sorties.size(), orientationSorties, true) 
                                              : pos;
        }
        
        return pos;
    }
    
    public Position getPositionIO(int _index, int _nbDeIO, Orientation _orientation, boolean _milieu)
    {
        Position pos = null;
        
        double longueur;
        double positionSurAxe;
        
        if (_index <= _nbDeIO)
        {
            longueur = (_orientation == Orientation.NORD || _orientation == Orientation.SUD) ? taille.largeur()
                                                                                             : taille.hauteur();
            
            if (_milieu || _nbDeIO == 1)
                positionSurAxe = (longueur / (_nbDeIO + 1)) * (_index);
            else
                positionSurAxe = (longueur / (_nbDeIO - 1)) * (_index - 1);
            
            pos = (_orientation == Orientation.NORD)  ? new Position(position.x() + positionSurAxe, position.y()) :
                  (_orientation == Orientation.SUD)   ? new Position(position.x() + positionSurAxe, position.y() + taille.hauteur()) :
                  (_orientation == Orientation.EST)   ? new Position(position.x() + taille.largeur(), position.y() + positionSurAxe) :
                  (_orientation == Orientation.OUEST) ? new Position(position.x(), position.y() + positionSurAxe)
                                                      : null;
            
        }
        
        return pos;
    }
    
    public Orientation getOrientationEntrees()
    {
        return orientationEntrees;
    }
    
    public Orientation getOrientationSorties()
    {
        return orientationSorties;
    }
    
    public void setOrientation(Orientation _entrees, Orientation _sorties)
    {
        if (_entrees != _sorties)
        {
            orientationEntrees = _entrees;
            orientationSorties = _sorties;
        }
    }
    
    
    
}

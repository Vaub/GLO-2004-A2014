/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import domaine.usine.EntreeUsine;
import domaine.usine.Equipement;
import domaine.usine.FluxDeMatiere;
import domaine.usine.Station;
import gui.RecyclApp;
import utils.Orientation;
import utils.Position;
import utils.Taille;

/**
 *
 * @author Vincent
 */
public class ModificationEquipement extends ActionRecyclApp
{
    public final Equipement equipement;
    
    public ModificationEquipement(Equipement _equip, RecyclApp _app)
    {
        super(_app);
        equipement = _equip;
    }
    
    public void modifier(Position _pos, Taille _dim, 
                         Orientation _orientationEntrees,
                         Orientation _orientationSorties)
    {
        if (equipement instanceof Equipement)
            ((Equipement)equipement).setOrientation(_orientationEntrees, _orientationSorties);
        
        if (!equipement.getPosition().equals(_pos))
            app.controleur.deplacerEquipement(equipement, _pos);
        
        if (!equipement.getTaille().equals(_dim))
            app.controleur.redimensionnerEquipement(equipement, _dim);
        
        app.actualiser();
    }
    
    public void modifierPosition(Position _pos)
    {
        this.modifier(_pos, 
                      equipement.getTaille(),
                      equipement.getOrientationEntrees(),
                      equipement.getOrientationSorties());
    }
    
    public void modifierTaille(Taille _taille)
    {
        this.modifier(equipement.getPosition(), 
                      _taille,
                      equipement.getOrientationEntrees(),
                      equipement.getOrientationSorties());
    }
    
    public void modifierOrientation(Orientation _orientationEntrees, 
                                    Orientation _orientationSorties)
    {
        this.modifier(equipement.getPosition(), 
                      equipement.getTaille(),
                      _orientationEntrees,
                      _orientationSorties);
    }
    
    public void modifierOrientationEntree(Orientation _o)
    {
        if (equipement.getOrientationEntrees()!= _o)
        {
            Orientation temp = equipement.getOrientationSorties();
            if (temp == _o)
            {
                temp = (_o == Orientation.SUD)   ? Orientation.NORD  :
                       (_o == Orientation.NORD)  ? Orientation.SUD :
                       (_o == Orientation.EST)   ? Orientation.OUEST 
                                                 : Orientation.EST;
            }
            
            equipement.setOrientation(_o, temp);
            app.actualiser();
        }
    }
    
    public void modifierOrientationSortie(Orientation _o)
    {
        if (equipement.getOrientationSorties() != _o)
        {
            Orientation temp = equipement.getOrientationEntrees();
            if (temp == _o)
            {
                temp = (_o == Orientation.SUD)   ? Orientation.NORD  :
                       (_o == Orientation.NORD)  ? Orientation.SUD :
                       (_o == Orientation.EST)   ? Orientation.OUEST 
                                                 : Orientation.EST;
            }
            
            equipement.setOrientation(temp, _o);
            app.actualiser();
        }
    }
    
    public void modifierFluxDeMatiere(FluxDeMatiere _flux) {
        if (equipement instanceof EntreeUsine) {
                ((EntreeUsine) equipement).setMatieres(_flux);
                app.actualiser();
        }     
    }
    
    public void modifierCapaciteMaximale(float _capaciteMax) {
        if (equipement instanceof Station) {
                ((Station) equipement).setCapaciteMaximale(_capaciteMax);
                
                app.controleur.actualiserSchema();
                app.actualiser();
        }
    }
}

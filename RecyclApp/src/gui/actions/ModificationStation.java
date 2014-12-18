/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import domaine.usine.Station;
import gui.RecyclApp;
import utils.Orientation;

/**
 *
 * @author Vincent
 */
public class ModificationStation extends ActionRecyclApp
{
    public final Station station;

    public ModificationStation(Station _station, RecyclApp _app)
    {
        super(_app);
        this.station = _station;
    }
    
    public void modifierCapaciteMaximale(double _capacite)
    {
        if (station.getCapaciteMaximale() != _capacite)
        {
            station.setCapaciteMaximale(_capacite);
            
            app.controleur.actualiserSchema();
            app.actualiser();
        }
    }
    
    public void modifierNombreSorties(int _nb)
    {
        if (station.getSorties().size() != _nb)
        {
            if (station.getSorties().size() > _nb)
                station.enleverSortie();
            else
                station.ajouterSortie();
            
            app.actualiser();
        }
    }
    
    public void modifierOrientationSorties(Orientation _o)
    {
        if (station.getOrientationSorties() != _o)
        {
            station.setOrientation(station.getOrientationEntrees(), _o);
            app.actualiser();
        }
    }
    
    public void modifierOrientationEntrees(Orientation _o)
    {
        if (station.getOrientationEntrees() != _o)
        {
            station.setOrientation(_o, station.getOrientationSorties());
            app.actualiser();
        }
    }
}

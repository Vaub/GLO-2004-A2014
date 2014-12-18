/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.actions;

import domaine.usine.Convoyeur;
import gui.RecyclApp;

/**
 *
 * @author owner
 */
public class ModificationConvoyeur extends ActionRecyclApp
{
    private final Convoyeur convoyeur;
    
    public ModificationConvoyeur(Convoyeur _c, RecyclApp _app) 
    {
        super(_app);
        this.convoyeur = _c;
    }
    
    public void modifierCapaciteMax(float valeur)
    {
        if(this.convoyeur.getCapaciteMaximale() != valeur)
        {
            this.convoyeur.setCapaciteMaximale(valeur);
            app.controleur.actualiserSchema();
        }
        app.actualiser();
    }
    
}

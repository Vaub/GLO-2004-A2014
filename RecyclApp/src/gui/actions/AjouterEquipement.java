/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import domaine.usine.Equipement;
import gui.RecyclApp;

/**
 *
 * @author Vincent
 */
public class AjouterEquipement extends ActionRecyclApp
{
    public AjouterEquipement(RecyclApp _app)
    {
        super(_app);
    }
    
    public final boolean ajouterEquipement(Equipement _equipement)
    {
        app.controleur.sauvegarderUndo();
        
        boolean estAjouter = app.controleur.ajouterEquipement(_equipement);
        if (estAjouter)
        {
            app.zoneDeTravail.outils.selectionEquipement.doClick();
            app.actualiser();
        }
        else
            app.controleur.undo();
        
        return estAjouter;
    }
}

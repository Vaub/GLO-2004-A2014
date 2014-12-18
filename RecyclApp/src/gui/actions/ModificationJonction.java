/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import domaine.usine.Jonction;
import domaine.usine.Station;
import gui.RecyclApp;

/**
 *
 * @author Vincent
 */
public class ModificationJonction extends ActionRecyclApp
{
    public final Jonction jonction;

    public ModificationJonction(Jonction _jonction, RecyclApp _app)
    {
        super(_app);
        this.jonction = _jonction;
    }
    
    public void modifierNombreEntrees(int _nb)
    {
        if (jonction.getEntrees().size() != _nb)
        {
            if (jonction.getEntrees().size() > _nb)
                jonction.enleverEntree();
            else
                jonction.ajouterEntree();
            
            app.actualiser();
        }
    }
    
}

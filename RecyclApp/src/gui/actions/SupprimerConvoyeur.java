/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import domaine.usine.Convoyeur;
import gui.RecyclApp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Vincent
 */
public class SupprimerConvoyeur extends ActionRecyclApp implements ActionListener
{
    private final Convoyeur convoyeur;
    
    public SupprimerConvoyeur(Convoyeur _c, RecyclApp _app)
    {
        super(_app);
        
        convoyeur = _c;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (convoyeur != null)
        {
            app.controleur.enleverLiaisonConvoyeur(convoyeur);
            
            app.paramElements.setComposant(null);
            app.controleur.actualiserSchema();
            app.actualiser();
        }
    }
}

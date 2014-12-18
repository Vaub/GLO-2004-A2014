/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import gui.RecyclApp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Magalie
 */
public class Annuler extends ActionRecyclApp implements ActionListener
{
    public Annuler(RecyclApp _app)
    {
        super(_app);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        app.controleur.undo(); 
        app.actualiser();
        
        app.zoneDeTravail.schema.clearAdapteurs();
    }
    
}

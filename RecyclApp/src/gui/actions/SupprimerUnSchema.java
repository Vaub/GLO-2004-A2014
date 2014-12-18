/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import domaine.controle.Schema;
import gui.controls.Icone;
import gui.RecyclApp;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Vincent
 */
public class SupprimerUnSchema extends ActionRecyclApp implements ActionListener
{
    public Schema aSupprimer;
    
    public SupprimerUnSchema(Schema _schema, RecyclApp _app)
    {
        super(_app);
        aSupprimer = _schema;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "Voulez-vous vraiment supprimer votre schéma '"+ aSupprimer.getNom() +"' ?","Confirmation",dialogButton);
        
        if(dialogResult == JOptionPane.YES_OPTION)
        {
            if (app != null && aSupprimer != null)
            {
                app.controleur.supprimerUnSchema(aSupprimer);
                aSupprimer = null;

                app.actualiser();
                app.zoneDeTravail.schema.clearAdapteurs();
            }
        }        
    }    
}

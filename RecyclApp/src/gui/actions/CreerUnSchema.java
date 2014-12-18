/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import domaine.controle.Schema;
import gui.RecyclApp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Vincent
 */
public class CreerUnSchema extends ActionRecyclApp implements ActionListener
{
    public CreerUnSchema(RecyclApp _app)
    {
        super(_app);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JOptionPane jpop = new JOptionPane();
        String s_nom = jpop.showInputDialog(null, "Nom du schéma :", "Nouveau Schéma", JOptionPane.QUESTION_MESSAGE); 
        
        if (s_nom != null && !s_nom.equals("") && !s_nom.isEmpty())
        {
            Schema n_schema = new Schema(s_nom);
            
            app.controleur.ajouterUnSchema(n_schema);
            app.actualiser();                        
            app.zoneDeTravail.schema.clearAdapteurs();
        }
    }
    
}

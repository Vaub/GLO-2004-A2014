/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import domaine.usine.Equipement;
import gui.RecyclApp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author Vincent
 */
public class SupprimerEquipement extends ActionRecyclApp implements ActionListener
{
    private Set<Equipement> equipement;
    
    public SupprimerEquipement(Set<Equipement> _equipement, RecyclApp _app)
    {
        super(_app);
        equipement = _equipement;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (equipement != null && !equipement.isEmpty())
        {
            String message = (equipement.size() == 1) ? "Voulez-vous vraiment supprimer cet équipement?"
                                                      : "Voulez-vous vraiment supprimer ces "+ equipement.size() +" équipements?";
            
            int confirmation = JOptionPane.showConfirmDialog(null, 
                                                            message, 
                                                            "Supprimer", JOptionPane.OK_CANCEL_OPTION);
            
            if (confirmation == JOptionPane.OK_OPTION)
            {
                app.controleur.sauvegarderUndo();
                for (Equipement equip : equipement)
                        app.controleur.supprimerEquipement(equip);
                
                app.paramElements.setComposant(null);
                app.zoneDeTravail.schema.adapteurSelection.clearSelection();
                
                System.gc();
                app.actualiser();
            }
        }
    }
    
}

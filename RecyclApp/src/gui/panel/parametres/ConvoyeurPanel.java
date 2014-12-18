/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.panel.parametres;

import domaine.usine.Convoyeur;
import gui.RecyclApp;
import gui.actions.ModificationConvoyeur;
import gui.actions.SupprimerConvoyeur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author owner
 */
public class ConvoyeurPanel extends ComposantPanel implements ChangeListener
{   
    private final Convoyeur convoyeur;
    
    private final JLabel l_qte = new JLabel("Capacité maximale");
    private final JSpinner a_qte = new JSpinner();
    
    private final ModificationConvoyeur modifConvoyeur;
    
    public ConvoyeurPanel(Convoyeur c, RecyclApp app) 
    {
        super(c, app);
        this.convoyeur = c;
        
        modifConvoyeur = new ModificationConvoyeur(c, app);
        
        if(this.convoyeur != null)
        {
            this.initialiserPanel();
        }
    }
    
    private void initialiserPanel()
    {
       this.updatePanel();
       
       b_supprimer.addActionListener(new SupprimerConvoyeur(convoyeur, app));
       
       a_qte.addChangeListener(this);
       
       g_composant_description.addComponent(l_qte);
       g_composant_valeur.addComponent(a_qte);
       
       g_composant_v.addGroup(layout_composant.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(l_qte).addComponent(a_qte));
    }

    @Override
    public void updatePanel()
    {
        if (convoyeur == null) return;
        
        super.updatePanel();
        
        a_qte.setModel(new SpinnerNumberModel(this.convoyeur.getCapaciteMaximale(), 0d, 1e10, 0.01d));
    }

    

    @Override
    public void stateChanged(ChangeEvent e) 
    {
       if (this.convoyeur == null) return;
       
       if(e.getSource() == this.a_qte)
       {
           double capaciteMax = this.convoyeur.getCapaciteMaximale();
           try
            {
                capaciteMax = (double)a_qte.getValue();
            } catch (Exception ex) { }
           
            modifConvoyeur.modifierCapaciteMax(Math.round(capaciteMax));
       }
    }
}

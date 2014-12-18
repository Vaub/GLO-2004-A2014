/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel.parametres;

import domaine.usine.Equipement;
import domaine.usine.Composant;
import gui.RecyclApp;
import gui.actions.ModificationEquipement;
import gui.actions.SupprimerEquipement;
import gui.controls.BoutonImage;
import gui.controls.Icone;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashSet;
import java.util.Set;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import utils.Position;
import utils.Taille;

/**
 *
 * @author Vincent
 */
public class EquipementPanel extends ComposantPanel implements ChangeListener
{
    private final Equipement equipement;
    
    protected final ModificationEquipement modificationsEquipement;
    
    protected final JLabel l_position = new JLabel("Position"),
                           l_taille   = new JLabel("Taille");
    
    protected final JSpinner a_positionX = new JSpinner(),
                             a_positionY = new JSpinner(),
                             a_tailleL   = new JSpinner(),
                             a_tailleH   = new JSpinner();

    public EquipementPanel(Equipement _equipement, RecyclApp _app)
    {
        super(_equipement, _app);
        
        modificationsEquipement = new ModificationEquipement(_equipement, app);
        equipement = _equipement;
        
        if (equipement != null)
        {
            this.init();
        }
    }
    
    private void init()
    {
        this.updatePanel();
        
        Set<Equipement> supprimer = new HashSet<>();
        supprimer.add(equipement);
        
        b_supprimer.addActionListener(new SupprimerEquipement(supprimer, app));
        
        a_positionX.addChangeListener(this);
        a_positionY.addChangeListener(this);
        a_tailleL.addChangeListener(this);
        a_tailleH.addChangeListener(this);
        
        g_composant_description.addComponent(l_taille)
                               .addComponent(l_position);
        
        g_composant_valeur.addGroup(layout_composant.createSequentialGroup()
                                                    .addComponent(a_tailleL)
                                                    .addComponent(a_tailleH))
                          .addGroup(layout_composant.createSequentialGroup()
                                                    .addComponent(a_positionX)
                                                    .addComponent(a_positionY));
        
        g_composant_v.addGroup(layout_composant.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                               .addComponent(l_taille)
                                               .addGroup(layout_composant.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                         .addComponent(a_tailleL)
                                                                         .addComponent(a_tailleH)));
         
        g_composant_v.addGroup(layout_composant.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                               .addComponent(l_position)
                                               .addGroup(layout_composant.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                         .addComponent(a_positionX)
                                                                         .addComponent(a_positionY)));
    }

    @Override
    public void updatePanel()
    {
        if (equipement == null) return;
        
        super.updatePanel();
        
        a_positionX.setModel(new SpinnerNumberModel(equipement.getPosition().x(), 0d, 1e6d, 1e-3d));
        a_positionY.setModel(new SpinnerNumberModel(equipement.getPosition().y(), 0d, 1e6d, 1e-3d));
        a_tailleL.setModel(new SpinnerNumberModel(equipement.getTaille().largeur(), 1e-3d, 1e6d, 1e-3d));
        a_tailleH.setModel(new SpinnerNumberModel(equipement.getTaille().hauteur(), 1e-3d, 1e6d, 1e-3d));
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        if (equipement == null) return;
        
        if (e.getSource() == a_positionX)
        {
            double posX = equipement.getPosition().x();
            try
            {
                posX = (double)a_positionX.getValue();
            } catch (Exception ex) { }
            
            modificationsEquipement.modifierPosition(new Position(posX, 
                                                        equipement.getPosition().y()));
        }
        else if (e.getSource() == a_positionY)
        {
            double posY = equipement.getPosition().y();
            try
            {
                posY = (double)a_positionY.getValue();
            } catch (Exception ex) { }
            
            modificationsEquipement.modifierPosition(new Position(equipement.getPosition().x(), 
                                                        posY));
        }
        else if (e.getSource() == a_tailleL)
        {
            double largeur = equipement.getTaille().largeur();
            try
            {
                largeur = (double)a_tailleL.getValue();
            } catch (Exception ex) { }
            
            modificationsEquipement.modifierTaille(new Taille(largeur, 
                                                    equipement.getTaille().hauteur()));
        }
        else if (e.getSource() == a_tailleH)
        {
            double hauteur = equipement.getTaille().largeur();
            try
            {
                hauteur = (double)a_tailleH.getValue();
            } catch (Exception ex) { }
            
            modificationsEquipement.modifierTaille(new Taille(equipement.getTaille().largeur(), 
                                                    hauteur));
        }
    }
    
}

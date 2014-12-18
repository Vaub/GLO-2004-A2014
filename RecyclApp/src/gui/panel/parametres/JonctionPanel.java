/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel.parametres;

import domaine.usine.Equipement;
import domaine.usine.Jonction;
import gui.RecyclApp;
import gui.actions.ModificationStation;
import gui.actions.ModificationJonction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import utils.Orientation;

/**
 *
 * @author Vincent
 */
public class JonctionPanel extends EquipementPanel implements ActionListener
{
    private final Jonction jonction;
    
    private final ModificationJonction modifications;
    
    private final JLabel l_entrees  = new JLabel("Nombre d'entrées"),
                         l_orientationEntrees = new JLabel("Orientation entrées"),
                         l_orientationSorties = new JLabel("Orientation sorties");
    
    private final JSpinner a_entrees = new JSpinner();
    
    private final JComboBox<Orientation> a_orientationEntrees = new JComboBox<>(Orientation.values()),
                                         a_orientationSorties = new JComboBox<>(Orientation.values());
    
    public JonctionPanel(Jonction _jonction, RecyclApp _app)
    {
        super(_jonction, _app);
        
        jonction = _jonction;
        modifications = new ModificationJonction(_jonction, _app);
        
        if (jonction != null)
        {
            this.init();
        }
    }
    
    private void init()
    {
        this.updatePanel();
        
        a_entrees.addChangeListener(this);
        
        a_orientationEntrees.addActionListener(this);
        a_orientationSorties.addActionListener(this);
        
        g_composant_description.addComponent(l_entrees)
                               .addComponent(l_orientationEntrees)
                               .addComponent(l_orientationSorties);
        
        g_composant_valeur.addComponent(a_entrees)
                          .addComponent(a_orientationEntrees)
                          .addComponent(a_orientationSorties);
        
        g_composant_v.addGroup(layout_composant.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                               .addComponent(l_entrees).addComponent(a_entrees));
        g_composant_v.addGroup(layout_composant.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                               .addComponent(l_orientationEntrees).addComponent(a_orientationEntrees));
        g_composant_v.addGroup(layout_composant.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                               .addComponent(l_orientationSorties).addComponent(a_orientationSorties));
    }

    @Override
    public void updatePanel()
    {
        if (jonction == null) return;
        
        super.updatePanel();
        
        a_entrees.setModel(new SpinnerNumberModel(jonction.getEntrees().size(), 1, 100, 1));
        
        a_orientationEntrees.setSelectedItem(jonction.getOrientationEntrees());
        a_orientationSorties.setSelectedItem(jonction.getOrientationSorties());
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        super.stateChanged(e);
        
        if (jonction == null) return;
        
        if (e.getSource() == a_entrees)
        {
            int nbEntrees = jonction.getEntrees().size();
            try
            {
                nbEntrees = (int)a_entrees.getValue();
            } catch (Exception ex) { }
            
            modifications.modifierNombreEntrees(nbEntrees);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (jonction == null) return;
        
        if (e.getSource() == a_orientationEntrees || e.getSource() == a_orientationSorties)
            modificationsEquipement.modifierOrientation((Orientation)a_orientationEntrees.getSelectedItem(),
                                                        (Orientation)a_orientationSorties.getSelectedItem());
    }
    
}

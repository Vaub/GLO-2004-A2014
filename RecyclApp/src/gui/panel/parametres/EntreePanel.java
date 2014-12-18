/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel.parametres;

import domaine.usine.EntreeUsine;
import domaine.usine.Equipement;
import gui.RecyclApp;
import gui.dialogs.ModifierApparenceStation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import utils.Orientation;

/**
 *
 * @author Vincent
 */
public class EntreePanel extends EquipementPanel implements ActionListener
{
    private final EntreeUsine entree;
    
    private final JLabel l_orientationSortie = new JLabel("Orientation sortie");
    private final JComboBox<Orientation> a_orientationSortie = new JComboBox<>(Orientation.values());
    
    public EntreePanel(EntreeUsine _entree, RecyclApp _app)
    {
        super(_entree, _app);
        
        entree = _entree;
        if (entree != null)
        {
            this.init();
        }
    }
    
    private void init()
    {
        this.updatePanel();
        
        a_orientationSortie.addActionListener(this);
        
        g_composant_description.addComponent(l_orientationSortie);
        g_composant_valeur.addComponent(a_orientationSortie);
        
        g_composant_v.addGroup(layout_composant.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                               .addComponent(l_orientationSortie)
                                               .addComponent(a_orientationSortie));
    }

    @Override
    public void updatePanel()
    {
        if (entree == null) return;
        
        super.updatePanel();
        
        a_orientationSortie.setSelectedItem(entree.getOrientationSorties());
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (entree == null) return;
        
        if (e.getSource() == a_orientationSortie)
            modificationsEquipement.modifierOrientationSortie((Orientation)a_orientationSortie.getSelectedItem());
    }
}

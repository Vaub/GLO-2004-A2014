/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel.parametres;

import domaine.usine.SortieUsine;
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
public class SortiePanel extends EquipementPanel implements ActionListener
{
    private final SortieUsine sortie;
    
    private final JLabel l_orientationEntree = new JLabel("Orientation entrée");
    private final JComboBox<Orientation> a_orientationEntree = new JComboBox<>(Orientation.values());
    
    public SortiePanel(SortieUsine _sortie, RecyclApp _app)
    {
        super(_sortie, _app);
        
        sortie = _sortie;
        if (sortie != null)
        {
            this.init();
        }
    }
    
    private void init()
    {
        this.updatePanel();
        
        a_orientationEntree.addActionListener(this);
        
        g_composant_description.addComponent(l_orientationEntree);
        g_composant_valeur.addComponent(a_orientationEntree);
        
        g_composant_v.addGroup(layout_composant.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                               .addComponent(l_orientationEntree)
                                               .addComponent(a_orientationEntree));
    }

    @Override
    public void updatePanel()
    {
        if (sortie == null) return;
        
        super.updatePanel();
        
        a_orientationEntree.setSelectedItem(sortie.getOrientationEntrees());
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (sortie == null) return;
        
        if (e.getSource() == a_orientationEntree)
            modificationsEquipement.modifierOrientationEntree((Orientation)a_orientationEntree.getSelectedItem());
    }
}

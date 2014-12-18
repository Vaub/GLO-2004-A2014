/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel.parametres;

import domaine.usine.Equipement;
import domaine.usine.Composant;
import domaine.usine.Station;
import gui.RecyclApp;
import gui.actions.ModificationStation;
import gui.controls.BoutonImage;
import gui.controls.Icone;
import gui.dialogs.ModifierApparenceStation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import utils.Orientation;

/**
 *
 * @author Vincent
 */
public final class StationPanel extends EquipementPanel implements ActionListener
{
    private final Station station;
    
    private final ModificationStation modifications;
    
    protected TransformationPanel p_transformation;
    protected TriPanel p_tri;
    
    private final JLabel l_capacite = new JLabel("Capacité maximale"),
                         l_sorties  = new JLabel("Nombre de sorties"),
                         l_orientationEntrees = new JLabel("Orientation entrées"),
                         l_orientationSorties = new JLabel("Orientation sorties");
    
    private final JSpinner a_capacite = new JSpinner(),
                           a_sorties  = new JSpinner();
    
    private final JComboBox<Orientation> a_orientationEntrees = new JComboBox<>(Orientation.values()),
                                         a_orientationSorties = new JComboBox<>(Orientation.values());
    
    private final BoutonImage b_apparence = new BoutonImage("Modifier l'apparence", 
                                                            Icone.PEINTURER, 
                                                            20, 
                                                            true);
    
    public StationPanel(Station _composant, RecyclApp _app)
    {
        super(_composant, _app);
        
        station = _composant;
        modifications = new ModificationStation(station, _app);
        
        if (station != null)
        {
            p_tri = new TriPanel(_app, station);
            p_transformation = new TransformationPanel(_app, station);
            
            p_tri.setBackground(Color.WHITE);
            p_transformation.setBackground(Color.WHITE);
            
            this.init();
            
            tab.addTab("Transformation", p_transformation);
            tab.addTab("Tri", p_tri);
            
            this.add(p_icon, BorderLayout.SOUTH);
        }
    }
    
    private void init()
    {
        this.updatePanel();
        
        b_apparence.setVerticalTextPosition(SwingConstants.CENTER);
        b_apparence.setHorizontalTextPosition(SwingConstants.RIGHT);
        b_apparence.addActionListener(this);
        
        p_icon.add(b_apparence);
        
        a_capacite.addChangeListener(this);
        a_sorties.addChangeListener(this);
        a_orientationEntrees.addActionListener(this);
        a_orientationSorties.addActionListener(this);
        
        g_composant_description.addComponent(l_capacite)
                               .addComponent(l_sorties)
                               .addComponent(l_orientationEntrees)
                               .addComponent(l_orientationSorties);
        
        g_composant_valeur.addComponent(a_capacite)
                          .addComponent(a_sorties)
                          .addComponent(a_orientationEntrees)
                          .addComponent(a_orientationSorties);
        
        g_composant_v.addGroup(layout_composant.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                               .addComponent(l_capacite).addComponent(a_capacite));
        g_composant_v.addGroup(layout_composant.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                               .addComponent(l_sorties).addComponent(a_sorties));
        g_composant_v.addGroup(layout_composant.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                               .addComponent(l_orientationEntrees).addComponent(a_orientationEntrees));
        g_composant_v.addGroup(layout_composant.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                               .addComponent(l_orientationSorties).addComponent(a_orientationSorties));
    }
    
    @Override
    public void updatePanel()
    {
        if (station == null) return;
        
        super.updatePanel();
        
        p_tri.updateTable();
        p_transformation.mettreAJourTable();
        
        a_capacite.setModel(new SpinnerNumberModel(station.getCapaciteMaximale(), 1.0d, 1e6d, 0.1d));
        a_sorties.setModel(new SpinnerNumberModel(station.getSorties().size(), 1, 100, 1));
        
        a_orientationEntrees.setSelectedItem(station.getOrientationEntrees());
        a_orientationSorties.setSelectedItem(station.getOrientationSorties());
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        super.stateChanged(e);
        
        if (station == null) return;
        
        if (e.getSource() == a_capacite)
        {
            double capacite = station.getCapaciteMaximale();
            try
            {
                capacite = (double)a_capacite.getValue();
            } catch (Exception ex) { }
            
            modifications.modifierCapaciteMaximale(capacite);
        }
        else if (e.getSource() == a_sorties)
        {
            int nbSorties = station.getSorties().size();
            try
            {
                nbSorties = (int)a_sorties.getValue();
            } catch (Exception ex) { }
            
            modifications.modifierNombreSorties(nbSorties);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (station == null) return;
        
        if (e.getSource() == a_orientationEntrees)
            modifications.modifierOrientationEntrees((Orientation)a_orientationEntrees.getSelectedItem());
        else if (e.getSource() == a_orientationSorties)
            modifications.modifierOrientationSorties((Orientation)a_orientationSorties.getSelectedItem());
        else if (e.getSource() == b_apparence)
            new ModifierApparenceStation(app, station);
    }
    
    
}

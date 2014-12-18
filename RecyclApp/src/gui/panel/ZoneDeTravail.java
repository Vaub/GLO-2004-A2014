/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel;

import domaine.usine.EntreeUsine;
import gui.panel.PanelSchema.Etat;
import gui.RecyclApp;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import domaine.usine.Jonction;
import domaine.usine.Matiere;
import domaine.usine.SortieUsine;
import domaine.usine.Station;
import utils.Position;
import utils.Taille;

/**
 *
 * @author Vincent
 */
public class ZoneDeTravail extends JPanel implements ActionListener
{
    public final RecyclApp parent;
    
    public final Outils outils;
    public final PanelSchema schema;
    
    public JScrollPane gridPane;
    
    public ZoneDeTravail(RecyclApp _parent)
    {
        parent = _parent;
        
        outils = new Outils(_parent);
        schema = new PanelSchema(this);
        
        //this.setOpaque(false);
        this.initLayout();
        
        outils.selectionNormale.addActionListener(this);
        outils.selectionEquipement.addActionListener(this);
        outils.selectionConnecter.addActionListener(this);
        outils.station.addActionListener(this);
        outils.jonction.addActionListener(this);
        outils.entree.addActionListener(this);
        outils.sortie.addActionListener(this);
    }
    
    private void initLayout()
    {
        GridBagConstraints gbc = new GridBagConstraints();
        
        this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        
        JPanel bordureDuSchema = new JPanel(new BorderLayout());
        bordureDuSchema.setOpaque(false);
        bordureDuSchema.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        
        gridPane = new JScrollPane(schema);
        gridPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //gridPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //gridPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //gridPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        //gridPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        bordureDuSchema.add(gridPane);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        
        this.add(outils, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        
        this.add(bordureDuSchema, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == outils.station)
        {
            schema.setEquipAjout(new Station("", new Position(50,50), 
                                                 new Taille(1,1),
                                                 5000,
                                                 parent.controleur.getMatieresSchema()));
            schema.setEtat(Etat.AJOUT);
        }
        else if (e.getSource() == outils.jonction)
        {
            schema.setEquipAjout(new Jonction(new Position(50,50)));
            schema.setEtat(Etat.AJOUT);
        }
        else if (e.getSource() == outils.entree)
        {
            schema.setEquipAjout(new EntreeUsine(new Position(50,50), new Taille(1,1)));
            schema.setEtat(Etat.AJOUT);
        }
        else if (e.getSource() == outils.sortie)
        {
            schema.setEquipAjout(new SortieUsine(new Position(50,50), new Taille(1,1)));
            schema.setEtat(Etat.AJOUT);
        }
        else if (e.getSource() == outils.selectionConnecter)
        {
            schema.setEtat(Etat.CONNEXION);
        }
        else if (e.getSource() == outils.selectionNormale)
        {
            schema.setEtat(Etat.NORMAL);
        }
        else
            schema.setEtat(Etat.SELECTION);
    }
}

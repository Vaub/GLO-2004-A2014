/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel;

import gui.controls.Icone;
import gui.controls.ToggleImage;
import gui.RecyclApp;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import ressources.Parametres;
import utils.ColorUtilities;

/**
 *
 * @author Vincent
 */
public class Outils extends JPanel
{
    protected final RecyclApp parent;
    
    protected JPanel selections;
    protected Box outils;
    
    public ToggleImage selectionNormale,
                       selectionEquipement,
                       selectionConnecter,
                       jonction,
                       station,
                       entree,
                       sortie;
    
    protected JLabel titre;
    
    public Outils(RecyclApp _parent)
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));
        this.setOpaque(false);
        
        parent = _parent;
        
        titre = new JLabel("Boîte à outils", SwingConstants.CENTER);
        titre.setForeground(Color.WHITE);
        
        titre.setFont(new Font(titre.getFont().getName(), Font.BOLD, 16));
        
        this.initSelection();
        this.initEquipement();
        
        ButtonGroup groupeOutils = new ButtonGroup();
        groupeOutils.add(selectionNormale);
        groupeOutils.add(selectionEquipement);
        groupeOutils.add(selectionConnecter);
        groupeOutils.add(jonction);
        groupeOutils.add(station);
        groupeOutils.add(entree);
        groupeOutils.add(sortie);

        this.add(selections);
        this.add(Box.createVerticalStrut(10));
        this.add(outils);
        this.add(Box.createVerticalGlue());
        
        selectionEquipement.doClick();
        
        this.revalidate();
    }
    
    private void initSelection()
    {
        Color couleurIcones = Parametres.getCouleur(Parametres.COULEUR_CONTRASTE),
              couleurContraste = ColorUtilities.ContrastWhiteOrBlack(Parametres.getCouleur(Parametres.COULEUR_FOND));
        
        selectionNormale = new ToggleImage("Déplacer", 
                                           Icone.MAIN, 30, false, 
                                           couleurIcones, 
                                           couleurContraste);
        
        selectionEquipement = new ToggleImage("Sélectionner", 
                                              Icone.SELECTION, 30, false, 
                                              couleurIcones, 
                                              couleurContraste);
        
        selectionConnecter = new ToggleImage("Connecter", 
                                             Icone.CONNECTER, 30, false, 
                                             couleurIcones, 
                                             couleurContraste);
        
        selections = new JPanel(new FlowLayout());
        selections.setOpaque(false);
        
        selections.add(selectionEquipement);
        selections.add(selectionConnecter);
        selections.add(selectionNormale);
        
        selectionEquipement.setAlignmentX(CENTER_ALIGNMENT);
        selectionConnecter.setAlignmentX(CENTER_ALIGNMENT);
        selectionNormale.setAlignmentX(CENTER_ALIGNMENT);
        selections.setAlignmentX(CENTER_ALIGNMENT);
    }
    
    private void initEquipement()
    {
        Color couleurIcones = Parametres.getCouleur(Parametres.COULEUR_CONTRASTE),
              couleurContraste = ColorUtilities.ContrastWhiteOrBlack(Parametres.getCouleur(Parametres.COULEUR_FOND));
        
        jonction = new ToggleImage("Jonction", 
                                   Icone.JONCTION, 80, false, 
                                   couleurIcones, 
                                   couleurContraste);
        
        station = new ToggleImage("Station", 
                                  Icone.STATION, 80, false, 
                                  couleurIcones, 
                                  couleurContraste);
        
        entree = new ToggleImage("Entrée", 
                                 Icone.ENTREE, 80, false, 
                                 couleurIcones, 
                                 couleurContraste);
        
        sortie = new ToggleImage("Sortie", 
                                 Icone.SORTIE, 80, false, 
                                 couleurIcones, 
                                 couleurContraste);
        
        outils = Box.createVerticalBox();
        outils.add(station);
        outils.add(Box.createVerticalStrut(10));
        outils.add(jonction);
        outils.add(Box.createVerticalStrut(10));
        outils.add(entree);
        outils.add(Box.createVerticalStrut(10));
        outils.add(sortie);
        
        station.setAlignmentX(CENTER_ALIGNMENT);
        jonction.setAlignmentX(CENTER_ALIGNMENT);
        entree.setAlignmentX(CENTER_ALIGNMENT);
        sortie.setAlignmentX(CENTER_ALIGNMENT);
        outils.setAlignmentX(CENTER_ALIGNMENT);
    }
    
    public void resetGUI()
    {
        Color couleurToggle = Parametres.getCouleur(Parametres.COULEUR_CONTRASTE),
              couleurContraste = ColorUtilities.ContrastWhiteOrBlack(Parametres.getCouleur(Parametres.COULEUR_FOND));
        
        jonction.setCouleur(couleurToggle, couleurContraste);
        station.setCouleur(couleurToggle, couleurContraste);
        entree.setCouleur(couleurToggle, couleurContraste);
        sortie.setCouleur(couleurToggle, couleurContraste);
        selectionNormale.setCouleur(couleurToggle, couleurContraste);
        selectionEquipement.setCouleur(couleurToggle, couleurContraste);
        selectionConnecter.setCouleur(couleurToggle, couleurContraste);
        
//        if (selectionEquipement.isSelected())
//            selectionNormale.doClick();
//        
//        selectionEquipement.doClick();
        
        this.repaint();
    }
}

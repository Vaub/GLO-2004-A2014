/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controls;

import utils.ColorUtilities;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author Vincent
 */
public class ToggleImage extends JToggleButton implements ItemListener
{
    public Border BORDER = BorderFactory.createEmptyBorder(1, 1, 1, 1);
    
    public Color HOVER_BACKGROUND = new Color(225,225,225);
    public Border HOVER_BORDER = BorderFactory.createLineBorder((new Color(225,225,225)).darker().darker(), 1);
    
    protected Color couleur;
    
    protected ImageIcon imageToggled,
                        imageNotToggled;
    
    
    protected String texte;
    
    /**
     * Constructeur par défaut de BoutonImage
     * 
     * @param _texte Texte du bouton (incluant le tooltip)
     * @param _icone Icone/Image du bouton
     * @param _taille Taille de l'icone/image
     * @param _afficherTexte Affichage du texte bas-centre par défaut
     * @param _couleurIcone Couleur de l'îcone (null pour la couleur par défaut)
     */
    public ToggleImage(String _texte, Icone _icone, int _taille, boolean _afficherTexte, Color _toggled, Color _notToggled)
    {
        Image imageModifiee = _icone.getImage().getImage(),
              imageModifieeT = _icone.getImage().getImage();
        
        if (_notToggled != null)
        {
            this.setForeground(_notToggled);
            imageModifiee = ColorUtilities.ColorImage(imageModifiee, _notToggled);
        }
        
        if (_toggled != null)
            imageModifieeT = ColorUtilities.ColorImage(imageModifiee, _toggled);
        
        // On met l'icone de la taille souhaitée
        imageModifiee = imageModifiee.getScaledInstance(_taille, _taille, Image.SCALE_AREA_AVERAGING);
        imageModifieeT = imageModifieeT.getScaledInstance(_taille, _taille, Image.SCALE_AREA_AVERAGING);

        texte = _texte;
        
        imageToggled = new ImageIcon(imageModifieeT);
        imageNotToggled = new ImageIcon(imageModifiee);
        
        //couleur = _couleurIcone;
        
        this.setIcon(imageNotToggled);
        this.setToolTipText(texte);
        
        if (_afficherTexte)
        {
            this.setHorizontalTextPosition(SwingConstants.CENTER);
            this.setVerticalTextPosition(SwingConstants.BOTTOM);
            this.setText(" "+ texte +" ");
        }
        
        //this.setOpaque(false);
        //this.setContentAreaFilled(false);
        
        this.init();
    }
    
    private void init()
    {
        this.setContentAreaFilled(false); 
        this.setFocusPainted(false); 
        this.setOpaque(false);
        
        this.setBorder(BORDER);
        this.setBackground(null);
        
        this.addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent e)
    {
        if (e.getStateChange() == ItemEvent.SELECTED)
            this.setIcon(imageToggled);
        else
             this.setIcon(imageNotToggled);
    }
    
    public final void setCouleur(Color _toggled, Color _notToggled)
    {
        Image imageModifiee = imageToggled.getImage(),
              imageModifieeT = imageNotToggled.getImage();
        
        if (_notToggled != null)
        {
            this.setForeground(_notToggled);
            imageModifiee = ColorUtilities.ColorImage(imageModifiee, _notToggled);
        }
        
        if (_toggled != null)
            imageModifieeT = ColorUtilities.ColorImage(imageModifiee, _toggled);
        
        imageToggled = new ImageIcon(imageModifieeT);
        imageNotToggled = new ImageIcon(imageModifiee);
        
        if (this.isSelected())
            this.setIcon(imageToggled);
        else
            this.setIcon(imageNotToggled);
    }
}

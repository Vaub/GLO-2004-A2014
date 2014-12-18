/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controls;

import utils.ColorUtilities;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * Classe permettant la création d'un bouton contenant une icone/image
 * 
 * @author Vincent
 */
public class BoutonImage extends JButton implements MouseListener
{
    public static Border BORDER = BorderFactory.createEmptyBorder(1, 1, 1, 1);
    
    public static Color HOVER_BACKGROUND = new Color(225,225,225);
    public static Border HOVER_BORDER = BorderFactory.createLineBorder((new Color(225,225,225)).darker().darker(), 1);
    
    protected Color couleur;
    
    protected ImageIcon image;
    protected String texte;
    
    public BoutonImage(String _texte, Icone _icone, int _taille, boolean _afficherTexte)
    {
        this(_texte, _icone, _taille, _afficherTexte, null);
    }
    
    /**
     * Constructeur par défaut de BoutonImage
     * 
     * @param _texte Texte du bouton (incluant le tooltip)
     * @param _icone Icone/Image du bouton
     * @param _taille Taille de l'icone/image
     * @param _afficherTexte Affichage du texte bas-centre par défaut
     * @param _couleurIcone Couleur de l'îcone (null pour la couleur par défaut)
     */
    public BoutonImage(String _texte, Icone _icone, int _taille, boolean _afficherTexte, Color _couleurIcone)
    {
        if (_icone != null)
        {
            Image imageModifiee = _icone.getImage(_taille, _taille).getImage();
            if (_couleurIcone != null)
            {
                this.setForeground(_couleurIcone);
                imageModifiee = ColorUtilities.ColorImage(imageModifiee, _couleurIcone);
            }

            // On met l'icone de la taille souhaitée
            //imageModifiee = imageModifiee.getScaledInstance(_taille, _taille, Image.SCALE_AREA_AVERAGING);
            image = new ImageIcon(imageModifiee);
        }

        texte = _texte;
        
        
        this.setIcon(image);
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
        
        this.addMouseListener(this);
    }

    @Override
    public void setEnabled(boolean b)
    {
        if (!b)
        {
            this.setBackground(null);
            this.setBorder(null);
        }
        
        super.setEnabled(b); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (this.isEnabled())
        {
            this.setOpaque(true);
            this.setBackground(HOVER_BACKGROUND.darker());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if ( this.isEnabled() &&
            ( e.getX() <= this.getWidth() && e.getY() <= this.getHeight() ) &&
            ( e.getX() >= 0 && e.getY() >= 0 ))
        {
            this.setOpaque(true);
            
            this.setBorder(HOVER_BORDER);
            this.setBackground(HOVER_BACKGROUND);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        if (this.isEnabled())
        {
            this.setOpaque(true);
            
            this.setBorder(HOVER_BORDER);
            this.setBackground(HOVER_BACKGROUND);
        }
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        if (this.isEnabled())
        {
            this.setOpaque(false);
            
            this.setBorder(BORDER);
            this.setBackground(null);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controls;

import static gui.controls.BoutonImage.BORDER;
import static gui.controls.BoutonImage.HOVER_BACKGROUND;
import static gui.controls.BoutonImage.HOVER_BORDER;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTextField;
import utils.ColorUtilities;

/**
 *
 * @author Vincent
 */
public class BoutonCouleur extends JTextField implements MouseListener
{
    protected Color couleur;
    
    public BoutonCouleur(Color _couleur)
    {
        couleur = (_couleur != null) ? _couleur
                                     : Color.BLACK;
        
        this.init();
    }
    
    private void init()
    {
        this.setEditable(false);
        this.setCouleur(couleur);
        
        this.addMouseListener(this);
    }
    
    public void setCouleur(Color _couleur)
    {
        couleur = (_couleur != null) ? _couleur
                                     : new Color(0,0,0);
        
        couleur = new Color(couleur.getRed(), couleur.getGreen(), couleur.getBlue());
        
        Color texte = ColorUtilities.ContrastWhiteOrBlack(couleur);
        
        this.setForeground(texte);
        this.setBackground(couleur);
        
        this.setText(couleur.getRed() +", "+ couleur.getGreen() +", "+ couleur.getBlue());
    }
    
    public Color getCouleur()
    {
        return couleur;
    }
    
    /*@Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        g.setColor(couleur);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }*/
    
    @Override
    public void mousePressed(MouseEvent e)
    {
        setCouleur(JColorChooser.showDialog(this, "Couleur", couleur));
    }
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel;

import gui.RecyclApp;
import utils.ColorUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import ressources.Parametres;

/**
 *
 * @author Vincent
 */
public class PanelFond extends JPanel
{
    public BufferedImage fond;
    
    public PanelFond()
    {
        this.setLayout(new BorderLayout());
        this.setBackground(Parametres.getCouleur(Parametres.COULEUR_FOND));
        
        fond = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        if (fond.getWidth() != this.getWidth() ||
            fond.getHeight() != this.getHeight())
            this.creerFond();
        
        g2d.drawImage(fond, 0, 0, null);
    }
    
    private void creerFond()
    {
        fond = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = fond.createGraphics();
        
        Color fond = Parametres.getCouleur(Parametres.COULEUR_FOND);
        
        this.setBackground(fond);
        Color contrasteGrille = ColorUtilities.ContrastWhiteOrBlack(fond);
        
        int largeur = this.getWidth();
        int hauteur = this.getHeight();
        
        g2d.setColor(ColorUtilities.AppliquerAlpha(contrasteGrille, fond, 0.05f));
        for (int x = 10; x < largeur; x += 10)
            g2d.drawLine(x, 0, x, hauteur);
        for (int y = 10; y < hauteur; y += 10)
            g2d.drawLine(0, y, largeur, y);
        
        g2d.setColor(ColorUtilities.AppliquerAlpha(contrasteGrille, fond, 0.15f));
        for (int x = 20; x < largeur; x += 20)
            g2d.drawLine(x, 0, x, hauteur);
        for (int y = 20; y < hauteur; y += 20)
            g2d.drawLine(0, y, largeur, y);
        
        g2d.setColor(ColorUtilities.AppliquerAlpha(contrasteGrille, fond, 0.3f));
        for (int x = 80; x < largeur; x += 80)
            g2d.drawLine(x, 0, x, hauteur);
        for (int y = 80; y < hauteur; y += 80)
            g2d.drawLine(0, y, largeur, y);
        
        g2d.dispose();
    }
    
    public void resetGUI()
    {
        this.creerFond();
        this.repaint();
    }
    
}

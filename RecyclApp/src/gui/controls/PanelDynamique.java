/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controls;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * JPanel permettant l'agrandissement vers la gauche
 * 
 * @author Vincent
 */
public class PanelDynamique extends JPanel implements MouseListener, MouseMotionListener
{
    public static final int LARGEUR_SELECTION = 5;
    
    private boolean enModification;
    private Point origine;
    
    /**
     * Constructeur principal
     */
    public PanelDynamique()
    {
        this.resetModifications();
        
        this.setOpaque(false);
        
        this.setBorder(BorderFactory.createEmptyBorder(0,LARGEUR_SELECTION,0,0));
        this.addMouseMotionListener(this);
        
        //this.setMaximumSize(new Dimension(500, this.getMaximumSize().height));
    }
    
    private void resetModifications()
    {
        enModification = false;
        origine = null;
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        int translationX = 0;
        
        if (enModification &&
            origine != null)
        {
            translationX = (e.getX() - origine.x) * -1;
            
            for (Component c : this.getComponents())
            {
                int width = c.getPreferredSize().width + translationX;
                
                if (width < 900 &&
                    width > 350)
                    c.setPreferredSize(new Dimension(width, c.getPreferredSize().height));
            }
            
            //if (this.getPreferredSize().width + translationX >= this.getMinimumSize().width &&
            //    this.getPreferredSize().width + translationX < 600)
            //{
             //   this.setPreferredSize(new Dimension(this.getPreferredSize().width + translationX, this.getPreferredSize().height));
            //}
        }
        
        this.revalidate();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        origine = e.getPoint();

        if ((e.getX() >= 0 && e.getX() <= LARGEUR_SELECTION))
        {
            enModification = true;
            this.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
        }
        else
        {
            this.resetModifications();
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    // <editor-fold desc="MouseListener event non-utilisés">
    @Override
    public void mouseReleased(MouseEvent e)
    {
        this.resetModifications();
    }
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        
    }

    @Override
    public void mousePressed(MouseEvent e)
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
    // </editor-fold>
}

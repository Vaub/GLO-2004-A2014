/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel;

import gui.controls.BoutonImage;
import gui.controls.ToggleImage;
import gui.controls.Icone;
import gui.RecyclApp;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 *
 * @author Vincent
 */
public class BarreEtat extends JPanel implements ActionListener, MouseListener, ChangeListener
{
    public RecyclApp parent;
    
    public boolean zoomPerformant;
    
    public BoutonImage zoomValue;
    public JSlider zoomSlider;
    
    public BoutonImage agrandir,
                       reduire;
    
    public ToggleImage grille;
    public ToggleImage magnetique;
    
    public JLabel positionX,
                  positionY;
    
    public BarreEtat(RecyclApp _parent)
    {
        parent = _parent;
        zoomPerformant = false;
                
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        this.setBackground(new Color(75,75,75));
        this.setBorder(BorderFactory.createEmptyBorder(4, 2, 2, 3));
        
        this.init();
        
        this.repaint();
    }
    
    private void init()
    {
        this.initSlider();
        
        reduire = new BoutonImage("Réduire", Icone.MOINS, 12, false, Color.WHITE);
        reduire.addActionListener(this);
        
        agrandir = new BoutonImage("Agrandir", Icone.PLUS, 12, false, Color.WHITE);
        agrandir.addActionListener(this);
        
        zoomValue = new BoutonImage("1.00x", null, 0, true, Color.WHITE);
        zoomValue.setPreferredSize(new Dimension(45, 20));
        
        zoomValue.addActionListener(this);
        
        Font etatFont = new Font(zoomValue.getFont().getFontName(), 
                                 Font.BOLD, zoomValue.getFont().getSize());
                
        zoomValue.setFont(etatFont);
        
        zoomValue.setForeground(Color.WHITE);
        zoomValue.setBorder(null);
        zoomValue.setOpaque(false);
        
        magnetique = new ToggleImage("Magnétiser la grille", Icone.MAGNET, 20, false, Color.WHITE, new Color(150,150,150));
        magnetique.addActionListener(this);
        
        grille = new ToggleImage("Afficher la grille", Icone.GRILLE, 20, false, Color.WHITE, new Color(150,150,150));
        grille.addActionListener(this);
        grille.doClick();
        
        positionX = new JLabel("0.00");
        positionX.setFont(etatFont);
        positionX.setForeground(Color.WHITE);
        
        positionY = new JLabel("0.00");
        positionY.setFont(etatFont);
        positionY.setForeground(Color.WHITE);
        
        this.add(magnetique);
        this.add(Box.createHorizontalStrut(2));
        this.add(grille);
        this.add(Box.createHorizontalStrut(5));
        this.add(positionX);
        this.add(Box.createHorizontalStrut(5));
        this.add(positionY);
        this.add(Box.createHorizontalGlue());
        this.add(reduire);
        this.add(zoomSlider);
        this.add(agrandir);
        this.add(zoomValue);
    }
    
    private void initSlider()
    {
        zoomSlider = new JSlider(JSlider.HORIZONTAL, 0, 94, 14);
        zoomSlider.setOpaque(false);
        
        zoomSlider.setMinimumSize(new Dimension(150, 15));
        zoomSlider.setPreferredSize(new Dimension(150, 15));
        zoomSlider.setMaximumSize(new Dimension(150, 15));
        
        zoomSlider.setUI(new SliderUI(zoomSlider));
        
        zoomSlider.addMouseListener(this);
        zoomSlider.addChangeListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == grille)
        {
            magnetique.setEnabled(grille.isSelected());
            parent.zoneDeTravail.schema.afficherGrille(grille.isSelected());
        }
        else if (e.getSource() == magnetique)
        {
            parent.zoneDeTravail.schema.setMagnetique(magnetique.isSelected());
        }  
        else if (e.getSource() == zoomValue)
        {
            zoomSlider.setValue(14);
        }
        else if (e.getSource() == agrandir)
        {
            int goTo = (zoomSlider.getValue() == zoomSlider.getMaximum()) ? zoomSlider.getValue() 
                                                                          : zoomSlider.getValue() + 1;

            zoomSlider.setValue(goTo);
        }
        else if (e.getSource() == reduire)
        {
            int goTo = (zoomSlider.getValue() == zoomSlider.getMinimum()) ? zoomSlider.getValue() 
                                                                          : zoomSlider.getValue() - 1;

            zoomSlider.setValue(goTo);
        }
    }
    
    @Override
    public void stateChanged(ChangeEvent e)
    {
        if (e.getSource() == zoomSlider)
        {
            int zoom = zoomSlider.getValue();
            
            
            double zoomFloat = (zoom < 74) ? (0.05f*zoom + 0.3) :
                               (0.30f*zoom - 18.2f);
            
            //zoomValue.setText(new DecimalFormat("##.##").format(zoomFloat) + "x");
            zoomValue.setText(String.format("%.02fx", zoomFloat));
            
            if (!zoomSlider.getValueIsAdjusting())
                parent.zoneDeTravail.schema.setZoom((float)zoomFloat);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (e.getSource() == zoomSlider)
            zoomPerformant = true;
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (e.getSource() == zoomSlider)
        {
            zoomPerformant = false;
        }
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

class SliderUI extends BasicSliderUI
{
    public SliderUI(JSlider b)
    {
        super(b);
    }

    @Override
    public void paintTrack(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.WHITE);
        
        if (slider.getOrientation() == SwingConstants.HORIZONTAL) 
        {
            g2d.drawLine(trackRect.x, trackRect.y + trackRect.height / 2, 
                         trackRect.x + trackRect.width, trackRect.y + trackRect.height / 2);
        } 
        else 
        {
            g2d.drawLine(trackRect.x + trackRect.width / 2, trackRect.y, 
                         trackRect.x + trackRect.width / 2, trackRect.y + trackRect.height);
        }
    }

    @Override
    public void paintThumb(Graphics g)
    {
        thumbRect.width = 4;
        
        Graphics2D g2d = (Graphics2D) g;
        int x1 = thumbRect.x;
        int x2 = thumbRect.x + thumbRect.width;
        int topY = thumbRect.y;
        
        GeneralPath shape = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        shape.moveTo(x1, topY);
        shape.lineTo(x2, topY);
        shape.lineTo(x2, topY + thumbRect.height);
        shape.lineTo(x1, topY + thumbRect.height);
        shape.closePath();
        g2d.setPaint(Color.WHITE);
        g2d.fill(shape);
        
        //g2d.setPaint(Color.WHITE);
        //g2d.fillRect(x1, thumbRect.y, x2, thumbRect.height);
    }
    
    
    
    
}

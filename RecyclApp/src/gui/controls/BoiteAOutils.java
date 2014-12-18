/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * JPanel possédant une barre de tire, un JScrollPane et pouvant être mis en JDialog
 * 
 * @author Vincent
 */
public class BoiteAOutils extends JPanel implements MouseListener, ActionListener, WindowListener
{
    private JFrame parent;
    
    private JLabel titre;
    
    private JPanel panelTitre;
    
    protected JPanel panelContenu;
    
    private JDialog dialogOutils;
    
    private BoutonImage boutonFermeture;
    
    /**
     * Création de la boîte à outils
     * 
     * @param _parent Le JFrame de base (pour le JDialog)
     * @param _titre Le titre de la boîte
     */
    public BoiteAOutils(JFrame _parent, String _titre)
    {
        parent = _parent;
        
        titre = new JLabel(_titre);
        titre.setFont(new Font(titre.getFont().getName(), 
                               Font.BOLD, 
                               titre.getFont().getSize() + 2));

        this.init();
        this.initDialog();
        
        this.setPreferredSize(new Dimension(260, 
                                            this.getPreferredSize().height));
        
    }
    
    private void init()
    {
        boutonFermeture = new BoutonImage("Agrandir", Icone.POPUP, 15, false);
        
        panelTitre = new JPanel();
        panelTitre.setLayout(new BoxLayout(panelTitre, BoxLayout.X_AXIS));
        
        panelTitre.setBackground(new Color(220,220,220));
        panelTitre.setMinimumSize(new Dimension(200, 50));
        panelTitre.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        panelTitre.add(titre);
        panelTitre.add(Box.createHorizontalGlue());
        panelTitre.add(boutonFermeture);
        
        panelContenu = null;
        panelContenu = new JPanel();
        panelContenu.setBorder(null);
        panelContenu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelContenu.setPreferredSize(new Dimension(260,50));
        
        this.setLayout(new BorderLayout());
        this.add(panelTitre, BorderLayout.NORTH);
        //this.add(panelBordure, BorderLayout.CENTER);
        
        boutonFermeture.addActionListener(this);
    }
    
    private void initDialog()
    {
        dialogOutils = new JDialog(parent);
        dialogOutils.setTitle(titre.getText());
        dialogOutils.setModalityType(Dialog.ModalityType.MODELESS);
        dialogOutils.setLocationRelativeTo(null);
        
        dialogOutils.addWindowListener(this);
    }
    
    /**
     * Permet d'ajouter son JPanel personalisé à la boîte
     * À noter que le JPanel est encapsulé dans un JScrollPane et possède des "marges" de 10px
     * 
     * @param _contenu Le JPanel à ajouter
     */
    public void setContenu(JComponent _contenu)
    {
        try
        {
            dialogOutils.remove(panelContenu);
            this.remove(panelContenu);
        } catch (Exception e) { }
        
        _contenu.setBorder(BorderFactory.createEmptyBorder(2, 3, 2, 1));
        panelContenu = new JPanel();
        panelContenu.setLayout(new BorderLayout());
        
        panelContenu.setBorder(null);
        panelContenu.setPreferredSize(new Dimension(260, 300));
        
        panelContenu.add(_contenu, BorderLayout.CENTER);
        
        if (this.isVisible())
        {
            this.add(panelContenu, BorderLayout.CENTER);
            
            this.revalidate();
            this.repaint();
        }
        else
        {
            dialogOutils.add(panelContenu);
            
            dialogOutils.revalidate();
            dialogOutils.repaint();
        }
        
        panelContenu.revalidate();
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == boutonFermeture)
        {
            if (panelContenu != null)
            {
                dialogOutils.add(panelContenu);
            }
            
            dialogOutils.pack();
            dialogOutils.setMinimumSize(panelContenu.getPreferredSize());
            dialogOutils.setVisible(true);
            
            this.setVisible(false);
        }
        
        this.revalidate();
    }
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
    }
    
    @Override
    public void windowClosing(WindowEvent e)
    {
        if (e.getComponent() == dialogOutils)
        {
            if (panelContenu != null)
                this.add(panelContenu, BorderLayout.CENTER);
            
            this.setVisible(true);
        }
        
        this.setPreferredSize(this.getMinimumSize());
        this.revalidate();
    }
    
    // <editor-fold desc="Méthode souris/fenêtres non-utilisés">
    
    @Override
    public void mouseReleased(MouseEvent e)
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
    
    @Override
    public void windowOpened(WindowEvent e)
    {
    }
    
    @Override
    public void windowClosed(WindowEvent e)
    {
    }

    @Override
    public void windowIconified(WindowEvent e)
    {
    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {
    }

    @Override
    public void windowActivated(WindowEvent e)
    {
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
    }
    
    // </editor-fold>
}

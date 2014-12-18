/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.panel.BarreDeMenu;
import gui.panel.ParametresSchema;
import gui.panel.ParametresElement;
import gui.panel.BarreEtat;
import gui.panel.PanelMenu;
import gui.panel.PanelFond;
import gui.panel.ZoneDeTravail;
import domaine.controle.Controleur;
import gui.controls.PanelDynamique;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author Vincent
 */
public class RecyclApp extends JFrame
{
    public static final Dimension DIMENSIONS_MINIMALES = new Dimension(600,650);
    
    public Controleur controleur;
    
    public BarreDeMenu barreDeMenu;
    
    public ZoneDeTravail zoneDeTravail;
    
    public PanelFond fond;
    public PanelMenu menu;
    
    public BarreEtat barreEtat;
    
    public ParametresElement paramElements;
    public ParametresSchema paramSchema;
    
    public RecyclApp()
    {
        super("RecyclApp");
        
        try // OS L&F
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }
        
        
        controleur = new Controleur();
        
        barreDeMenu = new BarreDeMenu(this);
        barreDeMenu.setBorder(null);
        
        zoneDeTravail = new ZoneDeTravail(this);
        
        fond = new PanelFond();
        fond.setLayout(new BorderLayout());
        
        menu = new PanelMenu(this);
        barreEtat = new BarreEtat(this);
        
        this.initLayout();
        
        this.setJMenuBar(barreDeMenu);
        
        this.setMinimumSize(DIMENSIONS_MINIMALES);
        this.setPreferredSize(new Dimension(1024, 768));
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        String path = "/images/recyclApp.png";
        
        try
        {
            InputStream imgStrea = this.getClass().getResourceAsStream(path);
            BufferedImage img = ImageIO.read(imgStrea);
            this.setIconImage(img);
        }
        catch(IOException e){ System.out.println("Mauvais stream...");}
        
        this.pack();
    }
    
    public void initLayout()
    {
        GridBagConstraints gbc = new GridBagConstraints();
        
        PanelDynamique panelBoiteAOutils = new PanelDynamique();
        panelBoiteAOutils.setOpaque(false);
        panelBoiteAOutils.setLayout(new GridBagLayout());
       
        paramElements = new ParametresElement(this, "Composant");
        paramElements.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
        
        paramSchema = new ParametresSchema(this, "Schéma");
        paramSchema.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
        
        //Box outils = Box.createVerticalBox();
        //outils.add(paramSchema);
        //outils.add(paramElements);
        //outils.add(Box.createVerticalGlue());
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        
        panelBoiteAOutils.add(paramSchema, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        
        panelBoiteAOutils.add(paramElements, gbc);
        //panelBoiteAOutils.add(paramSchema);
        //panelBoiteAOutils.add(paramElements);
        
        fond.add(zoneDeTravail, BorderLayout.CENTER);
        fond.add(panelBoiteAOutils, BorderLayout.EAST);
        
        this.setLayout(new BorderLayout());
        this.add(menu, BorderLayout.NORTH);
        this.add(fond, BorderLayout.CENTER);
        this.add(barreEtat, BorderLayout.SOUTH);
    }
    
    public void actualiser()
    {
        menu.onglets.actualiserOnglets();
        
        if (paramSchema != null)
            paramSchema.updatePanelShema();
        
        if (paramElements != null)
            paramElements.updaterPanel();
        
        menu.annuler.setEnabled(!controleur.undoEstVide());
        menu.revenir.setEnabled(!controleur.redoEstVide());
        zoneDeTravail.schema.repaint();
    }
}

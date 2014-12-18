/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel;

import gui.controls.BoutonImage;
import gui.controls.Icone;
import gui.RecyclApp;
import gui.actions.Annuler;
import gui.actions.CreerUnSchema;
import gui.actions.ExporterUnSchema;
import gui.actions.OuvrirParametres;
import gui.actions.OuvrirUnSchema;
import gui.actions.Retablir;
import gui.actions.SauvegarderUnSchema;
import gui.panel.PanelOnglets;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Vincent
 */
public class PanelMenu extends JPanel
{
    public static final int TAILLE_ICONES = 30;
    
    protected RecyclApp parent;
    
    protected JPanel panelBordure;
    
    public BoutonImage ajouter,
                       ouvrir,
                       sauvegarder,
                       annuler,
                       revenir,
                       exporter,
                       parametres;
    
    public PanelOnglets onglets;
    
    public PanelMenu(RecyclApp _parent)
    {
        parent = _parent;
        
        panelBordure = new JPanel(new BorderLayout());
        //panelBordure.setLayout(new BoxLayout(panelBordure, BoxLayout.X_AXIS))
        panelBordure.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        this.init();
        
        this.setLayout(new BorderLayout());
        this.add(panelBordure, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
    }
    
    private void init() 
    {
        ajouter = new BoutonImage("Ajouter", Icone.AJOUTER, TAILLE_ICONES, false);
        ajouter.addActionListener(new CreerUnSchema(this.parent));
        ouvrir = new BoutonImage("Ouvrir", Icone.OUVRIR, TAILLE_ICONES, false);
        ouvrir.addActionListener(new OuvrirUnSchema(this.parent));
        
        sauvegarder = new BoutonImage("Sauvegarder", Icone.SAUVEGARDER, TAILLE_ICONES, false);
        sauvegarder.addActionListener(new SauvegarderUnSchema(this.parent));
        
        annuler = new BoutonImage("Annuler", Icone.ANNULER, TAILLE_ICONES, false);
        annuler.setEnabled(false);
        annuler.addActionListener(new Annuler(this.parent));
        
        revenir = new BoutonImage("Revenir", Icone.REVENIR, TAILLE_ICONES, false);
        revenir.setEnabled(false);
        revenir.addActionListener(new Retablir(this.parent));
        
        exporter = new BoutonImage("Exporter", Icone.EXPORTER, TAILLE_ICONES, false);
        exporter.addActionListener(new ExporterUnSchema(this.parent));
        
        parametres = new BoutonImage("Paramètres", Icone.PARAMETRES, TAILLE_ICONES, false);
        parametres.addActionListener(new OuvrirParametres(parent));
        
        onglets = new PanelOnglets(parent);
        
        Box gauche = Box.createHorizontalBox();
        Box droite = Box.createHorizontalBox();
        
        gauche.add(ajouter);
        gauche.add(Box.createHorizontalStrut(2));
        gauche.add(ouvrir);
        gauche.add(Box.createHorizontalStrut(2));
        gauche.add(sauvegarder);
        gauche.add(Box.createHorizontalStrut(2));
        gauche.add(annuler);
        gauche.add(Box.createHorizontalStrut(2));
        gauche.add(revenir);
        gauche.add(Box.createHorizontalStrut(10));
        //panelBordure.add(onglets);
        //panelBordure.add(Box.createHorizontalGlue());
        droite.add(Box.createHorizontalStrut(10));
        droite.add(exporter);
        droite.add(Box.createHorizontalStrut(2));
        droite.add(parametres);
        
        panelBordure.add(gauche, BorderLayout.WEST);
        panelBordure.add(onglets, BorderLayout.CENTER);
        panelBordure.add(droite, BorderLayout.EAST);
    }
}

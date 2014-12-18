/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel;

import domaine.controle.Schema;
import gui.controls.BoutonImage;
import gui.controls.Icone;
import gui.RecyclApp;
import gui.actions.SelectionnerUnSchema;
import gui.controls.Onglet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Vincent
 */
public class PanelOnglets extends JPanel implements ActionListener
{
    public final RecyclApp app;
    public ArrayList<Onglet> onglets;
    
    protected BoutonImage menuBouton;
    protected JPopupMenu menuSchemas;
    
    public PanelOnglets(RecyclApp _app)
    {
        app = _app;
        onglets = new ArrayList<>();
        
        this.setLayout(new BorderLayout());
        
        menuBouton = new BoutonImage("Schémas", Icone.DROPDOWN, 15, false);
        menuSchemas = new JPopupMenu();
        
        this.actualiserOnglets();
        menuBouton.addActionListener(this);
    }
    
    public void actualiserOnglets()
    {
        this.removeAll();
        menuSchemas.removeAll();
        
        onglets.clear();
        
        HashSet<Schema> schemas = new HashSet<>(app.controleur.getSchemas());
        Schema selectionne = app.controleur.getSchema();
        
        Onglet onglet;
        Box boxOnglets = Box.createHorizontalBox();
        
        if (selectionne != null)
        {
            onglet = new Onglet(selectionne, app, true);
            onglets.add(onglet);
            
            boxOnglets.add(onglet);
            
            MenuSchema m = new MenuSchema(selectionne);
            m.setIcon(Icone.CHECK.getImage(15,15));
            m.setFont(new Font(m.getFont().getFontName(), Font.BOLD, m.getFont().getSize()));
            
            menuSchemas.add(m);
        }
        
        for (Schema s : schemas)
        {
            if (s != selectionne)
            {
                onglet = new Onglet(s, app, false);
                onglets.add(onglet);
                
                boxOnglets.add(onglet);
                menuSchemas.add(new MenuSchema(s));
            }
        }
        
        Box boxBouton = Box.createHorizontalBox();
        boxBouton.add(Box.createHorizontalStrut(10));
        boxBouton.add(menuBouton);
        
        this.add(boxOnglets, BorderLayout.CENTER);
        this.add(boxBouton, BorderLayout.EAST);
        
        this.repaint();
        this.revalidate();
    }
    
    public ArrayList<Onglet> getOnglets()
    {
        return new ArrayList<>(this.onglets);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == menuBouton && !menuSchemas.isVisible())
        {
            menuSchemas.show(this, this.getWidth() - menuSchemas.getPreferredSize().width, 
                                   this.getHeight());
        }
    }
    
    public class MenuSchema extends JMenuItem
    {
        public MenuSchema(Schema _schema)
        {
            super((_schema.getNom().length() > 100) ? _schema.getNom().substring(0,100)+"..." : _schema.getNom());
            this.addActionListener(new SelectionnerUnSchema(_schema, app));
        }
    }
}

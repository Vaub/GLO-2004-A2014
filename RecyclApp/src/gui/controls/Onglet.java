/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controls;

import domaine.controle.Schema;
import gui.RecyclApp;
import gui.actions.SelectionnerUnSchema;
import gui.actions.SupprimerUnSchema;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Vincent
 */
public class Onglet extends JPanel implements MouseListener
{
    public final RecyclApp app;
    protected Schema schema;
    
    protected BoutonImage fermer;
    protected JLabel nom;
    
    protected boolean estSelectionne;
    
    public Onglet(Schema _schema, RecyclApp _app, boolean _estSelectionne)
    {
        app = _app;
        schema = _schema;
        
        estSelectionne = _estSelectionne;
        
        if (schema != null)
            initOnglet();
        else
            this.setVisible(false);
    }
    
    private void initOnglet()
    {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        nom = new JLabel((schema.getNom().length() > 40) ? schema.getNom().substring(0,40)+"..." : schema.getNom());
        nom.setFont(new Font(nom.getFont().getFontName(), (estSelectionne) ? Font.BOLD : Font.PLAIN, 14));
        
        fermer = new BoutonImage("Fermer le schéma", Icone.SUPPRIMER, 14, false);
        
        this.add(Box.createHorizontalStrut(5));
        this.add(nom);
        this.add(Box.createHorizontalStrut(7));
        this.add(fermer);
        this.add(Box.createHorizontalStrut(5));
        
        this.addMouseListener(this);
        this.addMouseListener(new SelectionnerUnSchema(schema, app));
        
        fermer.addActionListener(new SupprimerUnSchema(schema, app));
    }
    
    public void fermerOnglet()
    {
        fermer.doClick();
    }
    
    @Override
    public void mousePressed(MouseEvent e)
    {
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
        if (!estSelectionne)
            this.setBackground(new Color(225,225,225));
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        this.setBackground(null);
    }

    @Override
    public String toString()
    {
        return (schema != null) ? schema.getNom() : "";
    }

    
    
}

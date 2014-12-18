/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.adapters;

import gui.RecyclApp;
import gui.panel.PanelSchema;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import domaine.usine.Convoyeur;
import domaine.usine.IO;
import gui.actions.SupprimerConvoyeur;
import utils.Position;

/**
 *
 * @author Vincent
 */
public class SchemaConnexionAdapter extends SchemaAdapter implements ActionListener
{
    public Position flexion;
    public Convoyeur convoyeur;
    
    public IO noeud;
    
    public boolean relierNoeuds,
                   deplacerFlexion;
    
    public SchemaConnexionAdapter(RecyclApp _app, PanelSchema _schema)
    {
        super(_app, _schema);
        
        relierNoeuds = false;
        deplacerFlexion = false;
    }
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        super.mouseClicked(e);
    }
    
    @Override
    public void mouseMoved(MouseEvent e)
    {
        super.mouseMoved(e);
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        super.mousePressed(e);
        
        flexion = null;
        
        noeud = controleur.getIO(this.getPositionReelle());
        if (noeud != null)
        {
            convoyeur = null;
            relierNoeuds = true;
        }
        else
        {
            Convoyeur convTemp = controleur.selectionnerConvoyeur(this.getPositionReelle());
            app.paramElements.setComposant(convTemp);
            
            if (convoyeur == null ||
                convoyeur != convTemp)
            {
                convoyeur = convTemp;
                //app.paramElements.updatePanelEquipement(convoyeur);
                
                if (convoyeur != null)
                    flexion = controleur.getPointFlexionConvoyeur(convoyeur, this.getPositionReelle());
            }
            else
            {
                flexion = controleur.getPointFlexionConvoyeur(convoyeur, this.getPositionReelle());
                if (flexion == null)
                {
                    controleur.ajouterPointFlexionConvoyeur(convoyeur, this.getPositionReelle());
                    flexion = controleur.getPointFlexionConvoyeur(convoyeur, this.getPositionReelle());
                }
            }
            
            deplacerFlexion = (flexion != null);
        }
            
        
        schema.repaint();
    }
    
    @Override
    public void mouseDragged(MouseEvent e)
    {
        super.mouseDragged(e);
        
        if (deplacerFlexion && 
            flexion != null && convoyeur != null)
        {
            flexion.setX(schema.getPositionReelle(this.getMagnetCoord()).x());
            flexion.setY(schema.getPositionReelle(this.getMagnetCoord()).y());
            
            schema.repaint();
        }
        else if (relierNoeuds)
            schema.repaint();
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
        super.mouseReleased(e);
        
        if (relierNoeuds &&
            noeud != null)
        {
            controleur.relierEquipements(noeud, controleur.getIO(this.getPositionReelle()));
            app.actualiser();
        }
        
        relierNoeuds = false;
        deplacerFlexion = false;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (flexion != null &&
            convoyeur != null)
        {
            controleur.enleverPointFlexionConvoyeur(convoyeur, flexion);
            flexion = null;

            app.actualiser();
        }
        else if (convoyeur != null)
        {
            (new SupprimerConvoyeur(convoyeur, app)).actionPerformed(e);
            convoyeur = null;
        }
    }
    
    @Override
    public void clear()
    {
        relierNoeuds = false;
        deplacerFlexion = false;
        
        flexion = null;
        convoyeur = null;
        
        noeud = null;
    }
}

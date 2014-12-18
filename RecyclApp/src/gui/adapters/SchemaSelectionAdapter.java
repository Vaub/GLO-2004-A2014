/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.adapters;

import gui.RecyclApp;
import gui.panel.PanelSchema;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import domaine.usine.Equipement;
import gui.actions.SupprimerEquipement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Vincent
 */
public class SchemaSelectionAdapter extends SchemaAdapter implements ActionListener
{
    public final static int TARGET = 10;
    
    public static int TICK = 0;
    
    public enum Redimension { AUCUNE, NO, NE, SE, SO }
    
    
    public final Map<Equipement, Point> selection;
    
    private boolean deplacement;
    public boolean ctrlActif;
    
    public Equipement elementRedimension;
    private Redimension redimension;
    
    public Point selectionner,
                 positionCurseurOriginale;
    
    public SchemaSelectionAdapter(RecyclApp _app, PanelSchema _schema)
    {
        super(_app, _schema);
        
        ctrlActif = false;
        
        elementRedimension = null;
        selection = new HashMap<>();
        
        positionCurseurOriginale = null;
        selectionner = null;
        
        redimension = Redimension.AUCUNE;
        deplacement = false;
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
        
        if (TICK % 10 == 0) // mouseMoved was going crazy
            TICK = 1;
        else
        {
            TICK++;
            return;
        }
        
        Equipement equipement = controleur.selectionnerEquipementAvecMarge(this.getPositionReelle(), 
                                                                          ((double)TARGET)/schema.getRatioTaille());
        
        if (selection.containsKey(equipement))
        {
            elementRedimension = equipement;
            
            Point posEquip = elementRedimension.getPosition().point(schema.getRatioTaille());
            Dimension dimEquip = elementRedimension.getTaille().dimension(schema.getRatioTaille());
            
            if (posEquip.x - 5 <= e.getPoint().x && posEquip.x + 5 >= e.getPoint().x &&
                posEquip.y - 5 <= e.getPoint().y && posEquip.y + 5 >= e.getPoint().y)
            {
                schema.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                this.redimension = Redimension.NO;
            }
            else if (posEquip.x + dimEquip.width - 5 <= e.getPoint().x && posEquip.x + dimEquip.width + 5 >= e.getPoint().x &&
                     posEquip.y - 5 <= e.getPoint().y && posEquip.y + 5 >= e.getPoint().y)
            {
                schema.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                this.redimension = Redimension.NE;
            }
            else if (posEquip.x + dimEquip.width - 5 <= e.getPoint().x && posEquip.x + dimEquip.width + 5 >= e.getPoint().x &&
                     posEquip.y + dimEquip.height - 5 <= e.getPoint().y && posEquip.y + dimEquip.height + 5 >= e.getPoint().y)
            {
                schema.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                this.redimension = Redimension.SE;
            }
            else if (posEquip.x - 5 <= e.getPoint().x && posEquip.x + 5 >= e.getPoint().x &&
                     posEquip.y + dimEquip.height - 5 <= e.getPoint().y && posEquip.y + dimEquip.height + 5 >= e.getPoint().y)
            {
                schema.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                this.redimension = Redimension.SO; 
            }
            else
            {
                this.redimension = Redimension.AUCUNE;    
                schema.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }
        else
        {
            this.redimension = Redimension.AUCUNE;    
            schema.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        super.mousePressed(e);
        if (redimension == Redimension.AUCUNE) 
        {
            Equipement selectionTmp = controleur.selectionnerEquipement(this.getPositionReelle());
            
            if (selection.containsKey(selectionTmp))
            {
                this.deplacement = true;
                if (this.deplacement)
                {
                    positionCurseurOriginale = new Point(position);
                    
                    for (Entry<Equipement, Point> entry : selection.entrySet())
                        selection.put(entry.getKey(), entry.getKey().getPosition().point(schema.getRatioTaille()));

                    controleur.sauvegarderUndo();
                }
            }
            else if (selectionTmp != null)
            {
                if (!ctrlActif)
                    selection.clear();
                
                selection.put(selectionTmp, null);
            }
            else
            {
                selection.clear();
                selectionner = new Point(position);
            }
        }
        else
            controleur.sauvegarderUndo();
        
        
        app.paramElements.setComposant(this.getSelection());
        
        schema.repaint();
    }
    
    @Override
    public void mouseDragged(MouseEvent e)
    {
        Point relatif = position;
        super.mouseDragged(e);
        
        if (this.deplacement)
        {
            boolean changements = false;
            for (Entry<Equipement, Point> entry : selection.entrySet())
            {
                if (entry.getKey() != null && entry.getValue() != null)
                {
                    Point pos = new Point(entry.getValue());

                    pos.x += (relatif.x - positionCurseurOriginale.x);
                    pos.y += (relatif.y - positionCurseurOriginale.y);

                    pos = schema.getMagnetCoord(pos);

                    if (controleur.deplacerEquipement(entry.getKey(), schema.getPositionReelle(pos)))
                        changements = true;
                }
            }
            
            if (changements)
                schema.repaint();
        }
        else if (redimension != Redimension.AUCUNE && elementRedimension != null)
        {
            Point posEquipement = elementRedimension.getPosition().point(schema.getRatioTaille());
            Dimension dimEquipement = elementRedimension.getTaille().dimension(schema.getRatioTaille());
            
            position = this.getMagnetCoord();
            
            switch (redimension)
            {
                case NO:
                    
                    if (position.x > 0)
                        dimEquipement.width += (posEquipement.x - position.x);
                    
                    if (position.y > 0)
                        dimEquipement.height += (posEquipement.y - position.y);
                    
                    if (dimEquipement.width > 0)
                        posEquipement.x = position.x;
                                
                    if (dimEquipement.height > 0)
                        posEquipement.y = position.y;
                    
                    break;
                case NE:
                    
                    if (position.x > 0)
                        dimEquipement.width += (position.x - (posEquipement.x + dimEquipement.width));
                    
                    if (position.y > 0)
                        dimEquipement.height += (posEquipement.y - position.y);
                    
                    if (dimEquipement.height > 0)
                        posEquipement.y = position.y;
                    
                    break;
                case SE:
                    
                    dimEquipement.width += (position.x - (posEquipement.x + dimEquipement.width));
                    dimEquipement.height += (position.y - (posEquipement.y + dimEquipement.height));
                    
                    break;
                case SO:
                    
                    if (position.x > 0)
                        dimEquipement.width += (posEquipement.x - position.x);
                    
                    if (position.y > 0)
                        dimEquipement.height += position.y - (posEquipement.y + dimEquipement.height);
                    
                    if (dimEquipement.width > 0)
                        posEquipement.x = position.x;
                    
                    break;
            }
            
            if (controleur.deplacerEquipement(elementRedimension, schema.getPositionReelle(posEquipement)) &&
                controleur.redimensionnerEquipement(elementRedimension, schema.getTailleReelle(dimEquipement)))
            {
                schema.repaint();
            }
        }
        else if (selectionner != null)
            schema.repaint();
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
        super.mouseReleased(e);
        
        schema.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        
        if (this.selectionner != null)
        {
            Point pos1 = this.selectionner,
                  pos2 = position,
                  origine = new Point((pos1.x > pos2.x) ? pos2.x : pos1.x,
                                      (pos1.y > pos2.y) ? pos2.y : pos1.y),
                  fin = new Point((pos2.x < pos1.x) ? pos1.x : pos2.x,
                                  (pos2.y < pos1.y) ? pos1.y : pos2.y);
            
            Dimension taille = new Dimension(fin.x - origine.x, fin.y - origine.y);
            
            Set<Equipement> selectionnes = controleur.selectionnerDesEquipement(schema.getPositionReelle(origine), 
                                                                                schema.getTailleReelle(taille));
            
            for (Equipement equipement : selectionnes)
                selection.put(equipement, null);
            
            this.selectionner = null;
            schema.repaint();
        }
        
        this.deplacement = false;
        this.redimension = Redimension.AUCUNE;
        
        positionCurseurOriginale = null;
        //positionEquipementOriginale = null;
        
        app.paramElements.setComposant(this.getSelection());
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (!selection.isEmpty())
        {
            (new SupprimerEquipement(selection.keySet(), app)).actionPerformed(e); 
        }
    }
    
    @Override
    public void clear()
    {
        selection.clear();
        
        deplacement = false;
        selectionner = null;
        positionCurseurOriginale = null;
        
        redimension = Redimension.AUCUNE;
    }
    
    public Equipement getSelection()
    {
        return (selection.size() == 1) ? selection.keySet().iterator().next()
                                       : null;
    }
    
    public void clearSelection()
    {
        this.selection.clear();
    }
}

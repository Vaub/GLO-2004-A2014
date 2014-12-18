/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.adapters;

import domaine.controle.Controleur;
import gui.RecyclApp;
import gui.panel.PanelSchema;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import utils.Position;

/**
 *
 * @author Vincent
 */
public abstract class SchemaAdapter extends MouseAdapter
{
    public final RecyclApp app;
    public final PanelSchema schema;
    
    protected final Controleur controleur;
    
    protected Point position;
    
    public SchemaAdapter(RecyclApp _app, PanelSchema _schema)
    {
        app = _app;
        schema = _schema;
        
        controleur = app.controleur;
        position = new Point(0,0);
    }
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        position = this.getPosition(e.getPoint());
    }
    
    @Override
    public void mouseMoved(MouseEvent e)
    {
        position = this.getPosition(e.getPoint());
        
        Position pos = new Position(e.getPoint().x / schema.getRatioTaille(),
                                    e.getPoint().y / schema.getRatioTaille());
        
        app.barreEtat.positionX.setText(String.format("%.02f", pos.x()));
        app.barreEtat.positionY.setText(String.format("%.02f", pos.y()));
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        position = this.getPosition(e.getPoint());
    }
    
    @Override
    public void mouseDragged(MouseEvent e)
    {
        position = this.getPosition(e.getPoint());
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
        position = this.getPosition(e.getPoint());
    }
    
    public Position getPositionReelle()
    {
        return schema.getPositionReelle(position);
    }
    
    public Point getMagnetCoord()
    {
        return schema.getMagnetCoord(position);
    }
    
    public Point getPosition()
    {
        return new Point(position);
    }
    
    public Point getPosition(Point _p)
    {
        return new Point((_p.x > 0) ? _p.x : 0,
                         (_p.y > 0) ? _p.y : 0);
    }
    
    public abstract void clear();
}

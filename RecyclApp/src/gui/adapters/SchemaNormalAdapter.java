/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.adapters;

import gui.RecyclApp;
import gui.panel.PanelSchema;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JScrollBar;

/**
 *
 * @author Vincent
 */
public class SchemaNormalAdapter extends SchemaAdapter
{
    public boolean redimension;
    
    public SchemaNormalAdapter(RecyclApp _app, PanelSchema _schema)
    {
        super(_app, _schema);
        
        redimension = false;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        super.mousePressed(e);
        redimension = true;
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        Point delta = new Point(position.x - e.getX(),
                                position.y - e.getY());
        
        if (redimension)
        {
            
            JScrollBar hSB = schema.zoneDeTravail.gridPane.getHorizontalScrollBar(),
                       vSB = schema.zoneDeTravail.gridPane.getVerticalScrollBar();
                       
            
            if ((hSB.getValue() + delta.x) <= hSB.getMaximum() &&
                (hSB.getValue() + delta.x) >= hSB.getMinimum())
            {
                hSB.setValue(hSB.getValue() + delta.x);
            }
            
            if ((vSB.getValue() + delta.y) <= vSB.getMaximum() &&
                (vSB.getValue() + delta.y) >= vSB.getMinimum())
            {
                vSB.setValue(vSB.getValue() + delta.y);
            }
        }
        
        position = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        super.mouseReleased(e);
        redimension = false;
    }
    
    @Override
    public void clear()
    {
    }
    
}

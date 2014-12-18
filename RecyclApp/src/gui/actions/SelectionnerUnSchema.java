/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import domaine.controle.Schema;
import gui.RecyclApp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Vincent
 */
public class SelectionnerUnSchema extends ActionRecyclApp implements MouseListener, ActionListener
{
    public Schema aSelectionner;
    
    public SelectionnerUnSchema(Schema _schema, RecyclApp _app)
    {
        super(_app);
        aSelectionner = _schema;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.selectionner();
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        this.selectionner();
    }
    
    private void selectionner()
    {
        if (app != null && aSelectionner != null &&
            app.controleur.selectionner(aSelectionner))
        {            
            app.actualiser();            
            app.zoneDeTravail.schema.clearAdapteurs();
        }
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
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import gui.RecyclApp;
import gui.dialogs.ModifierParametres;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Vincent
 */
public class OuvrirParametres extends ActionRecyclApp implements ActionListener 
{
    public OuvrirParametres(RecyclApp _app)
    {
        super(_app);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        new ModifierParametres(app);
    }
    
}

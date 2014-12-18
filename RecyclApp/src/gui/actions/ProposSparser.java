/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import gui.dialogs.APropos;
import gui.RecyclApp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Vincent
 */
public class ProposSparser extends ActionRecyclApp implements ActionListener
{
    public ProposSparser(RecyclApp _app)
    {
        super(_app);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        APropos fenetreA = new APropos();
    }
    
}

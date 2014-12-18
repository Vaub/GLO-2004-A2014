/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import gui.dialogs.ModifierParametres;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ressources.Parametres;

/**
 *
 * @author Vincent
 */
public class RemiseADefaut implements ActionListener
{
    private final String parametre;
    public final ModifierParametres aActualiser;
    
    public RemiseADefaut(String _parametre, ModifierParametres _modifParam)
    {
        parametre = _parametre;
        aActualiser = _modifParam;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object defaut = Parametres.getDefaut(parametre);
        if (defaut != null)
            Parametres.changerParametre(parametre, defaut);
        
        aActualiser.updateParametres();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import domaine.usine.Matiere;
import domaine.usine.MatriceTri;
import domaine.usine.Sortie;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Vincent
 */
public class ModifierMatriceTri implements ActionListener
{
    Matiere matiere;
    Sortie sortie;
    
    MatriceTri matrice;
    
    public ModifierMatriceTri(MatriceTri _matrice, Sortie _sortie, Matiere _matiere)
    {
        matrice = _matrice;
        sortie = _sortie;
        matiere = _matiere;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        double pourcentage = 0;
        if (matrice != null &&
            e.getSource() instanceof JTextField)
        {
            try
            {
                double nombre = Double.parseDouble(((JLabel)e.getSource()).getText());
                pourcentage = nombre/100d;
            } catch (Exception ex) { }
            
            matrice.modifierQuantite(sortie, matiere, pourcentage);
        }
    }
}

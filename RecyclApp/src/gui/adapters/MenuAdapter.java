/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.adapters;
import domaine.controle.Controleur;
import domaine.controle.Schema;
import domaine.usine.Equipement;
import gui.controls.Icone;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import gui.panel.BarreDeMenu;
import gui.RecyclApp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

/**
 *
 * @author Vincent
 */
public class MenuAdapter implements ActionListener
{
    public final BarreDeMenu menu;
    public final RecyclApp app;
    
    public MenuAdapter(BarreDeMenu _menu, RecyclApp _app)
    {
        menu = _menu;
        app = _app;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Exemple
        if (e.getSource() == menu.i_recent)
        {
           
        }
        else if (e.getSource() == menu.i_fermer)
        {
           
        }
        else if (e.getSource() == menu.i_fermerTout)
        {
           
        }
        //else if (e.getSource() == menu.i_couper){}
        //else if (e.getSource() == menu.i_copier){}
        //else if (e.getSource() == menu.i_coller){}
        else if (e.getSource() == menu.i_supprimer)
        {
       
        }
        else if (e.getSource() == menu.i_selectionne)
        {
           app.zoneDeTravail.outils.selectionNormale.doClick();
        }
        else if (e.getSource() == menu.i_agrandir)
        {
           app.barreEtat.agrandir.doClick();
        }
        else if (e.getSource() == menu.i_reduire)
        {
           app.barreEtat.reduire.doClick();
        }
        else if (e.getSource() == menu.i_options)
        {
           
        }
        else if (e.getSource() == menu.i_aStation)
        {
           app.zoneDeTravail.outils.station.doClick();
        }
        else if (e.getSource() == menu.i_aJonction)
        {
           app.zoneDeTravail.outils.jonction.doClick();
        }
        else if (e.getSource() == menu.i_aEntree)
        {
           app.zoneDeTravail.outils.entree.doClick();
        }
        else if (e.getSource() == menu.i_aSortie)
        {
           app.zoneDeTravail.outils.sortie.doClick();
        }
        else if (e.getSource() == menu.i_aConvoyeur)
        {
           app.zoneDeTravail.outils.selectionConnecter.doClick();
        }
        

    }
    

    
}

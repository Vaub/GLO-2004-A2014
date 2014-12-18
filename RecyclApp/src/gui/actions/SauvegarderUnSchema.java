/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.IOException;
import domaine.controle.Schema;
import gui.controls.Icone;
import gui.RecyclApp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Magalie
 */
public class SauvegarderUnSchema extends ActionRecyclApp implements ActionListener
{
    public SauvegarderUnSchema(RecyclApp _app)
    {
        super(_app);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JFileChooser choix = new JFileChooser();
        FileFilter filtre2 = new FileNameExtensionFilter("RecyclApp Schéma (*.recycl)", "recycl");
        choix.addChoosableFileFilter(filtre2);
        choix.setDialogTitle("Sauvegarder un schéma");
        int returnVal2 = choix.showSaveDialog(null);
        if(returnVal2 == JFileChooser.APPROVE_OPTION)
        {   
            String nom_fichier = new String("");
            java.io.File sfichier = choix.getSelectedFile();
            String nom_sfichier = sfichier.toString();
            if(nom_sfichier.contains(".recycl"))
            {
                nom_fichier = nom_sfichier;
            }
            else
            {
                nom_fichier = nom_sfichier + ".recycl";
            }
            Schema e_schema = null;
            e_schema = app.controleur.getSchema();
            e_schema.sauvegarder(nom_fichier);
            
            JOptionPane.showMessageDialog(null, "Vous sauvegardez le fichier " + nom_fichier, "Sauvegarde du Schéma", JOptionPane.INFORMATION_MESSAGE,(Icone.SAUVEGARDER2.getImage(30,30)));
        }
           
    }
    
}

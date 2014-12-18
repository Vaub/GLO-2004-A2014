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
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Magalie
 */
public class ExporterUnSchema extends ActionRecyclApp implements ActionListener
{
    
    public ExporterUnSchema(RecyclApp _app)
    {
        super(_app);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        
        JFileChooser choix = new JFileChooser();
   
        FileFilter filtre2 = new FileNameExtensionFilter("Schéma RecyclApp (*.png)", "png");
        choix.addChoosableFileFilter(filtre2);
        choix.setDialogTitle("Exporter un schéma");
        String path = new String("");
        File f = choix.getCurrentDirectory();
        path = f.toString();
        System.out.println(f.getAbsolutePath());
        
        int returnVal2 = choix.showSaveDialog(null);
        if(returnVal2 == JFileChooser.APPROVE_OPTION)
        {   
            String nom_fichier = new String("");
            java.io.File sfichier = choix.getSelectedFile();
            String nom_sfichier = sfichier.toString();
            if(nom_sfichier.contains(".png"))
            {
                nom_fichier = nom_sfichier;
            }
            else if(nom_sfichier.contains("..."))
            {
                nom_fichier = path + "\\" + "defaut.png";
            }
            else
            {
                nom_fichier = nom_sfichier + ".png";
            }
            app.zoneDeTravail.schema.exporter(nom_fichier);
            
            JOptionPane.showMessageDialog(null, "Vous exportez le fichier " + nom_fichier, "Exporter du Schéma", JOptionPane.INFORMATION_MESSAGE,(Icone.EXPORTER.getImage(30,30)));
        }
    }
}

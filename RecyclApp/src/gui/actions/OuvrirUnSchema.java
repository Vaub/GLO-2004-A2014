/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;
import domaine.controle.Schema;
import gui.controls.Icone;
import gui.RecyclApp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Magalie
 */
public class OuvrirUnSchema extends ActionRecyclApp implements ActionListener
{
    public OuvrirUnSchema(RecyclApp _app)
    {
        super(_app);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
         JFileChooser choix = new JFileChooser();
           FileNameExtensionFilter filtre = new FileNameExtensionFilter("RecyclApp Schéma (*.recycl)", "recycl");
           choix.addChoosableFileFilter(filtre);
           choix.setFileFilter(filtre);
           choix.setAcceptAllFileFilterUsed(false);
           choix.setDialogTitle("Ouvrir un schéma");
           
           int returnVal = choix.showOpenDialog(null);
           if(returnVal == JFileChooser.APPROVE_OPTION)
           {
              java.io.File fichier = choix.getSelectedFile();
              String nom_fichier = fichier.getPath();
              //JOptionPane.showMessageDialog(null, "Vous ouvrez le fichier " + nom_fichier, "Ouverture Schéma", JOptionPane.INFORMATION_MESSAGE,(Icone.OUVRIR2.getImage(30,30)));
              Schema nouveau_schema = null;
              
            try
            {
                nouveau_schema = Schema.ouvrir(nom_fichier);
            } 
            catch (IOException ex)
            {
                JOptionPane.showMessageDialog(null, "Fichier non-trouvé ou corrompu");
            } 
            catch (ClassNotFoundException ex)
            {
                JOptionPane.showMessageDialog(null, "Fichier non-valide, assurez-vous d'avoir le bon format.");
            }
            
              if(nouveau_schema != null){app.controleur.ajouterUnSchema(nouveau_schema);}
              else{System.out.println(System.getProperty("user.dir"));}
              app.actualiser();
              
           }
           else
           {
               JOptionPane.showMessageDialog(null, "Le fichier que vous avez sélectionné n'est pas un schéma !", "Erreur : Ouverture Schéma", JOptionPane.INFORMATION_MESSAGE,(Icone.FICHIERF.getImage(40,40)));
           }
           
    }
    
}

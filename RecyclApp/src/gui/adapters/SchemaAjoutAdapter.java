/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.adapters;

import gui.RecyclApp;
import gui.panel.PanelSchema;
import java.awt.event.MouseEvent;
import domaine.usine.Equipement;
import domaine.usine.Jonction;
import domaine.usine.Station;
import gui.actions.AjouterEquipement;
import gui.dialogs.ModifierStation;
import javax.swing.JOptionPane;

/**
 *
 * @author Vincent
 */
public class SchemaAjoutAdapter extends SchemaAdapter
{
    public Equipement ajout;
    
    public SchemaAjoutAdapter(RecyclApp _app, PanelSchema _schema)
    {
        super(_app, _schema);
    }
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        super.mouseClicked(e);
    }
    
    @Override
    public void mouseMoved(MouseEvent e)
    {
        super.mouseMoved(e);
        
        position = this.getMagnetCoord();
        schema.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        super.mousePressed(e);
        
        boolean ajouter = true;
        AjouterEquipement action = new AjouterEquipement(app);
        
        if (ajout != null)
        {
            if (ajout instanceof Jonction)
            {
               String input = JOptionPane.showInputDialog(null, 
                                                          "Nombre d'entrées", 
                                                          "Jonction", 
                                                          JOptionPane.QUESTION_MESSAGE);
               
               int nbEntrees = 1;
               
               try
               {
                nbEntrees = Integer.parseInt(input);
               } catch (Exception ex) { }
               
               nbEntrees = (nbEntrees <= 0) ? 1 : nbEntrees;
               
               ajout = new Jonction(ajout.getPosition(), nbEntrees);
            }
            else if (ajout instanceof Station)
            {
                ModifierStation options = new ModifierStation(app);
                
                ajouter = options.estModifie;
                if (options.estModifie)
                {
                    ajout = new Station(options.t_nom.getText(), 
                                        ajout.getPosition(), 
                                        options.taille, 5000, 
                                        controleur.getMatieresSchema());
                    
                    for (int i = 1; i < options.nbDeSorties; i++)
                        ((Station)ajout).ajouterSortie();
                    
                    
                }
            }
            
            if (ajouter && action.ajouterEquipement(ajout))
                ajout = null;
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e)
    {
        super.mouseDragged(e);
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
        super.mouseReleased(e);
    }

    @Override
    public void clear()
    {
        ajout = null;
    }
}

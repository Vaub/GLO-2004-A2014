package gui.panel;

import domaine.usine.Composant;
import domaine.usine.Convoyeur;
import domaine.usine.EntreeUsine;
import domaine.usine.Equipement;
import domaine.usine.Jonction;
import domaine.usine.SortieUsine;
import domaine.usine.Station;
import gui.RecyclApp;
import gui.controls.BoiteAOutils;
import gui.panel.parametres.ComposantPanel;
import gui.panel.parametres.ConvoyeurPanel;
import gui.panel.parametres.EntreePanel;
import gui.panel.parametres.EquipementPanel;
import gui.panel.parametres.JonctionPanel;
import gui.panel.parametres.SortiePanel;
import gui.panel.parametres.StationPanel;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Olivier
 */
public class ParametresElement extends BoiteAOutils 
{
    RecyclApp parent;
    
    ComposantPanel panel;

    public ParametresElement(JFrame _parent, String _titre)
    {
        super(_parent, _titre);
        
        parent = (RecyclApp) _parent;
        panel = new ComposantPanel(null, parent);
        
        this.setContenu(panel);
        
        this.setMinimumSize(new Dimension(350, this.getMinimumSize().height));
        this.setPreferredSize(this.getMinimumSize());
    }
    
    public final void updaterPanel()
    {
        if (panel != null)
            panel.updatePanel();
        
        this.revalidate();
        this.repaint();
    }
    
    public final void setComposant(Composant _selection) 
    {
        int tabSelectionnee = (panel != null) ? panel.getTabSelectionnee() : 0;
        tabSelectionnee = (tabSelectionnee < 0) ? 1 : tabSelectionnee;
        
        if (_selection instanceof Equipement)
        {
            if (_selection instanceof Station)
                panel = new StationPanel((Station)_selection, parent);
            else if (_selection instanceof Jonction)
                panel = new JonctionPanel((Jonction)_selection, parent);
            else if (_selection instanceof EntreeUsine)
                panel = new EntreePanel((EntreeUsine)_selection, parent);
            else if (_selection instanceof SortieUsine)
                panel = new SortiePanel((SortieUsine)_selection, parent);
            else
                 panel = new EquipementPanel((Equipement)_selection, parent);
        }
        else if(_selection instanceof Convoyeur)
            panel = new ConvoyeurPanel((Convoyeur)_selection, parent);
        else
            panel = new ComposantPanel(_selection, parent);
        
        panel.selectionnerTab(tabSelectionnee);
        this.setContenu(panel);

        this.panelContenu.revalidate();
        //parent.actualiser();
        this.repaint();
        
    }
}

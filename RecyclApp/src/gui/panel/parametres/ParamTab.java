/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel.parametres;

import gui.controls.BoutonImage;
import gui.controls.Icone;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Vincent
 */
public abstract class ParamTab extends JPanel
{
    protected JTabbedPane tab;
    protected JPanel p_icon;
    
    protected BoutonImage sauvegarder;
    
    public ParamTab()
    {
        tab = new JTabbedPane();
        p_icon = new JPanel();
        
        this.init();
        
        this.setLayout(new BorderLayout());
    }
    
    private void init()
    {
        sauvegarder = new BoutonImage("Sauvegarder", Icone.SAUVEGARDER, 15, false);
        
        tab = new JTabbedPane();
        tab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        p_icon.setLayout(new BoxLayout(p_icon, BoxLayout.X_AXIS));
    }
    
    public void selectionnerTab(int _index)
    {
        _index = (_index < tab.getTabCount()) ? _index :
                 (tab.getTabCount() > 0)      ? 0 : -1;
        
        tab.setSelectedIndex(_index);
    }
    
    public final int getTabSelectionnee()
    {
        return tab.getSelectedIndex();
    }
    
    public abstract void updatePanel();
}

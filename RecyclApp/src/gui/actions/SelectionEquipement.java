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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Magalie
 */
public class SelectionEquipement implements ActionListener
{
    public RecyclApp app;
    
    public SelectionEquipement(RecyclApp _app)
    {
        this.app = _app;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        app.zoneDeTravail.outils.selectionEquipement.doClick();
    }
    
}

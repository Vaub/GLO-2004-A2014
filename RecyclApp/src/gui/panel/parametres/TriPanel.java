/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel.parametres;

import domaine.usine.Matiere;
import domaine.usine.MatriceTri;
import domaine.usine.Sortie;
import domaine.usine.Station;
import gui.RecyclApp;
import java.awt.BorderLayout;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import utils.NumericUtilities;

/**
 *
 * @author Vincent
 */
public class TriPanel extends JPanel implements ActionListener, TableModelListener
{
    public final RecyclApp app;
    private final Station station;
    
    private JTable table;
    private MatriceTriTableModel model;
    
    private JCheckBox afficherTout;
    
    public TriPanel(RecyclApp _app, Station _station)
    {
        app = _app;
        station = _station;
        
        model = new MatriceTriTableModel();
        model.addTableModelListener(this);
        
        afficherTout = new JCheckBox("Afficher toutes les matieres");
        afficherTout.addActionListener(this);
        
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setShowGrid(false);
        
        this.setLayout(new BorderLayout());
        
        this.updateTable();
        this.init();
    }
    
    private void init()
    {
        Box options = Box.createHorizontalBox();
        options.add(afficherTout);
        options.add(Box.createHorizontalGlue());
        
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setAlignmentX(CENTER_ALIGNMENT);
        
        JPanel bordure = new JPanel();
        bordure.setLayout(new BoxLayout(bordure, BoxLayout.Y_AXIS));
        bordure.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        bordure.add(tableScroll);
        bordure.add(Box.createVerticalStrut(2));
        bordure.add(options);
        
        this.add(bordure, BorderLayout.CENTER);
    }
    
    public final void updateTable()
    {
        Sortie[] sorties = new Sortie[0];
        Object[][] data = new Object[0][0];
        if (app!= null && station != null)
        {
            MatriceTri matrice = station.getMatriceTri();
            
            List<Sortie> listeSorties = matrice.getListeSorties();
            List<Matiere> matieres = new ArrayList<>((afficherTout.isSelected()) ? app.controleur.getMatieresSchema()
                                                                                 : station.getMatieres().getMatiere().keySet());
            
            sorties = listeSorties.toArray(new Sortie[listeSorties.size()]);
            
            data = new Object[matieres.size()][sorties.length + 1];
            for (int i = 0; i < matieres.size(); i++)
            {
                data[i][0] = matieres.get(i);
                for (int j = 1; j < sorties.length + 1; j++)
                    data[i][j] = NumericUtilities.round(matrice.getQuantite(sorties[j - 1], matieres.get(i))*100, 5);
            } 
        }
        
        model.colonnes = sorties;
        model.data = data;
        
        model.fireTableStructureChanged();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == afficherTout)
        {
            this.updateTable();
        }
    }

    @Override
    public void tableChanged(TableModelEvent e)
    {
        int row = e.getFirstRow();
        int column = e.getColumn();
        
        if (column > 0)
        {
            try
            {
                float pourcentage = (float)model.getValueAt(row, column);

                if (station != null)
                {
                    station.getMatriceTri().modifierQuantite(model.colonnes[column - 1], 
                                                             (Matiere)model.getValueAt(row, 0), pourcentage/100d);
                    
                    app.controleur.actualiserSchema();
                    app.paramElements.updaterPanel();
                }

                updateTable();
                

            } catch (Exception ex) { }
        }
        
    }
    
    private class MatriceTriTableModel extends AbstractTableModel
    {
        public Sortie[] colonnes = new Sortie[0];
        public Object[][] data = new Object[0][0];
        
        @Override
        public int getRowCount()
        {
            return data.length;
        }

        @Override
        public int getColumnCount()
        {
            return colonnes.length + 1;
        }
        
        @Override
        public String getColumnName(int column)
        {
            String retour = "Matiere";
            
            if (column > 0)
                retour = ""+ (column);
            
            return retour;
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            return data[rowIndex][columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return (columnIndex > 0);
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex)
        {
            try
            {
                data[rowIndex][columnIndex] = Float.parseFloat((String)aValue);
                this.fireTableCellUpdated(rowIndex, columnIndex);
            } catch (Exception ex) { }
        }
        
    }
}

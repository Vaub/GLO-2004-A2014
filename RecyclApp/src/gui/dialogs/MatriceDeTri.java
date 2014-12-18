/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.dialogs;

import domaine.usine.Matiere;
import domaine.usine.MatriceTri;
import domaine.usine.Sortie;
import gui.RecyclApp;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Vincent
 */
public class MatriceDeTri extends JDialog
{
    public final RecyclApp app;
    private final MatriceTri matrice;
    
    public MatriceDeTri(RecyclApp _app, MatriceTri _matrice)
    {
        app = _app;
        matrice = _matrice;
        
        this.setLayout(new BorderLayout());
        this.setModal(true);
        
        this.init();
        this.pack();
        
        this.setVisible(true);
    }
    
    private void init()
    {
        JTable matriceDeTri;
        MatriceDeTriModel model = new MatriceDeTriModel();
        
        Sortie[] sorties = new Sortie[0];
        Object[][] data = new Object[0][0];
        if (app!= null && matrice != null)
        {
            List<Sortie> listeSorties = matrice.getListeSorties();
            List<Matiere> matieres = new ArrayList<>(app.controleur.getMatieresSchema());
            
            sorties = listeSorties.toArray(new Sortie[listeSorties.size()]);
            
            data = new Object[matieres.size()][sorties.length + 1];
            for (int i = 0; i < matieres.size(); i++)
            {
                data[i][0] = matieres.get(i);
                for (int j = 1; j < sorties.length + 1; j++)
                    data[i][j] = matrice.getQuantite(sorties[j - 1], matieres.get(i))*100;
            } 
        }
        
        model.colonnes = sorties;
        model.data = data;
        
        matriceDeTri = new JTable(model);
        matriceDeTri.setPreferredScrollableViewportSize(new Dimension(500, 70));
        matriceDeTri.setFillsViewportHeight(true);
        
        JScrollPane pane = new JScrollPane(matriceDeTri);
        this.add(pane, BorderLayout.CENTER);
        
    }
    
    private class MatriceDeTriModel extends AbstractTableModel
    {
        public Sortie[] colonnes;
        public Object[][] data;
        
        @Override
        public int getRowCount()
        {
            return data.length;
        }

        @Override
        public int getColumnCount()
        {
            return colonnes.length;
        }
        
        @Override
        public String getColumnName(int column)
        {
            String retour = "Matiere";
            
            if (column > 0)
                retour = "Sortie "+ (column);
            
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
                data[rowIndex][columnIndex] = (float)aValue;
                this.fireTableCellUpdated(rowIndex, columnIndex);
            } catch (Exception ex) { }
        }
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel.parametres;

import domaine.usine.Matiere;
import gui.RecyclApp;
import gui.controls.Icone;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Vincent
 */
public class ListeDeMatierePanel extends JPanel implements ActionListener, TableModelListener
{
    private final RecyclApp app;
    
    private JTable table;
    private ListeDeMatiereModel model;
    
    private final JButton a_ajouter = new JButton("Ajouter"),
                          a_supprimer = new JButton("Supprimer");
    
    public ListeDeMatierePanel(RecyclApp _app)
    {
        app = _app;
        
        model = new ListeDeMatiereModel();
        
        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setFillsViewportHeight(true);
        table.setShowGrid(false);
        
        a_ajouter.addActionListener(this);
        a_supprimer.addActionListener(this);
        
        JScrollPane tScrollPane = new JScrollPane(table);
        
        Box buttons = Box.createHorizontalBox();
        buttons.add(Box.createHorizontalGlue());
        buttons.add(a_ajouter);
        buttons.add(Box.createHorizontalStrut(5));
        buttons.add(a_supprimer);
        buttons.add(Box.createHorizontalGlue());
        
        buttons.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        this.add(tScrollPane);
        this.add(buttons, BorderLayout.SOUTH);
        
        this.updateTable();
    }
    
    public final void updateTable()
    {
        Set<Matiere> matieres = app.controleur.getMatieresSchema();
        Object[][] data = new Object[matieres.size()][2];
        
        int i = 0;
        for (Matiere m : matieres)
        {
            data[i][0] = m;
            data[i][1] = false;
            
            i++;
        }
        
        model.data = data;
        model.fireTableStructureChanged();
    }

    @Override
    public void tableChanged(TableModelEvent e)
    {
        if (e.getColumn() == 0)
        {
            this.updateTable();
            app.actualiser();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == a_ajouter)
        {
            String nom = JOptionPane.showInputDialog(null, 
                                                     "Nom de la matière", 
                                                     "Ajouter de la matière",
                                                     JOptionPane.QUESTION_MESSAGE);
            if (nom != null && !nom.isEmpty())
                app.controleur.ajouterMatiere(new Matiere(nom));
        }
        else if (e.getSource() == a_supprimer)
        {
            for (int i = 0; i < model.data.length; i++)
            {
                try
                {
                    if ((boolean)model.data[i][1]) app.controleur.enleverMatiere((Matiere)model.data[i][0]);
                } catch (Exception ex) { }
            }
        }
        
        this.updateTable();
        app.actualiser();
    }
    
    private class ListeDeMatiereModel extends AbstractTableModel
    {
        public Object[] colonnes = { "Nom", "Supprimer" };
        public Object[][] data = new Object[0][2];

        @Override
        public int getRowCount()
        {
            return data.length;
        }

        @Override
        public int getColumnCount()
        {
            return 2;
        }

        @Override
        public String getColumnName(int column)
        {
            return colonnes[column].toString();
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return (columnIndex != 0);
        }
        
        @Override
        public Class<?> getColumnClass(int columnIndex)
        {
            if (columnIndex == 1)
                return Boolean.class;
            
            return super.getColumnClass(columnIndex);
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            if (columnIndex == 1)
            {
                return (boolean)data[rowIndex][columnIndex];
            }
            else
                return data[rowIndex][columnIndex].toString();
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex)
        {
            if (columnIndex == 1)
            {
                try
                {
                    data[rowIndex][columnIndex] = (boolean)aValue;
                    this.fireTableCellUpdated(rowIndex, columnIndex);
                } catch (Exception ex) { }
            }
        }
        
    }
}

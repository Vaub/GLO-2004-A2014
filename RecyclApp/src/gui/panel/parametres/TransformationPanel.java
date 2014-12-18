/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel.parametres;

import domaine.usine.Matiere;
import domaine.usine.MatriceTransformation;
import domaine.usine.Station;
import gui.RecyclApp;
import java.awt.BorderLayout;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
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
public class TransformationPanel extends JPanel implements ActionListener, TableModelListener
{
    public final RecyclApp app;
    private Station station;
    
    private JPanel panelC, panelN, panelS;
    private JTable tableau;
    private JCheckBox afficherMatieres;
    private MatriceTableModel modelTable;
    
    public TransformationPanel(RecyclApp a, Station s)
    {
        this.app = a;
        this.station = s;
        
        modelTable = new MatriceTableModel();
        modelTable.addTableModelListener(this);
        
        tableau = new JTable(modelTable);
        tableau.setShowGrid(false);
        tableau.setFillsViewportHeight(true);

        this.setLayout(new BorderLayout());
        
        this.init();
        this.mettreAJourTable();
    }
    
    private void init()
    {        
        JScrollPane tableScroll = new JScrollPane(tableau);
           
        panelC = new JPanel();
        panelC.setLayout(new BoxLayout(panelC, BoxLayout.Y_AXIS));
        panelC.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelC.add(tableScroll);
         
        afficherMatieres = new JCheckBox("Afficher toutes les matieres");
        afficherMatieres.addActionListener(this);
        
        panelS = new JPanel();
        panelS.setLayout(new BoxLayout(panelS, BoxLayout.X_AXIS));
        panelS.add(afficherMatieres);
        
        //this.add(panelN, BorderLayout.NORTH);
        this.add(panelC, BorderLayout.CENTER);
        this.add(panelS, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == this.afficherMatieres)
        {
            this.mettreAJourTable();
        }
    }

    @Override
     public void tableChanged(TableModelEvent e) 
    {   
        int ligne = e.getFirstRow();
        int colonne = e.getColumn();
        
        if(colonne > 0)
        {
            try
            {
                float pourcentage = (float) modelTable.getValueAt(ligne, colonne);
                if(station != null)
                {
                   station.getMatriceTransformation().ajouterTransformation((Matiere)modelTable.donnees[ligne][0], 
                           modelTable.colonnes[colonne-1], (pourcentage/100f));
                   
                   app.controleur.actualiserSchema();
                   app.paramElements.updaterPanel();
                }
                this.mettreAJourTable();
            }catch(Exception ex){}
        }
    }
    
    public final void mettreAJourTable()
    {
        Matiere[] matieresTemp = new Matiere[0];
        Object[][] donneesTemp = new Object[0][0];
        
        if (app!= null && station != null)
        {
            MatriceTransformation matrice = station.getMatriceTransformation();
            
            List<Matiere> matieresColonne = new ArrayList<>(app.controleur.getMatieresSchema());
            
            List<Matiere> matieresLigne = new ArrayList<>((afficherMatieres.isSelected()) ? app.controleur.getMatieresSchema()
                                                                       : this.station.getMatieres().getMatiere().keySet());
                                                                     
            matieresTemp = matieresColonne.toArray(new Matiere[matieresColonne.size()]);
            
            //À changer la taille
            donneesTemp = new Object[matieresLigne.size()][matieresColonne.size()+1];
            
            for (int i = 0; i < matieresLigne.size(); i++)
            {
                donneesTemp[i][0] = matieresLigne.get(i);
                for (int j = 1; j <= matieresColonne.size(); j++)
                {
                    donneesTemp[i][j] = NumericUtilities.roundFloat(matrice.getPourcentage(matieresLigne.get(i), matieresColonne.get(j-1))*100f, 5);
                }
            } 
        }
        
        modelTable.colonnes = matieresTemp;
        modelTable.donnees = donneesTemp;
        modelTable.fireTableStructureChanged();
    }   
    
    private class MatriceTableModel extends AbstractTableModel
    {
        public Matiere[] colonnes = new Matiere[0];
        public Object[][] donnees = new Object[0][0];

        @Override
        public int getRowCount() 
        {
            return donnees.length;
        }

        @Override
        public int getColumnCount() 
        {
            return colonnes.length + 1;
        }
        
        @Override
        public String getColumnName(int column)
        {
            String nom = "Matiere";
            if (column > 0)
            {
                nom = colonnes[column-1].toString();
            }
            return nom;
        }
        
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex)
        {
            try
            {
                donnees[rowIndex][columnIndex] = Float.parseFloat((String)aValue);
                this.fireTableCellUpdated(rowIndex, columnIndex);
            } catch (NumberFormatException ex) {}
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) 
        {
            return donnees[rowIndex][columnIndex];
        }
        
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return (columnIndex > 0);
        }
    }
}

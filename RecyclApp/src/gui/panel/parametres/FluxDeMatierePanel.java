/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel.parametres;

import domaine.usine.EntreeUsine;
import domaine.usine.FluxDeMatiere;
import domaine.usine.Composant;
import domaine.usine.Matiere;
import gui.RecyclApp;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import utils.NumericUtilities;
import utils.Pair;

/**
 *
 * @author Vincent
 */
public class FluxDeMatierePanel extends JPanel implements ActionListener, TableModelListener
{
    private final RecyclApp app;
    private FluxDeMatiere flux;
    
    private final Composant composant;
    
    private JTable table;
    private MatiereTableModel model;
    
    private final Box b_box;
    
    private final JButton b_ajouter,
                          b_supprimer;

    public FluxDeMatierePanel(Composant _composant, FluxDeMatiere _flux, RecyclApp _app)
    {
        app = _app;
        flux = _flux;
        
        composant = _composant;
        
        model = new MatiereTableModel();
        
        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setFillsViewportHeight(true);
        table.setShowGrid(false);
        
        model.addTableModelListener(this);
        
        b_ajouter = new JButton("Ajouter");
        b_ajouter.addActionListener(this);
        
        b_supprimer = new JButton("Supprimer");
        b_supprimer.addActionListener(this);
        
        b_box = Box.createHorizontalBox();
        b_box.add(Box.createHorizontalGlue());
        b_box.add(b_ajouter);
        b_box.add(Box.createHorizontalStrut(5));
        b_box.add(b_supprimer);
        b_box.add(Box.createHorizontalGlue());
        
        b_box.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        
        this.setLayout(new BorderLayout());
        
        JScrollPane tScrollPane = new JScrollPane(table);
        
        JPanel panelBordure = new JPanel(new BorderLayout());
        panelBordure.add(table.getTableHeader(), BorderLayout.NORTH);
        panelBordure.add(tScrollPane);
        
        this.add(panelBordure);
        this.add(b_box, BorderLayout.SOUTH);
        
        if (composant instanceof EntreeUsine)
            this.setEdit(true);
        else
            this.setEdit(false);
    }
    
    public final void setEdit(boolean _edit)
    {
        model.canEdit = _edit;
        
        b_box.setVisible(_edit);
        updateTable(null);
    }
    
    public final void updateTable(FluxDeMatiere _flux)
    {
        Object[][] data = new Object[0][0];
        flux = (_flux != null) ? _flux : flux;
        
        FluxDeMatiere totalEntree = app.controleur.getMatieresEntrees();
        
        if (flux != null)
        {
            HashMap<Matiere, Double> matieres = flux.getMatiere();
            
            data = new Object[matieres.size()][(model.canEdit) ? 5 : 4];
            
            int i = 0;
            
            for (Entry<Matiere, Double> e : matieres.entrySet())
            {
                data[i][0] = e.getKey();
                data[i][1] = e.getValue();
                
                data[i][2] = NumericUtilities.round((e.getValue() / flux.getQuantiteTotale())*100, 3) +" %";
                
                String taux = "-";
                if (totalEntree.getMatiere().containsKey(e.getKey()))
                    taux = NumericUtilities.round((e.getValue() / totalEntree.getMatiere().get(e.getKey()))*100, 3) +" %";
                
                data[i][3] = taux;
                
                if (model.canEdit)
                    data[i][4] = false;
                
                i++;
            }
        }
        
        model.data = data;
        model.fireTableStructureChanged();
        
        this.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == b_supprimer && model.canEdit)
        {
            for (int i = 0; i < model.data.length; i++)
            {
                try
                {
                    if ((boolean)model.data[i][4])
                        flux.enleverMatiere((Matiere)model.data[i][0]);
                    
                } catch (Exception ex) { }
            }
            
            app.controleur.changerMatiereEntree((EntreeUsine)composant, flux);
        }
        else if (e.getSource() == b_ajouter && model.canEdit)
        {
            AjouterMatiere ajout = new AjouterMatiere(app.controleur.getMatieresSchema());
            if (ajout.message == JOptionPane.OK_OPTION)
            {
                Pair<Matiere, Double> matiereAAjouter = new Pair<>(ajout.getMatiere(),
                                                                   ajout.getQuantie());
                
                if (matiereAAjouter.x != null)
                {
                    flux.setMatiere(matiereAAjouter.x, matiereAAjouter.y);
                    
                    app.controleur.changerMatiereEntree((EntreeUsine)composant, flux);
                }
            }
        }
        
        
                    
        this.updateTable(null);
        app.actualiser();
    }

    @Override
    public void tableChanged(TableModelEvent e)
    {
        if (model.canEdit && composant instanceof EntreeUsine && 
            e.getColumn() == 1)
        {
            try
            {
                Matiere m = (Matiere)model.data[e.getFirstRow()][0];
                double qte = (double)model.data[e.getFirstRow()][1];
                
                flux.setMatiere(m, qte);
                
                app.controleur.changerMatiereEntree((EntreeUsine)composant, flux);
                
                this.updateTable(null);
                app.actualiser();
            } catch (Exception ex) { }
        }
    }
    
    private class MatiereTableModel extends AbstractTableModel
    {
        public Object[] colonnes = { "Nom", "Quantité (kg/h)", "Pureté", "Récupération" };
        public Object[][] data = new Object[0][0];
        
        public boolean canEdit = false;
        
        @Override
        public int getRowCount()
        {
            return data.length;
        }

        @Override
        public int getColumnCount()
        {
            return (canEdit) ? colonnes.length + 1 :
                               colonnes.length;
        }
        
        @Override
        public String getColumnName(int column)
        {
            String retour = "Supprimer";
            
            if (column < 4)
                retour = colonnes[column].toString();
            
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
            return (canEdit && columnIndex > 0);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex)
        {
            if (columnIndex == 4)
                return Boolean.class;
            
            return super.getColumnClass(columnIndex);
        }
        
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex)
        {
            if (canEdit)
            {
                if (columnIndex == 1)
                {
                    try
                    {
                        data[rowIndex][columnIndex] = Double.parseDouble((String)aValue);
                        this.fireTableCellUpdated(rowIndex, columnIndex);
                    } catch (Exception ex) { }
                }
                else if (columnIndex == 4)
                {
                    data[rowIndex][columnIndex] = (boolean)aValue;
                    this.fireTableCellUpdated(rowIndex, columnIndex);
                }
            }
        }
        
    }
    
    private class AjouterMatiere extends JDialog implements ActionListener
    {
        private final JComboBox<Matiere> a_matiere;
        private final JSpinner           a_quantite;
        
        private final JButton b_valider,
                              b_annuler;
        
        public int message = JOptionPane.CLOSED_OPTION;
        
        public AjouterMatiere(Set<Matiere> matieres)
        {
            a_matiere = new JComboBox<>(matieres.toArray(new Matiere[matieres.size()]));
            a_quantite = new JSpinner(new SpinnerNumberModel(0d, 0d, 1e6d, 1e-3d));
            
            b_valider = new JButton("Valider");
            b_valider.addActionListener(this);
            
            b_annuler = new JButton("Annuler");
            b_annuler.addActionListener(this);
            
            this.init();
            
            this.setTitle("Ajouter de la matière");
            
            this.setLocationRelativeTo(null);
            
            this.pack();
            this.setResizable(false);
            
            this.setModal(true);
            this.setVisible(true);
        }
        
        private void init()
        {
            JLabel l_matiere  = new JLabel("Matière"),
                   l_quantite = new JLabel("Quantité");
            
            JPanel panelOptions = new JPanel();
            
            GroupLayout layout = new GroupLayout(panelOptions);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            
            GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
            hGroup.addGroup(layout.createParallelGroup().addComponent(l_matiere).addComponent(l_quantite));
            hGroup.addGroup(layout.createParallelGroup().addComponent(a_matiere).addComponent(a_quantite));
            layout.setHorizontalGroup(hGroup);
            
            GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
            vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l_matiere)
                                                                                      .addComponent(a_matiere));
            vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l_quantite)
                                                                                      .addComponent(a_quantite));
            layout.setVerticalGroup(vGroup);
            
            panelOptions.setLayout(layout);
            
            Box b_box = Box.createHorizontalBox();
            b_box.add(Box.createHorizontalGlue());
            b_box.add(b_valider);
            b_box.add(Box.createHorizontalStrut(5));
            b_box.add(b_annuler);
            b_box.add(Box.createHorizontalGlue());
            
            b_box.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
            
            this.add(panelOptions, BorderLayout.CENTER);
            this.add(b_box, BorderLayout.SOUTH);
        }
        
        public Matiere getMatiere()
        {
            return (a_matiere.getSelectedItem() != null) ? (Matiere)a_matiere.getSelectedItem() 
                                                         : null;
        }
        
        public double getQuantie()
        {
            double quantite = 0d;
            try
            {
                quantite = (double)a_quantite.getValue();
                quantite = NumericUtilities.round(quantite, 3);
            } catch (Exception ex) { }
            
            return quantite;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == b_valider)
            {
                message = JOptionPane.OK_OPTION;
            }
            
            this.setVisible(false);
        }
    }
}

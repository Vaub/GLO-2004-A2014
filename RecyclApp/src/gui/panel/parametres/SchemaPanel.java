/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel.parametres;

import domaine.controle.Schema;
import gui.RecyclApp;
import gui.actions.ModificationSchema;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Vincent
 */
public class SchemaPanel extends ParamTab implements ChangeListener, DocumentListener
{
    public final Schema schema;
    private final RecyclApp app;
    
    private final ModificationSchema modifications;
    
    protected JPanel p_parametres;
    protected ListeDeMatierePanel p_matieres;
    
    private final JLabel l_nom    = new JLabel("Nom"),
                         l_pixels = new JLabel("Mètres par pixels");
    
    private final JTextField a_nom  = new JTextField();
    private final JSpinner a_pixels = new JSpinner();
    
    public SchemaPanel(Schema _schema, RecyclApp _app)
    {
        app = _app;
        schema = _schema;
        
        modifications = new ModificationSchema(_schema, _app);
        
        if (schema != null)
        {
            p_parametres = new JPanel();
            p_matieres = new ListeDeMatierePanel(_app);

            p_parametres.setBackground(Color.WHITE);
            p_matieres.setBackground(Color.WHITE);
            
            this.init();
            
            tab.addTab("Paramètres", p_parametres);
            tab.addTab("Matières", p_matieres);
            
            tab.setSelectedComponent(p_parametres);
            
            this.add(tab, BorderLayout.CENTER);
        }
    }
    
    private void init()
    {
        this.updatePanel();
        
        a_nom.getDocument().addDocumentListener(this);
        a_pixels.addChangeListener(this);
        
        GroupLayout layout = new GroupLayout(p_parametres);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().addComponent(l_nom).addComponent(l_pixels));
        hGroup.addGroup(layout.createParallelGroup().addComponent(a_nom).addComponent(a_pixels));
        layout.setHorizontalGroup(hGroup);
        
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l_nom).addComponent(a_nom));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l_pixels).addComponent(a_pixels));
        layout.setVerticalGroup(vGroup);
        
        p_parametres.setLayout(layout);
    }
    
    @Override
    public void updatePanel()
    {
        if (schema == null) return;
        
        try
        {
            a_nom.setText(schema.getNom());
        } catch (Exception e) { }
        
        a_pixels.setModel(new SpinnerNumberModel(schema.getPixelsParMetre(), 1, 10000, 1));
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        if (schema == null) return;
        
        if (e.getSource() == a_pixels)
        {
            int nbPixels = schema.getPixelsParMetre();
            try
            {
                nbPixels = (int)a_pixels.getValue();
            } catch (Exception ex) { }
            
            modifications.modifierPixelsParMetre(nbPixels);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        if (e.getDocument() == a_nom.getDocument())
        {
            modifications.modifierNom(a_nom.getText());
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        this.insertUpdate(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {
    }
    
}

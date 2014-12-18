/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel.parametres;

import domaine.usine.FluxDeMatiere;
import domaine.usine.Composant;
import gui.RecyclApp;
import gui.actions.ModificationComposant;
import gui.actions.ModificationEquipement;
import gui.controls.BoutonCouleur;
import gui.controls.BoutonImage;
import gui.controls.Icone;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Vincent
 */
public class ComposantPanel extends ParamTab implements DocumentListener
{
    public final RecyclApp app;
    
    private final ModificationComposant modifications;
    
    private final Composant composant;
    
    protected JPanel p_composant;
    protected FluxDeMatierePanel p_matiere;
    
    protected GroupLayout layout_composant;
    protected ParallelGroup g_composant_description,
                            g_composant_valeur;
    
    protected SequentialGroup g_composant_v;
    
    protected final JLabel l_nom      = new JLabel("Nom"),
                           l_couleur  = new JLabel("Couleur");
    
    protected final JTextField a_nom = new JTextField();
    protected final BoutonCouleur a_couleur = new BoutonCouleur(Color.BLACK);
    
    protected final BoutonImage b_supprimer = new BoutonImage("Supprimer l'équipement", 
                                                              Icone.SUPPRIMER_COMPOSANT, 
                                                              20, 
                                                              false);
    
    
    public ComposantPanel(Composant _composant, RecyclApp _app)
    {
        super();
        
        app = _app;
        composant = _composant;
        
        modifications = new ModificationComposant(_composant, _app);
        
        if (composant != null)
        {
            p_composant = new JPanel();
            
            p_matiere = new FluxDeMatierePanel(composant, composant.getMatieres(), app);
            p_matiere.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            
            p_composant.setBackground(Color.WHITE);
            p_matiere.setBackground(Color.WHITE);
            
            JScrollPane s_composant = new JScrollPane(p_composant);
            s_composant.setBorder(null);
            s_composant.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            s_composant.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            
            this.init();
            
            tab.addTab("Paramètres", p_composant);
            tab.addTab("Matières", p_matiere);
            
            tab.setSelectedComponent(p_matiere);
            
            this.add(tab, BorderLayout.CENTER);
            
            this.add(p_icon, BorderLayout.SOUTH);
        }
    }
    
    private void init()
    {
        this.updatePanel();
        
        a_nom.getDocument().addDocumentListener(this);
        a_couleur.getDocument().addDocumentListener(this);
        
        p_icon.add(b_supprimer);
        
        layout_composant = new GroupLayout(p_composant);
        layout_composant.setAutoCreateGaps(true);
        layout_composant.setAutoCreateContainerGaps(true);
        
        p_composant.setLayout(layout_composant);
        
        g_composant_valeur = layout_composant.createParallelGroup(GroupLayout.Alignment.LEADING);
        g_composant_description = layout_composant.createParallelGroup(GroupLayout.Alignment.LEADING);
        
        g_composant_v = layout_composant.createSequentialGroup();
        
        layout_composant.setHorizontalGroup(layout_composant.createSequentialGroup()
                                                            .addGroup(g_composant_description)
                                                            .addGroup(g_composant_valeur));
        
        layout_composant.setVerticalGroup(g_composant_v);
        
        g_composant_description.addComponent(l_nom)
                               .addComponent(l_couleur);
        
        g_composant_valeur.addComponent(a_nom)
                          .addComponent(a_couleur);
        
        g_composant_v.addGroup(layout_composant.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l_nom)
                                                                                                   .addComponent(a_nom));
        g_composant_v.addGroup(layout_composant.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l_couleur)
                                                                                                   .addComponent(a_couleur));
    }
    
    @Override
    public void updatePanel()
    {
        if (composant == null) return;
        
        if (p_matiere != null)
            p_matiere.updateTable(composant.getMatieres());
        
        try
        {
            a_nom.setText(composant.getNom());
            a_couleur.setCouleur(composant.getCouleur());
        } catch (Exception e) { }
    }

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        if (composant == null) return;
        
        if (e.getDocument() == a_nom.getDocument())
        {
            modifications.modifierNom(a_nom.getText());
        }
        else if (e.getDocument() == a_couleur.getDocument())
        {
            modifications.modifierCouleur(a_couleur.getCouleur());
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

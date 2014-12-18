/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel;

import gui.RecyclApp;

import gui.adapters.MenuAdapter;
import gui.actions.CreerUnSchema;
import gui.actions.FermerUnSchema;
import gui.actions.OuvrirUnSchema;
import gui.actions.SauvegarderUnSchema;
import gui.actions.ProposSparser;
import gui.actions.Annuler;
import gui.actions.ExporterUnSchema;
import gui.actions.OuvrirParametres;
import gui.actions.Retablir;
import gui.actions.SelectionConvoyeurs;
import gui.actions.SelectionEquipement;
import gui.actions.SelectionNormale;
import gui.actions.SupprimerUnSchema;
import gui.actions.SupprimerSchemas;
import gui.controls.Icone;
import java.awt.Color;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

/**
 *
 * @author Magalie
 */
public class BarreDeMenu extends JMenuBar
{
    
    public RecyclApp parent;
    public JMenu m_fichier,
                 m_edition,
                 m_outils,
                 m_aide,
                 subm_selection,
                 subm_ajouter,
                 subm_affichage;
    
    public JMenuItem i_nouveau,
                     i_ouvrir,
            i_recent,
            i_sauvegarder,
            i_fermer,
            i_fermerTout,
            i_quitter,
            i_annuler,
            i_retablir,
            //i_couper,
            //i_copier,
            //i_coller,
            i_supprimer,
            i_selectionne,
            i_agrandir,
            i_reduire,
            i_options,
            i_aStation,
            i_aJonction,
            i_aEntree,
            i_aSortie,
            i_aConvoyeur,
            i_support,
            i_propos,
            i_exporter;
    
    public ButtonGroup bg_selectionner;
    
    public JRadioButtonMenuItem bmi_sItemSelection;
    
    public BarreDeMenu(RecyclApp _parent)
    {
        parent = _parent;
        this.init();
    }
    
    public void init()
    {
      //Panel de la barre de Menu
      m_fichier = new JMenu("Fichier");
      m_edition = new JMenu("Edition");
      m_outils = new JMenu("Outils");
      m_aide = new JMenu("Aide");
      subm_affichage = new JMenu("Affichage");
      subm_selection = new JMenu("Sélectionner...");
      subm_ajouter = new JMenu("Ajouter");
      
      bg_selectionner = new ButtonGroup();
      
      m_fichier.getAccessibleContext().setAccessibleDescription("Options du fichier");
      m_edition.getAccessibleContext().setAccessibleDescription("Édition");
      m_outils.getAccessibleContext().setAccessibleDescription("Outils");
      m_aide.getAccessibleContext().setAccessibleDescription("Aide");
      add(m_fichier);
      add(m_edition);
      add(m_outils);
      add(m_aide);
      
      //Création des sous-item dans le Menu Fichier
      i_nouveau = new JMenuItem("Nouveau...",(Icone.NOUVEAU.getImage(20,20)));
      i_nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
      i_nouveau.addActionListener(new CreerUnSchema(this.parent));
      
      i_ouvrir = new JMenuItem("Ouvrir...",(Icone.OUVRIR2.getImage(20,20)));
      i_ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
      i_ouvrir.addActionListener(new OuvrirUnSchema(this.parent));
      
      i_recent = new JMenuItem("Récent");
      i_recent.setMnemonic(KeyEvent.VK_R);
      
      i_sauvegarder = new JMenuItem("Sauvegarder",(Icone.SAUVEGARDER2.getImage(20,20)));
      i_sauvegarder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
      i_sauvegarder.addActionListener(new SauvegarderUnSchema(this.parent));
      
      i_exporter = new JMenuItem("Exporter Schéma",(Icone.EXPORTER.getImage(20,20)));
      i_exporter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
      i_exporter.addActionListener(new ExporterUnSchema(this.parent));
      
      i_fermer = new JMenuItem("Fermer");
      i_fermer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
      i_fermer.addActionListener(new SupprimerUnSchema(this.parent.controleur.getSchema(),this.parent));
      
      i_fermerTout = new JMenuItem("Fermer Tout");
      i_fermerTout.setMnemonic(KeyEvent.VK_Q);
      i_fermerTout.addActionListener(new SupprimerSchemas(this.parent));
      
      i_quitter = new JMenuItem("Quitter", Icone.QUITTER.getImage(20,20));
      i_quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
      i_quitter.addActionListener(new FermerUnSchema(this.parent));
      
      JSeparator separer = new JSeparator();
      JSeparator separer2 = new JSeparator();
      
      separer.setForeground(new Color(201,199, 205, 150));
      separer2.setForeground(new Color(201,199, 205, 150));
      
      m_fichier.add(i_nouveau);
      m_fichier.add(i_ouvrir);
      m_fichier.add(separer);
      m_fichier.add(i_sauvegarder);
      m_fichier.add(i_exporter);
      m_fichier.add(i_fermer);
      m_fichier.add(i_fermerTout);
      m_fichier.add(separer2);
      m_fichier.add(i_quitter);
      
      //Création des sous-item dans le Menu Edition
      i_annuler = new JMenuItem("Annuler", (Icone.ANNULER.getImage(15,15)));
      i_annuler.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
      i_annuler.addActionListener(new Annuler(this.parent));
      
      i_retablir = new JMenuItem("Rétablir", (Icone.REVENIR.getImage(15,15)));
      i_retablir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
      i_retablir.addActionListener(new Retablir(this.parent));
      
      //i_couper = new JMenuItem("Couper", (Icone.COUPER.getImage(15,15)));
      //i_couper.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK)); 
      
      //i_copier = new JMenuItem("Copier", (Icone.COPIER.getImage(15,15)));
      //i_copier.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
      
      //i_coller = new JMenuItem("Coller", (Icone.COLLER.getImage(15,15)));
      //i_coller.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK)); 
      
      i_supprimer = new JMenuItem("Supprimer", (Icone.SUPPRIMER.getImage(15,15)));
      i_supprimer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, ActionEvent.ALT_MASK));
      i_retablir.addActionListener(new MenuAdapter(this, this.parent));
      
      //i_selectionne = new JMenuItem("Sélectionner");

      JSeparator e_separer = new JSeparator();
      
      e_separer.setForeground(new Color(201,199, 205, 150));
      
      //Création des sous-item dans le sous-menu Affichage
      i_agrandir = new JMenuItem("Aggrandir", (Icone.ZOOMP.getImage(15, 15)));
      i_agrandir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.CTRL_MASK));
      i_agrandir.addActionListener(new MenuAdapter(this, this.parent));
      
      i_reduire = new JMenuItem("Réduire", (Icone.ZOOMM.getImage(15,15)));
      i_reduire.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK));
      i_reduire.addActionListener(new MenuAdapter(this, this.parent));
      
      subm_affichage.add(i_agrandir);
      subm_affichage.add(i_reduire);
      
      m_edition.add(i_annuler);
      m_edition.add(i_retablir);
      m_edition.add(e_separer);
      //m_edition.add(i_couper);
      //m_edition.add(i_copier);
      //m_edition.add(i_coller);
      //m_edition.add(i_supprimer);
      //m_edition.add(i_selectionne);
      m_edition.add(subm_affichage);
      
        //Création des sous-item dans le Menu Outils
      i_options = new JMenuItem("Options", (Icone.PARAMETRES.getImage(15,15)));
      i_options.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.CTRL_MASK));
      i_options.addActionListener(new OuvrirParametres(parent));

        //Création du sous menu Ajouter
      i_aStation = new JMenuItem("Ajouter une station");
      i_aStation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
      i_aStation.addActionListener(new MenuAdapter(this, this.parent));
      
      i_aEntree = new JMenuItem("Ajouter une entrée");
      i_aEntree.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
      i_aEntree.addActionListener(new MenuAdapter(this, this.parent));
      
      i_aSortie = new JMenuItem("Ajouter une sortie");
      i_aSortie.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
      i_aSortie.addActionListener(new MenuAdapter(this, this.parent));
      
      i_aJonction = new JMenuItem("Ajouter une jonction");
      i_aJonction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.ALT_MASK));
      i_aJonction.addActionListener(new MenuAdapter(this, this.parent));
      
      //i_aConvoyeur = new JMenuItem("Ajouter un lien");
      //i_aConvoyeur.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
      //i_aConvoyeur.addActionListener(new MenuAdapter(this, this.parent));
      
      subm_ajouter.add(i_aStation);
      subm_ajouter.add(i_aEntree);
      subm_ajouter.add(i_aSortie);
      subm_ajouter.add(i_aJonction);
      //subm_ajouter.add(i_aConvoyeur);
      
      JRadioButtonMenuItem bselection = new JRadioButtonMenuItem("Équipements");
      bselection.addActionListener(new SelectionEquipement(parent));
      
      JRadioButtonMenuItem bselectionc = new JRadioButtonMenuItem("Convoyeurs");
      bselectionc.addActionListener(new SelectionConvoyeurs(parent));
      
      JRadioButtonMenuItem bselectionn = new JRadioButtonMenuItem("Normale");
      bselectionn.addActionListener(new SelectionNormale(parent));
      
      ButtonGroup group = new ButtonGroup();
      group.add(bselection);
      group.add(bselectionc);
      group.add(bselectionn);
      subm_selection.add(bselection);
      subm_selection.add(bselectionc);
      subm_selection.add(bselectionn);
      
      m_outils.add(subm_ajouter);
      m_outils.add(subm_selection);
      m_outils.add(i_options);
      
      
        //Création des sous-item dans le Menu Aide
      //i_support = new JMenuItem("Support", (Icone.AIDE.getImage(20,20)));
      //i_support.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.CTRL_MASK));
      //i_support.addActionListener(new SupportSchema(this.parent));
      
      i_propos = new JMenuItem("À propos", (Icone.ABOUT.getImage(20,20)));
      i_propos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.CTRL_MASK)); 
      i_propos.addActionListener(new ProposSparser(this.parent));
      m_aide.add(i_propos);
      
    }

}

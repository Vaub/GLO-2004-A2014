/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.dialogs;

import gui.RecyclApp;
import gui.actions.RemiseADefaut;
import gui.controls.BoutonCouleur;
import gui.controls.BoutonImage;
import gui.controls.Icone;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import ressources.Parametres;

/**
 *
 * @author Vincent
 */
public class ModifierParametres extends JDialog implements ActionListener
{
    public final RecyclApp app;
    
    public CardLayout layout;
    
    public JPanel conteneur,
                  apparence,
                  composants,
                  performance;
    
    public Box menu,
               actions;
    
    public BoutonImage b_apparence,
                       b_composants,
                       b_performance;
    
    public JButton defaut,
                   appliquer,
                   quitter;
    
    //==================
    // Options
    //==================
    
    public JSpinner casesParMetre,
                    maximumUndoRedo;
    
    public BoutonCouleur couleurPrincipale,
                         couleurContraste,
                         couleurFondGrille,
                         couleurComposants,
                         couleurEntrees,
                         couleurSorties;
    
    //==================
    
    public ModifierParametres(RecyclApp _app)
    {
        app = _app;
        
        this.setModal(true);
        this.setTitle("Préférences");
        this.setIconImage(Icone.PARAMETRES.getImage().getImage());
        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        layout = new CardLayout();
        
        conteneur = new JPanel();
        conteneur.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        conteneur.setLayout(layout);
        
        this.initApparance();
        this.initComposants();
        this.initPerformance();
        this.initMenu();
        this.init();
        
        this.pack();
        this.setLocationRelativeTo(app);
        
        this.setVisible(true);
        this.setMinimumSize(this.getSize());
    }
    
    private void init()
    {
        defaut = new JButton("Défaut");
        defaut.setMinimumSize(new Dimension(75, 30));
        defaut.setPreferredSize(new Dimension(75, 30));
        defaut.addActionListener(this);
                
        appliquer = new JButton("Appliquer");
        appliquer.setMinimumSize(new Dimension(100, 30));
        appliquer.setPreferredSize(new Dimension(100, 30));
        appliquer.addActionListener(this);
        
        quitter = new JButton("Quitter");
        quitter.setMinimumSize(new Dimension(75, 30));
        quitter.setPreferredSize(new Dimension(75, 30));
        quitter.addActionListener(this);
        
        actions = Box.createHorizontalBox();
        actions.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        actions.add(Box.createHorizontalGlue());
        actions.add(defaut);
        actions.add(Box.createHorizontalStrut(5));
        actions.add(appliquer);
        actions.add(Box.createHorizontalStrut(5));
        actions.add(quitter);
        actions.add(Box.createHorizontalGlue());
        
        
        this.add(menu, BorderLayout.NORTH);
        this.add(conteneur, BorderLayout.CENTER);
        this.add(actions, BorderLayout.SOUTH);
    }
    
    private void initApparance()
    {
        JLabel l_cPrincipale = new JLabel("Thème "),
               l_cContraste  = new JLabel("Contraste "),
               l_casesMetre  = new JLabel("Cases par mètre "),
               l_cFondGrille = new JLabel("Fond de la grille ");
        
        BoutonImage r_cPrincipale = CreerBoutonReset("theme", Parametres.COULEUR_FOND),
                    r_cContraste = CreerBoutonReset("contraste", Parametres.COULEUR_CONTRASTE),
                    r_cCasesMetre = CreerBoutonReset("cases par metre", Parametres.CASES_PAR_METRE),
                    r_cFondGrille = CreerBoutonReset("fond de la grille", Parametres.COULEUR_FOND_GRILLE);
        
        couleurPrincipale = new BoutonCouleur(Parametres.getCouleur(Parametres.COULEUR_FOND));
        couleurContraste = new BoutonCouleur(Parametres.getCouleur(Parametres.COULEUR_CONTRASTE));
        
        casesParMetre = new JSpinner(new SpinnerNumberModel(Parametres.getEntier(Parametres.CASES_PAR_METRE), 1, 100, 1));
        couleurFondGrille = new BoutonCouleur(Parametres.getCouleur(Parametres.COULEUR_FOND_GRILLE));
        
        JPanel themeApp = new JPanel(new BorderLayout());
        themeApp.setBorder(BorderFactory.createTitledBorder("Palette"));
        
        GroupLayout groupLayout = new GroupLayout(themeApp);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
        hGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(l_cPrincipale).addComponent(l_cContraste));
        hGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(couleurPrincipale).addComponent(couleurContraste));
        hGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(r_cPrincipale).addComponent(r_cContraste));
        groupLayout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();
        vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l_cPrincipale)
                                                                                       .addComponent(couleurPrincipale)
                                                                                       .addComponent(r_cPrincipale));
        vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l_cContraste)
                                                                                       .addComponent(couleurContraste)
                                                                                       .addComponent(r_cContraste));
        groupLayout.setVerticalGroup(vGroup);
        
        themeApp.setLayout(groupLayout);
        
        JPanel grilleParam = new JPanel(new BorderLayout());
        grilleParam.setBorder(BorderFactory.createTitledBorder("Grille"));
        
        groupLayout = new GroupLayout(grilleParam);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        
        hGroup = groupLayout.createSequentialGroup();
        hGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(l_casesMetre).addComponent(l_cFondGrille));
        hGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(casesParMetre).addComponent(couleurFondGrille));
        hGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(r_cCasesMetre).addComponent(r_cFondGrille));
        groupLayout.setHorizontalGroup(hGroup);

        vGroup = groupLayout.createSequentialGroup();
        vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l_casesMetre).addComponent(casesParMetre).addComponent(r_cCasesMetre));
        vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l_cFondGrille).addComponent(couleurFondGrille).addComponent(r_cFondGrille));
        groupLayout.setVerticalGroup(vGroup);
        
        grilleParam.setLayout(groupLayout);
        
        apparence = new JPanel(new BorderLayout());
        apparence.setName("apparence");
        //apparence.setLayout(new BoxLayout(apparence, BoxLayout.Y_AXIS));
        
        Box boxApparence = Box.createVerticalBox();
        boxApparence.add(themeApp);
        boxApparence.add(grilleParam);
        
        apparence.add(boxApparence);
        
        layout.addLayoutComponent(apparence, "apparence");
        conteneur.add(apparence);
    }
    
    private void initComposants()
    {
        JLabel l_couleurComposants = new JLabel("Couleur par défaut"),
               l_couleurEntrees = new JLabel("Couleur des entrées"),
               l_couleurSorties = new JLabel("Couleur des sorties");
        
        couleurComposants = new BoutonCouleur(Parametres.getCouleur(Parametres.COULEUR_COMPOSANTS));
        couleurEntrees = new BoutonCouleur(Parametres.getCouleur(Parametres.COULEUR_ENTREES));
        couleurSorties = new BoutonCouleur(Parametres.getCouleur(Parametres.COULEUR_SORTIES));
        
        BoutonImage r_couleurComposants = CreerBoutonReset("couleur des composants", Parametres.COULEUR_COMPOSANTS),
                    r_couleurEntrees = CreerBoutonReset("couleur des composants", Parametres.COULEUR_ENTREES),
                    r_couleurSorties = CreerBoutonReset("couleur des composants", Parametres.COULEUR_SORTIES);
        
        JPanel p_composants = new JPanel(new BorderLayout());
        
        GroupLayout groupLayout = new GroupLayout(p_composants);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
        hGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(l_couleurComposants).addComponent(l_couleurEntrees).addComponent(l_couleurSorties));
        hGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(couleurComposants).addComponent(couleurEntrees).addComponent(couleurSorties));
        hGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(r_couleurComposants).addComponent(r_couleurEntrees).addComponent(r_couleurSorties));
        groupLayout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();
        vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l_couleurComposants)
                                                                                       .addComponent(couleurComposants)
                                                                                       .addComponent(r_couleurComposants));
        vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l_couleurEntrees)
                                                                                       .addComponent(couleurEntrees)
                                                                                       .addComponent(r_couleurEntrees));
        vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l_couleurSorties)
                                                                                       .addComponent(couleurSorties)
                                                                                       .addComponent(r_couleurSorties));
        groupLayout.setVerticalGroup(vGroup);
        
        p_composants.setLayout(groupLayout);
        
        Box boxComposants = Box.createVerticalBox();
        boxComposants.add(p_composants);
        
        composants = new JPanel(new BorderLayout());
        composants.setName("performance");
        
        composants.add(boxComposants);
        
        layout.addLayoutComponent(composants, "composants");
        conteneur.add(composants);
    }
    
    private void initPerformance()
    {
        JLabel l_MaximumDeUndoRedo = new JLabel("Maximum en mémoire");
        BoutonImage r_maximumUndoRedo = CreerBoutonReset("maximum annuler/rétablir", Parametres.MAX_UNDO_REDO);
        
        maximumUndoRedo = new JSpinner(new SpinnerNumberModel(Parametres.getEntier(Parametres.MAX_UNDO_REDO), 5, 5000, 1));
        
        JPanel p_undoredo = new JPanel(new BorderLayout());
        p_undoredo.setBorder(BorderFactory.createTitledBorder("Annuler / Rétablir"));
        
        GroupLayout groupLayout = new GroupLayout(p_undoredo);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
        hGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(l_MaximumDeUndoRedo));
        hGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(maximumUndoRedo));
        hGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(r_maximumUndoRedo));
        groupLayout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();
        vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l_MaximumDeUndoRedo)
                                                                                       .addComponent(maximumUndoRedo)
                                                                                       .addComponent(r_maximumUndoRedo));
        groupLayout.setVerticalGroup(vGroup);
        
        p_undoredo.setLayout(groupLayout);
        
        Box boxPerformance = Box.createVerticalBox();
        boxPerformance.add(p_undoredo);
        
        performance = new JPanel(new BorderLayout());
        performance.setName("performance");
        
        performance.add(boxPerformance);
        
        layout.addLayoutComponent(performance, "performance");
        conteneur.add(performance);
    }
    
    private void initMenu()
    {
        b_apparence = new BoutonImage("Apparence", Icone.APPARENCE, 32, true);
        b_composants = new BoutonImage("Composants", Icone.COMPOSANTE, 32, true);
        b_performance = new BoutonImage("Performances", Icone.PERFORMANCE, 32, true);
        
        
        b_apparence.addActionListener(this);
        b_composants.addActionListener(this);
        b_performance.addActionListener(this);
        
        menu = Box.createHorizontalBox();
        menu.add(b_apparence);
        menu.add(b_composants);
        menu.add(b_performance);
    }
    
    public void updateParametres()
    {
        couleurPrincipale.setCouleur(Parametres.getCouleur(Parametres.COULEUR_FOND));
        couleurContraste.setCouleur(Parametres.getCouleur(Parametres.COULEUR_CONTRASTE));
        
        casesParMetre.setValue(Parametres.getEntier(Parametres.CASES_PAR_METRE));
        couleurFondGrille.setCouleur(Parametres.getCouleur(Parametres.COULEUR_FOND_GRILLE));
        
        couleurComposants.setCouleur(Parametres.getCouleur(Parametres.COULEUR_COMPOSANTS));
        couleurEntrees.setCouleur(Parametres.getCouleur(Parametres.COULEUR_ENTREES));
        couleurSorties.setCouleur(Parametres.getCouleur(Parametres.COULEUR_SORTIES));
        
        maximumUndoRedo.setValue(Parametres.getEntier(Parametres.MAX_UNDO_REDO));
    }
    
    public void save()
    {
        Parametres.changerParametre(Parametres.COULEUR_FOND, couleurPrincipale.getCouleur());
        Parametres.changerParametre(Parametres.COULEUR_CONTRASTE, couleurContraste.getCouleur());
        
        int cases = Parametres.getEntier(Parametres.CASES_PAR_METRE);
        try
        {
            cases = (int)casesParMetre.getModel().getValue();
        } catch (Exception e) { }
        
        Parametres.changerParametre(Parametres.CASES_PAR_METRE, cases);
        Parametres.changerParametre(Parametres.COULEUR_FOND_GRILLE, couleurFondGrille.getCouleur());
        
        Parametres.changerParametre(Parametres.COULEUR_COMPOSANTS, couleurComposants.getCouleur());
        Parametres.changerParametre(Parametres.COULEUR_ENTREES, couleurEntrees.getCouleur());
        Parametres.changerParametre(Parametres.COULEUR_SORTIES, couleurSorties.getCouleur());
        
        int maxUR = Parametres.getEntier(Parametres.MAX_UNDO_REDO);
        try
        {
            maxUR = (int)maximumUndoRedo.getModel().getValue();
        } catch (Exception e) { }
        
        Parametres.changerParametre(Parametres.MAX_UNDO_REDO, maxUR);
        
        app.fond.resetGUI();
        app.zoneDeTravail.outils.resetGUI();
        app.zoneDeTravail.schema.resetGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == appliquer)
        {
            this.save();
        }
        else if (e.getSource() == quitter)
        {
            this.setVisible(false);
            this.dispose();
        }
        else if (e.getSource() == defaut)
        {
            Parametres.retourDefaut();
            
            this.updateParametres();
        }
        else if (e.getSource() == b_apparence)
            layout.show(conteneur, "apparence");
        else if (e.getSource() == b_performance)
            layout.show(conteneur, "performance");
        else if (e.getSource() == b_composants)
            layout.show(conteneur, "composants");
        
        this.revalidate();
    }
    
    public BoutonImage CreerBoutonReset(String _nom, String _param)
    {
        BoutonImage bouton = new BoutonImage("Defaut du "+_nom, Icone.ANNULER, 15, false);
        bouton.addActionListener(new RemiseADefaut(_param, this));
        
        return bouton;
    }
}

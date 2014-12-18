/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.dialogs;
import gui.RecyclApp;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import domaine.usine.Equipement;
import java.io.InputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout.Alignment;
import utils.Taille;
/**
 *
 * @author Proprietaire
 */
public class ModifierStation extends JDialog implements ActionListener
{
    
    public boolean estModifie;
    
    public int nbDeSorties;
    public Taille taille;
    
    public JLabel _dimension, _nbSorties, l_nom;
    public JTextField x_dim, y_dim, t_nom;
    public JSpinner nb_sorties;
    public JButton quitter, modifier;
    public Equipement selection;
    
    public ModifierStation(JFrame _frame)
    {   
        initModifierStation();
        
        estModifie = false;
        
        nbDeSorties = 1;
        taille = new Taille(1,1);
        
        this.pack();
        this.setMinimumSize(new Dimension(225, this.getPreferredSize().height));
        this.setPreferredSize(new Dimension(225, this.getPreferredSize().height));
        
        this.setLocationRelativeTo(_frame);
        
        this.setModal(true);
        //this.setResizable(false);
        
        this.setTitle("Station");
        
        this.setVisible(true);
    }
    
    private void initModifierStation()
    {
            this.setLayout(new BorderLayout());
            
            // Correct d'avoir un logo, mais c'est plus une chose de about ;)
            /*String path = "/images/Sparser.png";
            try
            {
                InputStream imgStrea = this.getClass().getResourceAsStream(path);
                BufferedImage img = ImageIO.read(imgStrea);
                modifierStation.setIconImage(img);
            }
            catch(IOException e){ System.out.println("Mauvais stream...");}*/

            x_dim = new JTextField("1");
            y_dim = new JTextField("1");

            _nbSorties = new JLabel("Nombre de sorties : ");
            
            SpinnerModel sm = new SpinnerNumberModel(1, 1, 1000, 1);
            nb_sorties = new JSpinner(sm);
            
            l_nom = new JLabel("Nom de la station : ");
            t_nom = new JTextField("");

            JPanel nbDeSorties = new JPanel();
            GroupLayout sLayout = new GroupLayout(nbDeSorties);
            sLayout.setAutoCreateGaps(true);
            sLayout.setAutoCreateContainerGaps(true);
            
            GroupLayout.SequentialGroup hGroup = sLayout.createSequentialGroup();
            hGroup.addGroup(sLayout.createParallelGroup().addComponent(l_nom).addComponent(_nbSorties));
            hGroup.addGroup(sLayout.createParallelGroup().addComponent(t_nom).addComponent(nb_sorties));
            sLayout.setHorizontalGroup(hGroup);
            
            GroupLayout.SequentialGroup vGroup = sLayout.createSequentialGroup();
            vGroup.addGroup(sLayout.createParallelGroup(Alignment.BASELINE).addComponent(l_nom).addComponent(t_nom));
            vGroup.addGroup(sLayout.createParallelGroup(Alignment.BASELINE).addComponent(_nbSorties).addComponent(nb_sorties));
            sLayout.setVerticalGroup(vGroup);
            
            nbDeSorties.setLayout(sLayout);
            
            JLabel largeur = new JLabel("Largeur : "),
                   hauteur = new JLabel("Hauteur : ");
            
            JPanel dimensions = new JPanel();
            dimensions.setBorder(BorderFactory.createTitledBorder("Dimensions"));
            
            GroupLayout dLayout = new GroupLayout(dimensions);
            dLayout.setAutoCreateGaps(true);
            dLayout.setAutoCreateContainerGaps(true);
            
            hGroup = dLayout.createSequentialGroup();
            hGroup.addGroup(dLayout.createParallelGroup().addComponent(largeur).addComponent(hauteur));
            hGroup.addGroup(dLayout.createParallelGroup().addComponent(x_dim).addComponent(y_dim));
            dLayout.setHorizontalGroup(hGroup);
            
            vGroup = dLayout.createSequentialGroup();
            vGroup.addGroup(dLayout.createParallelGroup(Alignment.BASELINE).addComponent(largeur).addComponent(x_dim));
            vGroup.addGroup(dLayout.createParallelGroup(Alignment.BASELINE).addComponent(hauteur).addComponent(y_dim));
            dLayout.setVerticalGroup(vGroup);
            
            dimensions.setLayout(dLayout);
            
            JPanel content = new JPanel();
            content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
            
            content.add(nbDeSorties);
            content.add(dimensions);

            JPanel control = new JPanel();
            control.setLayout(new BoxLayout(control, BoxLayout.X_AXIS));
            control.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
            
            quitter = new JButton("Quitter");
            modifier = new JButton("Modifier");
            control.add(modifier);
            control.add(Box.createHorizontalStrut(5));
            control.add(quitter);
            
            content.add(control);
            content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            this.add(content, BorderLayout.CENTER);
            
            quitter.addActionListener(this);
            modifier.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == modifier)
        {
            estModifie = true;
            
            double largeur = 1d,
                   hauteur = 1d;
            
            try
            {
                largeur = Double.parseDouble(x_dim.getText());
            } catch (Exception ex) { }
            
            try
            {
                hauteur = Double.parseDouble(y_dim.getText());
            } catch (Exception ex) { }
            
            taille = new Taille((largeur <= 1e-3) ? 0.1 : largeur,
                                (hauteur <= 1e-3) ? 0.1 : hauteur);
            
            try
            {
                nbDeSorties = (int)nb_sorties.getValue();
            } catch (Exception ex) { }
        }
        
        this.setVisible(false);
    }

}
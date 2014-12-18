/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.dialogs;

import domaine.usine.Station;
import gui.RecyclApp;
import gui.controls.Icone;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import utils.Pair;

/**
 *
 * @author owner
 */
public class ModifierApparenceStation extends JDialog implements ActionListener
{
    public final RecyclApp APP;
    private Station station;
    
    private JButton ouvrirImg, modifier, quitter;
    private JCheckBox checkForme;
    private JPanel panelS, panelC;
    private JLabel nom, nom1, forme, nomImg;
    private Pair<Image, String> pairImg;
    
    public ModifierApparenceStation(RecyclApp a, Station s)
    {
        this.APP = a;
        this.station = s;
        
        this.setTitle("Modification de l'apparence");
        this.initFenetre();
        
        this.setPreferredSize(new Dimension(300,205));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setModal(true);
        this.pack();
        
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
       if(e.getSource() == this.ouvrirImg)
       {
            choisirImage();
       }
       else if(e.getSource() == this.checkForme)
       {
           if(this.checkForme.isSelected())
           {
               pairImg.x = null;
               pairImg.y = null;
               
               nomImg.setText("Aucune image");
               nomImg.setToolTipText(null);
           }
       }
       else if(e.getSource() == this.modifier)
       {
            modifierImage();
       }
       else if(e.getSource() == this.quitter)
       {
            this.setVisible(false);
            this.dispose();
       }
    }
    
    private void initFenetre()
    {
        pairImg = new Pair<>(null,null);
        
        nomImg = new JLabel("Aucune image");
        nomImg.setPreferredSize(new Dimension(130,15));
        
        if(station.getImage().x != null)
        {
            pairImg.x = (station.getImage().x);
            pairImg.y = (station.getImage().y);
            
           nomImg.setText(pairImg.y);
        }

        nom = new JLabel("Image", Icone.DEFAUT.getImage(20, 20), JLabel.LEFT);
        nom.setPreferredSize(new Dimension(100,20));
     
        nom1 = new JLabel("      Nom : ");
        nom1.setPreferredSize(new Dimension(50,15));
        
        forme = new JLabel("  Forme géométrique", Icone.CARRE.getImage(15, 15), JLabel.LEFT);
        forme.setPreferredSize(new Dimension(150,20));
                
        ouvrirImg = new JButton("Ouvrir");
        ouvrirImg.setMinimumSize(new Dimension(190, 30));
        ouvrirImg.addActionListener(this);
        
        checkForme = new JCheckBox();
        checkForme.addActionListener(this);
        
        checkForme.setSelected(pairImg.x == null);
        
        panelC = new JPanel();
        panelC.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10), 
                        BorderFactory.createTitledBorder("Représentation")));
        
        GroupLayout layout = new GroupLayout(panelC);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        
        GroupLayout.SequentialGroup groupeVertical = layout.createSequentialGroup();
        groupeVertical.addGroup(layout.createParallelGroup(Alignment.BASELINE).
            addComponent(nom) .addComponent(Box.createHorizontalStrut(40)) .addComponent(ouvrirImg));
        groupeVertical.addGroup(layout.createParallelGroup(Alignment.BASELINE).
            addComponent(nom1) .addComponent(nomImg));
        groupeVertical.addGroup(layout.createParallelGroup(Alignment.BASELINE).
            addComponent(Box.createHorizontalStrut(200)));
        groupeVertical.addGroup(layout.createParallelGroup(Alignment.BASELINE).
            addComponent(Box.createHorizontalStrut(200)));
        groupeVertical.addGroup(layout.createParallelGroup(Alignment.BASELINE).
            addComponent(Box.createHorizontalStrut(200)));
        groupeVertical.addGroup(layout.createParallelGroup(Alignment.BASELINE).
            addComponent(Box.createHorizontalStrut(200)));
        groupeVertical.addGroup(layout.createParallelGroup(Alignment.BASELINE).
            addComponent(forme) .addComponent(Box.createHorizontalStrut(30)) .addComponent(checkForme));
        
        layout.setVerticalGroup(groupeVertical);
        
        modifier = new JButton("Modifier");
        modifier.setMinimumSize(new Dimension(150, 30));
        modifier.addActionListener(this);
        
        quitter = new JButton("Quitter");
        quitter.setMinimumSize(new Dimension(150, 30));
        quitter.addActionListener(this);
        
        panelS = new JPanel();
        panelS.setLayout(new FlowLayout());
        panelS.add(modifier);
        panelS.add(quitter);
        
        this.add(panelC, BorderLayout.CENTER);
        this.add(panelS, BorderLayout.SOUTH);
    }
    private void choisirImage()
    {
            JFileChooser choixImg = new JFileChooser();
            choixImg.setDialogType(JFileChooser.OPEN_DIALOG);
            FileFilter filtre = new FileNameExtensionFilter("Images", ImageIO.getReaderFileSuffixes());
            choixImg.addChoosableFileFilter(filtre);
            choixImg.setAcceptAllFileFilterUsed(false);
            choixImg.setDialogTitle("Sélectionner une image");

            int retour = choixImg.showOpenDialog(null);
            if(retour == JFileChooser.APPROVE_OPTION) 
            {
                try      
                {
                    File f = choixImg.getSelectedFile();
                    
                    pairImg.x = ImageIO.read(f);
                    pairImg.y = f.getName();
                    
                    nomImg.setText(pairImg.y);
                    nomImg.setToolTipText(pairImg.y);
                    
                    checkForme.setSelected(false);
                } catch (IOException ex) {}
            }
            else if(retour == JFileChooser.CANCEL_OPTION)
            {
                checkForme.setSelected(pairImg.x == null);
            }
    }
    
    private void modifierImage()
    {
        if((pairImg.x == null) && (!this.checkForme.isSelected()))
        {
            JOptionPane.showMessageDialog(null, "Veuillez choisir une représentation.",
                                            "Attention!", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            station.setImage(pairImg);
            APP.actualiser();
            this.setVisible(false);
            this.dispose();
        }
    }
}

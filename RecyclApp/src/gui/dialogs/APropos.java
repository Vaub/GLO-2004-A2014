/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.dialogs;
import gui.controls.Icone;
import java.io.InputStream;
import java.io.IOException;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
/**
 *
 * @author Proprietaire
 */
public class APropos extends JFrame{
    
    JDialog a_Propos;
    
    public APropos()
    {
        initAPropos();
    }
    
    private void initAPropos()
    {
        JButton button = new JButton("Fermer");

        a_Propos = new JDialog(this, "A propos", true);
        a_Propos.setSize(450, 400);
        a_Propos.setLocationRelativeTo(null);
        
        String path = "/images/Sparser.png";
        
        try
        {
            InputStream imgStrea = this.getClass().getResourceAsStream(path);
            BufferedImage img = ImageIO.read(imgStrea);
            a_Propos.setIconImage(img);
        }
        catch(IOException e){ System.out.println("Mauvais stream...");}
        
        
        JLabel icone = new JLabel(Icone.SPARSERI.getImage(200,170));
        icone.setHorizontalAlignment(JLabel.CENTER);
        JLabel description = new JLabel("<html><br/>Sparser© participe au développement d'applications pour le marché<br/>d'ingénierie de Québec depuis"
                + " 2014. L'application RecyclApp permet<br/> de faire le design virtuel d'un centre de tri tout en permettant à l'utilisateur "
                + "<br/> de profiter des différentes intéractions des équipements.<br/> Bon Tri ! "
                + "<br/><br/> L'équipe Sparser©<br/></html>");
        
        description.setHorizontalTextPosition(JLabel.CENTER);
        Font police = new Font("Arial", Font.ROMAN_BASELINE, 12);
        description.setFont(police);
        description.setHorizontalAlignment(JLabel.CENTER);

        
                
        JPanel des = new JPanel();
        des.add(description);
        
        JPanel bottom  = new JPanel();
        bottom.add(button);
        
        JPanel mainfenetre = new JPanel();
        mainfenetre.setLayout(new BorderLayout());
        mainfenetre.add(icone, BorderLayout.NORTH);
        mainfenetre.add(des, BorderLayout.CENTER);
        mainfenetre.add(bottom, BorderLayout.SOUTH);
        
        a_Propos.getContentPane().add(mainfenetre);

        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                a_Propos.dispose();
            }
        });

        a_Propos.setVisible(true);

    }    
    
}

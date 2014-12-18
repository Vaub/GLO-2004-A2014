/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recyclapp;

import gui.RecyclApp;
import javax.swing.SwingUtilities;

/**
 * Début du programme RecyclApp
 * 
 * @author Vincent
 */
public class Main
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        RecyclApp app = new RecyclApp();

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                app.setVisible(true);
            }
        });
    }

}

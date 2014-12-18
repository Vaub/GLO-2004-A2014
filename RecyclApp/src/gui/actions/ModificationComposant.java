/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import domaine.usine.FluxDeMatiere;
import domaine.usine.Composant;
import gui.RecyclApp;
import java.awt.Color;

/**
 *
 * @author Vincent
 */
public class ModificationComposant extends ActionRecyclApp
{
    public final Composant composant;

    public ModificationComposant(Composant _composant, RecyclApp _app)
    {
        super(_app);
        this.composant = _composant;
    }
    
    public void modifierNom(String _nom)
    {
        if (!composant.getNom().equals(_nom))
        {
            composant.setNom(_nom);
            app.actualiser();
        }
    }
    
    public void modifierCouleur(Color _couleur)
    {
        if (!composant.getCouleur().equals(_couleur))
        {
            composant.setCouleur(_couleur);
            app.actualiser();
        }
    }
}

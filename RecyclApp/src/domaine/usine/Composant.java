/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domaine.usine;

import java.awt.Color;
import java.io.Serializable;
import utils.Orientation;
import utils.Position;
import utils.Taille;

/**
 *
 * @author Vincent
 */
public abstract class Composant implements Serializable
{
    public abstract String getNom();
    public abstract Color getCouleur();
    
    public abstract FluxDeMatiere getMatieres();
    
    public abstract void setNom(String _nom);
    public abstract void setCouleur(Color _couleur);
}

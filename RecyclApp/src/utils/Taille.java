/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Dimension;
import java.io.Serializable;

/**
 *
 * @author Vincent
 */
public class Taille implements Serializable
{
    private double largeur,
                   hauteur;
    
    public Taille(double _largeur, double _hauteur)
    {
        largeur = (_largeur < 0) ? 0 : _largeur;
        hauteur = (_hauteur < 0) ? 0 : _hauteur;
    }
    
    public Taille(Taille _copie)
    {
        largeur = _copie.largeur;
        hauteur = _copie.hauteur;
    }
    
    public double largeur()
    {
        return largeur;
    }
    
    public double hauteur()
    {
        return hauteur;
    }
    
    public void setLargeur(double _largeur)
    {
        largeur = (_largeur < 0) ? 0 : _largeur;
    }
    
    public void setHauteur(double _hauteur)    
    {
        hauteur = (_hauteur < 0) ? 0 : _hauteur;
    }
    
    public Dimension dimension(double _pixelParMetre)
    {
        return new Dimension(Math.round((float)(largeur * _pixelParMetre)), 
                             Math.round((float)(hauteur * _pixelParMetre)));
    }
    
    @Override
    public boolean equals(Object obj)
    {
        boolean estEgal = false;
        
        if (obj instanceof Taille)
        {
            Taille dim2 = (Taille)obj;
            estEgal = (this.largeur == dim2.largeur) && (this.hauteur == dim2.hauteur);
        }
        
        return estEgal;
    }
    
    @Override
    public String toString()
    {
        return String.format("%f metres x %f metres", largeur, hauteur);
    }
    
    public static Taille fromDimension(Dimension _dim, double _pixelsParMetre)
    {
        return new Taille(_dim.width / _pixelsParMetre, _dim.height / _pixelsParMetre);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domaine.usine;

import java.io.Serializable;

/**
 *
 * @author Vincent
 */
public class Matiere implements Serializable
{
    protected String nom;
    
    public Matiere(String _nom)
    {
        nom = _nom.toLowerCase();
    }
    
    public String getNom()
    {
        return nom;
    }

    @Override
    public String toString()
    {
        return (nom.length() > 1) ? Character.toUpperCase(nom.charAt(0)) + nom.substring(1)
                                  : nom;
    }
    
    @Override
    public int hashCode()
    {
        return nom.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return (obj instanceof Matiere) ? hashCode() == obj.hashCode() : false;
    }
}

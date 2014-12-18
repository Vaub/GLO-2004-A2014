/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domaine.usine;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import utils.ColorUtilities;
import utils.Pair;
import utils.Position;
import utils.Taille;

/**
 *
 * @author owner
 */
public class Station extends Equipement
{
    private Entree entree;
    
    private transient Pair<Image, String> imgTransient;
    private Pair<byte[], String> img;
    
    private double capaciteMaximale;
    private Set<Matiere> listeMatieres;
    
    private MatriceTri matriceTri;
    private MatriceTransformation matriceTrans;
    
    private boolean estValide;
    
    public Station(String _nom, Position _position, Taille _taille, float capaciteMaximale,
            Set<Matiere> listeMatieres)
    {
        super(_nom, _position, _taille);
        
        imgTransient = new Pair<>(null,null);
        img = new Pair<>(null,null);
        
        this.capaciteMaximale = capaciteMaximale;
        this.listeMatieres = listeMatieres;
        
        estValide = true;
        
        entree = new Entree(this);

        entrees.add(entree);
        sorties.add(new Sortie(this));
        
        matriceTrans = new MatriceTransformation(this.listeMatieres);
        matriceTri = new MatriceTri(this.listeMatieres, sorties);
    }

    @Override
    public void calculTransit(HashSet<Equipement> _dejaVisite) 
    {
        FluxDeMatiere flux = this.getMatieres();
        _dejaVisite.add(this);
        
        estValide = flux.getQuantiteTotale() <=  this.capaciteMaximale;
        if (estValide)
        {
            matriceTri.setSorties(sorties);
            matriceTri.trierFlux(flux);

            for (Sortie s : sorties)
            {
                if (s.getMatieres().getQuantiteTotale() > 0)
                {
                    s.estValide = true;
                    
                    Equipement suivant = s.transiter();
                    if (suivant != null)
                        suivant.calculTransit(new HashSet<>(_dejaVisite));
                }
            }
        }
    }
    
    @Override
    public FluxDeMatiere getMatieres() 
    {
        return matriceTrans.transformerFlux(this.entree.getMatieres());
    } 
        
    public void ajouterMatiere(Matiere matiere)
    {
        this.listeMatieres.add(matiere);
       
        matriceTri.setMatiere(listeMatieres);
    }
    
    public void supprimerMatiere(Matiere matiere)
    {
        this.listeMatieres.remove(matiere);
        
        matriceTri.setMatiere(listeMatieres);
    }
    
    public void ajouterSortie(Sortie _sortie)
    {
        this.sorties.add(_sortie);
        matriceTri.ajouterSortie(_sortie);
    }
    
    public boolean enleverSortie(Sortie _sortie)
    {
        boolean estEnleve = false;
        if(this.sorties.contains(_sortie))
        {
            int index = this.sorties.indexOf(_sortie);
            this.sorties.get(index).enleverLiaison();
            
            this.sorties.remove(_sortie);
            matriceTri.supprimerSortie(_sortie);
            estEnleve = true;
        }
        return estEnleve;
    }
    
    public int getNumeroDeLaSortie(Sortie _sortie)
    {
        int numero = -1;

        for (int i = 0; i < sorties.size() && numero == -1; i++)
        {
            if (sorties.get(i) == _sortie)
                numero = i+1;
        }
        
        return numero;
    }
    
    public MatriceTransformation getMatriceTransformation()
    {
        return this.matriceTrans;
    }
    
    public MatriceTri getMatriceTri()
    {
        return this.matriceTri;
    }
    
    public double getCapaciteMaximale()
    {
        return capaciteMaximale;
    }
    
    public Entree getEntree()
    {
        return entree;
    }
    
    public Pair<Image,String> getImage()
    {
        if (imgTransient == null)
            imgTransient = new Pair<>(null,null);
        
        Image image = imgTransient.x;
        if (image == null && img.x != null)
        {
            imgTransient.x = ColorUtilities.OuvrirImage(this.img.x);
            image = imgTransient.x;
        }
        
        return new Pair<>(image, this.img.y);
    }
    
    public void setImage(Pair<Image,String> pair)
    {
        if (imgTransient == null)
            imgTransient = new Pair<>(null,null);
        
        byte[] image = ColorUtilities.SerialiserImage((BufferedImage)pair.x);
        
        imgTransient = pair;
        this.img = new Pair<>(image, pair.y);
    }
    
    public void ajouterSortie()
    {
        this.ajouterSortie(new Sortie(this));
    }
    
    public void enleverSortie()
    {
        if (sorties.size() > 1)
        {
            int index = sorties.size() - 1;
            
            sorties.get(index).enleverLiaison();
            sorties.remove(index);
        }
    }
    
    public void setCapaciteMaximale(double _capacite)
    {
        capaciteMaximale = (_capacite <= 0) ? capaciteMaximale : _capacite;
    }
    
    public boolean estValide()
    {
        return this.estValide;
    }
}

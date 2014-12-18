/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domaine.usine;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import utils.NumericUtilities;

/**
 *
 * @author owner
 */
public class MatriceTri implements Serializable
{

    private HashMap<Matiere, HashMap<Sortie, Double>> matriceTri;
    private ArrayList<Sortie> listeSorties;

    public MatriceTri(Set<Matiere> listeMatieres, ArrayList<Sortie> listeSorties)
    {
        matriceTri = new HashMap<>();
        this.listeSorties = new ArrayList<>(listeSorties);

        //Initialiser de matriceTri
        Iterator<Matiere> itr = listeMatieres.iterator();
        while (itr.hasNext())
        {
            Matiere temp = itr.next();

            HashMap<Sortie, Double> sortie = new HashMap<>();
            for (Sortie s : this.listeSorties)
            {
                sortie.put(s, 0d);
            }
            matriceTri.put(temp, sortie);
            matriceTri.get(temp).put(this.listeSorties.get(0), 1d); //100% des matières à la première sortie
        }
    }

    public void trierFlux(FluxDeMatiere flux)
    {

        for (Sortie s : listeSorties)
        {
            s.setMatieres(new FluxDeMatiere());
        }

        for (Entry<Matiere, Double> f : flux.getMatiere().entrySet())
        {
            if (!matriceTri.containsKey(f.getKey()))
            {
                HashSet<Matiere> matieres = new HashSet<>(matriceTri.keySet());
                matieres.add(f.getKey());

                this.setMatiere(matieres);
                this.validerMatriceTri(null);
            }

            HashMap<Sortie, Double> liste = matriceTri.get(f.getKey());

            for (Entry<Sortie, Double> s : liste.entrySet())
            {
                s.getKey().ajouterMatiere(f.getKey(), (f.getValue() * s.getValue()));
            }
        }
    }
    
    public boolean validerMatriceTri(Sortie _aIgnorer)
    {
        return this.validerMatriceTri(_aIgnorer, null);
    }
    
    public boolean validerMatriceTri(Sortie _aIgnorer, Matiere _matiere)
    {
        double total,
               moyenne;
        
        boolean retour = false;
        
        for (Entry<Matiere, HashMap<Sortie, Double>> c : matriceTri.entrySet()) //Pour chaque matière
        {
            if (_matiere != null &&
                !c.getKey().equals(_matiere))
                continue;
            
            total = 0f;
            moyenne = 0f;
            
            Map<Sortie, Double> map = matriceTri.get(c.getKey());
            Deque<Sortie> sorties = new ArrayDeque<>();
            
            for (Entry<Sortie, Double> e : map.entrySet())
            {
                if (e.getKey() != _aIgnorer)
                    sorties.add(e.getKey());
                
                total += e.getValue();
            }
            
            total = NumericUtilities.round(total, 5);
            
            Sortie s1 = this.listeSorties.get(0);
            
            if (total < 1d)
            {
                double ajout = NumericUtilities.round(1d - total, 5);
                
                if (_aIgnorer != s1) // On ajoute tout ce qu'on peut à la sortie 1
                {
                    double original = map.get(s1);
                    double valeur = this.getResteAjout(1d - original, ajout);
                    map.put(s1, valeur);
                    
                    ajout = NumericUtilities.round(ajout - (valeur - original), 5);
                }
                
                while (ajout > 0 && !sorties.isEmpty()) // On fill les autres sauf la s1 et celle sélectionnée
                {
                    Sortie sortie = sorties.pop();
                    if (sortie == s1 || _aIgnorer == sortie) continue;
                    
                    double original = map.get(sortie);
                    double valeur = this.getResteAjout(1d - original, ajout);
                    map.put(sortie, valeur);
                    
                    ajout = NumericUtilities.round(ajout - (valeur - original), 5);
                }
                
                if (ajout > 0) // Si il en reste : free for all
                {
                    for (Sortie sortie : this.listeSorties)
                    {
                        double original = map.get(sortie);
                        double valeur = this.getResteAjout(1d - original, ajout);
                        map.put(sortie, valeur);
                        
                        ajout = NumericUtilities.round(ajout - (valeur - original), 5);
                    }
                }
            }
            else if (total > 1d)
            {
                double retrait = NumericUtilities.round(total - 1d, 5);
                
                if (_aIgnorer != s1)
                {
                    double original = map.get(s1);
                    double valeur = getResteRetrait(original, retrait);
                    
                    map.put(s1, valeur);
                    retrait = NumericUtilities.round(retrait - (original - valeur), 5);
                }
                
                while (retrait > 0 && !sorties.isEmpty())
                {
                    Sortie sortie = sorties.pop();
                    if (sortie == s1 || _aIgnorer == sortie) continue;
                    
                    double original = map.get(sortie);
                    double valeur = getResteRetrait(original, retrait);
                    
                    map.put(sortie, valeur);
                    retrait = NumericUtilities.round(retrait - (original - valeur), 5);
                }
                
                if (retrait > 0)
                {
                    for (Sortie sortie : this.listeSorties)
                    {
                        double original = map.get(sortie);
                        double valeur = getResteRetrait(original, retrait);
                    
                        map.put(sortie, valeur);
                        retrait = NumericUtilities.round(retrait - (original - valeur), 5);
                    }
                }
            }
            else
                retour = true;
        }

        return retour;
    }
    
    private double getResteAjout(double _capacite, double _ajout)
    {
        double aAjouter = (_ajout > _capacite) ? _capacite
                                               : _ajout;
        
        return NumericUtilities.round((1d - _capacite) + aAjouter, 5);
    }
    
    private double getResteRetrait(double _capacite, double _retrait)
    {
        double aEnlever = (_retrait > _capacite) ? _capacite
                                                 : _retrait;
        
        return NumericUtilities.round(_capacite - aEnlever, 5);
    }

    /**
     * Mettre à jour la matrice selon le hashset passé en paramètre d'entrée.
     *
     * @param listeMatiere Liste de matières
     */
    public void setMatiere(Set<Matiere> listeMatiere)
    {
        HashMap<Matiere, HashMap<Sortie, Double>> matriceTemp = new HashMap<>();

        Iterator<Matiere> itr = listeMatiere.iterator();
        while (itr.hasNext())
        {
            Matiere temp = itr.next();
            if (matriceTri.containsKey(temp))
            {
                matriceTemp.put(temp, matriceTri.get(temp));
            } else
            {
                HashMap<Sortie, Double> sortie = new HashMap<>();
                for (Sortie s : this.listeSorties)
                {
                    sortie.put(s, (s == listeSorties.get(0)) ? 1d : 0d);
                }
                matriceTemp.put(temp, sortie);
            }
        }
        matriceTri = matriceTemp;
    }

    public void setSorties(ArrayList<Sortie> _sorties)
    {
        ArrayList<Sortie> sortiesOriginales = new ArrayList<>(this.listeSorties);
        for (Sortie s : sortiesOriginales)
        {
            if (!_sorties.contains(s))
            {
                this.supprimerSortie(s);
            }
        }

        for (Sortie s : _sorties)
        {
            this.ajouterSortie(s);
        }
    }

    /**
     * Ajouter la sortie passée en paramètre d'entrée.
     *
     * @param sortieAAjouter Sortie à ajouter.
     * @return Vrai si la sortie a été ajoutée. Faux sinon.
     */
    public boolean ajouterSortie(Sortie sortieAAjouter)
    {
        boolean estAjouter = false;

        if (!listeSorties.contains(sortieAAjouter))
        {
            for (Entry<Matiere, HashMap<Sortie, Double>> courant : matriceTri.entrySet())
            {
                matriceTri.get(courant.getKey()).put(sortieAAjouter, 0d); //Valeur 0 par défaut
            }
            listeSorties.add(sortieAAjouter);
            estAjouter = true;

            validerMatriceTri(null);
        }
        return estAjouter;
    }

    /**
     * Supprimer la sortie spécifiée de la matrice.
     *
     * @param sortieAEnlever Sortie à supprimer.
     * @return Vrai si la matrice a été enlevée.
     */
    public boolean supprimerSortie(Sortie sortieAEnlever)
    {
        boolean estEnleve = false;

        if (listeSorties.contains(sortieAEnlever) && (listeSorties.size() >= 1))
        {
            for (Entry<Matiere, HashMap<Sortie, Double>> courant : matriceTri.entrySet())
            {
                matriceTri.get(courant.getKey()).remove(sortieAEnlever);
            }
            listeSorties.remove(sortieAEnlever);
            estEnleve = true;
        }
        return estEnleve;
    }

    /**
     * Modifier un pourcentage dans la matrice de tri.
     *
     * @param s Sortie dont le pourcentage soit être modifié.
     * @param m Matière
     * @param pourcentage Nouvelle quantité
     * @return Vrai si la modification a été effectuée. Faux sinon.
     */
    public boolean modifierQuantite(Sortie s, Matiere m, Double pourcentage)
    {
        boolean estModifie = false;
        pourcentage = NumericUtilities.round(pourcentage, 5);

        if (listeSorties.contains(s) && (pourcentage >= 0) && (pourcentage <= 1))
        {
            matriceTri.get(m).put(s, pourcentage);
            this.validerMatriceTri(s, m);

            estModifie = true;
        }
        return estModifie;
    }

    public double getQuantite(Sortie s, Matiere m)
    {
        double quantite = 0f;

        if (matriceTri.containsKey(m)
                && matriceTri.get(m).containsKey(s))
        {
            quantite = matriceTri.get(m).get(s);
        }

        return quantite;
    }

    public ArrayList<Sortie> getListeSorties()
    {
        return listeSorties;
    }

    public HashMap<Matiere, HashMap<Sortie, Double>> getMatriceTri()
    {
        return matriceTri;
    }
}

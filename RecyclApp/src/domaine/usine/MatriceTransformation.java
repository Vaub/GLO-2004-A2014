/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domaine.usine;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import utils.NumericUtilities;

/**
 *
 * @author owner
 */
public class MatriceTransformation implements Serializable
{
    private HashMap<Matiere, HashMap<Matiere, Float>> matrice;
    private HashSet<Matiere> listeMatieresTrans;
    
    public MatriceTransformation(Set<Matiere> listeMatieres)
    {
        matrice = new HashMap<>();
        listeMatieresTrans = new HashSet<>();
        
        Iterator<Matiere> itr = listeMatieres.iterator();
        while (itr.hasNext())
        {
            ajouterProduitATransformer(itr.next());
        }
    }
    
    //Flux à transformer selon la matrice
    public FluxDeMatiere transformerFlux(FluxDeMatiere flux)
    {
        FluxDeMatiere f = new FluxDeMatiere();
        
        for(Entry<Matiere, Double> m : flux.getMatiere().entrySet())
        {
            if(matrice.containsKey(m.getKey()))
            {
                ajouterFlux(m.getKey(), m.getValue(), f);
            }
            else
            {
                f.setMatiere(m.getKey(), m.getValue()); //Si la matière n'est pas dans la matrice
            }
        }
        return f;
    }
    
    public boolean ajouterTransformation(Matiere ligne, Matiere colonne, float pourcentage)
    {
        boolean estModifie = false;

        pourcentage = NumericUtilities.roundFloat(pourcentage, 5);
        if((pourcentage <= 1) && (pourcentage >= 0))
        {
            if(!matrice.containsKey(ligne))
            {
                ajouterProduitATransformer(ligne);
            }

            if(!listeMatieresTrans.contains(colonne))
            {
                ajouterProduitTrans(colonne);
            }
            
            matrice.get(ligne).put(colonne, 0f);
            
            //Traiter le pourcentage...
            if((getPourcentageLigne(ligne) + pourcentage) <= 1)
            {
                matrice.get(ligne).put(colonne, NumericUtilities.roundFloat(pourcentage, 5));
                estModifie = true;
                
                if(getPourcentageLigne(ligne) != 1f)
                {
                    float restant = 1f - getPourcentageLigne(ligne);

                    try
                    {
                       float pourcentageCourant = matrice.get(ligne).get(ligne);
                       matrice.get(ligne).put(ligne, NumericUtilities.roundFloat(pourcentageCourant+restant, 5));
                    } catch(Exception ex){}  
                }
            }
            else
            {
                float aSupprimer = getPourcentageLigne(ligne) + pourcentage - 1;
                for (Entry<Matiere, Float> m : matrice.get(ligne).entrySet())
                {
                    if (!m.getKey().equals(colonne) && aSupprimer > 0)
                    {
                        float valeur = (m.getValue() <= aSupprimer) ? m.getValue() : aSupprimer;
                        matrice.get(ligne).put(m.getKey(), NumericUtilities.roundFloat(m.getValue() - valeur, 5));
                        
                        aSupprimer -= valeur;
                    }
                }
                
                matrice.get(ligne).put(colonne, NumericUtilities.roundFloat(pourcentage, 5));
                estModifie = true;
            }
        }
        return estModifie;
    }
    
    public boolean validerMatriceTransformation()
    {
        boolean ok;
        for(Entry<Matiere, HashMap<Matiere, Float>> m : matrice.entrySet())
        {
            ok = (getPourcentageLigne(m.getKey()) <= 1);
            if(!ok)
            {
                return false;
            }
        }
        return true;
    }
    
    public void ajouterProduitTrans(Matiere matiere)
    {
        if(!listeMatieresTrans.contains(matiere))
        {
            for(Entry<Matiere, HashMap<Matiere, Float>> m : matrice.entrySet())
            {
                matrice.get(m.getKey()).put(matiere, (float) 0); 
            }
            listeMatieresTrans.add(matiere);
        }
    }
    
    public void enleverProduitTrans(Matiere matiere)
    {
        if(listeMatieresTrans.contains(matiere))
        {
            for(Entry<Matiere, HashMap<Matiere, Float>> m : matrice.entrySet())
            {
                matrice.get(m.getKey()).remove(matiere);
            }
            listeMatieresTrans.remove(matiere);
        }
    }
    
    public void ajouterProduitATransformer(Matiere matiere)
    {
        if(!matrice.containsKey(matiere))
        {
            HashMap<Matiere, Float> temp = new HashMap<>();
            Iterator<Matiere> itr = listeMatieresTrans.iterator();
            
            while(itr.hasNext()) 
            {
                temp.put(itr.next(), (float) 0);
            }
            
            matrice.put(matiere, temp);
            ajouterProduitTrans(matiere);

            matrice.get(matiere).put(matiere, (float) 1); //diagonalisation
        }
    }
    
    public void enleverProduitATransformer(Matiere matiere)
    {
        if(matrice.containsKey(matiere))
        {
            matrice.remove(matiere);
        }
    }
    
    public float getPourcentage(Matiere ligne, Matiere colonne)
    {
        float pourcentage = 0f;
        
        if(matrice.containsKey(ligne) && matrice.get(ligne).containsKey(colonne))
        {
            pourcentage = matrice.get(ligne).get(colonne);
        }
        
        return pourcentage;
    }
    
    public HashMap<Matiere, HashMap<Matiere, Float>> getMatriceTransformation()
    {
        return matrice;
    }
    
    private void ajouterFlux(Matiere m, Double qte, FluxDeMatiere f)
    {
        float pourcentageRestant = 1;
        
        HashMap<Matiere, Float> temp = matrice.get(m); //Aller chercher la ligne
        for(Entry<Matiere, Float> c : temp.entrySet())
        {
            if(c.getValue() != 0) //Si le pourcentage de transformation n'est pas à 0
            {
                double valeur = (!f.getMatiere().containsKey(c.getKey())) ? 
                        (c.getValue()*qte) : (f.getMatiere().get(c.getKey()) + (c.getValue()*qte));
                f.setMatiere(c.getKey(), valeur);
            }
        }
        pourcentageRestant -=  getPourcentageLigne(m);   //Qte non transformée
        
        if(pourcentageRestant != 0) //Vérifier si 100% de la matière a été transformée
        {
            double valeurAAjouter = (f.getMatiere().containsKey(m)) ?
                    (f.getMatiere().get(m) + (pourcentageRestant*qte)) : (pourcentageRestant*qte);
            f.setMatiere(m, valeurAAjouter);
        }
    }
    
    private float getPourcentageLigne(Matiere matiere)
    {
        float pourcentage = 0;
        if(matrice.containsKey(matiere))
        {
            HashMap<Matiere, Float> m = matrice.get(matiere);
            for(Entry<Matiere, Float> temp : m.entrySet())
            {
                pourcentage += m.get(temp.getKey());
            }
        }
        return pourcentage;
    }
}

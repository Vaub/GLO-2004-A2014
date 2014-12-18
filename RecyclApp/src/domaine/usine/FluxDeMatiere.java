/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domaine.usine;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;
import utils.NumericUtilities;

/**
 *
 * @author Vincent
 */
public class FluxDeMatiere implements Serializable
{
    protected HashMap<Matiere, Double> flux;
    
    public FluxDeMatiere()
    {
        flux = new HashMap<>();
    }
    
    public FluxDeMatiere(FluxDeMatiere _copie)
    {
        flux = new HashMap<>(_copie.flux);
    }
    
    public HashMap<Matiere, Double> getMatiere()
    {
        return new HashMap<>(flux);
    }
    
    public void combinerFlux(FluxDeMatiere _flux)
    {
        for (Entry<Matiere, Double> e : _flux.getMatiere().entrySet())
            this.changerMatiere(e.getKey(), e.getValue());
    }
    
    public void setMatiere(Matiere _matiere, double _quantite)
    {
        _quantite = (_quantite < 0) ? 0 
                                    : NumericUtilities.round(_quantite, 5);
        
        if (_quantite == 0)
            flux.remove(_matiere);
        else
            flux.put(_matiere, _quantite);
    }
    
    public void changerMatiere(Matiere _matiere, double _quantite)
    {   
        if (flux.containsKey(_matiere))
        {
            double total = NumericUtilities.round(flux.get(_matiere) + _quantite, 5);
            
            if (total > 0)
                flux.put(_matiere, total);
            else
                flux.remove(_matiere);
        }
        else if (_quantite > 0)
            flux.put(_matiere, _quantite);
    }
    
    public void enleverMatiere(Matiere _matiere)
    {
        flux.remove(_matiere);
    }
    
    public double getQuantiteMatiere(Matiere _matiere)
    {
        return (flux.containsKey(_matiere)) ? flux.get(_matiere) : 0;
    }
    
    public void supprimerTout()
    {
        flux.clear();
    }
    
    public double getQuantiteTotale()
    {
        double quantiteTotale = 0;
        for (double qte : flux.values())
            quantiteTotale += qte;
        
        return NumericUtilities.round(quantiteTotale, 5);
    }
    
}

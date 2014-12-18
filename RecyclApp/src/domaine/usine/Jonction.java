package domaine.usine;

import java.util.HashSet;
import utils.Orientation;
import utils.Position;
import utils.Taille;

/**
 *
 * @author Olivier
 */
public class Jonction extends Equipement {
    
    protected Sortie sortie;
    
    public Jonction(Position _position) 
    {
        this(_position, 2);
    }
    
    public Jonction(Position _position, int _nbEntrees)
    {
        super("Jonction", _position, new Taille(1,1));
        
        _nbEntrees = (_nbEntrees <= 0) ? 1 : _nbEntrees;
        
        sortie = new Sortie(this);
        this.sorties.add(sortie);
        
        
        for (int i = 1; i <= _nbEntrees; i++)
            this.ajouterEntree();
    }
    
    public void ajouterEntree()
    {
        this.ajouterEntree(new Entree(this));
    }
    
    public void ajouterEntree(Entree _entree) {
        if(_entree.parent == this) {
           this.entrees.add(_entree);
        } else {
           Entree nouvelleEntree = new Entree(this);
           
           if (_entree.estReliee()) {
                nouvelleEntree.relierA(_entree.relierA);
                _entree.relierA.relierA(nouvelleEntree);
           }
           
           nouvelleEntree.setMatieres(_entree.getMatieres());
           this.entrees.add(nouvelleEntree);
        }
    }
    
    public void enleverEntree()
    {
        if (entrees.size() > 1)
        {
            int index = entrees.size() - 1;
            
            this.entrees.get(index).enleverLiaison();
            this.entrees.remove(index);
        }
    }
    
    public void enleverEntree(int _index) {
        if (_index < entrees.size() && _index > 1)
        {
            this.entrees.get(_index).enleverLiaison();
            this.entrees.remove(_index);
        }
    }
    
    public void setSortie(Sortie _sortie) {
        Sortie nouvelleSortie = new Sortie(this);
        
        
        if (_sortie.estReliee()) {
            nouvelleSortie.relierA( _sortie.relierA);
            _sortie.relierA.relierA(nouvelleSortie);
        }
        
        
        
        nouvelleSortie.setMatieres(_sortie.getMatieres());
        sorties.clear();
        sorties.add(nouvelleSortie);
    }
    
    public Sortie getSortie() {
        return this.sorties.get(0);
    }

    @Override
    public FluxDeMatiere getMatieres()
    {
        FluxDeMatiere flux = new FluxDeMatiere();
        for (Entree e : entrees)
            flux.combinerFlux(e.getMatieres());
        
        return flux;
    }
    
    @Override
    public void calculTransit(HashSet<Equipement> _dejaVisite) 
    {
        if (!_dejaVisite.contains(this))
        {
            _dejaVisite.add(this);
            sortie.setMatieres(this.getMatieres());

            Equipement suivant = sortie.transiter();
            if (suivant != null)
                suivant.calculTransit(_dejaVisite);
        }
        else
            sortie.setValide(false);
    }

    @Override
    public Position getPositionEntree(Entree _entree)
    {
        Position pos = null;

        for (int i = 0; i < entrees.size() && pos == null; i++)
        {
            pos = (entrees.get(i) == _entree) ? getPositionIO(i + 1, entrees.size(), orientationEntrees, false) 
                                              : pos;
        }
        
        return pos;
    }

    @Override
    public void clearMatiere()
    {
        sortie.setValide(true);
        super.clearMatiere(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domaine.controle;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import domaine.usine.Convoyeur;
import domaine.usine.EntreeUsine;
import domaine.usine.Equipement;
import domaine.usine.FluxDeMatiere;
import domaine.usine.IO;
import domaine.usine.Matiere;
import ressources.Parametres;
import utils.Pair;
import utils.Position;
import utils.Taille;

/**
 *
 * @author Vincent
 */
public class Controleur
{
    private Schema schemaSelectionne;
    private HashMap<Schema, Pair<ArrayDeque<byte[]>, ArrayDeque<byte[]>>> schemas;
    
    public Controleur()
    {
        schemaSelectionne = null;
        schemas = new HashMap<>();
    }
    
    public void renommerSchema(String _nom)
    {
        if (schemaSelectionne != null)
            schemaSelectionne.setNom(_nom);
    }
    
    public void changerPixelsParMetre(int _pixelParMetre)
    {
        if (schemaSelectionne != null)
            schemaSelectionne.setPixelsParMetre(_pixelParMetre);
    }
    
    public void actualiserSchema()
    {
        if (schemaSelectionne != null)
            schemaSelectionne.calculDuSchema();
    }
    
    public void ajouterMatiere(Matiere _matiere)
    {
        if (schemaSelectionne != null)
            schemaSelectionne.ajouterMatiere(_matiere);
    }
    
    public void enleverMatiere(Matiere _matiere)
    {
        if (schemaSelectionne != null)
            schemaSelectionne.enleverMatiere(_matiere);
    }
    
    public boolean positionValide(Equipement _valider)
    {
        boolean valide = true;

        if (schemaSelectionne != null)
            valide = schemaSelectionne.validerElement(_valider);
        
        return valide;
    }
    
    public boolean deplacerEquipement(Equipement _equip, Position _position)
    {
        return (schemaSelectionne != null) ? schemaSelectionne.deplacerEquipement(_equip, _position) 
                                           : false;
        
    }
    
    public boolean redimensionnerEquipement(Equipement _equip, Taille _taille)
    {
        return (schemaSelectionne != null) ? schemaSelectionne.redimensionnerEquipement(_equip, _taille) 
                                           : false;
    }
   
    public boolean relierEquipements(IO _de, IO _a)
    {
        boolean liaison = false;
        
        if (schemaSelectionne != null &&
            _de != null && _a != null)
        {
            this.sauvegarderUndo();
            
            Set<Equipement> equipements = schemaSelectionne.getElements();
            if (equipements.contains(_de.parent) &&
                equipements.contains(_a.parent))
            {
                liaison = _de.relierA(_a);
            }
            
            schemaSelectionne.calculDuSchema();
        }
        
        return liaison;
    }
    
    public Equipement selectionnerEquipement(Position _pos)
    {
        Equipement selection = null;
        
        if (schemaSelectionne != null)
            selection = schemaSelectionne.selectionnerEquipementAPosition(_pos, 0);
        
        return selection;
    }
    
    public Equipement selectionnerEquipementAvecMarge(Position _pos, double _marge)
    {
        Equipement selection = null;
        
        if (schemaSelectionne != null)
            selection = schemaSelectionne.selectionnerEquipementAPosition(_pos, _marge);
        
        return selection;
    }
    
    public Set<Equipement> selectionnerDesEquipement(Position _pos, Taille _taille)
    {
        Set<Equipement> selection = null;
        
        if (schemaSelectionne != null)
            selection = schemaSelectionne.selectionnerEquipements(_pos, _taille);
        
        return selection;
    }
    
    public HashSet<Equipement> listerEquipements()
    {
        HashSet<Equipement> equipements = new HashSet<>();
        
        if (schemaSelectionne != null)
            equipements = new HashSet<>(schemaSelectionne.getElements());
        
        return equipements;
    }
    
    public boolean ajouterEquipement(Equipement _equip)
    {
        boolean estAjoute = false;
        
        if (schemaSelectionne != null)
            estAjoute = schemaSelectionne.ajouterElement(_equip);

        return estAjoute;
    }
    
    public boolean supprimerEquipement(Equipement _equip)
    {
        return (schemaSelectionne != null) ? schemaSelectionne.supprimerEquipement(_equip) : false; 
    }
    
    public IO getIO(Position _position)
    {
        return (schemaSelectionne != null) ? schemaSelectionne.selectionnerIOAPosition(_position) : null;
    }
    
    public Convoyeur selectionnerConvoyeur(Position _position)
    {
        return (schemaSelectionne != null) ? schemaSelectionne.selectionnerConvoyeurAPos(_position) : null;
    }
    
    public Taille getTailleSchema()
    {
        return (schemaSelectionne != null) ? schemaSelectionne.tailleMaximale() 
                                     : new Taille(1,1);
    }
    
    public float getPixelsParMetres()
    {
        return (schemaSelectionne != null) ? schemaSelectionne.getPixelsParMetre() 
                                           : 80;
    }
    
    public FluxDeMatiere getMatieresEntrees()
    {
        return (schemaSelectionne != null) ? schemaSelectionne.getMatieresAEntree()
                                           : new FluxDeMatiere();
    }
    
    public Set<Matiere> getMatieresSchema()
    {
        return (schemaSelectionne != null) ? schemaSelectionne.getMatieres()
                                           : new HashSet<>();
    }
    
    public void changerMatiereEntree(EntreeUsine entree, FluxDeMatiere _flux)
    {
        if (schemaSelectionne != null)
            schemaSelectionne.changerMatiereEntree(entree, _flux);
    }
    
    public boolean selectionner(Schema _schema)
    {
        boolean estSelectionne = true;
        
        if (schemas.containsKey(_schema))
            schemaSelectionne = _schema;
        else
            estSelectionne = false;
        
        return estSelectionne;
    }
    
    public void undo()
    {
        if (schemaSelectionne != null && schemas.containsKey(schemaSelectionne) &&
            !schemas.get(schemaSelectionne).x.isEmpty())
        {
            try
            {
                sauvegarderRedo();
                Schema aAjoute = Schema.uncompressSchema(schemas.get(schemaSelectionne).x.pop());

                schemas.put(aAjoute, schemas.get(schemaSelectionne));
                schemas.remove(schemaSelectionne);
                
                schemaSelectionne = aAjoute;
            } catch (Exception e) { }
        }
    }
    
    public void redo()
    {
        if (schemaSelectionne != null && schemas.containsKey(schemaSelectionne) &&
            !schemas.get(schemaSelectionne).y.isEmpty())
        {
            try
            {
                sauvegarderUndo();
                Schema aAjoute = Schema.uncompressSchema(schemas.get(schemaSelectionne).y.pop());

                schemas.put(aAjoute, schemas.get(schemaSelectionne));
                schemas.remove(schemaSelectionne);
                
                schemaSelectionne = aAjoute;
            } catch (Exception e) { }
    }
        }
    
    public void sauvegarderUndo()
    {
        if (schemaSelectionne != null && schemas.containsKey(schemaSelectionne))
        {
            ArrayDeque<byte[]> undo = schemas.get(schemaSelectionne).x;
            
            try
            {
                undo.push(Schema.compressSchema(schemaSelectionne));

                if (undo.size() > Parametres.getEntier(Parametres.MAX_UNDO_REDO))
                    undo.pollLast();
                
            } catch (Exception e) { }
        }
    }
    
    public void sauvegarderRedo()
    {
        if (schemaSelectionne != null && schemas.containsKey(schemaSelectionne))
        {
            ArrayDeque<byte[]> redo = schemas.get(schemaSelectionne).y;
            
            try
            {
                redo.push(Schema.compressSchema(schemaSelectionne));

                if (redo.size() > Parametres.getEntier(Parametres.MAX_UNDO_REDO))
                    redo.pollLast();
                
            } 
            catch (Exception e) 
            { 
                System.err.println(e.getStackTrace()); 
            }
        }
    }
    
    public Schema getSchema()
    {
        return schemaSelectionne;
    }
    
    public Set<Schema> getSchemas()
    {
        return Collections.unmodifiableSet(schemas.keySet());
    }
    
    public boolean ajouterUnSchema(Schema _schema)
    {
        boolean estCreer = false;
        
        if (!schemas.containsKey(_schema))
        {
            schemas.put(_schema, new Pair<>(new ArrayDeque<>(), new ArrayDeque<>()));
            selectionner(_schema);
            
            estCreer = true;
        }
        
        return estCreer;
    }
    
    public boolean supprimerUnSchema(Schema _schema)
    {
        boolean estSupprime = false;
        
        if (schemas.containsKey(_schema))
        {
            schemas.remove(_schema);
            
            if (!schemas.isEmpty())
                schemaSelectionne = schemas.entrySet().iterator().next().getKey();
            else
                schemaSelectionne = null;
        }
        
        return estSupprime;
    }
    
    
    public void equipementModifie(Equipement _equip)
    {
        if (schemaSelectionne != null &&
            schemaSelectionne.getElements().contains(_equip))
        {
            schemaSelectionne.calculDuSchema();
        }
    }
    
    public boolean undoEstVide()
    {
        return (schemaSelectionne != null) ? schemas.get(schemaSelectionne).x.isEmpty() : true;
    }
    
    public boolean redoEstVide()
    {
        return (schemaSelectionne != null) ? schemas.get(schemaSelectionne).y.isEmpty() : true;
    }
    
    public void enleverLiaisonConvoyeur(Convoyeur _convoyeur)
    {
        if (_convoyeur != null)
        {
            this.sauvegarderUndo();
            _convoyeur.enleverLiaison();
        }
    }
    
    public Position getPointFlexionConvoyeur(Convoyeur _convoyeur, Position _point)
    {
        Position position = null;
        if (_convoyeur != null && _point != null)
            position = _convoyeur.getPointInflexion(_point);
        
        return position;
    }
    
    public void ajouterPointFlexionConvoyeur(Convoyeur _convoyeur, Position _point)
    {
        if (_convoyeur != null && _point != null)
        {
            this.sauvegarderUndo();
            _convoyeur.ajouterPoint(_point);
        }
    }
    
    public void enleverPointFlexionConvoyeur(Convoyeur _convoyeur, Position _point)
    {
        if (_convoyeur != null && _point != null)
        {
            this.sauvegarderUndo();
            _convoyeur.enleverPoint(_point);
        }
    }
}

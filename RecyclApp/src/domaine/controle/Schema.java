/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domaine.controle;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import domaine.usine.Convoyeur;
import domaine.usine.Entree;
import domaine.usine.EntreeUsine;
import domaine.usine.Equipement;
import domaine.usine.FluxDeMatiere;
import domaine.usine.IO;
import domaine.usine.Matiere;
import domaine.usine.Sortie;
import domaine.usine.SortieUsine;
import domaine.usine.Station;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayDeque;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import utils.NumericUtilities;
import utils.Position;
import utils.Taille;

/**
 *
 * @author Vincent
 */
public class Schema implements Serializable
{
    private String nom;
    
    protected HashSet<Matiere> matieres;
    protected HashSet<Equipement> elements;
    
    protected int pixelsParMetre;
    
    public Schema(String _nom)
    {
        nom = _nom;
        
        elements = new HashSet<>();
        matieres = new HashSet<>();
        
        elements.add(new EntreeUsine(new Position(1,1), new Taille(1,1)));
        elements.add(new SortieUsine(new Position(8,8), new Taille(1,1)));
        
        matieres.add(new Matiere("Aluminium"));
        matieres.add(new Matiere("Bois"));
        matieres.add(new Matiere("Papier"));
        matieres.add(new Matiere("Fer"));
        
        pixelsParMetre = 50;
    }
    
    public void calculDuSchema()
    {
        ArrayDeque<EntreeUsine> entrees = new ArrayDeque<>();
        
        for (Equipement e : elements)
        {
            e.clearMatiere();
            if (e instanceof EntreeUsine)
                entrees.add((EntreeUsine)e);
        }
        
        while (!entrees.isEmpty())
            entrees.poll().calculTransit(new HashSet<>());
    }
    
    public boolean ajouterElement(Equipement _equipement)
    {
        boolean estAjoute = true;
        
        if (validerElement(_equipement) &&
            !elements.contains(_equipement))
        {
            elements.add(_equipement);
        }
        else
            estAjoute = false;
        
        return estAjoute;
    }
    
    public void changerMatiereEntree(EntreeUsine entree, FluxDeMatiere _flux)
    {
        if (elements.contains(entree))
        {
            entree.setMatieres(_flux);
            calculDuSchema();
        }
    }
    
    public boolean validerElement(Equipement _equipement)
    {
        boolean valider = true;
        
        Taille aDim = _equipement.getTaille();
        
        Position aP1 = _equipement.getPosition();
        Position aP2 = new Position(aP1.x() + aDim.largeur(), aP1.y() + aDim.hauteur());
        
        Iterator<Equipement> itr = elements.iterator();
        while (itr.hasNext() && valider)
        {
            Equipement e = itr.next();
            
            if (e != _equipement)
            {
                Taille eDim = e.getTaille();
                Position eP1 = e.getPosition();
                Position eP2 = new Position(eP1.x() + eDim.largeur(), eP1.y() + eDim.hauteur());

                if (aP1.x() < eP2.x() && aP2.x() > eP1.x() &&
                    aP1.y() < eP2.y() && aP2.y() > eP1.y())
                {
                    valider = false;
                }
            }
        }
        
        return valider;
    }
    
    public boolean supprimerEquipement(Equipement _equip)
    {
        boolean estSupprime = false;
        
        if (_equip != null && elements.contains(_equip))
        {
            elements.remove(_equip);
            
            for (Entree e : _equip.getEntrees())
                e.enleverLiaison();
            
            for (Sortie s : _equip.getSorties())
                s.enleverLiaison();
            
            estSupprime = true;
            this.calculDuSchema();
        }

        return estSupprime;
    }
    
    public Equipement selectionnerEquipementAPosition(Position _pos, double _marge)
    {
        Equipement equipementTrouve = null;
        
        Iterator<Equipement> itr = elements.iterator();
        while (itr.hasNext() && equipementTrouve == null)
        {
            Equipement e = itr.next();
            
            Taille eDim = new Taille(e.getTaille().largeur() + _marge, 
                                     e.getTaille().hauteur() + _marge);
            
            Position ePos = new Position(e.getPosition().x() - (_marge/2d),
                                         e.getPosition().y() - (_marge/2d));
            
            
            if (ePos.x() <= _pos.x() && ePos.x() + eDim.largeur() >= _pos.x() &&
                ePos.y() <= _pos.y() && ePos.y() + eDim.hauteur() >= _pos.y())
            {
                equipementTrouve = e;
            }
        }
        
        return equipementTrouve;
    }
    
    public Set<Equipement> selectionnerEquipements(Position _pos, Taille _taille)
    {
        Set<Equipement> equipementsTrouves = new HashSet<>();
        
        double zoneX1 = _pos.x(),
               zoneX2 = _pos.x() + _taille.largeur(),
               zoneY1 = _pos.y(),
               zoneY2 = _pos.y() + _taille.hauteur();
        
        for (Equipement e : elements)
        {
           double eX1 = e.getPosition().x(),
                  eX2 = e.getPosition().x() + e.getTaille().largeur(),
                  eY1 = e.getPosition().y(),
                  eY2 = e.getPosition().y() + e.getTaille().hauteur();
           
           if (eX1 < zoneX2 && eX2 > zoneX1 &&
               eY1 < zoneY2 && eY2 > zoneY1)
               equipementsTrouves.add(e);
        }
        
        return equipementsTrouves;
    }
    
    public IO selectionnerIOAPosition(Position _pos)
    {
        IO ioSelectionne = null;
        
        Iterator<Equipement> itr = elements.iterator();
        while (itr.hasNext() && ioSelectionne == null)
        {
            Equipement e = itr.next();
            
            Iterator<Entree> eItr = e.getEntrees().iterator();
            while (eItr.hasNext() && ioSelectionne == null)
            {
                Entree entree = eItr.next();
                ioSelectionne = (entree.estDessus(_pos)) ? entree : null;
            }
            
            Iterator<Sortie> sItr = e.getSorties().iterator();
            while (sItr.hasNext() && ioSelectionne == null)
            {
                Sortie sortie = sItr.next();
                ioSelectionne = (sortie.estDessus(_pos)) ? sortie : null;
            }
        }
        
        return ioSelectionne;
    }
    
    public Convoyeur selectionnerConvoyeurAPos(Position _pos)
    {
        Convoyeur convoyeurSelectionne = null;
        
        Iterator<Equipement> itr = elements.iterator();
        while (itr.hasNext() && convoyeurSelectionne == null)
        {
            Equipement e = itr.next();
            
            Iterator<Sortie> sItr = e.getSorties().iterator();
            while (sItr.hasNext() && convoyeurSelectionne == null)
            {
                Sortie sortie = sItr.next();
                convoyeurSelectionne = (sortie.convoyeurSelectionne(_pos)) ? sortie.getConvoyeur() : null;
            }
        }
        
        return convoyeurSelectionne;
    }
    
    public Set<Equipement> getElements()
    {
        return Collections.unmodifiableSet(elements);
    }
    
    public void ajouterMatiere(Matiere _matiere)
    {
        if (!matieres.contains(_matiere))
        {
            matieres.add(_matiere);
            
            for (Equipement e : this.getElements())
            {
                if (e instanceof Station)
                {
                    ((Station)e).getMatriceTri().setMatiere(matieres);
                    ((Station)e).getMatriceTransformation().ajouterProduitATransformer(_matiere);
                }
            }
            
            this.calculDuSchema();
        }
    }
    
    public void enleverMatiere(Matiere _matiere)
    {
        if (matieres.contains(_matiere))
        {
            for (Equipement e : this.elements)
            {
                if (e instanceof EntreeUsine)
                {
                    FluxDeMatiere flux = e.getMatieres();
                    flux.enleverMatiere(_matiere);

                    ((EntreeUsine)e).setMatieres(flux);
                }
            }
            
            matieres.remove(_matiere);
            this.calculDuSchema();
        }
    }
    
    public FluxDeMatiere getMatieresAEntree()
    {
        FluxDeMatiere flux = new FluxDeMatiere();
        
        for (Equipement e : elements)
        {
            if (e instanceof EntreeUsine)
                flux.combinerFlux(e.getMatieres());
        }
        
        return flux;
    }
    
    public Set<Matiere> getMatieres()
    {
        return Collections.unmodifiableSet(matieres);
    }
    
    public int getPixelsParMetre()
    {
        return pixelsParMetre;
    }
    
    public void setPixelsParMetre(int _pixelsParMetre)
    {
        pixelsParMetre = (_pixelsParMetre < 0) ? 0
                                               : _pixelsParMetre;
    }
    
    public void setNom(String _nom)
    {
        nom = _nom;
    }
    
    public String getNom()
    {
        return this.nom;
    }
    
    public Taille tailleMaximale()
    {
        Taille taille = new Taille(0, 0);
        for (Equipement e : elements)
        {
            double largeur = e.getPosition().x() + e.getTaille().largeur();
            double hauteur = e.getPosition().y() + e.getTaille().hauteur();
            
            taille.setLargeur((largeur > taille.largeur()) ? largeur + 1 
                                                           : taille.largeur());
            
            taille.setHauteur((largeur > taille.hauteur()) ? hauteur + 1 
                                                           : taille.hauteur());
        }
        
        return taille;
    }
    
    public boolean deplacerEquipement(Equipement _equip, Position _pos)
    {
        boolean estDeplace = false;
        _pos = new Position(NumericUtilities.round(_pos.x(), 3), 
                            NumericUtilities.round(_pos.y(), 3));
        
        if (_equip != null && elements.contains(_equip))
        {
            Position originale = _equip.getPosition();
            _equip.setPosition(_pos.x(), _pos.y());
            
            if (!this.validerElement(_equip))
                _equip.setPosition(originale.x(), originale.y());
            else
                estDeplace = true;
        }
        
        return estDeplace;
    }
    
    public boolean redimensionnerEquipement(Equipement _equip, Taille _dim)
    {
        boolean estSupprimer = false;
        
        if (_equip != null && elements.contains(_equip))
        {
            Taille originale = _equip.getTaille();
            _equip.setTaille(_dim);
            
            if (!this.validerElement(_equip))
                _equip.setTaille(originale);
            else
                estSupprimer = true;
        }
        
        return estSupprimer;
    }
    
    public void sauvegarder(String _nom) 
    {
        try
        {
            String _chemin = new String(nom +".recycl");
            
            if(_nom.toLowerCase().contains(".recycl"))
            {
               _chemin = _nom; 
            }
            FileOutputStream fout = new FileOutputStream(_chemin);

            try (ObjectOutputStream oos = new ObjectOutputStream(fout))
            {
                oos.writeObject(this);
                oos.flush();
                oos.close();
            }
            
          fout.close();
        
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            System.err.println(e.getLocalizedMessage());
        }
      
    }
    
    public static Schema ouvrir(String _chemin) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        Schema schema = null;
        FileInputStream fin = new FileInputStream(_chemin);

        ObjectInputStream ois = new ObjectInputStream(fin);
        //ois.defaultReadObject();
        schema =(Schema) ois.readObject();
            


        fin.close();
       
       return schema;
    }
    
    public static byte[] compressSchema(Schema _schema) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gzipOut = new GZIPOutputStream(baos);
        
        try (ObjectOutputStream objectOut = new ObjectOutputStream(gzipOut))
        {
            objectOut.writeObject(_schema);
        }

        return baos.toByteArray();
    }
    
    public static Schema uncompressSchema(byte[] _schema) throws IOException, ClassNotFoundException
    {
        Schema schema = null;
        
        ByteArrayInputStream bais = new ByteArrayInputStream(_schema);
        GZIPInputStream gzipIn = new GZIPInputStream(bais);
        
        try (ObjectInputStream objectIn = new ObjectInputStream(gzipIn))
        {
            schema = (Schema) objectIn.readObject();
        }
        
        return schema;
    }
}

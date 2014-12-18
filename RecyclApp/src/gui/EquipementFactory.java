/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import utils.ColorUtilities;
import gui.panel.PanelSchema.Etat;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import domaine.usine.Convoyeur;
import domaine.usine.Entree;
import domaine.usine.Equipement;
import domaine.usine.IO;
import domaine.usine.Jonction;
import domaine.usine.Sortie;
import domaine.usine.Station;
import ressources.Parametres;
import utils.Orientation;

/**
 *
 * @author Vincent
 */
public class EquipementFactory
{
    protected Graphics2D g2d;
    
    protected double tailleDesCases,
                     zoom;
    
    protected Etat etat;
    
    public EquipementFactory(Graphics2D _g2d, Etat _etat)
    {
        this(_g2d, 20f, 1f, Etat.SELECTION);
    }
    
    public EquipementFactory(Graphics2D _g2d, double _tailleDesCases, double _zoom, Etat _etat)
    {
        g2d = _g2d;
        
        etat = _etat;
        zoom = _zoom;
        tailleDesCases = (_tailleDesCases < 0) ? 1 : _tailleDesCases;
    }
    
    public void equipement(Equipement _equip)
    {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (_equip instanceof Station)
            this.station((Station)_equip);
        else if (_equip instanceof Jonction)
            this.jonction((Jonction)_equip);
        else
        {
            Point pos = _equip.getPosition().point(tailleDesCases);
            Dimension dim = _equip.getTaille().dimension(tailleDesCases);
        
            _equip.setCouleur(_equip.getCouleur());

            g2d.setColor(_equip.getCouleur());
            g2d.fillRect(pos.x, pos.y, dim.width, dim.height);
        }
        
        int rayonIO = Math.round((float)(IO.TAILLE * tailleDesCases) / 2f);
        
        Color sortie = Parametres.getCouleur(Parametres.COULEUR_SORTIES),
              entree = Parametres.getCouleur(Parametres.COULEUR_ENTREES),
              connexion = new Color(255,0,255),
              nonValide = new Color(255,0,0);
        
        for (Sortie s : _equip.getSorties())
        {
            g2d.setColor((s.estValide()) ? sortie : nonValide);
            
            Point pos = s.getPosition().point(tailleDesCases);
            g2d.fillOval(pos.x - rayonIO, pos.y - rayonIO, rayonIO*2, rayonIO*2);
            
            if (etat == Etat.CONNEXION)
            {
                g2d.setColor(connexion);
                g2d.drawRect(pos.x - rayonIO, pos.y - rayonIO, rayonIO*2, rayonIO*2);
            }
        }
        
        for (Entree e : _equip.getEntrees())
        {
            g2d.setColor(entree);
            
            Point pos = e.getPosition().point(tailleDesCases);
            g2d.fillRect(pos.x - rayonIO, pos.y - rayonIO, rayonIO*2, rayonIO*2);
            
            if (etat == Etat.CONNEXION)
            {
                g2d.setColor(connexion);
                g2d.drawRect(pos.x - rayonIO, pos.y - rayonIO, rayonIO*2, rayonIO*2);
            }
        }
    }
    
    public void station(Station _station)
    {
        Point pos = _station.getPosition().point(tailleDesCases);
        Dimension dim = _station.getTaille().dimension(tailleDesCases);
        
        if(_station.getImage().x == null)
        {
            g2d.setColor((_station.estValide()) ? _station.getCouleur() : Color.RED);
            g2d.fillRect(pos.x, pos.y, dim.width, dim.height);
        }
        else
            g2d.drawImage(_station.getImage().x, pos.x, pos.y, dim.width, dim.height, null);

        g2d.setColor(ColorUtilities.ContrastWhiteOrBlack(_station.getCouleur()));
        
        int offset = Math.round((float)IO.TAILLE * (float)tailleDesCases) + Math.round(5*(float)zoom);
        g2d.setFont(new Font(g2d.getFont().getFontName(), 
                             Font.PLAIN, 
                             Math.round(12 * (float)zoom)));  
            
        FontMetrics metrics = g2d.getFontMetrics();
            
        for (Sortie s : _station.getSorties())
        {
            String numero = _station.getNumeroDeLaSortie(s) +"";
            
            Point position = s.getPosition().point(tailleDesCases);
            Dimension taille = new Dimension(metrics.stringWidth(numero), metrics.getHeight());
            
            Point dessinString;
            
            switch (_station.getOrientationSorties())
            {
                case NORD:
                    
                    dessinString = new Point(position.x - Math.round(taille.width/2),
                                             position.y + offset);
                    break;
                    
                case SUD:
                    
                    dessinString = new Point(position.x - Math.round(taille.width/2),
                                             position.y - offset);
                    break;
                    
                case EST:
                    
                    dessinString = new Point(position.x - offset - taille.width,
                                             position.y + Math.round(taille.height/3));
                    break;
                    
                case OUEST:
                    
                    dessinString = new Point(position.x + offset,
                                             position.y + Math.round(taille.height/3));
                    break;
                    
                default:
                    dessinString = new Point(position.x, position.y);
            }
            
            g2d.drawString(_station.getNumeroDeLaSortie(s) +"", dessinString.x, dessinString.y);
        }
        
        String nom = _station.getNom();
        
        if (metrics.stringWidth(nom) > dim.width)
        {
            while (metrics.stringWidth(nom+"...") > dim.width - Math.round(5 * (float)zoom) &&
                   metrics.stringWidth("...") < dim.width - Math.round(5 * (float)zoom))
                nom = (nom.length() > 1) ? nom.substring(0, nom.length() - 2) : "";
            
            nom += "...";
        }

        Dimension tailleNom = new Dimension(metrics.stringWidth(nom), metrics.getHeight());
        Point positionNom = new Point(Math.round(pos.x + 5*(float)zoom), Math.round(pos.y + tailleNom.height));
        
        switch (_station.getOrientationSorties())
        {
            case NORD:
                
                positionNom = new Point(positionNom.x, 
                                        positionNom.y + dim.height - tailleNom.height - Math.round(5*(float)zoom));
                break;
            
                
            case OUEST:
                
                positionNom = new Point(positionNom.x + dim.width - tailleNom.width - 2*Math.round(5*(float)zoom), 
                                        positionNom.y);
                
                if (_station.getOrientationEntrees() == Orientation.NORD)
                    positionNom.y += dim.height - tailleNom.height - Math.round(5*(float)zoom);
                
                break;
                
            case EST:
                
                if (_station.getOrientationEntrees() == Orientation.NORD)
                    positionNom.y += dim.height - tailleNom.height - Math.round(5*(float)zoom);
                
                break;
        }
        
        
        g2d.drawString(nom, positionNom.x, positionNom.y);
    }
    
    public void jonction(Jonction _jonction)
    {
        Point pos = _jonction.getPosition().point(tailleDesCases);
        Dimension dim = _jonction.getTaille().dimension(tailleDesCases);
        
        g2d.setColor(_jonction.getCouleur());
        
        Stroke original = g2d.getStroke();
        g2d.setStroke(new BasicStroke(Math.round(3*zoom)));
        
        Point centre = new Point(pos.x + Math.round(dim.width/2), pos.y + Math.round(dim.height/2));
        for (Entree e : _jonction.getEntrees())
        {
            Point posE = e.getPosition().point(tailleDesCases);
            g2d.drawLine(posE.x, posE.y, centre.x, centre.y);
        }
        
        Point sortie = _jonction.getSortie().getPosition().point(tailleDesCases);
        g2d.drawLine(centre.x, centre.y, sortie.x, sortie.y);
        
        g2d.setStroke(original);
    }
}

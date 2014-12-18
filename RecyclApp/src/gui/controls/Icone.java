/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controls;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Conteneur des différents icônes pouvant être utilisées par le programme
 * Pour ajouter une image, ajouter là au package "images" et ajoutez une entrée dans l'enum
 * 
 * @author Vincent
 */
public enum Icone
{
    DEFAUT("Defaut_100.png"),
    
    AJOUTER("Ajouter_100.png"),
    OUVRIR("Ouvrir_100.png"),
    SAUVEGARDER("Sauvegarder_100.png"),
    ANNULER("Annuler_100.png"),
    REVENIR("Revenir_100.png"),
    EXPORTER("Exporter_100.png"),
    PARAMETRES("Parametres_100.png"),
    
    POPUP("Popup_100.png"),
    
    SUPPRIMER("Supprimer_100.png"),
    MAIN("Main_100.png"),
    CONNECTER("Connecter_100.png"),
    GRILLE("Grille_100.png"),
    MAGNET("Magnetique_100.png"),
    
    CHECK("Check_100.png"),
    DROPDOWN("Dropdown_100.png"),
    
    PLUS("Plus_100.png"),
    MOINS("Moins_100.png"),
    
    STATION("Station_100.png"),
    JONCTION("Jonction_100.png"),
    ENTREE("Entree_100.png"),
    SORTIE("Sortie_100.png"),
    
    AIDE("Aide_100.png"),
    COUPER("Couper_100.png"),
    COPIER("Copier_100.png"),
    COLLER("Coller_100.png"),
    IMPRIMER("Imprimer_100.png"),
    NOUVEAU("Nouveau_100.png"),
    OUTILS("Outils_100.png"),
    OUVRIR2("Ouvrir2_100.png"),
    QUITTER("Quitter_100.png"),
    SAUVEGARDER2("Sauvegarder2_100.png"),
    ZOOMM("Zoom1_100.png"),
    ZOOMP("Zoom2_100.png"),
    ABOUT("About_100.png"),
    FICHIERF("FichierFail_100.png"),
    SPARSERI("Sparser.png"),
    BANNIERE("recyclApp.png"),
    
    PEINTURER("Paint_100.png"),
    SUPPRIMER_COMPOSANT("SupprimerComposant_100.png"),
    AJOUTER_MATIERE("AjouterMatiere_100.png"),
    
    IMAGE("Picture_100.png"),
    CARRE("Carre_32.png"),
    
    APPARENCE("Apparence_128.png"),
    COMPOSANTE("Composante_100.png"),
    PERFORMANCE("Performance_100.png"),
    
    SELECTION("Selectionner_100.png");
    
    private ImageIcon image;
    
    Icone(String image)
    {
        try
        {
            this.image = new ImageIcon(this.getClass().getResource("/images/"+ image));
        }
        catch (Exception e)
        {
            try
            {
                this.image = new ImageIcon(this.getClass().getResource("/images/Defaut_100.png"));
            } catch (Exception e2) { this.image = null; }
        }
    }
    
    /**
     * @return ImageIcon de l'icône choisie
     */
    public ImageIcon getImage()
    {
        return this.image;
    }
    
    public ImageIcon getImage(int _largeur, int _hauteur)
    {
        ImageIcon retour = this.image;
        
        Image imageModifiee;
        
        _largeur = (_largeur < 0) ? 0 : _largeur;
        _hauteur = (_hauteur < 0) ? 0 : _hauteur;
        
        if (_largeur == 0 && _hauteur > 0 ||
            _hauteur == 0 && _largeur > 0)
        {
            float ratio = (_hauteur > 0) ? _hauteur / image.getIconHeight() 
                                         : _largeur / image.getIconWidth();
            
            imageModifiee = image.getImage().getScaledInstance(Math.round(image.getIconHeight() * ratio), 
                                                               Math.round(image.getIconWidth() * ratio), 
                                                               Image.SCALE_AREA_AVERAGING);
            
            retour = new ImageIcon(imageModifiee);
        }
        else if (_largeur > 0 && _hauteur > 0)
        {
            imageModifiee = image.getImage().getScaledInstance(_largeur, 
                                                               _hauteur, 
                                                               Image.SCALE_AREA_AVERAGING);
            
            retour = new ImageIcon(imageModifiee);
        }
        
        return retour;
    }
}

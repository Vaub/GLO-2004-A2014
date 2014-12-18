/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.panel;

import gui.EquipementFactory;
import gui.adapters.SchemaConnexionAdapter;
import gui.adapters.SchemaSelectionAdapter;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import domaine.usine.Convoyeur;
import domaine.usine.Entree;
import domaine.usine.Equipement;
import domaine.usine.Composant;
import domaine.usine.IO;
import domaine.usine.Sortie;
import gui.RecyclApp;
import gui.adapters.SchemaAjoutAdapter;
import gui.adapters.SchemaNormalAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.InputMap;
import javax.swing.JScrollPane;
import ressources.Parametres;
import utils.ColorUtilities;
import utils.Position;
import utils.Taille;

/**
 *
 * @author Vincent
 */
public class PanelSchema extends JPanel
{ 
    public final ZoneDeTravail zoneDeTravail;
    
    protected boolean afficherGrille,
                      magnetique;
    
    protected float facteurDeZoom;
    
    protected Etat etat;
    
    //public Point positionCurseur;
    
    public SchemaAjoutAdapter adapteurAjout;
    public SchemaSelectionAdapter adapteurSelection;
    public SchemaConnexionAdapter adapteurConnexion;
    public SchemaNormalAdapter adapteurNormal;
    
    private final Action deleteConnexion,
                         deleteEquipement,
                         ctrlPressed,
                         ctrlReleased;
    
    public enum Etat 
    { 
        SELECTION,
        CONNEXION,
        NORMAL,
        MOUVEMENT,
        AJOUT;
    }
    
    public PanelSchema(ZoneDeTravail _zone)
    {
        //this.setBackground(Color.BLUE);
        this.setLayout(new BorderLayout());
        
        zoneDeTravail = _zone;
        
        etat = Etat.SELECTION;
        
        afficherGrille = true;
        facteurDeZoom = 1f;
        //positionCurseur = new Point(-1000,-1000);
        
        //grille = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        
        adapteurAjout = new SchemaAjoutAdapter(zoneDeTravail.parent, this);
        adapteurSelection = new SchemaSelectionAdapter(zoneDeTravail.parent, this);
        adapteurConnexion = new SchemaConnexionAdapter(zoneDeTravail.parent, this);
        adapteurNormal = new SchemaNormalAdapter(zoneDeTravail.parent, this);
        //adapteurGod = new SchemaMouseAdapter(zoneDeTravail.zoneDeTravail, this);
        
        deleteEquipement = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                adapteurSelection.actionPerformed(e);
            }
        };
        
        deleteConnexion = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                adapteurConnexion.actionPerformed(e);
            }
        };
        
        ctrlPressed = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                adapteurSelection.ctrlActif = true;
            }
        };
        
        ctrlReleased = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                adapteurSelection.ctrlActif = false;
            }
        };
        
        this.getActionMap().put("deleteEquipement", deleteEquipement);
        this.getActionMap().put("deleteConnexion", deleteConnexion);
        this.getActionMap().put("ctrlPressed", ctrlPressed);
        this.getActionMap().put("ctrlReleased", ctrlReleased);
        
        this.setEtat(etat);
    }
    
    public void afficherGrille(boolean _afficher)
    {
        afficherGrille = _afficher;
        this.repaint();
    }
    
    public void setEquipAjout(Equipement _equip)
    {
        adapteurAjout.ajout = _equip;
        this.repaint();
    }
    
    public void setEtat(Etat _etat)
    {
        etat = _etat;
        
        for (MouseListener ml : this.getMouseListeners())
            this.removeMouseListener(ml);
        
        for (MouseMotionListener mml : this.getMouseMotionListeners())
            this.removeMouseMotionListener(mml);
        
        //this.getInputMap().clear();
        
        Composant selection = null;
        switch (etat)
        {
            case SELECTION:
                this.addMouseListener(adapteurSelection);
                this.addMouseMotionListener(adapteurSelection);
                
                InputMap input = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                
                input.put(KeyStroke.getKeyStroke("DELETE"), "deleteEquipement");
                input.put(KeyStroke.getKeyStroke(KeyEvent.VK_CONTROL, KeyEvent.CTRL_DOWN_MASK, false), "ctrlPressed");
                input.put(KeyStroke.getKeyStroke(KeyEvent.VK_CONTROL, 0, true), "ctrlReleased");
                
                selection = adapteurSelection.getSelection();
                
                break;
            
            case CONNEXION:
                this.addMouseListener(adapteurConnexion);
                this.addMouseMotionListener(adapteurConnexion);
                
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke("DELETE"), "deleteConnexion");
                
                selection = adapteurConnexion.convoyeur;
                
                break;
                
            case NORMAL:
                this.addMouseListener(adapteurNormal);
                this.addMouseMotionListener(adapteurNormal);
                
                break;
                
            case AJOUT:
                this.addMouseListener(adapteurAjout);
                this.addMouseMotionListener(adapteurAjout);
                
                break;
        }
        
        if (zoneDeTravail.parent.paramElements != null)
            zoneDeTravail.parent.paramElements.setComposant(selection);
        
        this.repaint();
    }
    
    public void setMagnetique(boolean _magnetique)
    {
        magnetique = _magnetique;
    }
    
    public void setZoom(float _facteurDeZoom)
    {
        facteurDeZoom = (_facteurDeZoom < 0.25f) ? 0.25f
                                                 : _facteurDeZoom;
        
        //this.creerGrille();
        this.repaint();
    }
    
    public boolean getGrilleVisible()
    {
        return afficherGrille;
    }
    
    public boolean estMagnetique()
    {
        return magnetique;
    }
    
    public float zoom()
    {
        return facteurDeZoom;
    }
    public void exporter(String nomfichier)
    {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Parametres.getCouleur(Parametres.COULEUR_FOND_GRILLE));
        g.fillRect(0, 0, super.getWidth(), super.getHeight());
        this.creerGrille(g);
        this.creerSchema(g);
        g.dispose();
        File outputfile = new File(nomfichier);
        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException ex) {
            Logger.getLogger(PanelSchema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    protected void paintComponent(Graphics g)
    {
        Dimension tailleDuSchema = zoneDeTravail.parent.controleur.getTailleSchema().dimension(this.getRatioTaille());
        if (this.getPreferredSize().width < tailleDuSchema.width ||
            this.getPreferredSize().height < tailleDuSchema.height)
        {
            this.setPreferredSize(tailleDuSchema);
            this.revalidate();
        }
        
        super.paintComponents(g);
        g.clearRect(0, 0, super.getWidth(), super.getHeight());
        
        Graphics2D g2d = (Graphics2D) g;
        EquipementFactory factory = new EquipementFactory(g2d, this.getRatioTaille(), this.facteurDeZoom, this.getEtat());
        
        g2d.setColor(Parametres.getCouleur(Parametres.COULEUR_FOND_GRILLE));
        g2d.fillRect(0, 0, super.getWidth(), super.getHeight());
        
        if (zoneDeTravail.parent.controleur.getSchema() != null)
        {
            if (afficherGrille)
                this.creerGrille(g2d);

            this.creerSchema(g2d);

            switch (etat)
            {
                case SELECTION:
                    
                    if (adapteurSelection.selectionner != null)
                    {
                        Point origine,
                              fin,
                              pos1 = adapteurSelection.selectionner,
                              pos2 = adapteurSelection.getPosition();
                        
                        origine = new Point((pos1.x > pos2.x) ? pos2.x : pos1.x,
                                            (pos1.y > pos2.y) ? pos2.y : pos1.y);
                        
                        fin = new Point((pos2.x < pos1.x) ? pos1.x : pos2.x,
                                        (pos2.y < pos1.y) ? pos1.y : pos2.y);
                        
                        Color selection = ColorUtilities.ContrastWhiteOrBlack(Parametres.getCouleur(Parametres.COULEUR_FOND_GRILLE)),
                              sAire = selection.darker(),
                              sLigne = selection;
                        
                        float oAire = 0.5f,
                              oLigne = 0.9f;
                        
                        
                        if (selection.equals(Color.BLACK))
                        {
                            sAire = selection.brighter().brighter();
                            sLigne = selection.brighter();
                            
                            oAire = 0.2f;
                            oLigne = 0.8f;
                        }
                        
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, oAire));
                        
                        g2d.setColor(sAire);
                        g2d.fillRect(origine.x, origine.y, (fin.x - origine.x), (fin.y - origine.y));
                        
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, oLigne));
                        
                        g2d.setColor(sLigne);
                        g2d.drawRect(origine.x, origine.y, (fin.x - origine.x), (fin.y - origine.y));
                        
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
                    }
                    
                    break;
                
                case AJOUT:

                    if (adapteurAjout.ajout != null)
                    {
                        Position position;
                        Taille dim = adapteurAjout.ajout.getTaille();

                        position = this.getPositionReelle(adapteurAjout.getMagnetCoord());
                        position = new Position(position.x() - (dim.largeur()/2f),
                                                position.y() - (dim.hauteur()/2f));

                        adapteurAjout.ajout.setPosition(position.x(), position.y());

                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                        factory.equipement(adapteurAjout.ajout);

                        g2d.setColor((zoneDeTravail.parent.controleur.positionValide(adapteurAjout.ajout)) ? Color.GREEN
                                                                                                    : Color.RED);

                        Point point = adapteurAjout.ajout.getPosition().point(this.getRatioTaille());
                        Dimension taille = adapteurAjout.ajout.getTaille().dimension(this.getRatioTaille());

                        g2d.fillRect(point.x, 
                                     point.y, 
                                     taille.width, 
                                     taille.height);

                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                    }

                    break;

                case CONNEXION:

                    Point pos = adapteurConnexion.getPosition();
                    if (adapteurConnexion.relierNoeuds &&
                        adapteurConnexion.noeud != null)
                    {
                        Stroke originale = g2d.getStroke();
                        g2d.setStroke(new BasicStroke(3));
                        g2d.setColor(Color.BLACK);

                        Point de = adapteurConnexion.noeud.getPosition().point(this.getRatioTaille());
                        g2d.drawLine(de.x, de.y, pos.x, pos.y);

                        g2d.setStroke(originale);
                    }

                    break;
            }
        }
        
        g2d.dispose();
    }
    
    private void creerSchema(Graphics2D g2d)
    {
        EquipementFactory factory = new EquipementFactory(g2d, this.getRatioTaille(), this.facteurDeZoom, this.getEtat());
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        ArrayDeque<Convoyeur> convoyeurs = new ArrayDeque<>();
        ArrayDeque<Equipement> equipements = new ArrayDeque<>();
        
        for (Equipement e : zoneDeTravail.parent.controleur.listerEquipements())
        {
            equipements.push(e);
            
            for (Sortie sortie : e.getSorties())
                convoyeurs.push(sortie.getConvoyeur());
        }
        
        while (!convoyeurs.isEmpty())
        {
            Convoyeur convoyeur = convoyeurs.pop();
            if (convoyeur != null && convoyeur.parent.estReliee())
            {
                Color couleurConv = (etat == Etat.CONNEXION && 
                                     convoyeur == adapteurConnexion.convoyeur) ? Color.MAGENTA :
                                    (!convoyeur.parent.estValide()) ? Color.RED : convoyeur.getCouleur();

                g2d.setColor(couleurConv);
                
                Stroke originale = g2d.getStroke();
                g2d.setStroke(new BasicStroke(Convoyeur.TAILLE_PAR_DEFAUT * this.facteurDeZoom));

                Point origine = convoyeur.parent.getPosition().point(this.getRatioTaille()),
                      destination = convoyeur.parent.getIORelie().getPosition().point(this.getRatioTaille());

                GeneralPath pathConvoyeur = new GeneralPath();
                pathConvoyeur.moveTo(origine.x, origine.y);

                int rayonCercle = Math.round(10 * this.facteurDeZoom) / 2;

                for (Position p : convoyeur.getPoints())
                {
                    Point point = p.point(this.getRatioTaille());
                    pathConvoyeur.lineTo(point.x, point.y);

                    g2d.fillOval(point.x - rayonCercle, point.y - rayonCercle, 
                                 2*rayonCercle, 2*rayonCercle);
                }

                pathConvoyeur.lineTo(destination.x, destination.y);

                g2d.draw(pathConvoyeur);
                g2d.setStroke(originale);

                if (etat == Etat.CONNEXION && adapteurConnexion.flexion != null)
                {
                    Point point = adapteurConnexion.flexion.point(this.getRatioTaille());

                    g2d.setColor(Color.BLUE);
                    g2d.fillOval(point.x - rayonCercle, point.y - rayonCercle, 
                                 2*rayonCercle, 2*rayonCercle);

                    g2d.setColor(couleurConv);
                }
            }
        }
        
        while (!equipements.isEmpty())
            factory.equipement(equipements.pop());
        
        if (etat == Etat.SELECTION)
            for (Equipement equipement : adapteurSelection.selection.keySet())
            {
                Dimension dim = equipement.getTaille().dimension(this.getRatioTaille());
                Point position = equipement.getPosition().point(this.getRatioTaille());

                g2d.setColor(Color.BLACK);
                g2d.drawRect(position.x, position.y, dim.width, dim.height);

                int selectTaille = Math.round(10);
                int selectPos = Math.round(5);

                g2d.setColor(Color.WHITE);
                g2d.fillRect(position.x - selectPos, 
                             position.y - selectPos, 
                             selectTaille, selectTaille);

                g2d.fillRect(position.x + dim.width - selectPos, 
                             position.y - selectPos, 
                             selectTaille, selectTaille);

                g2d.fillRect(position.x + dim.width - selectPos, 
                             position.y + dim.height - selectPos, 
                             selectTaille, selectTaille);

                g2d.fillRect(position.x - selectPos, 
                             position.y + dim.height - selectPos, 
                             selectTaille, selectTaille);

                g2d.setColor(Color.BLACK);
                g2d.drawRect(position.x - selectPos, 
                             position.y - selectPos, 
                             selectTaille, selectTaille);

                g2d.drawRect(position.x + dim.width - selectPos, 
                             position.y - selectPos, 
                             selectTaille, selectTaille);

                g2d.drawRect(position.x + dim.width - selectPos, 
                             position.y + dim.height - selectPos, 
                             selectTaille, selectTaille);

                g2d.drawRect(position.x - selectPos, 
                             position.y + dim.height - selectPos, 
                             selectTaille, selectTaille);
            }
    }
    
    private void creerGrille(Graphics2D g2d)
    {
        Dimension taille = zoneDeTravail.parent.controleur.getTailleSchema().dimension(this.getRatioTaille());
        this.setPreferredSize(taille);
        this.revalidate();
        
        int nbDeCaseLargeur = Math.round(this.getWidth() / this.getTailleDesCases()) + 1;
        int nbDeCaseHauteur = Math.round(this.getHeight() / this.getTailleDesCases()) + 1;
        
        Color couleur = Parametres.getCouleur(Parametres.COULEUR_FOND_GRILLE);
        Color grille = ColorUtilities.ContrastWhiteOrBlack(couleur);
        
        int casesParMetre = Parametres.getEntier(Parametres.CASES_PAR_METRE);
        
        for (int i = 1; i < nbDeCaseLargeur; i++)
        {
            if (i % casesParMetre != 0)
                g2d.setColor(ColorUtilities.AppliquerAlpha(grille, 
                                                           couleur, 
                                                           0.10f));
            else
               g2d.setColor(ColorUtilities.AppliquerAlpha(grille, 
                                                          couleur, 
                                                          0.25f)); 
            
            int positionCase = Math.round(i*this.getTailleDesCases());
            g2d.drawLine(positionCase, 0, positionCase, this.getHeight());
        }
        for (int i = 1; i < nbDeCaseHauteur; i++)
        {
            if (i % casesParMetre != 0)
                g2d.setColor(ColorUtilities.AppliquerAlpha(grille, 
                                                           couleur, 
                                                           0.10f));
            else
               g2d.setColor(ColorUtilities.AppliquerAlpha(grille, 
                                                          couleur, 
                                                          0.25f)); 
            
            int positionCase = Math.round(i*this.getTailleDesCases());
            g2d.drawLine(0, positionCase, this.getWidth(), positionCase);
        }
    }
    
    public float getRatioTaille()
    {
        return zoneDeTravail.parent.controleur.getPixelsParMetres() * facteurDeZoom;
    }
    
    public float getTailleDesCases()
    {
        return this.getRatioTaille() / (float)Parametres.getEntier(Parametres.CASES_PAR_METRE);
    }
    
    public Etat getEtat()
    {
        return etat;
    }
    
    public Taille getTailleReelle(Dimension _taille)
    {
        Taille tailleReelle = new Taille(0.0f, 0.0f);
        
        if (_taille != null)
        {
            tailleReelle = new Taille(_taille.width / this.getRatioTaille(),
                                      _taille.height / this.getRatioTaille());
        }
        
        return tailleReelle;
    }
    
    public Position getPositionReelle(Point _position)
    {
        Position positionReelle = new Position(0.0f, 0.0f);
        
        if (_position != null)
        {
            positionReelle = new Position(_position.x / this.getRatioTaille(),
                                          _position.y / this.getRatioTaille());
        }
        
        return positionReelle;
    }
    
    public Point getMagnetCoord(Point _position)
    {
        Point positionMagnetique = new Point(_position);
        
        if (_position != null && 
            this.getGrilleVisible() && this.estMagnetique())
        {
            float ratio = this.getTailleDesCases();
            
            positionMagnetique.x = Math.round(Math.round(positionMagnetique.x / ratio) * ratio);
            positionMagnetique.y = Math.round(Math.round(positionMagnetique.y / ratio) * ratio);
        }
        
        return positionMagnetique;
    }
    
    public void clearAdapteurs()
    {
        adapteurAjout.clear();
        adapteurConnexion.clear();
        adapteurSelection.clear();
        
        this.repaint();
    }
    
    public void resetGUI()
    {
        this.repaint();
    }
}

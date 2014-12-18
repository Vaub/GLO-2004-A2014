/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ressources;

import java.awt.Color;
import java.util.HashMap;

/**
 *
 * @author Vincent
 */
public class Parametres
{
    private final static Parametres PARAMETRES = new Parametres();
    
    public final static String COULEUR_FOND = "COULEUR_FOND",
                               COULEUR_CONTRASTE = "COULEUR_CONTRASTE",
                               COULEUR_FOND_GRILLE = "COULEUR_FOND_GRILLE",
                               COULEUR_COMPOSANTS = "COULEUR_COMPOSANTS",
                               COULEUR_ENTREES = "COULEUR_ENTREES",
                               COULEUR_SORTIES = "COULEUR_SORTIES",
                               
                               CASES_PAR_METRE = "INT_CASES_PAR_METRE",
            
                               MAX_UNDO_REDO = "INT_MAX_UNDO_REDO";
    
    protected HashMap<String, Object> parametres,
                                      defauts;
    
    public Parametres()
    {
        parametres = new HashMap<>();
        defauts = new HashMap<>();
        
        this.initDefauts();
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void initDefauts()
    {
        defauts.put(COULEUR_FOND, new Color(0,100,225));
        defauts.put(COULEUR_CONTRASTE, (new Color(225,100,0)).brighter());
        defauts.put(COULEUR_FOND_GRILLE, new Color(255,255,255));
        defauts.put(COULEUR_COMPOSANTS, Color.BLACK);
        
        defauts.put(COULEUR_ENTREES, new Color(0,100,225));
        defauts.put(COULEUR_SORTIES, (new Color(225,100,0)).brighter());
        
        defauts.put(CASES_PAR_METRE, 5);
        
        defauts.put(MAX_UNDO_REDO, 100);
    }
    
    public static void changerParametre(String _param, Object _value)
    {
        PARAMETRES.parametres.put(_param, _value);
    }
    
    public static int getEntier(String _param)
    {
        Object retour = getParametre(_param);
        
        return (retour != null && retour instanceof Integer) ? (Integer)retour : null;
    }
    
    public static float getFloat(String _param)
    {
        Object retour = getParametre(_param);
        
        return (retour != null && retour instanceof Float) ? (Float)retour : null;
    }
    
    public static Color getCouleur(String _param)
    {
        Object retour = getParametre(_param);
        
        return (retour != null && retour instanceof Color) ? (Color)retour : null;
    }
    
    public static Object getDefaut(String _param)
    { 
        return PARAMETRES.defauts.get(_param);
    }
    
    public static Object getParametre(String _param)
    {
        Object retour = null;
        
        if (PARAMETRES.parametres.containsKey(_param))
            retour = PARAMETRES.parametres.get(_param);
        else if (PARAMETRES.defauts.containsKey(_param))
            retour = PARAMETRES.defauts.get(_param);
        
        return retour;
    }
    
    public static void retourDefaut()
    {
        for (String s : PARAMETRES.parametres.keySet())
            changerParametre(s, getDefaut(s));
    }
    
    public static void save()
    {
        // TODO
    }
    
    public static void load()
    {
        // TODO
    }
}

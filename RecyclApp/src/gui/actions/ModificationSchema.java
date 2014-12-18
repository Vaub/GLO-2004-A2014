/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import domaine.controle.Schema;
import gui.RecyclApp;

/**
 *
 * @author Vincent
 */
public class ModificationSchema extends ActionRecyclApp
{
    private final Schema schema;

    public ModificationSchema(Schema _schema, RecyclApp _app)
    {
        super(_app);
        this.schema = _schema;
    }
    
    public void modifierNom(String _nom)
    {
        if (_nom != null && !schema.getNom().equals(_nom) && !_nom.isEmpty())
        {
            app.controleur.renommerSchema(_nom);
            app.actualiser();
        }
    }
    
    public void modifierPixelsParMetre(int _pixelsParMetre)
    {
        if (schema.getPixelsParMetre() != _pixelsParMetre && _pixelsParMetre >= 1)
        {
            app.controleur.changerPixelsParMetre(_pixelsParMetre);
            app.actualiser();
        }
    }
}

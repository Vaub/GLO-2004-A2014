/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import gui.RecyclApp;

/**
 *
 * @author Vincent
 */
public abstract class ActionRecyclApp
{
    public final RecyclApp app;
    
    public ActionRecyclApp(RecyclApp _app)
    {
        app = _app;
    }
}

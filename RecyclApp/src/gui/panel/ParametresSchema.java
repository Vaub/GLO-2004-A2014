package gui.panel;

import gui.RecyclApp;
import gui.controls.BoiteAOutils;
import gui.panel.parametres.SchemaPanel;
import java.awt.Dimension;
import java.awt.event.ActionListener;

/**
 *
 * @author Olivier
 */
public class ParametresSchema extends BoiteAOutils implements ActionListener
{
    private final RecyclApp app;
    
    private SchemaPanel panel;
    
    public ParametresSchema(RecyclApp _app, String _titre)
    {
        super(_app, _titre);
        app = _app;
        
        panel = new SchemaPanel(app.controleur.getSchema(), _app);
        this.setContenu(panel);

        this.setMinimumSize(new Dimension(350, this.getMinimumSize().height));
        this.setPreferredSize(this.getMinimumSize());
    }
    
    public final void updatePanelShema() 
    {
        if (panel != null)
        {
            if (panel.schema == app.controleur.getSchema())
            {
                panel.updatePanel();
                
                this.revalidate();
                this.repaint();
            }
            else
            {
                panel = new SchemaPanel(app.controleur.getSchema(), app);
                this.setContenu(panel);
            }
        }
    }
}

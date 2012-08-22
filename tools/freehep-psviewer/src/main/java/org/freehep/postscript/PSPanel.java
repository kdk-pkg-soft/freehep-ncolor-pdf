// Copyright 2001-2004, FreeHEP.
package org.freehep.postscript;

import java.awt.Graphics;
import java.awt.Graphics2D;

import org.freehep.graphics2d.BufferedPanel;

/**
 * PostScript Panel for PostScript Processor,
 *
 * @author Mark Donszelmann
 * @version $Id: PSPanel.java 10178 2006-12-08 09:03:07Z duns $
 */
public class PSPanel extends BufferedPanel {
    
    private Graphics2D mirroredGraphics;

    public PSPanel() {
        super(false);
    }
    
    public Graphics getGraphics() {        
        return mirroredGraphics;
    }
        
    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x, y, w, h);
        if (mirroredGraphics != null) mirroredGraphics.dispose();
        mirroredGraphics = (Graphics2D)super.getGraphics().create();
    }
    
}

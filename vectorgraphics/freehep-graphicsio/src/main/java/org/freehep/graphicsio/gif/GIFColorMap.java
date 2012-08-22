// Copyright 2006, FreeHEP.
package org.freehep.graphicsio.gif;

/**
 * Creates colormap from set of pixels, making pixels index into the colormap.
 * 
 * @author duns
 * @version $Id$
 */
public interface GIFColorMap {
    
    public int[] create(int[][] pixels, int maxColors);
}

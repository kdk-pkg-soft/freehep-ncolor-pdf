package org.freehep.graphicsio.pdf;

import java.awt.color.ColorSpace;

/**
 * Copyright © 2007 Kodak. All rights reserved.
 * Reproduction or disclosure of this file or its contents
 * without written consent of Kodak is prohibited.
 * User: jlowry
 * Date: Apr 20, 2007
 */

public class SeparationColorSpace extends ColorSpace {
    private final int R = 0;
    private final int G = 1;
    private final int B = 2;

    private final int C = 0;
    private final int M = 1;
    private final int Y = 2;
    private final int K = 3;
    private String separationName;
    
    public SeparationColorSpace(String separationName) {
        super(ColorSpace.TYPE_4CLR, 4);
        this.separationName = separationName;
    }

    public String getSeparationName()
    {
    	return separationName;
    }
    
    public String getName(int i) {
        switch(i) {
            case 1:
                return "Cyan";
            case 2:
                return "Magenta";
            case 3:
                return "Yellow";
            case 4:
                return "Black";
        }

        return super.getName(i);
    }

    public float[] toRGB(float[] colorCMYK) {
        float[] colorRGB = new float[3];

        colorRGB[R] = (1 - colorCMYK[C])*(1  - colorCMYK[K]);
        colorRGB[G] = (1 - colorCMYK[M])*(1 - colorCMYK[K]);
        colorRGB[B] = (1 - colorCMYK[Y])*(1 - colorCMYK[K]);

        return colorRGB;
    }

    public float[] fromRGB(float[] floats) {
        return new float[4];
    }

    public float[] toCIEXYZ(float[] floats) {
        return new float[0];
    }

    public float[] fromCIEXYZ(float[] floats) {
        return new float[0];
    }
}

package org.freehep.graphicsio.pdf;

import java.awt.*;
import java.awt.color.ColorSpace;

/**
 * Copyright (c) 2012 Kodak. All rights reserved.<br>
 * Reproduction or disclosure of this file or its contents
 * without written consent of Kodak is prohibited.<p>
 * User: Jebb<br>
 * Date: 8/3/12<br>
 * Time: 5:22 PM<p>
 */
public class PDFSpotColor extends Color {
    private float tintValue;
    private String name;

    public PDFSpotColor(String name, float value[], float tintValue){
        super(ColorSpace.getInstance(ColorSpace.CS_sRGB), getActualColor(value, tintValue), 1.0f);
        this.tintValue = tintValue;
        this.name = name;
    }

    public float getTintValue(){
        return tintValue;
    }
    
    public String getName(){
        return name;
    }

    private static float[] getActualColor(float value[], float tintValue){
        float[] actualValue = new float[value.length];
        for (int i = 0; i < value.length; i++){
            actualValue[i] = 1 - (1 - value[i]) * tintValue;
        }

        return actualValue;
    }
}

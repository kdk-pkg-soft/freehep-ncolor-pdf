// AUTOMATICALLY GENERATED by FreeHEP JAVAGraphics2D

package org.freehep.graphicsio.java.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.freehep.graphics2d.TagString;
import org.freehep.graphics2d.VectorGraphics;
import org.freehep.graphicsio.test.TestingPanel;

public class TestTaggedString extends TestingPanel {

    public TestTaggedString(String[] args) throws Exception {
        super(args);
        setName("TestTaggedString");
    } // contructor

    public void paint(Graphics g) {
        vg[0] = VectorGraphics.create(g);
        vg[0].setCreator("FreeHEP JAVAGraphics2D");
        Paint0s0.paint(vg);
    } // paint

    private static class Paint0s0 {
        public static void paint(VectorGraphics[] vg) {
            vg[0].setColor(new Color(51, 51, 51, 255));
            vg[0].setFont(new Font("Dialog", 0, 12));
            vg[1] = (VectorGraphics)vg[0].create();
            vg[1].setClip(0, 0, 600, 600);
            vg[1].setColor(new Color(255, 255, 255, 255));
            vg[1].fillRect(0, 0, 600, 600);
            vg[1].setColor(new Color(0, 0, 0, 255));
            vg[1].setFont(new Font("SansSerif", 0, 30));
            vg[1].drawString(new TagString("Ant<sup><b>Bull</b></sup>Cat<i><sub>Dog</sub></i><u>Eel</u><sup><udash>Frog</udash></sup><udot>Gecko</udot><sub><strike>Hog</strike></sub>"), 0.0, 150.0);
            vg[1].setFont(new Font("Serif", 0, 30));
            vg[1].drawString(new TagString("Ant<sup><b>Bull</b></sup>Cat<i><sub>Dog</sub></i><u>Eel</u><sup><udash>Frog</udash></sup><udot>Gecko</udot><sub><strike>Hog</strike></sub>"), 0.0, 300.0);
            vg[1].setFont(new Font("Monospaced", 0, 30));
            vg[1].drawString(new TagString("Ant<sup><b>Bull</b></sup>Cat<i><sub>Dog</sub></i><u>Eel</u><sup><udash>Frog</udash></sup><udot>Gecko</udot><sub><strike>Hog</strike></sub>"), 0.0, 450.0);
            vg[1].dispose();
        } // paint
    } // class Paint0s0

    private VectorGraphics vg[] = new VectorGraphics[2];

    public static void main(String[] args) throws Exception {
        new TestTaggedString(args).runTest(600, 600);
    }
} // class

// AUTOMATICALLY GENERATED by FreeHEP JAVAGraphics2D

package org.freehep.graphicsio.java.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.freehep.graphics2d.VectorGraphics;
import org.freehep.graphicsio.test.TestingPanel;

public class TestTransparency extends TestingPanel {

    public TestTransparency(String[] args) throws Exception {
        super(args);
        setName("TestTransparency");
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
            vg[1].setBackground(new Color(255, 200, 0, 255));
            vg[1].clearRect(0, 0, 600, 600);
            vg[1].drawImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), 10, 10, 145, 145, null);
            vg[1].drawImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), 155, 10, 145, 145, null);
            vg[1].drawImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), 300, 10, 145, 145, null);
            vg[1].drawImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), 445, 10, 145, 145, null);
            vg[1].drawImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), 10, 155, 145, 145, null);
            vg[1].drawImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), 155, 155, 145, 145, null);
            vg[1].drawImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), 300, 155, 145, 145, null);
            vg[1].drawImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), 445, 155, 145, 145, null);
            vg[1].drawImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), 10, 300, 145, 145, null);
            vg[1].drawImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), 155, 300, 145, 145, null);
            vg[1].drawImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), 300, 300, 145, 145, null);
            vg[1].drawImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), 445, 300, 145, 145, null);
            vg[1].drawImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), 10, 445, 145, 145, null);
            vg[1].drawImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), 155, 445, 145, 145, null);
            vg[1].drawImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), 300, 445, 145, 145, null);
            vg[1].drawImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), 445, 445, 145, 145, null);
            vg[1].dispose();
        } // paint
    } // class Paint0s0

    private VectorGraphics vg[] = new VectorGraphics[2];

    public static void main(String[] args) throws Exception {
        new TestTransparency(args).runTest(600, 600);
    }
} // class

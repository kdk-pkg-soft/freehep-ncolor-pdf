// AUTOMATICALLY GENERATED by FreeHEP JAVAGraphics2D

package org.freehep.graphicsio.java.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import org.freehep.graphics2d.VectorGraphics;
import org.freehep.graphicsio.java.JAVAGeneralPath;
import org.freehep.graphicsio.test.TestingPanel;

public class TestTransforms extends TestingPanel {

    public TestTransforms(String[] args) throws Exception {
        super(args);
        setName("TestTransforms");
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
            vg[2] = (VectorGraphics)vg[1].create();
            vg[2].setStroke(new BasicStroke(
                5.0f, 1, 
                1, 10.0f, 
                null, 0.0f
            ));
            vg[2].translate(100, 100);
            vg[2].draw(new JAVAGeneralPath(1, new JAVAGeneralPath.PathElement[] {
                new JAVAGeneralPath.MoveTo(0.0f, 0.0f),
                new JAVAGeneralPath.LineTo(25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(25.0f, -50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, -50.0f),
                new JAVAGeneralPath.ClosePath()
            }));
            vg[2].dispose();
            vg[3] = (VectorGraphics)vg[1].create();
            vg[3].setStroke(new BasicStroke(
                5.0f, 1, 
                1, 10.0f, 
                null, 0.0f
            ));
            vg[3].translate(300, 100);
            vg[3].rotate(0.7853981633974483);
            vg[3].fill(new JAVAGeneralPath(1, new JAVAGeneralPath.PathElement[] {
                new JAVAGeneralPath.MoveTo(0.0f, 0.0f),
                new JAVAGeneralPath.LineTo(25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(25.0f, -50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, -50.0f),
                new JAVAGeneralPath.ClosePath()
            }));
            vg[3].dispose();
            vg[4] = (VectorGraphics)vg[1].create();
            vg[4].setStroke(new BasicStroke(
                5.0f, 1, 
                1, 10.0f, 
                null, 0.0f
            ));
            vg[4].translate(500, 100);
            vg[4].scale(2.0, 0.5);
            vg[4].fillAndDraw(new JAVAGeneralPath(1, new JAVAGeneralPath.PathElement[] {
                new JAVAGeneralPath.MoveTo(0.0f, 0.0f),
                new JAVAGeneralPath.LineTo(25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(25.0f, -50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, -50.0f),
                new JAVAGeneralPath.ClosePath()
            }), new Color(255, 0, 0, 255));
            vg[4].dispose();
            vg[5] = (VectorGraphics)vg[1].create();
            vg[5].setStroke(new BasicStroke(
                5.0f, 1, 
                1, 10.0f, 
                null, 0.0f
            ));
            vg[5].translate(100, 300);
            vg[5].shear(1.0, 0.0);
            vg[5].draw(new JAVAGeneralPath(1, new JAVAGeneralPath.PathElement[] {
                new JAVAGeneralPath.MoveTo(0.0f, 0.0f),
                new JAVAGeneralPath.LineTo(25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(25.0f, -50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, -50.0f),
                new JAVAGeneralPath.ClosePath()
            }));
            vg[5].dispose();
            vg[6] = (VectorGraphics)vg[1].create();
            vg[6].setStroke(new BasicStroke(
                5.0f, 1, 
                1, 10.0f, 
                null, 0.0f
            ));
            vg[6].translate(300, 300);
            vg[6].shear(0.0, 1.0);
            vg[6].draw(new JAVAGeneralPath(1, new JAVAGeneralPath.PathElement[] {
                new JAVAGeneralPath.MoveTo(0.0f, 0.0f),
                new JAVAGeneralPath.LineTo(25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(25.0f, -50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, -50.0f),
                new JAVAGeneralPath.ClosePath()
            }));
            vg[6].dispose();
            vg[7] = (VectorGraphics)vg[1].create();
            vg[7].setStroke(new BasicStroke(
                5.0f, 1, 
                1, 10.0f, 
                null, 0.0f
            ));
            vg[7].translate(500, 300);
            vg[7].rotate(-0.7853981633974483, 50.0, 50.0);
            vg[7].draw(new JAVAGeneralPath(1, new JAVAGeneralPath.PathElement[] {
                new JAVAGeneralPath.MoveTo(0.0f, 0.0f),
                new JAVAGeneralPath.LineTo(25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(25.0f, -50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, -50.0f),
                new JAVAGeneralPath.ClosePath()
            }));
            vg[7].dispose();
            vg[8] = (VectorGraphics)vg[1].create();
            vg[8].setStroke(new BasicStroke(
                5.0f, 1, 
                1, 10.0f, 
                null, 0.0f
            ));
            vg[8].translate(100, 500);
            vg[8].transform(new AffineTransform(2.0, 0.0, 1.0, 0.5, 50.0, 0.0));
            vg[8].draw(new JAVAGeneralPath(1, new JAVAGeneralPath.PathElement[] {
                new JAVAGeneralPath.MoveTo(0.0f, 0.0f),
                new JAVAGeneralPath.LineTo(25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(25.0f, -50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, -50.0f),
                new JAVAGeneralPath.ClosePath()
            }));
            vg[8].dispose();
            vg[9] = (VectorGraphics)vg[1].create();
            vg[9].setStroke(new BasicStroke(
                5.0f, 1, 
                1, 10.0f, 
                null, 0.0f
            ));
            vg[9].translate(300, 500);
            vg[9].transform(new AffineTransform(0.5, 1.0, 0.0, 2.0, 50.0, -50.0));
            vg[9].draw(new JAVAGeneralPath(1, new JAVAGeneralPath.PathElement[] {
                new JAVAGeneralPath.MoveTo(0.0f, 0.0f),
                new JAVAGeneralPath.LineTo(25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(25.0f, -50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, -50.0f),
                new JAVAGeneralPath.ClosePath()
            }));
            vg[9].dispose();
            vg[1].setTransform(new AffineTransform(1.0, 0.0, 0.0, 1.0, 400.0, 400.0));
            vg[10] = (VectorGraphics)vg[1].create();
            vg[10].setStroke(new BasicStroke(
                5.0f, 1, 
                1, 10.0f, 
                null, 0.0f
            ));
            vg[10].transform(new AffineTransform(0.5, 1.0, 0.0, 1.0, 0.0, 0.0));
            vg[10].draw(new JAVAGeneralPath(1, new JAVAGeneralPath.PathElement[] {
                new JAVAGeneralPath.MoveTo(0.0f, 0.0f),
                new JAVAGeneralPath.LineTo(25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, 50.0f),
                new JAVAGeneralPath.LineTo(25.0f, -50.0f),
                new JAVAGeneralPath.LineTo(-25.0f, -50.0f),
                new JAVAGeneralPath.ClosePath()
            }));
            vg[10].dispose();
            vg[1].setTransform(new AffineTransform(1.0, 0.0, 0.0, 1.0, 0.0, 0.0));
            vg[1].dispose();
        } // paint
    } // class Paint0s0

    private VectorGraphics vg[] = new VectorGraphics[11];

    public static void main(String[] args) throws Exception {
        new TestTransforms(args).runTest(600, 600);
    }
} // class

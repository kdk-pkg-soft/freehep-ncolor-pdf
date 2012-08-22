// Copyright 2000-2007, FreeHEP
package org.freehep.graphicsio.pdf;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;

import org.freehep.graphicsio.ImageConstants;
import org.freehep.util.io.ASCII85OutputStream;
import org.freehep.util.io.ASCIIHexOutputStream;
import org.freehep.util.io.CountedByteOutputStream;
import org.freehep.util.io.FinishableOutputStream;
import org.freehep.util.io.FlateOutputStream;

/**
 * This class allows you to write/print into a PDFStream. Several methods are
 * available to specify the content of a page, image. This class performs some
 * error checking, while writing the stream.
 * <p>
 * The stream allows to write dictionary entries. The /Length entry is written
 * automatically, referencing an object which will also be written just after
 * the stream is closed and the length is calculated.
 * <p>
 * 
 * @author Mark Donszelmann
 * @version $Id: PDFStream.java 12626 2007-06-08 22:23:13Z duns $
 */
public class PDFDeviceNColor extends PDFDictionary implements PDFConstants {

    private String name;

    private PDFObject object;

    private boolean dictionaryOpen;

    private OutputStream[] stream;

    private CountedByteOutputStream byteCountStream;

    private String[] encode;

    PDFDeviceNColor(PDF pdf, PDFByteWriter writer, Object[] colorObjects, PDFObject parent) throws IOException {
    	super(pdf, writer,parent,true);                
        object = parent;
        if (object == null)
            System.err
                    .println("PDFWriter: 'PDFStream' cannot have a null parent");
        // first write the dictionary
        dictionaryOpen = true;        
        entry("DeviceN", colorObjects);        
        entry("DeviceRGB", pdf.ref("NColor"));                          
        //startStream(encode);
        //tintTransform binary lookup in flateDecode
        //byte[] tintTransform = new byte[]{(byte)0x48,(byte)0x89,(byte)0xEC,(byte)0x56,(byte)0x5B,(byte)0x8E,(byte)0xDB,(byte)0x30,(byte)0x0C,(byte)0x9C,(byte)0x1B,(byte)0x58,(byte)0x27,(byte)0x30,(byte)0x75,(byte)0x8F,(byte)0x8D,(byte)0x28,(byte)0xDD,(byte)0xAA,(byte)0xB1,(byte)0x2D,(byte)0x09,(byte)0xBA,(byte)0x70,(byte)0x81,(byte)0x20,(byte)0x48,(byte)0xB3,(byte)0x48,(byte)0x60,(byte)0x20,(byte)0xA5,(byte)0x18,(byte)0xF8,(byte)0x51,(byte)0xB4,(byte)0x09,(byte)0xF6,(byte)0x23,(byte)0xDE,(byte)0x9F,(byte)0x86,(byte)0x10,(byte)0xB4,(byte)0xB3,(byte)0x6B,(byte)0x88,(byte)0x98,(byte)0x59,(byte)0x8E,(byte)0x28,(byte)0xDE,(byte)0x6E,(byte)0xB7,(byte)0x32,(byte)0x8E,(byte)0xB8,(byte)0x5E,(byte)0xC7,(byte)0x32,(byte)0xE6,(byte)0x7C,(byte)0x45,(byte)0xFA,(byte)0xBC,(byte)0xE2,(byte)0x9A,(byte)0xF0,(byte)0x09,(byte)0xFC,(byte)0xBA,(byte)0x5C,(byte)0x4A,(byte)0x3C,(byte)0x67,(byte)0x9C,(byte)0xD2,(byte)0x39,(byte)0xE6,(byte)0x61,(byte)0x48,(byte)0xE8,(byte)0xE3,(byte)0x09,(byte)0xA9,(byte)0x47,(byte)0x04,(byte)0xFA,(byte)0xE3,(byte)0x11,(byte)0xDD,(byte)0x01,(byte)0xF8,(byte)0x89,(byte)0x43,(byte)0x87,(byte)0xFD,(byte)0x1E,(byte)0xF8,(byte)0x21,(byte)0x50,(byte)0xB6,(byte)0x1A,(byte)0xA5,(byte)0x94,(byte)0x90,(byte)0x33,(byte)0x52,(byte)0xCA,(byte)0x21,(byte)0x7B,(byte)0x9F,(byte)0xC0,(byte)0x31,(byte)0x21,(byte)0xB1,(byte)0x9E,(byte)0x8A,(byte)0x31,(byte)0xF0,(byte)0xE0,(byte)0xD1,(byte)0xF3,(byte)0xC0,(byte)0x9E,(byte)0x99,(byte)0xE1,(byte)0xB8,(byte)0x07,(byte)0x3B,(byte)0x30,(byte)0xE0,(byte)0xBA,(byte)0x0E,(byte)0x4E,(byte)0xF3,(byte)0xEC,(byte)0x1D,(byte)0x76,(byte)0x3B,(byte)0xE0,(byte)0xA3,(byte)0x66,(byte)0xFB,(byte)0xC0,(byte)0x26,(byte)0x71,(byte)0x13,(byte)0x8A,(byte)0xCA,(byte)0x70,(byte)0x14,(byte)0xA2,(byte)0xCA,(byte)0x50,(byte)0x64,(byte)0x27,(byte)0x65,(byte)0x78,(byte)0x89,(byte)0x21,(byte)0x2A,(byte)0xC3,(byte)0x33,(byte)0xFB,(byte)0x41,(byte)0x19,(byte)0x9E,(byte)0x50,(byte)0x49,(byte)0x0A,(byte)0xC3,(byte)0x63,(byte)0x87,(byte)0x4E,(byte)0x19,(byte)0x1E,(byte)0x1C,(byte)0xF6,(byte)0xCA,(byte)0x70,(byte)0x91,(byte)0x1C,(byte)0x42,(byte)0xF0,(byte)0x1E,(byte)0xCC,(byte)0xD9,(byte)0x7A,(byte)0x6F,(byte)0x19,(byte)0xC4,(byte)0x92,(byte)0x94,(byte)0xF5,(byte)0x54,(byte)0x64,(byte)0xCB,(byte)0x6C,(byte)0xE1,(byte)0x68,(byte)0x20,(byte)0xCB,(byte)0x44,(byte)0x68,(byte)0xA9,(byte)0x07,(byte)0x39,(byte)0x10,(byte)0x40,(byte)0x9D,(byte)0x83,(byte)0xD3,(byte)0x3C,(byte)0xFB,(byte)0x16,(byte)0xBB,(byte)0x16,(byte)0x68,(byte)0xB7,(byte)0x94,(byte)0x2C,(byte)0x24,(byte)0x15,(byte)0x8C,(byte)0x40,(byte)0x56,(byte)0x70,(byte)0x05,(byte)0x92,(byte)0x82,(byte)0x0B,(byte)0xAA,(byte)0x72,(byte)0x89,(byte)0x33,(byte)0x30,(byte)0x28,(byte)0x38,(byte)0xC9,(byte)0x3F,(byte)0x42,(byte)0xC1,(byte)0x11,(byte)0xE8,(byte)0x14,(byte)0x1C,(byte)0x80,(byte)0xBD,(byte)0x82,(byte)0x45,(byte)0x32,(byte)0x10,(byte)0x14,(byte)0x48,(byte)0x36,(byte)0xAF,(byte)0x40,(byte)0xB2,(byte)0xB1,(byte)0x82,(byte)0x38,(byte)0x81,(byte)0x61,(byte)0x02,(byte)0x92,(byte)0xCD,(byte)0x29,(byte)0xE8,(byte)0x26,(byte)0x20,(byte)0xD9,(byte)0x76,(byte)0x0A,(byte)0xB6,(byte)0x93,(byte)0x5C,(byte)0x6E,(byte)0x25,(byte)0x8C,(byte)0x59,(byte)0xBC,(byte)0x9C,(byte)0x4B,(byte)0xF6,(byte)0x59,(byte)0x3C,(byte)0xB8,(byte)0xF2,(byte)0xE1,(byte)0x25,(byte)0xF0,(byte)0xD9,(byte)0xE3,(byte)0xC4,(byte)0x43,(byte)0xF4,(byte)0xE2,(byte)0x45,(byte)0x29,(byte)0xF7,(byte)0xE2,(byte)0xC3,(byte)0x23,(byte)0xDC,(byte)0xA1,(byte)0xEA,(byte)0xDC,(byte)0x77,(byte)0xD8,(byte)0x69,(byte)0xB9,(byte)0x67,(byte)0x86,(byte)0xA1,(byte)0x04,(byte)0x9B,(byte)0xBD,(byte)0xE4,(byte)0xF0,(byte)0xC1,(byte)0x5B,(byte)0x2F,(byte)0xE5,(byte)0xAD,(byte)0x25,(byte)0x26,(byte)0x3D,(byte)0xC5,(byte)0xD1,(byte)0xD2,(byte)0x60,(byte)0xD1,(byte)0x93,(byte)0xD4,(byte)0x9A,(byte)0xE4,(byte)0x6F,(byte)0xAE,(byte)0x96,(byte)0xB8,(byte)0xD5,(byte)0x2A,(byte)0xBB,(byte)0x0E,(byte)0xAD,(byte)0xE6,(byte)0xD9,(byte)0x39,(byte)0xB4,(byte)0x5A,(byte)0xEE,(byte)0x8F,(byte)0x5A,(byte)0xEA,(byte)0x6D,(byte)0x24,(byte)0x8B,(byte)0x11,(byte)0x95,(byte)0x61,(byte)0x16,(byte)0x3B,(byte)0x2A,(byte)0xC3,(byte)0xC5,(byte)0x87,(byte)0xD1,(byte)0xB2,(byte)0x32,(byte)0x1C,(byte)0xAA,(byte)0x21,(byte)0x2B,(byte)0xC3,(byte)0xC5,(byte)0x87,(byte)0x8F,(byte)0xAF,(byte)0x5E,(byte)0x08,(byte)0xD6,(byte)0x7A,(byte)0x2B,(byte)0x2A,(byte)0xBD,(byte)0x95,(byte)0x20,(byte)0x10,(byte)0x89,(byte)0xB9,(byte)0x49,(byte)0x4F,(byte)0x31,(byte)0x1B,(byte)0x62,(byte)0x03,(byte)0x67,(byte)0x98,(byte)0x0C,(byte)0x91,(byte)0x41,(byte)0xDB,(byte)0x38,(byte)0x98,(byte)0x16,(byte)0x0D,(byte)0xD0,(byte)0xB8,(byte)0x49,(byte)0xA9,(byte)0xB8,(byte)0xBA,(byte)0x55,(byte)0x63,(byte)0x6F,(byte)0x28,(byte)0xF9,(byte)0xD5,(byte)0x3E,(byte)0x94,(byte)0x6C,(byte)0x56,(byte)0x81,(byte)0x9F,(byte)0x80,(byte)0x9C,(byte)0xA5,(byte)0x07,(byte)0xC0,(byte)0x4D,(byte)0xBA,(byte)0x66,(byte)0xB0,(byte)0x9B,(byte)0xC0,(byte)0x76,(byte)0x92,(byte)0xEB,(byte)0x65,(byte)0x1E,(byte)0xF5,(byte)0x02,(byte)0x17,(byte)0x95,(byte)0x9D,(byte)0x56,(byte)0x9F,(byte)0x2E,(byte)0x7A,(byte)0x8D,(byte)0x4F,(byte)0x2A,(byte)0x7E,(byte)0x98,(byte)0xAE,(byte)0xF1,(byte)0x3D,(byte)0x8E,(byte)0x7A,(byte)0x8D,(byte)0x7F,(byte)0xAA,(byte)0xF8,(byte)0xFD,(byte)0x74,(byte)0x8D,(byte)0xEF,(byte)0x31,(byte)0xE7,(byte)0x09,(byte)0x2A,(byte)0x9B,(byte)0x57,(byte)0x9F,(byte)0xE6,(byte)0x3C,(byte)0xAC,(byte)0xCB,(byte)0xAD,(byte)0x3E,(byte)0xCD,(byte)0x79,(byte)0x9C,(byte)0xCA,(byte)0xDE,(byte)0xE8,(byte)0x1A,(byte)0x6F,(byte)0xC4,(byte)0x70,(byte)0xCE,(byte)0x63,(byte)0x75,(byte)0xD1,(byte)0xEA,(byte)0xD3,(byte)0x9C,(byte)0x87,(byte)0x74,(byte)0xAD,(byte)0xAB,(byte)0x38,(byte)0xE7,(byte)0x69,(byte)0xA7,(byte)0xF5,(byte)0x8E,(byte)0xD7,(byte)0x45,(byte)0x29,(byte)0xB7,(byte)0x90,(byte)0x47,(byte)0xA4,(byte)0x6B,(byte)0x0E,(byte)0xA3,(byte)0xF7,(byte)0x57,(byte)0xF0,(byte)0x67,(byte)0xC2,(byte)0x95,(byte)0x75,(byte)0x14,(byte)0x89,(byte)0xB1,(byte)0xF0,(byte)0x90,(byte)0xD1,(byte)0xA7,(byte)0x81,(byte)0x73,(byte)0xED,(byte)0x6A,(byte)0x2E,(byte)0xF6,(byte)0x48,(byte)0x4E,(byte)0x9B,(byte)0xF9,(byte)0xD3,(byte)0xF6,(byte)0x55,(byte)0xAC,(byte)0xCF,(byte)0xE0,(byte)0xE4,(byte)0x6D,(byte)0xB6,(byte)0x36,(byte)0x81,(byte)0x22,(byte)0x23,(byte)0x91,(byte)0x9E,(byte)0x62,(byte)0x0E,(byte)0xC4,(byte)0x5E,(byte)0x3A,(byte)0xB7,(byte)0xF4,(byte)0xB6,(byte)0xDA,(byte)0xD5,(byte)0xDA,(byte)0xDA,(byte)0xFF,(byte)0x5B,(byte)0x2D,(byte)0xFC,(byte)0xB7,(byte)0xB6,(byte)0x2F,(byte)0x79,(byte)0x54,(byte)0x94,(byte)0x61,(byte)0x96,(byte)0xA7,(byte)0x45,(byte)0x19,(byte)0x2E,(byte)0x8F,(byte)0x14,(byte)0x07,(byte)0x56,(byte)0x86,(byte)0x03,(byte)0x79,(byte)0x56,(byte)0x86,(byte)0xCB,(byte)0x23,(byte)0xF5,(byte)0x78,(byte)0x72,(byte)0x08,(byte)0xB6,(byte)0xB6,(byte)0x6C,(byte)0x69,(byte)0xCF,(byte)0xDE,(byte)0x78,(byte)0x6B,(byte)0x18,(byte)0x66,(byte)0xF5,(byte)0x48,(byte)0x91,(byte)0x25,(byte)0xB2,(byte)0x32,(byte)0x81,(byte)0xB0,(byte)0xB1,(byte)0xD4,(byte)0x10,(byte)0x9A,(byte)0xD5,(byte)0x23,(byte)0x35,(byte)0x29,(byte)0xDD,(byte)0x35,(byte)0xD2,(byte)0xC8,(byte)0xA5,(byte)0x85,(byte)0xBF,(byte)0x3B,(byte)0xF6,(byte)0x2B,(byte)0x23,(byte)0x94,(byte)0x62,(byte)0x75,(byte)0xE0,(byte)0xF4,(byte)0x41,(byte)0x26,(byte)0xC4,(byte)0x3A,(byte)0x70,(byte)0x2E,(byte)0x3E,(byte)0x8C,(byte)0x81,(byte)0x74,(byte)0xE0,(byte)0x94,(byte)0x5A,(byte)0x93,(byte)0x0E,(byte)0x9C,(byte)0x8B,(byte)0x0F,(byte)0x1F,(byte)0x4F,(byte)0x0E,(byte)0x36,(byte)0x04,(byte)0xA3,(byte)0x03,(byte)0xA7,(byte)0xD4,(byte)0xDA,(byte)0xE8,(byte)0xC0,(byte)0x29,(byte)0x25,(byte)0x36,(byte)0x7A,(byte)0x8A,(byte)0x58,(byte)0xCA,(byte)0x5E,(byte)0x07,(byte)0x4E,(byte)0xA9,(byte)0x75,(byte)0xA3,(byte)0x03,(byte)0xA7,(byte)0x94,(byte)0xB8,(byte)0xD1,(byte)0x2A,(byte)0xB7,(byte)0x0E,(byte)0x8D,(byte)0xE6,(byte)0x91,(byte)0x5A,(byte)0x37,(byte)0x5A,(byte)0xEE,(byte)0xB6,(byte)0x96,(byte)0x7A,(byte)0x1B,(byte)0xC9,(byte)0x32,(byte)0x3A,(byte)0x28,(byte)0x43,(byte)0x19,(byte)0x95,(byte)0xAC,(byte)0x32,(byte)0x5C,(byte)0x7C,(byte)0x58,(byte)0x67,(byte)0xA4,(byte)0xCA,(byte)0x50,(byte)0x0D,(byte)0x59,(byte)0x19,(byte)0x2E,(byte)0x3E,(byte)0x7C,(byte)0x7C,(byte)0xF5,(byte)0x64,(byte)0xFE,(byte)0x30,(byte)0xD6,(byte)0xCA,(byte)0xF4,(byte)0x21,(byte)0x3F,(byte)0x8C,(byte)0x21,(byte)0x98,(byte)0x3A,(byte)0x87,(byte)0x18,(byte)0x3D,(byte)0x25,(byte)0xE3,(byte)0x87,(byte)0xA9,(byte)0x13,(byte)0x88,(byte)0x6C,(byte)0xA6,(byte)0x69,(byte)0x0C,(byte)0x1A,(byte)0x11,(byte)0x67,(byte)0x1A,(byte)0x1D,(byte)0x45,(byte)0x16,(byte)0xA5,(byte)0xF2,(byte)0xBB,(byte)0x1A,(byte)0x7B,(byte)0x43,(byte)0xC9,(byte)0xAF,(byte)0xF6,(byte)0xA1,(byte)0x24,(byte)0x31,(byte)0x7F,(byte)0x02,(byte)0xFA,(byte)0x17,(byte)0xB8,(byte)0xCB,(byte)0x69,(byte)0xBF,(byte)0x00,(byte)0x5E,(byte)0x1F,(byte)0xE5,(byte)0xFF,(byte)0x7B,(byte)0x97,(byte)0x5F,(byte)0xCE,(byte)0x70,(byte)0xCE,(byte)0x63,(byte)0xA6,(byte)0x35,(byte)0xC7,(byte)0x9C,(byte)0xC7,(byte)0x68,(byte)0x09,(byte)0xD7,(byte)0x55,(byte)0x9C,(byte)0xF3,(byte)0x34,(byte)0x7F,(byte)0x7D,(byte)0x7A,(byte)0xC7,(byte)0x0B,(byte)0x62,(byte)0x1E,(byte)0xB2,(byte)0xEF,(byte)0xFB,(byte)0xE7,(byte)0xB4,(byte)0xFF,(byte)0x5A,(byte)0x99,(byte)0xFE,(byte)0xBE,(byte)0xC7,(byte)0x69,(byte)0xEF,(byte)0x9F,(byte)0x26,(byte)0x7C,(byte)0x72,(byte)0x2A,(byte)0x4C,(byte)0x96,(byte)0xF2,(byte)0x93,(byte)0x87,(byte)0xFE,(byte)0xBE,(byte)0x33,(byte)0xDF,(byte)0x10,(byte)0x2F,(byte)0x67,(byte)0xF8,(byte)0xE4,(byte)0x94,(byte)0x9D,(byte)0x4C,(byte)0x6F,(byte)0x27,(byte)0x97,(byte)0xCF,(byte)0xEB,(byte)0x1D,(byte)0x9B,(byte)0x46,(byte)0xF9,(byte)0xFF,(byte)0xAA,(byte)0xFC,(byte)0x72,(byte)0x86,(byte)0x4F,(byte)0x4E,(byte)0x99,(byte)0x3F,(byte)0x57,(byte)0xF3,(byte)0xAF,(byte)0xDE,(byte)0xFD,(byte)0x8E,(byte)0x77,(byte)0xBC,(byte)0xE3,(byte)0x1D,(byte)0x5F,(byte)0x8A,(byte)0xDF,(byte)0x02,(byte)0x0C,(byte)0x00,(byte)0x68,(byte)0xCB,(byte)0x8C,(byte)0x61,(byte)0x0A};
        //write(tintTransform);             
        
    }

    /**
     * Starts the stream, writes out the filters using the preset encoding, and
     * encodes the stream.
     */
    private void startStream() throws IOException {
        startStream(encode);
    }

    /**
     * Starts the stream, writes out the filters using the given encoding, and
     * encodes the stream.
     */
    private void startStream(String[] encode) throws IOException {
        if (dictionaryOpen) {
            PDFName[] filters = decodeFilters(encode);
            if (filters != null)
                entry("Filter", filters);

            super.close();
            dictionaryOpen = false;
            out.printPlain("stream\n");

            byteCountStream = new CountedByteOutputStream(out);
            stream = openFilters(byteCountStream, encode);
        }
    }

    private void write(int b) throws IOException {
        startStream();
        stream[0].write(b);
    }

    public void write(byte[] b) throws IOException {
        for (int i = 0; i < b.length; i++) {
            write((int) b[i]);
        }
    }

    private static PDFName[] decodeFilters(String[] encode) {
        PDFName[] filters = null;
        if ((encode != null) && (encode.length != 0)) {
            filters = new PDFName[encode.length];
            for (int i = 0; i < filters.length; i++) {
                filters[i] = new PDFName(encode[encode.length - i - 1]
                        + "Decode");
            }
        }
        return filters;
    }

    // open new stream using Standard Filters (see table 3.5)
    // stream[0] is the one to write to, the last one is s
    private static OutputStream[] openFilters(OutputStream s, String[] filters) {
        OutputStream[] os;
        if ((filters != null) && (filters.length != 0)) {
            os = new OutputStream[filters.length + 1];
            os[os.length - 1] = s;
            for (int i = os.length - 2; i >= 0; i--) {
                if (filters[i].equals("ASCIIHex")) {
                    os[i] = new ASCIIHexOutputStream(os[i + 1]);
                } else if (filters[i].equals("ASCII85")) {
                    os[i] = new ASCII85OutputStream(os[i + 1]);
                } else if (filters[i].equals("Flate")) {
                    os[i] = new FlateOutputStream(os[i + 1]);
                } else if (filters[i].equals("DCT")) {
                    os[i] = os[i + 1];
                } else {
                    System.err.println("PDFWriter: unknown stream format: "
                            + filters[i]);
                }
            }
        } else {
            os = new OutputStream[1];
            os[0] = s;
        }
        int test = os.length;
        return os;
    }

    // stream[0] is the first one to finish, the last one is not finished
    private static void closeFilters(OutputStream[] s) throws IOException {
        for (int i = 0; i < s.length - 1; i++) {
            s[i].flush();
            if (s[i] instanceof FinishableOutputStream) {
                ((FinishableOutputStream) s[i]).finish();
            }
        }
        s[s.length - 1].flush();
    }

    private void write(String s) throws IOException {
        byte[] b = s.getBytes("ISO-8859-1");
        for (int i = 0; i < b.length; i++) {
            write(b[i]);
        }
    }

    String getName() {
        return name;
    }

    public int getLength() {
        return byteCountStream.getCount();
    }

    public void print(String s) throws IOException {
        write(s);
    }

    public void println(String s) throws IOException {
        write(s);
        write(EOL);
    }

    public void comment(String comment) throws IOException {
        println("% " + comment);
    }

    // ==========================================================================
    // PDFStream Operators according to Table 4.1
    // ==========================================================================

    //
    // Graphics State operators (see Table 4.7)
    //
    private int gStates = 0;

    public void save() throws IOException {
        println("q");
        gStates++;
    }

    public void restore() throws IOException {
        if (gStates <= 0) {
            System.err.println("PDFStream: unbalanced saves()/restores(), too many restores");
        }
        gStates--;
        println("Q");
    }

    public void matrix(AffineTransform xform) throws IOException {
        matrix(xform.getScaleX(), xform.getShearY(), xform.getShearX(), xform
                .getScaleY(), xform.getTranslateX(), xform.getTranslateY());
    }

    public void matrix(double m00, double m10, double m01, double m11,
            double m02, double m12) throws IOException {
        println(PDFUtil.fixedPrecision(m00) + " " + PDFUtil.fixedPrecision(m10)
                + " " + PDFUtil.fixedPrecision(m01) + " "
                + PDFUtil.fixedPrecision(m11) + " "
                + PDFUtil.fixedPrecision(m02) + " "
                + PDFUtil.fixedPrecision(m12) + " cm");
    }

    public void width(double width) throws IOException {
        println(PDFUtil.fixedPrecision(width) + " w");
    }

    public void cap(int capStyle) throws IOException {
        println(capStyle + " J");
    }

    public void join(int joinStyle) throws IOException {
        println(joinStyle + " j");
    }

    public void mitterLimit(double limit) throws IOException {
        println(PDFUtil.fixedPrecision(limit) + " M");
    }

    public void dash(int[] dash, double phase) throws IOException {
        print("[");
        for (int i = 0; i < dash.length; i++) {
            print(" " + PDFUtil.fixedPrecision(dash[i]));
        }
        println("] " + PDFUtil.fixedPrecision(phase) + " d");
    }

    public void dash(float[] dash, double phase) throws IOException {
        print("[");
        for (int i = 0; i < dash.length; i++) {
            print(" " + PDFUtil.fixedPrecision(dash[i]));
        }
        println("] " + PDFUtil.fixedPrecision(phase) + " d");
    }

    public void flatness(double flatness) throws IOException {
        println(PDFUtil.fixedPrecision(flatness) + " i");
    }

    public void state(PDFName stateDictionary) throws IOException {
        println(stateDictionary + " gs");
    }

    //
    // Path Construction operators (see Table 4.9)
    //
    public void cubic(double x1, double y1, double x2, double y2, double x3,
            double y3) throws IOException {
        println(PDFUtil.fixedPrecision(x1) + " " + PDFUtil.fixedPrecision(y1)
                + " " + PDFUtil.fixedPrecision(x2) + " "
                + PDFUtil.fixedPrecision(y2) + " " + PDFUtil.fixedPrecision(x3)
                + " " + PDFUtil.fixedPrecision(y3) + " c");
    }

    public void cubicV(double x2, double y2, double x3, double y3)
            throws IOException {
        println(PDFUtil.fixedPrecision(x2) + " " + PDFUtil.fixedPrecision(y2)
                + " " + PDFUtil.fixedPrecision(x3) + " "
                + PDFUtil.fixedPrecision(y3) + " v");
    }

    public void cubicY(double x1, double y1, double x3, double y3)
            throws IOException {
        println(PDFUtil.fixedPrecision(x1) + " " + PDFUtil.fixedPrecision(y1)
                + " " + PDFUtil.fixedPrecision(x3) + " "
                + PDFUtil.fixedPrecision(y3) + " y");
    }

    public void move(double x, double y) throws IOException {
        println(PDFUtil.fixedPrecision(x) + " " + PDFUtil.fixedPrecision(y)
                + " m");
    }

    public void line(double x, double y) throws IOException {
        println(PDFUtil.fixedPrecision(x) + " " + PDFUtil.fixedPrecision(y)
                + " l");
    }

    public void closePath() throws IOException {
        println("h");
    }

    public void rectangle(double x, double y, double width, double height)
            throws IOException {
        println(PDFUtil.fixedPrecision(x) + " " + PDFUtil.fixedPrecision(y)
                + " " + PDFUtil.fixedPrecision(width) + " "
                + PDFUtil.fixedPrecision(height) + " re");
    }

    //
    // Path Painting operators (see Table 4.10)
    //
    public void stroke() throws IOException {
        println("S");
    }

    public void closeAndStroke() throws IOException {
        println("s");
    }

    public void fill() throws IOException {
        println("f");
    }

    public void fillEvenOdd() throws IOException {
        println("f*");
    }

    public void fillAndStroke() throws IOException {
        println("B");
    }

    public void fillEvenOddAndStroke() throws IOException {
        println("B*");
    }

    public void closeFillAndStroke() throws IOException {
        println("b");
    }

    public void closeFillEvenOddAndStroke() throws IOException {
        println("b*");
    }

    public void endPath() throws IOException {
        println("n");
    }

    //
    // Clipping Path operators (see Table 4.11)
    //
    public void clip() throws IOException {
        println("W");
    }

    public void clipEvenOdd() throws IOException {
        println("W*");
    }

    //
    // Text Object operators (see Table 5.4)
    //
    private boolean textOpen = false;

    public void beginText() throws IOException {
        if (textOpen)
            System.err.println("PDFStream: nested beginText() not allowed.");
        println("BT");
        textOpen = true;
    }

    public void endText() throws IOException {
        if (!textOpen)
            System.err
                    .println("PDFStream: unbalanced use of beginText()/endText().");
        println("ET");
        textOpen = false;
    }

    //
    // Text State operators (see Table 5.2)
    //
    public void charSpace(double charSpace) throws IOException {
        println(PDFUtil.fixedPrecision(charSpace) + " Tc");
    }

    public void wordSpace(double wordSpace) throws IOException {
        println(PDFUtil.fixedPrecision(wordSpace) + " Tw");
    }

    public void scale(double scale) throws IOException {
        println(PDFUtil.fixedPrecision(scale) + " Tz");
    }

    public void leading(double leading) throws IOException {
        println(PDFUtil.fixedPrecision(leading) + " TL");
    }

    private boolean fontWasSet = false;

    public void font(PDFName fontName, double size) throws IOException {
        println(fontName + " " + PDFUtil.fixedPrecision(size) + " Tf");
        fontWasSet = true;
    }

    public void rendering(int mode) throws IOException {
        println(mode + " Tr");
    }

    public void rise(double rise) throws IOException {
        println(PDFUtil.fixedPrecision(rise) + " Ts");
    }

    //
    // Text Positioning operators (see Table 5.5)
    //
    public void text(double x, double y) throws IOException {
        println(PDFUtil.fixedPrecision(x) + " " + PDFUtil.fixedPrecision(y)
                + " Td");
    }

    public void textLeading(double x, double y) throws IOException {
        println(PDFUtil.fixedPrecision(x) + " " + PDFUtil.fixedPrecision(y)
                + " TD");
    }

    public void textMatrix(double a, double b, double c, double d, double e,
            double f) throws IOException {
        println(PDFUtil.fixedPrecision(a) + " " + PDFUtil.fixedPrecision(b)
                + " " + PDFUtil.fixedPrecision(c) + " "
                + PDFUtil.fixedPrecision(d) + " " + PDFUtil.fixedPrecision(e)
                + " " + PDFUtil.fixedPrecision(f) + " Tm");
    }

    public void textLine() throws IOException {
        println("T*");
    }

    //
    // Text Showing operators (see Table 5.6)
    //
    public void show(String text) throws IOException {
        if (!fontWasSet)
            System.err
                    .println("PDFStream: cannot use Text Showing operator before font is set.");
        if (!textOpen)
            System.err
                    .println("PDFStream: Text Showing operator only allowed inside Text section.");
        println("(" + PDFUtil.escape(text) + ") Tj");
    }

    public void showLine(String text) throws IOException {
        if (!fontWasSet)
            System.err
                    .println("PDFStream: cannot use Text Showing operator before font is set.");
        if (!textOpen)
            System.err
                    .println("PDFStream: Text Showing operator only allowed inside Text section.");
        println("(" + PDFUtil.escape(text) + ") '");
    }

    public void showLine(double wordSpace, double charSpace, String text)
            throws IOException {
        if (!fontWasSet)
            System.err
                    .println("PDFStream: cannot use Text Showing operator before font is set.");
        if (!textOpen)
            System.err
                    .println("PDFStream: Text Showing operator only allowed inside Text section.");
        println(PDFUtil.fixedPrecision(wordSpace) + " "
                + PDFUtil.fixedPrecision(charSpace) + " ("
                + PDFUtil.escape(text) + ") \"");
    }

    public void show(Object[] array) throws IOException {
        print("[");
        for (int i = 0; i < array.length; i++) {
            Object object = array[i];
            if (object instanceof String) {
                print(" (" + PDFUtil.escape(object.toString()) + ")");
            } else if (object instanceof Integer) {
                print(" " + ((Integer) object).intValue());
            } else if (object instanceof Double) {
                print(" " + ((Double) object).doubleValue());
            } else {
                System.err
                        .println("PDFStream: input array of operator TJ may only contain objects of type 'String', 'Integer' or 'Double'");
            }
        }
        println("] TJ");
    }

    //
    // Type 3 Font operators (see Table 5.10)
    //
    public void glyph(double wx, double wy) throws IOException {
        println(PDFUtil.fixedPrecision(wx) + " " + PDFUtil.fixedPrecision(wy)
                + " d0");
    }

    public void glyph(double wx, double wy, double llx, double lly, double urx,
            double ury) throws IOException {
        println(PDFUtil.fixedPrecision(wx) + " " + PDFUtil.fixedPrecision(wy)
                + " " + PDFUtil.fixedPrecision(llx) + " "
                + PDFUtil.fixedPrecision(lly) + " "
                + PDFUtil.fixedPrecision(urx) + " "
                + PDFUtil.fixedPrecision(ury) + " d1");
    }

    //
    // Color operators (see Table 4.21)
    //
    public void colorSpace(PDFName colorSpace) throws IOException {
        println(colorSpace + " cs");
    }

    public void colorSpaceStroke(PDFName colorSpace) throws IOException {
        println(colorSpace + " CS");
    }

    public void colorSpace(double[] color) throws IOException {
        for (int i = 0; i < color.length; i++) {
            print(" " + color[i]);
        }
        println(" scn");
    }

    public void colorSpaceStroke(double[] color) throws IOException {
        for (int i = 0; i < color.length; i++) {
            print(" " + color[i]);
        }
        println(" SCN");
    }

    public void colorSpace(double[] color, PDFName name) throws IOException {
        if (color != null) {
            for (int i = 0; i < color.length; i++) {
                print(PDFUtil.fixedPrecision(color[i]) + " ");
            }
        }
        println(name + " scn");
    }

    public void colorSpaceStroke(double[] color, PDFName name)
            throws IOException {
        if (color != null) {
            for (int i = 0; i < color.length; i++) {
                print(PDFUtil.fixedPrecision(color[i]) + " ");
            }
        }
        println(name + " SCN");
    }

    public void colorSpace(double g) throws IOException {
        println(PDFUtil.fixedPrecision(g) + " g");
    }

    public void colorSpaceStroke(double g) throws IOException {
        println(PDFUtil.fixedPrecision(g) + " G");
    }

    public void colorSpace(double r, double g, double b) throws IOException {
        println(PDFUtil.fixedPrecision(r) + " " + PDFUtil.fixedPrecision(g)
                + " " + PDFUtil.fixedPrecision(b) + " rg");
    }

    public void colorSpaceStroke(double r, double g, double b)
            throws IOException {
        println(PDFUtil.fixedPrecision(r) + " " + PDFUtil.fixedPrecision(g)
                + " " + PDFUtil.fixedPrecision(b) + " RG");
    }

    public void colorSpace(double c, double m, double y, double k)
            throws IOException {
        println(PDFUtil.fixedPrecision(c) + " " + PDFUtil.fixedPrecision(m)
                + " " + PDFUtil.fixedPrecision(y) + " "
                + PDFUtil.fixedPrecision(k) + " k");
    }

    public void colorSpaceStroke(double c, double m, double y, double k)
            throws IOException {
        println(PDFUtil.fixedPrecision(c) + " " + PDFUtil.fixedPrecision(m)
                + " " + PDFUtil.fixedPrecision(y) + " "
                + PDFUtil.fixedPrecision(k) + " K");
    }

    //
    // Shading Pattern operator (see Table 4.24)
    //
    public void shade(PDFName name) throws IOException {
        println(name + " sh");
    }

    /**
     * returns the decode-format for an image -format
     *
     * @param encode {@link ImageConstants#ZLIB} or {@link ImageConstants#JPG}
     * @return {@link #decodeFilters(String[])}
     */
    private PDFName[] getFilterName(String encode) {
        if (ImageConstants.ZLIB.equals(encode)) {
            return decodeFilters(new String[] {
                ImageConstants.ENCODING_FLATE,
                ImageConstants.ENCODING_ASCII85});
        }

        if (ImageConstants.JPG.equals(encode)) {
            return decodeFilters(new String[] {
                ImageConstants.ENCODING_DCT,
                ImageConstants.ENCODING_ASCII85});
        }

        throw new IllegalArgumentException("unknown image encoding " + encode  + " for PDFStream");
    }

    /**
     * Image convenience function (see Table 4.35). Ouputs the data of the image
     * using "DeviceRGB" colorspace, and the requested encodings
     *
     * @param image Image to write
     * @param bkg Background color, null for transparent image
     * @param encode {@link org.freehep.graphicsio.ImageConstants#ZLIB} or {@link org.freehep.graphicsio.ImageConstants#JPG}
     * @throws java.io.IOException thrown by ImageBytes
     */
    public void image(RenderedImage image, Color bkg, String encode)
            throws IOException {

        ImageBytes bytes = new ImageBytes(image, bkg, encode, ImageConstants.COLOR_MODEL_RGB);

        entry("Width", image.getWidth());
        entry("Height", image.getHeight());
        entry("ColorSpace", pdf.name("DeviceRGB"));
        entry("BitsPerComponent", 8);
        entry("Filter", getFilterName(bytes.getFormat()));
        write(bytes.getBytes());
    }

    public void imageMask(RenderedImage image, String encode)
            throws IOException {

        ImageBytes bytes = new ImageBytes(image, null, encode, ImageConstants.COLOR_MODEL_A);

        entry("Width", image.getWidth());
        entry("Height", image.getHeight());
        entry("BitsPerComponent", 8);
        entry("ColorSpace", pdf.name("DeviceGray"));
        entry("Filter", getFilterName(bytes.getFormat()));
        write(bytes.getBytes());
    }


    /**
     * Inline Image convenience function (see Table 4.39 and 4.40). Ouputs the
     * data of the image using "DeviceRGB" colorspace, and the requested
     * encoding.

     * @param image Image to write
     * @param bkg Background color, null for transparent image
     * @param encode {@link org.freehep.graphicsio.ImageConstants#ZLIB} or {@link org.freehep.graphicsio.ImageConstants#JPG}
     * @throws java.io.IOException thrown by ImageBytes
     */
    public void inlineImage(RenderedImage image, Color bkg, String encode)
            throws IOException {

        ImageBytes bytes = new ImageBytes(image, bkg, ImageConstants.JPG, ImageConstants.COLOR_MODEL_RGB);

        println("BI");
        imageInfo("Width", image.getWidth());
        imageInfo("Height", image.getHeight());
        imageInfo("ColorSpace", pdf.name("DeviceRGB"));
        imageInfo("BitsPerComponent", 8);

        imageInfo("Filter", getFilterName(bytes.getFormat()));
        print("ID\n");

        write(bytes.getBytes());

        println("\nEI");
    }

    //
    // In-line Image operators (see Table 4.38)
    //
    private void imageInfo(String key, int number) throws IOException {
        println("/" + key + " " + number);
    }

    private void imageInfo(String key, PDFName name) throws IOException {
        println("/" + key + " " + name);
    }

    private void imageInfo(String key, Object[] array) throws IOException {
        print("/" + key + " [");
        for (int i = 0; i < array.length; i++) {
            print(" " + array[i]);
        }
        println("]");
    }

    /**
     * Draws the <i>points</i> of the shape using path <i>construction</i>
     * operators. The path is neither stroked nor filled.
     * 
     * @return true if even-odd winding rule should be used, false if non-zero
     *         winding rule should be used.
     */
    //public boolean drawPath(Shape s) throws IOException {
    //   PDFPathConstructor path = new PDFPathConstructor(this);
    //    return path.addPath(s);
   // }

    //
    // XObject operators (see Table 4.34)
    //
    public void xObject(PDFName name) throws IOException {
        println(name + " Do");
    }

    //
    // Marked Content operators (see Table 8.5)
    //
    // FIXME: missing all

    //
    // Compatibility operators (see Table 3.19)
    //
    private boolean compatibilityOpen = false;

    public void beginCompatibility() throws IOException {
        if (compatibilityOpen)
            System.err
                    .println("PDFStream: nested use of Compatibility sections not allowed.");
        println("BX");
        compatibilityOpen = true;
    }

    public void endCompatibility() throws IOException {
        if (!compatibilityOpen)
            System.err
                    .println("PDFStream: unbalanced use of begin/endCompatibilty().");
        println("EX");
        compatibilityOpen = false;
    }

}

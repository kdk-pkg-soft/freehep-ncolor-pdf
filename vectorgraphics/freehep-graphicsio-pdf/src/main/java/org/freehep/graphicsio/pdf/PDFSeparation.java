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
public class PDFSeparation extends PDFDictionary implements PDFConstants {

    private String name;

    private PDFObject object;

    private boolean dictionaryOpen;

    private OutputStream[] stream;

    private CountedByteOutputStream byteCountStream;

    private String[] encode;

    PDFSeparation(PDF pdf, PDFByteWriter writer, String spotName, PDFObject parent) throws IOException {
    	super(pdf, writer,parent,true);                
        object = parent;
        if (object == null)
            System.err
                    .println("PDFWriter: 'PDFStream' cannot have a null parent");
        // first write the dictionary
        dictionaryOpen = true;        
        entry("Separation", pdf.name(spotName)); 
        // Change to use RGB for rendering TT
        //entry("DeviceCMYK", pdf.ref(spotName+"Function"));                          
        entry("DeviceRGB", pdf.ref(spotName+"Function")); 
    }

}

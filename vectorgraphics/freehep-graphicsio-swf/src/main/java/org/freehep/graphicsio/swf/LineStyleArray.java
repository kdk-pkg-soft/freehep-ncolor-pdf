// Copyright 2001-2006, FreeHEP.
package org.freehep.graphicsio.swf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * SWF LineStyleArray.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: LineStyleArray.java 9979 2006-11-27 22:51:07Z duns $
 */
public class LineStyleArray {

    protected List /* LineStyle */lineStyles;

    public LineStyleArray() {
        this.lineStyles = new ArrayList();
    }

    public LineStyleArray(SWFInputStream swf, boolean isMorphStyle,
            boolean hasAlpha, boolean hasStyles) throws IOException {

        this();

        int lineStyleCount = swf.readUnsignedByte();
        if (lineStyleCount == 0xFF) {
            lineStyleCount = swf.readUnsignedShort();
        }
        for (int i = 0; i < lineStyleCount; i++) {
            lineStyles.add(new LineStyle(swf, isMorphStyle, hasAlpha, hasStyles));
        }
    }

    public void add(LineStyle lineStyle) {
        lineStyles.add(lineStyle);
    }

    public LineStyle get(int index) {
        return (LineStyle) lineStyles.get(index);
    }

    public void write(SWFOutputStream swf, boolean isMorphStyle, boolean hasAlpha, boolean hasStyles) throws IOException {

        if (lineStyles.size() >= 0xFF) {
            swf.writeUnsignedByte(0xFF);
            swf.writeUnsignedShort(lineStyles.size());
        } else {
            swf.writeUnsignedByte(lineStyles.size());
        }
        for (Iterator i = lineStyles.iterator(); i.hasNext();) {
            ((LineStyle) i.next()).write(swf, isMorphStyle, hasAlpha, hasStyles);
        }
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("  lineStyles: " + lineStyles.size() + "\n");
        int n = 0;
        for (Iterator i = lineStyles.iterator(); i.hasNext();) {
            s.append("    " + (n + 1) + " " + (i.next()) + "\n");
            n++;
        }
        return s.toString();
    }

}

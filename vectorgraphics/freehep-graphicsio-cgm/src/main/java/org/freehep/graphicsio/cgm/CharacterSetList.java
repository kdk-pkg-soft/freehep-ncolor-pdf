// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * CharacterSetList TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: CharacterSetList.java 8584 2006-08-10 23:06:37Z duns $
 */
public class CharacterSetList extends CGMTag {

    public static final int GSET_94 = 0;

    public static final int GSET_96 = 1;

    public static final int GSET_94_MULTIBYTE = 2;

    public static final int GSET_96_MULTIBYTE = 3;

    public static final int COMPLETE = 4;

    private int[] type;

    private String[] designation;

    public CharacterSetList() {
        super(1, 14, 1);
    }

    public CharacterSetList(int[] type, String[] designation) {
        this();
        this.type = type;
        this.designation = designation;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {

        for (int i = 0; i < type.length; i++) {
            cgm.writeEnumerate(type[i]);
            cgm.writeString(designation[i]);
        }
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.println("CHARSETLIST");
        cgm.indent();
        for (int i = 0; i < type.length; i++) {
            switch (type[i]) {
            default:
            case GSET_94:
                cgm.print("STD94");
                break;
            case GSET_96:
                cgm.print("STD96");
                break;
            case GSET_94_MULTIBYTE:
                cgm.print("STD94MULTIBYTE");
                break;
            case GSET_96_MULTIBYTE:
                cgm.print("STD96MULTIBYTE");
                break;
            case COMPLETE:
                cgm.print("COMPLETECODE");
                break;
            }
            cgm.print(", ");
            cgm.writeString(designation[i]);
            cgm.println();
        }
        cgm.outdent();
    }
}

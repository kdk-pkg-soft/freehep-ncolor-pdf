// Copyright 2004, FreeHEP.
package org.freehep.postscript;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * DCS (Document Structuring Conventions) level 3.0 for PostScript Processor
 *
 * as specified in PostScript Language Document Structuring Conventions
 * Specification, version 3.0, 25 September 1992.
 *
 * @author Mark Donszelmann
 * @version $Id: DSC.java 10178 2006-12-08 09:03:07Z duns $
 */
public class DSC {
     
    private static final String[] mode = {"diablo630", "fx100", "lj2000", "hpgl", "impress", "hplj", "ti855"};
    private static final String[] orientation = {"Portrait", "Landscape"};
    private static final String[] cmyk = {"Cyan", "Magenta", "Yellow", "Black"};
        
    private final static Object[] keyArgs = {
        //
        // 5.1 General Header Comments
        //
        "PS-Adobe-3.0",         TextLine.class,
        "PS-Adobe-2.1",         NoArgs.class,
        "PS-Adobe-2.0",         NoArgs.class,
        "PS-Adobe-1.0",         NoArgs.class,
        "BoundingBox:",         Rectangle.class,
        "Copyright:",           TextLine.class, 
        "Creator:",             TextLine.class, 
        "CreationDate:",        TextLine.class, 
        "DocumentData:",        new String[] {"Clean7Bit", "Clean8Bit", "Binary"},
        "Emulation:",           mode, 
        "EndComments",          NoArgs.class,
        "Extensions:",          new String[] {"DPS", "CMYK", "Composite", "FileSystem"},
        "For:",                 TextLine.class, 
        "LanguageLevel:",       UInt.class,
        "Orientation:",         orientation,
        "Pages:",               UInt.class,
        "PageOrder:",           new String[] {"Ascend", "Descend", "Special"},
        "Routing:",             TextLine.class, 
        "Title:",               TextLine.class, 
        "Version:",             Version.class,
        
        //
        // 5.2 General Body Comments
        //
        "BeginBinary:",         UInt.class,
        "EndBinary",            NoArgs.class,
        "BeginData:",           Unparsed.class,
        "EndData",              NoArgs.class,
        "BeginDefaults",        NoArgs.class,
        "EndDefaults",          NoArgs.class,
        "BeginEmulation:",      mode,
        "EndEmulation",         NoArgs.class,
        "BeginPreview:",        Unparsed.class,
        "EndPreview",           NoArgs.class,
        "BeginProlog",          NoArgs.class,
        "EndProlog",            NoArgs.class,
        "BeginSetup",           NoArgs.class,
        "EndSetup",             NoArgs.class,
        
        //
        // 5.3 General Page Comments
        //
        "BeginObject:",         Unparsed.class,
        "EndObject",            NoArgs.class,
        "BeginPageSetup",       NoArgs.class,
        "EndPageSetup",         NoArgs.class,
        "Page:",                Page.class,
        "PageBoundingBox:",     Rectangle.class,
        "PageOrientation:",     orientation,  
        
        //
        // 5.4 General Trailer Comments
        //
        "PageTrailer",          NoArgs.class,
        "Trailer",              NoArgs.class,
        "EOF",                  NoArgs.class,
        
        // 
        // 6.1 Requirement Header Comments
        // 
        "DocumentMedia:",               Unparsed.class,
        "DocumentNeededResources:",     Unparsed.class,
        "DocumentSuppliedResources:",   Unparsed.class,
        "DocumentPrinterRequired:",     Unparsed.class,
        "DocumentNeededFiles:",         Unparsed.class,
        "DocumentSuppliedFiles:",       Unparsed.class,
        "DocumentFonts:",               Unparsed.class,
        "DocumentNeededFonts:",         Unparsed.class,
        "DocumentSuppliedFonts:",       Unparsed.class,
        "DocumentProcSets:",            Unparsed.class,
        "DocumentNeededProcSets:",      Unparsed.class,
        "DocumentSuppliedProcSets:",    Unparsed.class,
        "OperatorIntervention:",        Unparsed.class,
        "OperatorMessage:",             Unparsed.class,
        "ProofMode:",                   new String[] {"TrustMe", "Substitute", "NotifyMe"},
        "Requirements:",                Unparsed.class,
        "VMlocation:",                  new String[] {"global", "local"},
        "VMusage:",                     Unparsed.class,
        
        //
        // 6.2 Requirements Body Comments
        //
        "BeginDocument:",       Unparsed.class,
        "EndDocument",          NoArgs.class,
        "IncludeDocument:",     Unparsed.class,
        "BeginFeature:",        Unparsed.class,
        "EndFeature",           NoArgs.class,
        "IncludeFeature:",      Unparsed.class,
        "BeginFile:",           Unparsed.class,
        "EndFile",              NoArgs.class,
        "IncludeFile:",         Unparsed.class,
        "BeginFont:",           Unparsed.class,
        "EndFont",              NoArgs.class,
        "IncludeFont:",         Unparsed.class,
        "BeginProcSet:",        Unparsed.class,
        "EndProcSet",           NoArgs.class,
        "IncludeProcSet:",      Unparsed.class,
        "BeginResource:",       Unparsed.class,
        "EndResource",          NoArgs.class,
        "IncludeResource:",     Unparsed.class,

        //
        // 6.3 Requirements Page Comments
        //
        "PageFonts:",           Unparsed.class,
        "PageFiles:",           Unparsed.class,
        "PageMedia:",           Unparsed.class,
        "PageRequirements:",    Unparsed.class,
        "PageResources:",       Unparsed.class,

        //
        // 7.1 Color Header Comments
        //
        "CMYKCustomColor:",         Unparsed.class,
        "DocumentCustomColors:",    Unparsed.class,
        "DocumentProcessColors:",   Unparsed.class,
        "RGBCustomColor:",          Unparsed.class,
        
        //
        // 7.2 Color Body Comments
        //
        "BeginCustomColor:",        Unparsed.class,
        "EndCustomColor",           NoArgs.class,
        "BeginProcessColor:",       cmyk,
        "EndProcesColor",           NoArgs.class,
        
        // 
        // 7.3 Color Page Comments
        // 
        "PageCustomColors:",        Unparsed.class,
        "PageProcessColors:",       Unparsed.class,
        
        //     
        // 8.2 Query Comments
        //
        "?BeginFeatureQuery:",      Unparsed.class,
        "?EndFeatureQuery:",        Unparsed.class,
        "?BeginFileQuery:",         Unparsed.class,
        "?EndFileQuery:",           Unparsed.class,
        "?BeginFontListQuery",      NoArgs.class,
        "?EndFontListQuery:",       Unparsed.class,
        "?BeginFontQuery:",         Unparsed.class,
        "?EndFontQuery:",           Unparsed.class,
        "?BeginPrinterQuery",       NoArgs.class,
        "?EndPrinterQuery:",        Unparsed.class,
        "?BeginQuery:",             Unparsed.class,
        "?EndQuery:",               Unparsed.class,
        "?BeginQuery:",             Unparsed.class,
        "?EndQuery:",               Unparsed.class,
        "?BeginQuery:",             Unparsed.class,
        "?EndQuery:",               Unparsed.class,
        "?BeginResourceListQuery:", new String[] {"font", "file", "procset", "pattern", "form", "encoding"},
        "?EndResourceListQuery:",   Unparsed.class,
        "?BeginResourceQuery:",     Unparsed.class,
        "?EndResourceQuery:",       Unparsed.class,
        "?BeginVMStatus",           NoArgs.class,
        "?EndVMStatus:",            Unparsed.class,
        
        //
        // 10. Special Structuring Comments
        //
        "?BeginExitServer:",        Unparsed.class,
        "?EndExitServer",           NoArgs.class,
    };

    private Collection listeners = new ArrayList();
    private Map parseTable = new HashMap();
    private Map dscTable = new HashMap();

    public DSC() {
        // fill the parseTable
        for (int i=0; i<keyArgs.length; i+=2) {
            String token = (String)keyArgs[i];
            try {
                Object obj = keyArgs[i+1];
                if (obj instanceof Class) {
                    obj = ((Class)obj).newInstance();
                } else if (obj instanceof String[]) {
                    obj = new Enumeration((String[])obj);
                }
                addDSCComment(token, obj);
            } catch (InstantiationException ie) {
                System.err.println("DSC: could not instantiate class: '"+token);
            } catch (IllegalAccessException ie) {
                System.err.println("DSC: no access to class: '"+token);
            }
        }
    }
    
    public Object addDSCComment(String comment, Object parse) {
        Object obj = parseTable.get(comment);
        parseTable.put(comment, parse);
        return obj;
    }
  
    public void addDSCEventListener(DSCEventListener listener) {
        listeners.add(listener);
    }
    
    public void removeDSCEventListener(DSCEventListener listener) {
        listeners.remove(listener);
    }
    
    // Parses a DCS comment (not pre-pended with the %% signs, but including the appended colon and arguments).
    public boolean parse(String line, OperandStack os) {
        String[] tokens = line.split("[ \t]", 2);
        String key = tokens[0];
        String params = "";
        if (tokens.length == 1) {
            // special case if space is missing after colon
            tokens = line.split(":", 2);
            if (tokens.length == 2) {
                key = tokens[0]+":";
                params = tokens[1];
            }
        } else {
            params = tokens[1];
        }
        
        // lookup comment, parse arguments and derive the state
        Arguments args = (Arguments)parseTable.get(key);
        int state;
        if (params.startsWith("(atend)")) {
            dscTable.put(key, new Boolean(true));
            state = (args != null) ? DSCEvent.PARSED : DSCEvent.UNPARSED;
        } else {
            if (args != null) {
                Object parsed = args.parse(key, params, os);
                if (parsed != null) {
                    dscTable.put(key, parsed);
                    state = DSCEvent.PARSED;
                } else {
                    state = DSCEvent.ERROR;
                }            
            } else {
                dscTable.put(key, params);
                state = DSCEvent.UNPARSED;
            }
        }
        
        // inform the listeners
        for (Iterator i=listeners.iterator(); i.hasNext(); ) {
            DSCEventListener listener = (DSCEventListener)i.next();
            listener.dscCommentFound(new DSCEvent(this, key, dscTable.get(key), state));
        }
        
        return state != DSCEvent.ERROR;
    }
       
    public Object get(String key) {
        return dscTable.get(key);
    }
        
    public static String text(String s) {
        if (s.startsWith("(") && s.endsWith(")")) {
            s = s.substring(1, s.length()-2);
        }
        return s;
    }    
        
    public static interface Arguments {
        public Object parse(String key, String params, OperandStack os);
    }
     
    public static class NoArgs implements Arguments {
        public Object parse(String key, String params, OperandStack os) {
            return new Boolean(true);
        }
    }
    
    public static class TextLine implements Arguments {
        public Object parse(String key, String params, OperandStack os) {
            return text(params);
        }
    }

    public static class Rectangle implements Arguments {
        public Object parse(String key, String params, OperandStack os) {
            try {
                String[] tokens = params.split("[ \t]", 4);
                if (tokens.length != 4) return null;
                int[] p = new int[4];
                for (int i=0; i<tokens.length; i++) {
                    p[i] = Integer.parseInt(tokens[i]);
                }
                return new java.awt.Rectangle(p[0], p[1], p[2]-p[0], p[3]-p[1]);
            } catch (NumberFormatException e) {
                return null;
            }            
        }
    }

    public static class UInt implements Arguments {
        public Object parse(String key, String params, OperandStack os) {
            try {
                String[] tokens = params.split("[ \t]", 2);
                return Long.valueOf(tokens[0]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    public static class Enumeration implements Arguments {
        private String[] enumeration;
        
        public Enumeration(String[] enumeration) {
            this.enumeration = enumeration;
        }
        
        public Object parse(String key, String params, OperandStack os) {
            String[] tokens = params.split("[ \t]", 2);
            for (int i=0; i<enumeration.length; i++) {
                if (enumeration[i].equals(tokens[0])) {
                    return tokens[0];
                }
            }
            return null;
        }
    }

    public static class Unparsed implements Arguments {
        public Object parse(String key, String params, OperandStack os) {
            return params;
        }
    }

//
// 5.1
//
    public static class Version implements Arguments {
        private double version;
        private long revision;

        public Version() {
        }
        
        public Version(double v, long r) {
            version = v;
            revision = r;
        }
        
        public double getVersion() {
            return version;
        }
        
        public long getRevision() {
            return revision;
        }
        
        public Object parse(String key, String params, OperandStack os) {
            try {
                String[] tokens = params.split("[ \t]", 2);            
                if (tokens.length != 2) return null;
                
                double v = Double.parseDouble(tokens[0]);
                long r = Long.parseLong(tokens[1]);
                
                return new Version(v, r);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        
        public String toString() {
            return "v"+version+"r"+revision;
        }
    }
  
//    
// 5.3
//
    public static class Page implements Arguments {
        private String label;
        private long number;

        public Page() {
        }
        
        public Page(String l, long n) {
            label = l;
            number = n;
        }
        
        public String getLabel() {
            return label;
        }
        
        public long getNumber() {
            return number;
        }
        
        public Object parse(String key, String params, OperandStack os) {
            try {
                String[] tokens = params.split("[ \t]", 2);            
                if (tokens.length != 2) return null;
                
                long n = Long.parseLong(tokens[1]);
                
                return new Page(text(tokens[0]), n);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        
        public String toString() {
            return label+" "+number;
        }
    }
    
}

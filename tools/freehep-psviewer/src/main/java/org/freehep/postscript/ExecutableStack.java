// Copyright 2001, FreeHEP.
package org.freehep.postscript;

/**
 * Excutable Stack for PostScript Processor
 *
 * @author Mark Donszelmann
 * @version $Id: ExecutableStack.java 8958 2006-09-12 23:37:43Z duns $
 */
public class ExecutableStack extends PostScriptStack {
    
    public PSFile getCurrentFile() {
        for (int i=elementCount-1; i>=0; i--) {
            if (elementData[i] instanceof PSFile) {
                return (PSFile)elementData[i];
            }
        }
        return null;
    }
    
    public String toString() {
        return "ExecutionStack";
    }
}
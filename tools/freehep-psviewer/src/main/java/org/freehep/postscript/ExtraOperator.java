// Copyright 2001, FreeHEP.
package org.freehep.postscript;

/**
 * Extra Operators for PostScript Processor
 *
 * @author Mark Donszelmann
 * @version $Id: ExtraOperator.java 8958 2006-09-12 23:37:43Z duns $
 */
public class ExtraOperator extends PSOperator {
    
    public static Class[] operators = {
        Break.class
    };

    public boolean execute(OperandStack os) {
        throw new RuntimeException("Cannot execute class: "+getClass());
    }
}

class Break extends ExtraOperator {

    public boolean execute(OperandStack os) {
        os.execStack().pop();
        throw new BreakException();
    }
}
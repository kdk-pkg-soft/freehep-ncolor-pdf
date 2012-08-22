// Copyright 2001, FreeHEP.
package org.freehep.postscript;


/**
 * OperandStack for PostScript Processor
 *
 * @author Mark Donszelmann
 * @version $Id: GStateStack.java 10178 2006-12-08 09:03:07Z duns $
 */
public class GStateStack extends PostScriptStack {
            
    public GStateStack() {
        super();
    }
    
    public Object push(Object o){
        throw new IllegalArgumentException("Only PSGState allowed on stack.");
    }
    
    public PSGState push(PSGState gs) {
        return (PSGState)super.push(gs);
    }
        
    public PSGState popGState() {
        return (PSGState)pop();
    }
 
    public String toString() {
        return "GStateStack";
    }
}
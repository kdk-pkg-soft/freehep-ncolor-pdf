// Copyright 2000, CERN, Geneva, Switzerland
package hep.physics.yappi;

/** 
 * Implements a decoder for the Monte Carlo Particle Numbering Scheme.
 * The scheme is documented in D.E. Groom et al., European Physical Journal C15, 1 (2000)
 * or on the web: http://pdg.lbl.gov/
 *
 * The implementation refers to the document of June 14, 2000 10:40.
 *
 * @author Mark Donszelmann
 * @version $Id: PDGID.java 8584 2006-08-10 23:06:37Z duns $
 */

public class PDGID {
    
    private int id;
    protected boolean particle = true;
    protected String family = "undefined";
    protected boolean likely = false;
    protected boolean excited = false;
    protected boolean technicolor = false;
    protected boolean susy = false;
    protected int susyState = 0;
    
    protected double I = Double.NaN;
    protected double J = Double.NaN;
    protected int P = 0;
    protected int C = 0;
    protected int G = 0;
    
    protected int n = 0;
    protected int nr = 0;
    protected int nL = 0;
    protected int nq1 = 0;
    protected int nq2 = 0;
    protected int nq3 = 0;
    protected int nJ = 0;
    
    /**
     * Creates a PDGID given the number
     *
     * @param id PDG ID for particle
     */
    public PDGID(int id) {
        this.id = id;
        decode(id);
    }
    
    /**
     * @return id
     */
    public int getID() {
        return id;
    }
    
    /**
     * @return true if particle, false if antiParticle
     */
    public boolean isParticle() {
        return particle;
    }
    
    /**
     * @return true if particle excited
     */
    public boolean isExcited() {
        return excited;
    }
    
    /**
     * @return true if particle is technicolor
     */
    public boolean isTechnicolor() {
        return technicolor;
    }
    
    /**
     * @return true if particle is super symmetric
     */
    public boolean isSusy() {
        return susy;
    }
    
    /**
     * @return susy state, or 0 if not a susy particle
     */
    public int getSusyState() {
        return susyState;
    }
    
    /**
     * @return family name
     */
    public String getFamily() {
        return family;
    }
    
    /**
     * @return true if particles qq states are unassigned or likely.
     */
    public boolean isLikely() {
        return likely;
    }
    
    /**
     * returns n
     */
    public int getN() {
        return n;
    }
    
    /**
     * returns nr
     */
    public int getNr() {
        return nr;
    }
    
    /**
     * returns nL
     */
    public int getNL() {
        return nL;
    }
    
    /**
     * returns nq1
     */
    public int getNq1() {
        return nq1;
    }
    
    /**
     * returns nq2
     */
    public int getNq2() {
        return nq2;
    }
    
    /**
     * returns nq3
     */
    public int getNq3() {
        return nq3;
    }
    
    /**
     * returns nJ
     */
    public int getNJ() {
        return nJ;
    }
    
    /**
     * returns I
     */
    public double getI() {
        return I;
    }
    
    /**
     * returns J
     */
    public double getJ() {
        return J;
    }
    
    /**
     * returns P or 0 if unspecified
     */
    public int getP() {
        return P;
    }
    
    /**
     * returns C or 0 if unspecified
     */
    public int getC() {
        return C;
    }
    
    /**
     * decodes id
     */
    protected void decode(int id) {
        // is particle or anti particle ?
        particle = (id > 0);
    
        id = Math.abs(id);
        n = (id / 1000000);
        int r = (id % 1000000);
        
        switch (n) {
            case 0:     // standard particle
                        break;
            case 1:     // left handed susy
            case 2:     // right handed susy
                        susy = true;
                        susyState = n;
                        break;
            case 3:     // technicolor
                        technicolor = true;
                        break;
            case 4:     // excited
                        excited = true;
                        break;
            case 9:     // likely
                        likely = true;
                        break;
            default:    break;
        }
        
        if (likely && (((id / 100000) % 10)) == 9) {
            decodeUser(id);
        } else {
            decodeBase(r);
        }
    }
    
    /**
     * user decoding method
     */
    protected void decodeUser(int r) {
    }

    /**
     * decodes id
     */
    protected void decodeBase(int id) {
        if (id < 11) {
            family = "quarks";
            switch (id) {
                case  1:    I=.5; J=.5; P=+1;  // d
                            break;
                case  2:    I=.5; J=.5; P=+1;  // u
                            break;
                case  3:    I= 0; J=.5; P=+1;  // s
                            break;
                case  4:    I= 0; J=.5; P=+1;  // c
                            break;
                case  5:    I= 0; J=.5; P=+1;  // b
                            break;
                case  6:    I= 0; J=.5; P=+1;  // t
                            break;
                case  7:    // b'
                case  8:    // t'
                            break;
                case  9:    I= 0; J=1;  P=-1;  // gluon for glueballs
                            break;
                default:    throw new IllegalArgumentException("Not a valid PDGID: "+id);
            }
            
        } else if (id < 21) {
            family = "leptons";
            switch (id) {
                case 11:    // e-
                case 12:    // nue
                case 13:    // mu-
                case 14:    // numu
                case 15:    // tau-
                case 16:    // nutau
                            J=.5;
                            break;
                case 17:    // tau'-
                case 18:    // nutau'
                            break;
                default:    throw new IllegalArgumentException("Not a valid PDGID: "+id);
            }

        } else if (id < 31) {
            family = "bosons";
            switch (id) {
                case 21:    I= 0; J=1;  P=-1;          // gluon
                            break;
                            // FIXME: I can be 0, 1
                case 22:    I= 0; J=1;  P=-1;  C=-1;  // gamma
                            break;
                case 23:    J=1;                        // Z0
                            break;
                case 24:    J=1;                        // W+
                            break;
                case 25:    // h0/H10
                            break;
            
                default:    throw new IllegalArgumentException("Not a valid PDGID: "+id);
            }
            
        } else if (id < 39) {
            family = "bosons";
            switch (id) {
                case 32:    // Z'/Z20
                case 33:    // Z"/Z30
                case 34:    // W'/W2+
                case 35:    // H0/H20
                case 36:    // A0/H30
                case 37:    // H+
                            break;
                default:    throw new IllegalArgumentException("Not a valid PDGID: "+id);
            }
            
        } else if (id < 81) {
            family = "exotic";
            switch (id) {
                case 39:    // G (graviton)
                case 41:    // R0
                case 42:    // LQc
                            break;
                default:    throw new IllegalArgumentException("Not a valid PDGID: "+id);
            }

        } else if (id < 101) {
            family = "reserved";
        } else {
            switch (id) {
                case  110:  // reggeon
                case  990:  // pomeron
                case 9990:  // odderon
                            family = "special";
                            decodeFlavors(id);
                            break;
                default:    family = "composites";
                            decodeComposites(id);
                            break;
            }
        }   
    }

    protected void decodeFlavors(int id) {
        // FIXME: there should be something special for 11, 22 and 33
        // decode nq1..3
        nq3 = (id / 10) % 10;
        nq2 = (id / 100) % 10;
        nq1 = (id / 1000) % 10;
    }        

    protected void decodeComposites(int id) {
        // special cases
        if (id == 130) {
            // Kl0
            family = "mesons";
            I=.5; J=0; P=-1; nq1=0; nq2=3; nq3=1; nJ=1;
            return;
        }
        
        if (id == 310) {
            // Ks0
            family = "mesons";
            I=.5; J=0; P=-1; nq1=0; nq2=3; nq3=1; nJ=1;
            return;
        }

        // calculate J
        nJ = id % 10;
        J = (nJ - 1 / 2.0);

        decodeFlavors(id);
        
        // which one ?
        if (nq3 == 0) {
            family = "diquarks";
        } else if (nq1 == 0) {
            family = "mesons";
            decodeMesons(id);
        } else {
            family = "baryons";
            decodeBaryons(id);
        }
        
        // decode nr;
        nr = (id / 100000) % 10;
    }
    
    protected void decodeMesons(int id) {
        // calculate P and C
        nL = (id / 1000) % 10;
        int L, S;
        
        if (J > 0) {
            switch (nL) {
                case 0:     L=(int)J-1; S=1;
                            break;
                case 1:     L=(int)J; S=0;
                            break;
                case 2:     L=(int)J; S=1;
                            break;
                case 3:     L=(int)J+1; S=1;
                            break;
                default:    throw new RuntimeException("nL contains a wrong number: "+nL);
            }
        } else {
            switch (nL) {
                case 0:     L=0; S=0;
                            break;
                case 1:     L=1; S=1;
                            break;
                default:    throw new RuntimeException("nL contains a wrong number: "+nL);
            }
        }
        
        // P = (-1)^(L+1)
        P = (((L+1) % 2) == 0) ? +1 : -1;
        
        // C = (-1)^(L+S)
        C = (((L+S) % 2) == 0) ? +1 : -1;
        
        // G = (-1)^(L+S+I)
    }
    
    protected void decodeBaryons(int id) {
        // FIXME: look up table 13.4
    }
    
    // FIXME: should include some more stuff
    public String toString() {
        return "[PDGID:"+id+"] Family="+family+"; "+((particle)?"particle":"anti-particle")+"; \n"+
               "   I="+I+"; J="+J+"; P="+((P==1)?"+":"-")+"; C="+((C==1)?"+":"-")+"; \n"+
               "   q1="+nq1+"; q2="+nq2+"; q3="+nq3+"; \n"+
               "   n="+n+"; nr="+nr+"; nL="+nL+"; nJ="+nJ+"; \n"+
               "   "+((excited)?"excited, ":"")+((technicolor)?"techicolor, ":"")+
                    ((likely)?"likely, ":"")+((susy)?"susy("+susyState+"), ":"")+"; \n"+
               "   \n";
    }
    
    public static void main(String[] args) {
        System.out.println(new PDGID(       4));
        System.out.println(new PDGID(      14));
        System.out.println(new PDGID( 4000002));
        System.out.println(new PDGID(      35));
        System.out.println(new PDGID(    4301));
        System.out.println(new PDGID( 3000113));
        System.out.println(new PDGID( 1000015));
        System.out.println(new PDGID( 2000011));
        System.out.println(new PDGID(      41));
        System.out.println(new PDGID(     110));
        System.out.println(new PDGID(    -990));
        System.out.println(new PDGID(    9990));
        System.out.println(new PDGID(     211));
        System.out.println(new PDGID(  100213));
        System.out.println(new PDGID(-9020215));
        System.out.println(new PDGID(     130));
        System.out.println(new PDGID(     310));
        System.out.println(new PDGID( 9010325));
        System.out.println(new PDGID(     412));
        System.out.println(new PDGID(   20423));
        System.out.println(new PDGID(  -20533));
        System.out.println(new PDGID( 9000445));
        System.out.println(new PDGID(  110553));
        System.out.println(new PDGID(    2212));
        System.out.println(new PDGID(    1114));
        System.out.println(new PDGID(    3324));
        System.out.println(new PDGID(    4322));
        System.out.println(new PDGID(    5414));
    }
}
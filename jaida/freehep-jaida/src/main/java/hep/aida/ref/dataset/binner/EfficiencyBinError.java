package hep.aida.ref.dataset.binner;


/**
 * Calculates the bin error for efficiency-type histograms
 * @author The FreeHEP team at SLAC.
 *
 */
public class EfficiencyBinError implements BinError {
    
    /** Get the minus error on a bin.
     * @param entries The entries in the bin.
     * @param height  The height of the bin.
     * @return The minus error.
     *
     *
     */
    public double minusError(int entries, double height) {
        return H95CL(height,entries,2);
    }
    
    /** Get the plus error on a bin.
     * @param entries The entries in the bin.
     * @param height  The height of the bin.
     * @return The plus error.
     *
     *
     */
    public double plusError(int entries, double height) {
        return H95CL(height,entries,1);
    }

    /**
     * 
     *   **MEMBER** H95CL
     * 
     *  H95CL RETURNS THE 95% CONFIDENCE LIMITS ON THE PROPORTION
     * 
     *      R1/R2
     * 
     *  WHERE  R1 IS THE NUMBER OF SUCCESSES
     *         R2 IS THE NUMBER OF TRIALS
     *         IER IS 1 FOR THE UPPER LIMIT
     *                2 FOR THE LOWER LIMIT
     *         H95CL RETURNS THE ERROR VALUE CORRESPONDING TO THIS LIMIT.
     * 
     *  THE METHOD USES THE TABLES TAKEN FROM
     * 
     *      HANDBOOK OF STATISTICAL TABLES
     *        OWEN   (QA297  Q9)
     * 
     *   AUTHOR:   A. BOYARSKI, SLAC      1980.
     * @author  Tony Johnson - Converted to Java
     */
    private double H95CL(double R1,double R2,int IER) {
        
        int N1 = (int) Math.round(R1);
        int NT = (int) Math.round(R2);
        
        if (N1>NT) throw new IllegalArgumentException();
        
        int NX = Math.min(N1,NT-N1);
        int KL = 2;
        if (IER == 2) KL=1;
        if (NX != N1) KL=3-KL;
        int NMX=NT-NX;
        double P;
        
        if (NX >= 10) {
            // NUMBERS ARE ALL LARGE, SO CAN USE ANALYTIC CALCULATION
            return 1.96*Math.sqrt(R1*(R2-R1)/R2)/R2;
        }
        else if (NMX >= 10) {
            int K = NX+1;
            
            if (NMX >= 500) {
                P=TAB2[8][K-1][KL-1]*500./NMX;
            }
            else if (NMX < 12) {
                // NMX = 10 OR 11
                P=TAB2[NMX-8][K-1][KL-1];
            }
            else {
                // NX IS SMALL AND NMX IS LARGE, (USE INTERPLOATED TABLE)
                int IT;
                if      (NMX >= 100) IT = 8;
                else if (NMX >= 49) IT = 7;
                else if (NMX >= 25) IT = 6;
                else if (NMX >= 20) IT = 5;
                else if (NMX >= 15) IT = 4;
                else                IT = 3;
                
                P = TAB2[IT-1][K-1][KL-1] + ((double) NMX-MX[IT-1])/(MX[IT]-MX[IT-1])*
                (TAB2[IT][K-1][KL-1]-TAB2[IT-1][K-1][KL-1]);
            }
        }
        else {
            // BOTH HITS AND MISSES ARE SMALL, CAN USE TABLE WITHOUT INTERPOLATION
            int K = INDX[NMX] + NX;
            P=TAB1[K-1][KL-1];
        }
        // FLIP THE RESULT IF N1/NT IS GT .5
        if (NX != N1) P = 1.0-P;
        return Math.abs(P-R1/Math.max(1.0,R2));
    }

    
    
    static final int[] INDX = {1,2,4,7,11,16,22,29,37,46};
    static final int[] MX = {10,11,12,15,20,25,50,100,500};
    
    static final double[][]
    TAB1 = { { .0000,1.0000},{ .0000,.9750 },{ .0126,.9874 },
    { .0000,.8419 },{ .0084,.9057 },{ .0676,.9324 },
    { .0000,.7076 },{ .0063,.8059 },{ .0527,.8534 },
    { .1181,.8819 },{ .0000,.6024 },{ .0051,.7164 },
    { .0433,.7772 },{ .0990,.8159 },{ .1570,.8430 },
    { .0000,.5218 },{ .0042,.6412 },{ .0367,.7096 },
    { .0852,.7551 },{ .1370,.7880 },{ .1871,.8129 },
    { .0000,.4593 },{ .0036,.5787 },{ .0319,.6509 },
    { .0749,.7007 },{ .1216,.7376 },{ .1675,.7662 },
    { .2110,.7890 },{ .0000,.4096 },{ .0032,.5265 },
    { .0281,.6001 },{ .0667,.6525 },{ .1093,.6921 },
    { .1517,.7233 },{ .1923,.7486 },{ .2304,.7696 },
    { .0000,.3694 },{ .0028,.4825 },{ .0252,.5561 },
    { .0602,.6097 },{ .0993,.6511 },{ .1386,.6842 },
    { .1766,.7114 },{ .2127,.7341 },{ .2466,.7534 },
    { .0000,.3363 },{ .0025,.4450 },{ .0228,.5178 },
    { .0549,.5719 },{ .0909,.6143 },{ .1276,.6486 },
    { .1634,.6771 },{ .1975,.7012 },{ .2298,.7218 },
    { .2602,.7398 }};
    
    static final double[][][]
    TAB2 = { { { .0000,.3085 } , { .0023,.4128} , { .0209,.4841},
    { .0504,.5381 } , { .0839,.5810} , { .1182,.6162},
    { .1520,.6456 } , { .1844,.6707} , { .2153,.6924},
    { .2445,.7114 } } ,
    { { .0000,.2849 } , { .0021,.3848} , { .0192,.4545},
      { .0466,.5080 } , { .0779,.5510} , { .1102,.5866},
      { .1421,.6167 } , { .1730,.6425} , { .2025,.6649},
      { .2306,.6847 } } ,
      { { .0000,.2646 } , { .0019,.3603} , { .0178,.4281},
        { .0433,.4809 } , { .0727,.5238} , { .1031,.5596},
        { .1334,.5900 } , { .1629,.6164} , { .1912,.6394},
        { .2182,.6598 } } ,
        { { .0000,.2180 } , { .0016,.3023} , { .0146,.3644},
          { .0358,.4142 } , { .0605,.4557} , { .0866,.4910},
          { .1128,.5217 } , { .1387,.5487} , { .1638,.5726},
          { .1880,.5941 } } ,
          { { .0000,.1684 } , { .0012,.2382} , { .0112,.2916},
            { .0278,.3359 } , { .0474,.3738} , { .0683,.4070},
            { .0897,.4364 } , { .1112,.4628} , { .1322,.4865},
            { .1529,.5083 } } ,
            { { .0000,.1372 } , { .0010,.1964} , { .0091,.2429},
              { .0227,.2823 } , { .0389,.3167} , { .0564,.3473},
              { .0745,.3747 } , { .0928,.3997} , { .1109,.4225},
              { .1288,.4437 } } ,
              { { .0000,.0711 } , { .0005,.1045} , { .0047,.1321},
                { .0118,.1567 } , { .0206,.1790} , { .0302,.1996},
                { .0403,.2187 } , { .0508,.2367} , { .0615,.2537},
                { .0722,.2699 } } ,
                { { .0000,.0362 } , { .0003,.0539} , { .0024,.0691},
                  { .0060,.0828 } , { .0106,.0956} , { .0156,.1077},
                  { .0211,.1191 } , { .0267,.1301} , { .0325,.1407},
                  { .0385,.1510 } } ,
                  { { .0000,.0074 } , { .0001,.0111} , { .0005,.0143},
                    { .0012,.0173 } , { .0022,.0202} , { .0032,.0230},
                    { .0044,.0256 } , { .0056,.0282} , { .0068,.0308},
                    { .0081,.0333 } } };
    
}

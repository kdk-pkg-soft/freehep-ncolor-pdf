package org.freehep.math.minuit;

/**
 *
 * @version $Id: MnSeedGenerator.java 8584 2006-08-10 23:06:37Z duns $
 */
class MnSeedGenerator implements MinimumSeedGenerator
{
   public MinimumSeed generate(MnFcn fcn, GradientCalculator gc, MnUserParameterState st, MnStrategy stra)
   {
      int n = st.variableParameters();
      MnMachinePrecision prec = st.precision();
      
      // initial starting values
      MnAlgebraicVector x = new MnAlgebraicVector(n);
      for( int i = 0; i < n; i++) x.set(i,st.intParameters().get(i));
      double fcnmin = fcn.valueOf(x);
      MinimumParameters pa = new MinimumParameters(x, fcnmin);
      
      FunctionGradient dgrad;
      if (gc instanceof AnalyticalGradientCalculator)
      {
         InitialGradientCalculator igc = new InitialGradientCalculator(fcn, st.trafo(), stra);
         FunctionGradient tmp = igc.gradient(pa);
         FunctionGradient grd = gc.gradient(pa);
         dgrad = new FunctionGradient(grd.grad(), tmp.g2(), tmp.gstep());

         if (((AnalyticalGradientCalculator) gc).checkGradient())
         {
            boolean good = true;
            HessianGradientCalculator hgc = new HessianGradientCalculator(fcn, st.trafo(), new MnStrategy(2));
            Pair<FunctionGradient, MnAlgebraicVector> hgrd = hgc.deltaGradient(pa, dgrad);
            for(int i = 0; i < n; i++)
            {
               if(Math.abs(hgrd.first.grad().get(i) - grd.grad().get(i)) > hgrd.second.get(i))
               {
                  System.err.println("gradient discrepancy of external parameter "+st.trafo().extOfInt(i)+" (internal parameter "+i+") too large.");
                  good = false;
               }
            }
            if(!good)
            {
               System.err.println("Minuit does not accept user specified gradient. To force acceptance, override 'virtual bool checkGradient() const' of FCNGradientBase.h in the derived class.");
               assert(good);
            }
         }         
      }
      else
      {
         dgrad = gc.gradient(pa);
      }
      MnAlgebraicSymMatrix mat = new MnAlgebraicSymMatrix(n);
      double dcovar = 1.;
      if(st.hasCovariance())
      {
         for( int i = 0; i < n; i++)
            for(int j = i; j < n; j++) mat.set(i,j,st.intCovariance().get(i,j));
         dcovar = 0.;
      } else
      {
         for(int i = 0; i < n; i++)
            mat.set(i,i,(Math.abs(dgrad.g2().get(i)) > prec.eps2() ? 1./dgrad.g2().get(i) : 1.));
      }
      MinimumError err = new MinimumError(mat, dcovar);
      double edm = new VariableMetricEDMEstimator().estimate(dgrad, err);
      MinimumState state = new MinimumState(pa, err, dgrad, edm, fcn.numOfCalls());
      
      
      if(NegativeG2LineSearch.hasNegativeG2(dgrad, prec))
      {
         if (gc instanceof AnalyticalGradientCalculator)
         {
            Numerical2PGradientCalculator ngc = new Numerical2PGradientCalculator(fcn, st.trafo(), stra);
            state = NegativeG2LineSearch.search(fcn, state, ngc, prec);           
         }
         else 
         {
            state = NegativeG2LineSearch.search(fcn, state, gc, prec);
         }
      }
      
      if(stra.strategy() == 2 && !st.hasCovariance())
      {
         //calculate full 2nd derivative
         MinimumState tmp = new MnHesse(stra).calculate(fcn, state, st.trafo(),0);
         return new MinimumSeed(tmp, st.trafo());
      }
      
      return new MinimumSeed(state, st.trafo());  
   }   
}

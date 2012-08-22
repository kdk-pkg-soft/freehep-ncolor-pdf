package org.freehep.math.minuit;

/**
 *
 * @version $Id: CombinedMinimumBuilder.java 8584 2006-08-10 23:06:37Z duns $
 */
class CombinedMinimumBuilder implements MinimumBuilder
{
   public FunctionMinimum minimum(MnFcn fcn, GradientCalculator gc, MinimumSeed seed, MnStrategy strategy, int maxfcn, double toler)
   {
      FunctionMinimum min = theVMMinimizer.minimize(fcn, gc, seed, strategy, maxfcn, toler);
      
      if(!min.isValid())
      {
         System.err.println("CombinedMinimumBuilder: migrad method fails, will try with simplex method first.");
         MnStrategy str = new MnStrategy(2);
         FunctionMinimum min1 = theSimplexMinimizer.minimize(fcn, gc, seed, str, maxfcn, toler);
         if(!min1.isValid())
         {
            System.err.println("CombinedMinimumBuilder: both migrad and simplex method fail.");
            return min1;
         }
         MinimumSeed seed1 = theVMMinimizer.seedGenerator().generate(fcn, gc, min1.userState(), str);
         
         FunctionMinimum min2 = theVMMinimizer.minimize(fcn, gc, seed1, str, maxfcn, toler);
         if(!min2.isValid())
         {
            System.err.println("CombinedMinimumBuilder: both migrad and method fails also at 2nd attempt.");
            System.err.println("CombinedMinimumBuilder: return simplex minimum.");
            return min1;
         }
         
         return min2;
      }
      return min;
   }
   
   private VariableMetricMinimizer theVMMinimizer = new VariableMetricMinimizer();
   private SimplexMinimizer theSimplexMinimizer = new SimplexMinimizer();
}

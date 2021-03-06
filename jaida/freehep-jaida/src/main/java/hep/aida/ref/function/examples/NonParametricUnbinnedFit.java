package hep.aida.ref.function.examples;

import hep.aida.ref.pdf.examples.*;
import hep.aida.*;
import hep.aida.ref.function.NonParametricFunction;
import hep.aida.ref.pdf.Dependent;
import hep.aida.ref.pdf.FunctionConverter;
import hep.aida.ref.pdf.Gaussian;
import hep.aida.ref.pdf.Parameter;
import hep.aida.ref.pdf.PdfFitter;
import hep.aida.ref.pdf.Sum;

import java.util.Random;

public class NonParametricUnbinnedFit {

    public static void main(String[] args) {

        IAnalysisFactory analysisFactory = IAnalysisFactory.create();
        ITreeFactory treeFactory = analysisFactory.createTreeFactory();
        ITree tree = treeFactory.create();
        IHistogramFactory histogramFactory = analysisFactory.createHistogramFactory(tree);
        IFunctionFactory functionFactory = analysisFactory.createFunctionFactory(tree);
        IFitFactory fitFactory = analysisFactory.createFitFactory();
        IPlotter plotter = analysisFactory.createPlotterFactory().create("Plotter");

        ICloud1D c1 = histogramFactory.createCloud1D("Cloud 1D");

        Random r_1 = new Random(123);
        Random r_2 = new Random(456);

        for (int i = 0; i < 1000; i++) {
            double x = r_1.nextGaussian();
//            if (r_1.nextDouble() < 0.2) {
//                x += 3 * r_2.nextGaussian();
//            }
            c1.fill(x);
        }
        IFitData data = fitFactory.createFitData();
        data.create1DConnection(c1);

        NonParametricFunction f = new NonParametricFunction("Non Parametric Function", data);





        plotter.show();

      plotter.region(0).plot(c1);
      plotter.region(0).plot(f);


    /*

        System.out.println("Mean1 " + s.getParameter("mean1").value());
        System.out.println("sigma1 " + s.getParameter("sigma1").value());
        System.out.println("mean2 " + s.getParameter("mean2").value());
        System.out.println("sigma2 " + s.getParameter("sigma2").value());


    ICloud1D signal = hf.createCloud1D("Signal");
    ICloud1D background = hf.createCloud1D("Background");

    ICloud1D data = hf.createCloud1D("Data");


    Random r = new Random();

    // Fill the Signal distribution
    for (int i = 0; i < 1000; i++ ) {
    signal.fill(r.nextGaussian());
    }

    // Fill the Background distribution with less points
    for (int i = 0; i < 1000; i++ ) {
    background.fill(1+ 3*r.nextGaussian());
    }

    // Fill the Data with a 10% background
    Random ratio = new Random();
    for (int i = 0; i < 5000; i++ ) {
    if ( ratio.nextDouble() < 0.9 )
    data.fill(r.nextGaussian());
    else
    data.fill(1+ 3*r.nextGaussian());
    }

    // Create IFitData to feed to the nonParametric function
    IFitData signalData = fitFactory.createFitData();
    signalData.create1DConnection(signal);
    IFitData backgroundData = fitFactory.createFitData();
    backgroundData.create1DConnection(background);


    //Create the nonParametric functions
    IFunction signalFunction = new NonParametricFunction("signalFunction",signalData);
    IFunction backgroundFunction = new NonParametricFunction("signalFunction",backgroundData);

    ArrayList functionsToSum = new ArrayList();
    functionsToSum.add(signalFunction);
    functionsToSum.add(backgroundFunction);

    IFunction finalDistribution = new SumOfFunctions("Distribution",functionsToSum);
    String[] pars = finalDistribution.parameterNames();
    finalDistribution.setParameter(pars[0],0.5);
    finalDistribution.setParameter(pars[1],0.5);

    for( int j = 0; j < pars.length; j++) {
    String par = pars[j];
    System.out.println("*** "+pars[j]+" "+finalDistribution.parameter(par));

    }

    IFitter jminuit = fitFactory.createFitter("uml","jminuit","noClone=true");

    IFitResult jminuitResult = jminuit.fit(data,finalDistribution);

    IPlotter plotter = af.createPlotterFactory().create("Plotter");

    plotter.show();
    plotter.createRegions(2,2);

    plotter.region(0).plot(signal);
    plotter.region(1).plot(background);
    plotter.region(2).plot(data);
    plotter.region(2).plot(jminuitResult.fittedFunction());

    System.out.println("jminuit Chi2="+jminuitResult.quality());


    IFunction finalFunction = jminuitResult.fittedFunction();
    pars = finalFunction.parameterNames();
    for( int j = 0; j < pars.length; j++) {
    String par = pars[j];
    System.out.println("*** "+pars[j]+" "+finalFunction.parameter(par));
     */
    }
}
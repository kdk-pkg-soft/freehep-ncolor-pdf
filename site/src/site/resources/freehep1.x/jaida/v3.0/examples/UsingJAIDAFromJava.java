import java.util.Random;
import hep.aida.*;

/**
 *
 * @author The FreeHEP team @ SLAC.
 *
 */
public class UsingJAIDAFromJava {
    

    public static void main(String[] args) throws java.io.IOException {
        
        IAnalysisFactory  factory = IAnalysisFactory.create();
        ITree             tree    = factory.createTreeFactory().create("UsingJAIDAFromJava.aida","xml",false,true);
        IHistogramFactory hf      = factory.createHistogramFactory(tree);
	IFitter           fitter  = factory.createFitFactory().createFitter("Chi2","uncmin");
        
        Random r = new Random();
        
        tree.mkdir("/Histograms");
        tree.cd("/Histograms");
        
        IHistogram1D h1 = hf.createHistogram1D("Histogram 1D",50,-3,3);
        IHistogram2D h2 = hf.createHistogram2D("Histogram 2D",40,-3,3,40,-3,3);
        
        tree.mkdir("/Clouds");
        tree.cd("/Clouds");
        
        ICloud1D c1 = hf.createCloud1D("Cloud 1D");
        ICloud2D c2 = hf.createCloud2D("Cloud 2D");
        
        IPlotter page1 = factory.createPlotterFactory().create("Page1");

        page1.show();
        page1.createRegions(2,2);
        
        page1.region(0).plot(h1);
        page1.region(1).plot(h2);
        page1.region(2).plot(c1);
        page1.region(3).plot(c2);
        
        for ( int i = 0; i < 10000; i++ ) {
            h1.fill(r.nextGaussian());
            h2.fill(r.nextGaussian(),r.nextGaussian());
            c1.fill(r.nextGaussian());
            c2.fill(r.nextGaussian(),r.nextGaussian());
        }

	IFitResult h1FitUncMin = fitter.fit(h1,"g");
	fitter.setEngine("minuit");
	IFitResult h1FitMinuit = fitter.fit(h1,"g");

        page1.region(0).plot(h1FitUncMin.fittedFunction());
        page1.region(0).plot(h1FitMinuit.fittedFunction());



        tree.commit();
    }
    
}





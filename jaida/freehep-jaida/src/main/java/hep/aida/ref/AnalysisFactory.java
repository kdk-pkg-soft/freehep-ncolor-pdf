package hep.aida.ref;

import hep.aida.IPlotterFactory;
import hep.aida.ref.plotter.PlotterFactory;

/**
 *
 * @author tonyj
 * @version $Id: AnalysisFactory.java 8584 2006-08-10 23:06:37Z duns $
 */
public class AnalysisFactory extends BatchAnalysisFactory
{
    public IPlotterFactory createPlotterFactory() {
        return new PlotterFactory();
    }
}

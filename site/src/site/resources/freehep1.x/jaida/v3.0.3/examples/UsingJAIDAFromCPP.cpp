#include <iostream>
#include <cstdlib>


#include "AIDA/AIDA.h"
#include "JIAnalysisFactory.h"

using namespace AIDA;

int main(int argc, char *argv[]) {

        AIDA::IAnalysisFactory* factory = AIDA_createAnalysisFactory();
        ITree*              tree    = factory->createTreeFactory()->create("UsingJAIDAFromCPP.aida","xml",false,true);
	IHistogramFactory*  hf      = factory->createHistogramFactory(*tree);

        tree->mkdir("/Histograms");
        tree->cd("/Histograms");

        IHistogram1D* h1 = hf->createHistogram1D("Histogram 1D",50,0,10);
        IHistogram2D* h2 = hf->createHistogram2D("Histogram 2D",40,0,10,40,0,10);
        
        tree->mkdir("/Clouds");
        tree->cd("/Clouds");
        
        ICloud1D* c1 = hf->createCloud1D("Cloud 1D");
        ICloud2D* c2 = hf->createCloud2D("Cloud 2D");


        IPlotter* page1 = factory->createPlotterFactory()->create("Page1");

        page1->show();
        page1->createRegions(2,2);

        page1->region(0)->plot(*h1);
        page1->region(1)->plot(*h2);
        page1->region(2)->plot(*c1);
        page1->region(3)->plot(*c2);

	srand( 0 );
        for ( int i = 0; i < 10000; i++ ) {
            h1->fill( 10*rand()/(double)RAND_MAX );
            h2->fill( 10*rand()/(double)RAND_MAX, 10*rand()/(double)RAND_MAX);
            c1->fill( 10*rand()/(double)RAND_MAX );
            c2->fill( 10*rand()/(double)RAND_MAX, 10*rand()/(double)RAND_MAX);
        }

	
        tree->commit();

	delete factory;

	return 1;
}






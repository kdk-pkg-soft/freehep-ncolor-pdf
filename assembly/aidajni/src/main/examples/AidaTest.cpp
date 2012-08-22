// Copyright 2002, 2003, Stanford Linear Accelerator Center

#ifdef WIN32
#ifndef GNU_GCC
// Disable warning C4786: identifier was truncated to '255' characters in the debug information
  #pragma warning ( disable : 4786 )
// Disable warning C4250: inherits via dominance
  #pragma warning ( disable : 4250 )
#endif
#endif

#include <string>
#include <cstdlib>
#include <cstdio>
#include <cassert>

#include "AIDA/AIDA.h"

using namespace AIDA;

/**
 *
 * This test file demonstrates how to call AIDA-Java from C++
 *
 * It tries to load some of the C++ proxies for all the Java AIDA classes.
 * By doing this, it tests if the java methods can be found by C++.
 * Some methods are actually tested, but most are not.
 *
 * @author Mark Donszelmann
 * @version $Id: AidaTest.cc,v 1.27 2004/01/23 22:50:29 duns Exp $
 */

void testHistogram1D(IHistogramFactory *factory) {
    printf("Creating Histogram1D...\n");
    IHistogram1D* histo1D = factory->createHistogram1D("1","Some random distribution",
                                              20000,-4.,4.);
    assert(histo1D != NULL);

    printf("Getting Annotation...\n");
    /* IAnnotation& annotation = */ histo1D->annotation();

    printf("Getting Axis...\n");
    /* const IAxis& axis = */ histo1D->axis();

    printf("Filling Histogram1D...\n");
	for (int i=0;i<100000;i++) {
	    float r = 0.;
		for (int j=0; j<10; j++) {
		    r += rand()/32768 -0.5;
		}
		histo1D->fill(r);
	}

    delete(histo1D);
}

void testHistogram2D(IHistogramFactory* factory) {
    printf("Creating Histogram2D...\n");
    IHistogram2D* histo2D = factory->createHistogram2D("2","Some random distribution",
                                              200,-4.,4.,200, -2.,2.);
    assert(histo2D != NULL);

    printf("Filling Histogram2D...\n");
	for (int i=0;i<100000;i++) {
	    float r = 0.;
		for (int j=0; j<10; j++) {
		    r += rand()/32768 -0.5;
		}
		histo2D->fill(r, r/2.0);
	}

    delete(histo2D);
}

void testHistogram3D(IHistogramFactory* factory) {
    printf("Creating Histogram3D...\n");
    IHistogram3D* histo3D = factory->createHistogram3D("3","Some random distribution",
                            200,-4.,4.,20,-2.,2.,20,-1.,1.);
    assert(histo3D != NULL);

    printf("Filling Histogram3D...\n");
	for (int i=0;i<100000;i++) {
	    float r = 0.;
		for (int j=0; j<10; j++) {
		    r += rand()/32768 -0.5;
		}
		histo3D->fill(r, r/2.0, r/4.0);
	}

    delete(histo3D);
}


void testHistogramArithmetics(IHistogramFactory *factory) {
    printf("Testing Histogram Arithmetics...\n");

    // Histograms1D
    IHistogram1D* hist1D1 = factory->createHistogram1D("hist1D1","Some random distribution",100,-4.,4.);
    assert(hist1D1 != NULL);
    IHistogram1D* hist1D2 = factory->createHistogram1D("hist1D2","Some random distribution",100,-4.,4.);
    assert(hist1D2 != NULL);

    // Histograms2D
    IHistogram2D* hist2D1 = factory->createHistogram2D("hist2D1","Some random distribution",100,-4.,4.,100,-4.,4.);
    assert(hist2D1 != NULL);
    IHistogram2D* hist2D2 = factory->createHistogram2D("hist2D2","Some random distribution",100,-4.,4.,100,-4.,4.);
    assert(hist2D2 != NULL);

    for (int i=0;i<100000;i++) {
        float r1 = 0.;
        float r2 = 0.;
        float r3 = 0.;
        float r4 = 0.;
	for (int j=0; j<10; j++) {
            r1 += rand()/32768 -0.5;
            r2 += rand()/32768 -0.5;
            r3 += rand()/32768 -0.5;
            r4 += rand()/32768 -0.5;
	}
	hist1D1->fill(r1);
	hist1D2->fill(r2);
	hist2D1->fill(r1,r3);
	hist2D2->fill(r2,r4);
    }


    printf("1D Arithmetics...\n");
    IHistogram1D* hist1Dadd      = factory->add("hist1Dadd", *hist1D1, *hist1D2);
    assert(hist1Dadd != NULL);
    IHistogram1D* hist1Dsubtract = factory->subtract("hist1Dsubtract", *hist1D1, *hist1D2);
    assert(hist1Dsubtract != NULL);
    IHistogram1D* hist1Dmultiply = factory->multiply("hist1Dmultiply", *hist1D1, *hist1D2);
    assert(hist1Dmultiply != NULL);
    IHistogram1D* hist1Ddivide   = factory->divide("hist1Ddivide", *hist1D1, *hist1D2);
    assert(hist1Ddivide != NULL);

    printf("2D Arithmetics...\n");
    IHistogram2D* hist2Dadd      = factory->add("hist2Dadd", *hist2D1, *hist2D2);
    assert(hist2Dadd != NULL);
    IHistogram2D* hist2Dsubtract = factory->subtract("hist2Dsubtract", *hist2D1, *hist2D2);
    assert(hist2Dsubtract != NULL);
    IHistogram2D* hist2Dmultiply = factory->multiply("hist2Dmultiply", *hist2D1, *hist2D2);
    assert(hist2Dmultiply != NULL);
    IHistogram2D* hist2Ddivide   = factory->divide("hist2Ddivide", *hist2D1, *hist2D2);
    assert(hist2Ddivide != NULL);

    delete(hist1D1);
    delete(hist1D2);
    delete(hist1Dadd);
    delete(hist1Dsubtract);
    delete(hist1Dmultiply);
    delete(hist1Ddivide);
    delete(hist2D1);
    delete(hist2D2);
    delete(hist2Dadd);
    delete(hist2Dsubtract);
    delete(hist2Dmultiply);
    delete(hist2Ddivide);
}



void testCloud1D(IHistogramFactory *factory) {
    printf("Creating Cloud1D...\n");
    ICloud1D* cloud1D = factory->createCloud1D("C1","Some random distribution",20000,"");
    assert(cloud1D != NULL);

    printf("Filling Cloud1D...\n");
	for (int i=0;i<100000;i++) {
	    float r = 0.;
		for (int j=0; j<10; j++) {
		    r += rand()/32768 -0.5;
		}
		cloud1D->fill(r);
	}

    printf("Converting to Histogram1D...\n");
    cloud1D->convert(20000,-4.,4.);
    /* const IHistogram1D & histo1D = */ cloud1D->histogram();

    delete(cloud1D);
}

void testCloud2D(IHistogramFactory *factory) {
    printf("Creating Cloud2D...\n");
    ICloud2D* cloud2D = factory->createCloud2D("C2","Some random distribution",20000,"");
    assert(cloud2D != NULL);

    printf("Filling Cloud2D...\n");
	for (int i=0;i<100000;i++) {
	    float r = 0.;
		for (int j=0; j<10; j++) {
		    r += rand()/32768 -0.5;
		}
		cloud2D->fill(r, r/2.0);
	}

    printf("Converting to Histogram2D...\n");
    cloud2D->convert(200,-4.,4.,200,-2.,2.);
    /* const IHistogram2D & histo2D = */ cloud2D->histogram();

    delete(cloud2D);
}

void testCloud3D(IHistogramFactory *factory) {
    printf("Creating Cloud3D...\n");
    ICloud3D* cloud3D = factory->createCloud3D("C3","Some random distribution",20000,"");
    assert(cloud3D != NULL);

    printf("Filling Cloud3D...\n");
	for (int i=0;i<100000;i++) {
	    float r = 0.;
		for (int j=0; j<10; j++) {
		    r += rand()/32768 -0.5;
		}
		cloud3D->fill(r, r/2.0, r/4.0);
	}

    printf("Converting to Histogram3D...\n");
    cloud3D->convert(200,-4.,4.,20,-2.,2.,20,-1.,1.);
    /* const IHistogram3D & histo3D = */ cloud3D->histogram();

    delete(cloud3D);
}

/*
 * Trees
 */
ITree* testTrees(IAnalysisFactory *factory) {
    printf("Creating TreeFactory...\n");
    ITreeFactory *treeFactory = factory->createTreeFactory();
    assert(treeFactory != NULL);

    printf("Creating Tree...\n");
    ITree *tree = treeFactory->create();
    assert(tree != NULL);

//    printf("Creating ManagedObject...\n");
//    IManagedObject *managedObject = tree->find("/");
//    assert(managedObject != NULL);

    return tree;
}

/*
 * Histograms
 */
void testHistograms(IAnalysisFactory* factory, ITree *tree) {
    printf("Creating HistogramFactory...\n");
    IHistogramFactory *histoFactory = factory->createHistogramFactory(*tree);
    assert(histoFactory != NULL);
    testHistogram1D(histoFactory);
    testHistogram2D(histoFactory);
    //testHistogram3D(histoFactory);
    testHistogramArithmetics(histoFactory);
    delete(histoFactory);
}

/*
 * Clouds
 */
void testClouds(IAnalysisFactory* factory, ITree *tree) {
    printf("Creating HistogramFactory...\n");
    IHistogramFactory *histoFactory = factory->createHistogramFactory(*tree);
    assert(histoFactory != NULL);
    testCloud1D(histoFactory);
    testCloud2D(histoFactory);
    testCloud3D(histoFactory);
    delete(histoFactory);
}

/*
 * Tuples
 */
ITuple* testTuples(IAnalysisFactory* factory, ITree *tree) {
    printf("Creating TupleFactory...\n");
    ITupleFactory *tupleFactory = factory->createTupleFactory(*tree);
    assert(tupleFactory != NULL);
    printf("Creating Tuple...\n");
    ITuple *tuple = tupleFactory->create("TestTuple", "TestTuple", "float px, py, pz, float energy, int charge");
    assert(tuple != NULL);
    return tuple;
}

/*
 * Plotter
 */

void testPlotters(IAnalysisFactory* factory) {
    printf("Creating PlotterFactory...\n");
    char* args[] = {(char*)"JAS", (char*)"-dummy"};
    IPlotterFactory *plotterFactory = factory->createPlotterFactory(2, args);
    assert(plotterFactory != NULL);
    printf("Creating Plotter...\n");
    /* IPlotter *plotter = */ plotterFactory->create();
}

/*
 * DataPointSet
 */
void testDataPointSet(IAnalysisFactory* factory, ITree *tree) {
    printf("Creating DataPointSetFactory...\n");
    IDataPointSetFactory *dpsFactory = factory->createDataPointSetFactory(*tree);
    assert(dpsFactory != NULL);

    printf("Creating a DataPointSet...\n");
    IDataPointSet* dps = dpsFactory->create("dps",3);
    assert(dps != NULL);

    printf("Filling DataPointSet...\n");
    for (int i=0;i<20;i++) {
        dps->addPoint();
        IDataPoint* point = dps->point(i);
        assert(point != NULL);
        for (int j=0; j<3; j++) {
            point->coordinate(j)->setValue(i+0.5);
            point->coordinate(j)->setErrorPlus(0.1);
            point->coordinate(j)->setErrorMinus(0.02);
       	}
    }

    delete(dps);
    delete(dpsFactory);
}


/**
 * @author Mark Donszelmann
 * @version $Id: AidaTest.cc,v 1.27 2004/01/23 22:50:29 duns Exp $
 */
int main(int argc, char** argv) {
    printf("Simple Test for AIDA JNI and FreeHEP AIDA Reference implementation...\n");
    printf("Creating AnalysisFactory...\n");

    IAnalysisFactory *factory = AIDA_createAnalysisFactory();
    assert(factory != NULL);
    ITree *tree = testTrees(factory);
    assert(tree != NULL);
    testHistograms(factory, tree);
    testClouds(factory, tree);
    ITuple *tuple = testTuples(factory, tree);
    assert(tuple != NULL);
    testPlotters(factory);
    testDataPointSet(factory, tree);

    return 0;
}

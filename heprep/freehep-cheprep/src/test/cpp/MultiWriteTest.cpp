// Copyright 2003-2007, FreeHEP.

/**
 * Writes multiple hepreps to one xml file.
 *
 * @author M.Donszelmann
 *
 * @version $Id: MultiWriteTest.cpp 12993 2007-07-10 20:01:44Z duns $
 */

#include <string>
#include <cstdlib>
#include <cstdio>
#include <iostream>
#include <fstream>

#include "HEPREP/HepRep.h"
#include "cheprep/XMLHepRepFactory.h"

#include "MultiWriteTest.h"

using namespace std;
using namespace HEPREP;
using namespace cheprep;

#ifdef LARGE_EVENTS
#define NUMBER_OF_POLYGONS 255
#define NUMBER_OF_POINTS 4
#define NUMBER_OF_HITS 512
#define NUMBER_OF_TRACKS 16
#define NUMBER_OF_TRACK_POINTS 32
#else
#define NUMBER_OF_POLYGONS 8
#define NUMBER_OF_POINTS 4
#define NUMBER_OF_HITS 32
#define NUMBER_OF_TRACKS 4
#define NUMBER_OF_TRACK_POINTS 4
#endif

MultiWriteTest::MultiWriteTest() {
    random.setSeed(0);
}

MultiWriteTest::~MultiWriteTest() {
}

HepRep* MultiWriteTest::makeRandomHepRep(HepRepFactory* factory) {
    vector<double> red, yellow, blue, gray;
    
    // contruct colors
    red.push_back(1);
    red.push_back(0);
    red.push_back(0);
    
    yellow.push_back(0);
    yellow.push_back(1);
    yellow.push_back(1);
    
    blue.push_back(0);
    blue.push_back(0);
    blue.push_back(1);
    
    // rbga
    gray.push_back(0.7);
    gray.push_back(0.7);
    gray.push_back(0.7);
    gray.push_back(0.5);

    HepRep* heprep = factory->createHepRep();

    // layers
	heprep->addLayer("Geometry");
	heprep->addLayer("Event");

    // geometry types
    HepRepTreeID* geometryTypeTreeID = factory->createHepRepTreeID("GeometryTypeTree", "1.0");
    HepRepTypeTree* geometryTypeTree = factory->createHepRepTypeTree(geometryTypeTreeID);
    heprep->addTypeTree(geometryTypeTree);
    HepRepType* geometryType = factory->createHepRepType(geometryTypeTree, "GeometryType");
    geometryType->addAttValue("drawAs", (string)"polygon");
    geometryType->addAttValue("color", gray);
    geometryType->addAttValue("visibility", true);
    geometryType->addAttValue("marksize", 2.5);
    geometryType->addAttValue("fontsize", 14);    

    // geometry
    HepRepInstanceTree* geometryTree = factory->createHepRepInstanceTree("Geometry", "MultiTest", geometryTypeTree);
    heprep->addInstanceTree(geometryTree);
    HepRepInstance* geometry = factory->createHepRepInstance(geometryTree, geometryType);

    for (int p=0; p<NUMBER_OF_POLYGONS; p++) {
        HepRepInstance* polygon = factory->createHepRepInstance(geometry, geometryType);
        for (int i=0; i<NUMBER_OF_POINTS; i++) {
            factory->createHepRepPoint(polygon, nextRandom(), nextRandom(), nextRandom());
        }
    }

    // event types
    HepRepTreeID* eventTypeTreeID = factory->createHepRepTreeID("EventTypeTree", "1.0");
    HepRepTypeTree* eventTypeTree = factory->createHepRepTypeTree(eventTypeTreeID);
    heprep->addTypeTree(eventTypeTree);
    HepRepType* eventType = factory->createHepRepType(eventTypeTree, "Event");
    HepRepType* hitType = factory->createHepRepType(eventType, "Hits");
    hitType->addAttValue("drawAs", (string)"point");
    hitType->addAttValue("color", yellow);
    HepRepType* trackType = factory->createHepRepType(eventType, "Tracks");
    trackType->addAttValue("drawAs", (string)"line");
    trackType->addAttValue("color", blue);

    // event
    HepRepInstanceTree* eventTree = factory->createHepRepInstanceTree("Event", "MultiTest", eventTypeTree);
    heprep->addInstanceTree(eventTree);
    HepRepInstance* event = factory->createHepRepInstance(eventTree, eventType);

    for (int h=0; h<NUMBER_OF_HITS; h++) {
        HepRepInstance* hit = factory->createHepRepInstance(event, hitType);
        factory->createHepRepPoint(hit, nextRandom(), nextRandom(), nextRandom());
    }

    for (int t=0; t<NUMBER_OF_TRACKS; t++) {
        HepRepInstance* track = factory->createHepRepInstance(event, trackType);
        for (int i=0; i<NUMBER_OF_TRACK_POINTS; i++) {
            /* HepRepPoint* point = */ factory->createHepRepPoint(track, nextRandom(), nextRandom(), nextRandom());
//            point->addAttValue("color", red);
        }
    }

    return heprep;
}

double MultiWriteTest::nextRandom() {
    double r = random.nextGaussian();
    return r;
}

void MultiWriteTest::write(HepRepFactory* factory, int nevents, string filename) {
    ostream* fos = new ofstream(filename.c_str(), ios::out | ios::binary );
    bool zip = filename.rfind(".zip") == filename.length()-4;
    bool gz = filename.rfind(".gz") == filename.length()-3;
    bool binary = filename.rfind(".bheprep") != string::npos;
    HepRepWriter* writer = factory->createHepRepWriter(fos, zip, zip || gz);
    for (int i=0; i<nevents; i++) {
        HepRep* heprep = makeRandomHepRep(factory);
        char buf[255];
        sprintf(buf, binary ? "event%d.bheprep" : "event%d.heprep", i);
        writer->write(heprep, buf);
        delete heprep;
        cerr << ".";
    }
    writer->close();
    delete writer;
    delete fos;
    cerr << endl;
}

int main(int argc, char** argv) {
    if (argc != 3) {
        cerr << "Usage: MultiWriteTest #events filename" << endl;
        return 1;
    }

    int nevents = atoi(argv[1]);
    HepRepFactory* factory = new XMLHepRepFactory();
    MultiWriteTest *test = new MultiWriteTest();
    test->write(factory, nevents, argv[2]);
    delete test;
    delete factory;
    return 0;
}

